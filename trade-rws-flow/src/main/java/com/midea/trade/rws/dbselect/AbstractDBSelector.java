package com.midea.trade.rws.dbselect;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.midea.trade.rws.enums.DBType;
import com.midea.trade.rws.group.DataSourceWrapper;
import com.midea.trade.rws.sorter.ExceptionSorter;
import com.midea.trade.rws.sorter.MySQLExceptionSorter;
import com.midea.trade.rws.sorter.OracleExceptionSorter;
import com.midea.trade.rws.util.NagiosUtils;

public abstract class AbstractDBSelector implements DBSelector {
	private static final Log logger = LogFactory	.getLog(AbstractDBSelector.class);
	private static final Map<DBType, ExceptionSorter> exceptionSorters = new HashMap<DBType, ExceptionSorter>(
			2);	static {
		exceptionSorters.put(DBType.ORACLE, new OracleExceptionSorter());
		exceptionSorters.put(DBType.MYSQL, new MySQLExceptionSorter());
	}	private DBType dbType = DBType.MYSQL;	protected ExceptionSorter exceptionSorter = exceptionSorters.get(dbType);	private String id = "undefined"; 
	private static final int default_retryBadDbInterval = 2000; // milliseconds
	protected static int retryBadDbInterval; // milliseconds	static {
		int interval = default_retryBadDbInterval;
		String propvalue = System.getProperty("com.taobao.tddl.DBSelector.retryBadDbInterval");
		if (propvalue != null) {
			try {
				interval = Integer.valueOf(propvalue.trim());
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		retryBadDbInterval = interval;
	}
	protected boolean readable = false;
	public void setReadable(boolean readable) {
		this.readable = readable;
	}
	protected boolean isSupportRetry = true; 

	public boolean isSupportRetry() {
		return isSupportRetry;
	}

	public void setSupportRetry(boolean isSupportRetry) {
		this.isSupportRetry = isSupportRetry;
	}

	public AbstractDBSelector() {
	}

	public AbstractDBSelector(String id) {
		this.id = id;
	}
	protected static class DataSourceHolder {
		public final DataSourceWrapper dsw;
		public final ReentrantLock lock = new ReentrantLock();
		public volatile boolean isNotAvailable = false;
		public volatile long lastRetryTime = 0;
		public DataSourceHolder(DataSourceWrapper dsw) {
			this.dsw = dsw;
		}
	}
	protected <T> T tryOnDataSourceHolder(DataSourceHolder dsHolder,Map<DataSource, SQLException> failedDataSources,
			DataSourceTryer<T> tryer, int times, Object... args)throws SQLException {		List<SQLException> exceptions = new LinkedList<SQLException>();
		if (failedDataSources != null) {
			exceptions.addAll(failedDataSources.values());
		}
		if (failedDataSources != null&& failedDataSources.containsKey(dsHolder.dsw)) {
			return tryer.onSQLException(exceptions, exceptionSorter, args);
		}

		try {
			if (dsHolder.isNotAvailable) {
				boolean toTry = System.currentTimeMillis()- dsHolder.lastRetryTime > retryBadDbInterval;
				if (toTry && dsHolder.lock.tryLock()) {
					try {
						//logger.info("使用指定dataSourceIndex的数据源:"+dsHolder.dsw.getConnection().getMetaData().getURL());
						T t = tryer.tryOnDataSource(dsHolder.dsw, args); // 同一个时间只会有一个线程继续使用这个数据源。
						dsHolder.isNotAvailable = false; // 用一个线程重试，执行成功则标记为可用，自动恢复
						return t;
					} finally {
						dsHolder.lastRetryTime = System.currentTimeMillis();
						dsHolder.lock.unlock();
					}
				} else {
					exceptions.add(new NoMoreDataSourceException("dsKey:"+ dsHolder.dsw.getDataSourceKey()+ " not Available,toTry:" + toTry));
					return tryer.onSQLException(exceptions, exceptionSorter,args);
				}
			} else {
				return tryer.tryOnDataSource(dsHolder.dsw, args); // 有一次成功直接返回
			}
		} catch (SQLException e) {
			if (exceptionSorter.isExceptionFatal(e)) {
				NagiosUtils.addNagiosLog(NagiosUtils.KEY_DB_NOT_AVAILABLE + "|"
						+ dsHolder.dsw.getDataSourceKey(), e.getMessage());
				dsHolder.isNotAvailable = true;
			}
			exceptions.add(e);
			return tryer.onSQLException(exceptions, exceptionSorter, args);
		}
	}

		
	public <T> T tryExecute(Map<DataSource, SQLException> failedDataSources,
			DataSourceTryer<T> tryer, int times, Object... args)
			throws SQLException {		// dataSourceIndex放在args最后一个.以后改动要注意
		// local set dataSourceIndex was placed first
		Integer dataSourceIndex = null;
		if (args != null && args.length > 0) {
			dataSourceIndex = (Integer) args[args.length - 1];
		}

		if (dataSourceIndex != null&& dataSourceIndex != NOT_EXIST_USER_SPECIFIED_INDEX) {
			DataSourceHolder dsHolder = findDataSourceWrapperByIndex(dataSourceIndex);
			if (dsHolder == null) {
				throw new IllegalArgumentException("找不到索引编号为 '"
						+ dataSourceIndex + "'的数据源");
			}
			return tryOnDataSourceHolder(dsHolder, failedDataSources, tryer,
					times, args);
		} else {
			return tryExecuteInternal(failedDataSources, tryer, times, args);
		}
	}				

	public <T> T tryExecute(DataSourceTryer<T> tryer, int times, Object... args)
			throws SQLException {
		return this.tryExecute(new LinkedHashMap<DataSource, SQLException>(0),
				tryer, times, args);
	}

	public DBType getDbType() {
		return dbType;
	}

	public void setDbType(DBType dbType) {
		this.dbType = dbType;
		this.exceptionSorter = exceptionSorters.get(this.dbType);
	}

	public final void setExceptionSorter(ExceptionSorter exceptionSorter) {
		this.exceptionSorter = exceptionSorter;
	}

	public String getId() {
		return id;
	}

	// public abstract DataSource findDataSourceByIndex(int dataSourceIndex);
	protected abstract DataSourceHolder findDataSourceWrapperByIndex(
			int dataSourceIndex);
		
	protected <T> T tryExecuteInternal(DataSourceTryer<T> tryer, int times,
			Object... args) throws SQLException {
		return this.tryExecuteInternal(
				new LinkedHashMap<DataSource, SQLException>(0), tryer, times,
				args);
	}

	protected abstract <T> T tryExecuteInternal(
			Map<DataSource, SQLException> failedDataSources,
			DataSourceTryer<T> tryer, int times, Object... args)
			throws SQLException;
}
