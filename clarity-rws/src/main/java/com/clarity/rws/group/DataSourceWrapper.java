package com.clarity.rws.group;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.clarity.rws.config.Weight;
import com.clarity.rws.enums.DBType;



public class DataSourceWrapper implements DataSource {
	
	private final String dataSourceKey;  //这个DataSource对应的dbKey 
	private final String weightStr;//权重信息字符串
	private final Weight weight;  //权重信息
	private final DataSource wrappedDataSource; //被封装的目标DataSource
	private final DBType dbType;//数据库类型
	private final int dataSourceIndex;//DataSourceIndex是指这个DataSource在Group中的位置
	

	public DataSourceWrapper(String dataSourceKey, String weightStr, DataSource wrappedDataSource, DBType dbType,
			int dataSourceIndex) {
		this.dataSourceKey = dataSourceKey;  //db0
		this.weight = new Weight(weightStr); //rwp1q1i0
		this.weightStr = weightStr;   //rwp1q1i0
		this.wrappedDataSource = wrappedDataSource;
		this.dbType = dbType;

		this.dataSourceIndex = dataSourceIndex;  //  0
	}

	public DataSourceWrapper(String dataSourceKey, String weightStr, DataSource wrappedDataSource, DBType dbType) {
		this(dataSourceKey, weightStr, wrappedDataSource, dbType, -1);
	}


	public boolean isMatchDataSourceIndex(int specifiedIndex) {
		if (weight.indexes != null && !weight.indexes.isEmpty()) {
			return weight.indexes.contains(specifiedIndex);
		} else {
			return this.dataSourceIndex == specifiedIndex;
		}
	}

	
	public boolean hasReadWeight() {
		return weight.r != 0;
	}


	public boolean hasWriteWeight() {
		return weight.w != 0;
	}

	public String toString() {
		return new StringBuilder("DataSourceWrapper{dataSourceKey=").append(dataSourceKey).append(", dataSourceIndex=")
				.append(dataSourceIndex).append(",weight=").append(weight).append("}").toString();
	}

	public String getDataSourceKey() {
		return dataSourceKey;
	}

	public String getWeightStr() {
		return weightStr;
	}

	/*public synchronized void setWeightStr(String weightStr) {
		if ((this.weightStr == weightStr) || (this.weightStr != null && this.weightStr.equals(weightStr)))
			return;
		this.weight = new Weight(weightStr);
		this.weightStr = weightStr;
	}*/

	public Weight getWeight() {
		return weight;
	}

	/*public int getDataSourceIndex() {
		return dataSourceIndex;
	}*/

	/*public void setDataSourceIndex(int dataSourceIndex) {
		this.dataSourceIndex = dataSourceIndex;
	}*/

	public DBType getDBType() {
		return dbType;
	}

	public DataSource getWrappedDataSource() {
		return wrappedDataSource;
	}


	public Connection getConnection() throws SQLException {
		return wrappedDataSource.getConnection();
	}

	public Connection getConnection(String username, String password) throws SQLException {
		return wrappedDataSource.getConnection(username, password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return wrappedDataSource.getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return wrappedDataSource.getLoginTimeout();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		wrappedDataSource.setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		wrappedDataSource.setLoginTimeout(seconds);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.getClass().isAssignableFrom(iface);
	}

	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> iface) throws SQLException {
		try {
			return (T) this;
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}//	@Override	public Logger getParentLogger() throws SQLFeatureNotSupportedException {		// TODO Auto-generated method stub		return null;	}

	/*
	//Since: 1.6

	//java.sql.Wrapper
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}
	*/
}
