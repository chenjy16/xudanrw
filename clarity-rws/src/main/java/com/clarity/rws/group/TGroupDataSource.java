package com.clarity.rws.group;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.clarity.rws.common.DataSourceFetcher;
import com.clarity.rws.common.TDDLConstant;
import com.clarity.rws.config.ConfigManager;
import com.clarity.rws.dbselect.DBSelector;
import com.clarity.rws.enums.DBType;
import com.clarity.rws.exception.ConfigException;
import com.clarity.rws.util.DsConfDO;
import com.clarity.rws.util.TimesliceFlowControl;

/**
 * TGroupDataSource通过  configmanager 来解析配置   然后封装在 selector里面!
	
	private static final Log logger = LogFactory.getLog(TGroupPreparedStatement.class);
	
	private DsConfDO dsconfDO;
	
	final AtomicInteger threadCount = new AtomicInteger();//包权限
	final AtomicInteger threadCountReject = new AtomicInteger();//包权限
	final AtomicInteger concurrentReadCount = new AtomicInteger(); //包权限
	final AtomicInteger concurrentWriteCount = new AtomicInteger(); //包权限
	volatile TimesliceFlowControl writeFlowControl; //包权限
	volatile TimesliceFlowControl readFlowControl; //包权限
	
	/**
	 * 写计数
	 */
	//final AtomicInteger writeTimes = new AtomicInteger();//包权限
	final AtomicInteger writeTimesReject = new AtomicInteger();//包权限

	/**
	 * 读计数
	 */
	//final AtomicInteger readTimes = new AtomicInteger();//包权限
	final AtomicInteger readTimesReject = new AtomicInteger();//包权限
	volatile ConnectionProperties connectionProperties = new ConnectionProperties(); //包权限
	
	
	
public static class ConnectionProperties {
		
		/**
		 * 线程count限制，0为不限制
		 */
		public volatile int threadCountRestriction;

		/**
		 * 允许并发读的最大个数，0为不限制
		 */
		public volatile int maxConcurrentReadRestrict;

		/**
		 * 允许并发写的最大个数，0为不限制
		 */
		public volatile int maxConcurrentWriteRestrict;
	}
	
	
		return dsKeyAndWeightCommaArray;
	}

	public DataSourceFetcher getDataSourceFetcher() {
		return dataSourceFetcher;
	}
	
	public TGroupDataSource() {}
	public TGroupDataSource(String dsKeyAndWeightCommaArray,
			DataSourceFetcher dataSourceFetcher,DsConfDO dsconfDO) {
		this.dsKeyAndWeightCommaArray = dsKeyAndWeightCommaArray;
		this.dataSourceFetcher = dataSourceFetcher;
		this.dsconfDO=dsconfDO;
		init();
	}

	/**
	
		
		logger.warn(dsconfDO.getTimeSliceInMillis()+"时间范围内，读并发数: " + dsconfDO.getReadRestrictTimes());
		this.readFlowControl = new TimesliceFlowControl("读流量", dsconfDO.getTimeSliceInMillis(), dsconfDO.getReadRestrictTimes());
		
		logger.warn(dsconfDO.getTimeSliceInMillis()+"时间范围内，读并发数: " + dsconfDO.getWriteRestrictTimes());
		this.writeFlowControl = new TimesliceFlowControl("写流量", dsconfDO.getTimeSliceInMillis(), dsconfDO.getWriteRestrictTimes());
		
		logger.warn("最大读并发数: " + dsconfDO.getMaxConcurrentReadRestrict());
		this.connectionProperties.maxConcurrentReadRestrict = dsconfDO.getMaxConcurrentReadRestrict();
		
		logger.warn("最大写并发数: " + dsconfDO.getMaxConcurrentWriteRestrict());
		this.connectionProperties.maxConcurrentWriteRestrict = dsconfDO.getMaxConcurrentWriteRestrict();	
		DataSourceFetcher wrapper = new DataSourceFetcher() {



	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}