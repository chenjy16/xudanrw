package com.clarity.rws.util;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.clarity.rws.common.DataSourceFetcher;
import com.clarity.rws.enums.DBType;

public class SpringDataSourceFetcher implements DataSourceFetcher, ApplicationContextAware {

	private ApplicationContext springContext; // 拿到上下文
	private DBType dbType = DBType.MYSQL;

	@Override
	public DataSource getDataSource(String key) {
		return (DataSource) this.springContext.getBean(key);
	}

	@Override
	public DBType getDataSourceDBType(String key) {
		return dbType;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.springContext = applicationContext;
	}

	public void setDBType(String type) {
		if ("oracle".equalsIgnoreCase(type))
			dbType = DBType.ORACLE;
		else if ("mysql".equalsIgnoreCase(type))
			dbType = DBType.MYSQL;
		else
			throw new IllegalArgumentException(type + " 不是有效的数据库类型，只能是mysql或oracle(不区分大小写)");
	}
}

