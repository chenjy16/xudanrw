package com.clarity.rws.common;

import javax.sql.DataSource;

import com.clarity.rws.enums.DBType;

public interface DataSourceFetcher {
	DataSource getDataSource(String key);
	DBType getDataSourceDBType(String key);
}