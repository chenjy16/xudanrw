package com.midea.trade.rws.common;

import javax.sql.DataSource;

import com.midea.trade.rws.enums.DBType;

public interface DataSourceFetcher {
	DataSource getDataSource(String key);
	DBType getDataSourceDBType(String key);
}