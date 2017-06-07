package com.trade.rws.dbselect;

import java.sql.SQLException;

import com.trade.rws.group.DataSourceWrapper;
public class OneDBSelector extends AbstractDBSelector {
	private final DataSourceHolder dsHolder;
	private final Map<String, DataSource> dsMap;

	public OneDBSelector(DataSourceWrapper dsw) {
		this.dsHolder = new DataSourceHolder(dsw);
		dsMap = new LinkedHashMap<String, DataSource>();
		dsMap.put(dsw.getDataSourceKey(), dsw.getWrappedDataSource());
	}

	public DataSource select() {
		return dsHolder.dsw;
	}

	public Map<String, DataSource> getDataSources() {
		return dsMap;
	}

	protected <T> T tryExecuteInternal(Map<DataSource, SQLException> failedDataSources, DataSourceTryer<T> tryer,
			int times, Object... args) throws SQLException {

		List<SQLException> exceptions;

		if (failedDataSources != null && failedDataSources.containsKey(dsHolder.dsw)) {
			exceptions = new ArrayList<SQLException>(failedDataSources.size());
			exceptions.addAll(failedDataSources.values());

			return tryer.onSQLException(exceptions, this.exceptionSorter, args);
		}

		try {
			return tryOnDataSourceHolder(dsHolder, failedDataSources, tryer, times, args);
		} catch (SQLException e) {
			exceptions = new ArrayList<SQLException>(1);
			exceptions.add(e);
		}
		return tryer.onSQLException(exceptions, this.exceptionSorter, args);
	}

	protected <T> T tryExecuteInternal(DataSourceTryer<T> tryer, int times, Object... args) throws SQLException {
		return this.tryExecute(null, tryer, times, args);
	}

	public DataSourceWrapper get(String dsKey) {
		return dsHolder.dsw.getDataSourceKey().equals(dsKey) ? dsHolder.dsw : null;
	}

	protected DataSourceHolder findDataSourceWrapperByIndex(int dataSourceIndex) {
		return dsHolder.dsw.isMatchDataSourceIndex(dataSourceIndex) ? dsHolder : null;
	}
}