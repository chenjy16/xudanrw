package com.midea.trade.rws;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.CollectionUtils;


public class DynamicDataSource extends AbstractDataSource implements InitializingBean{
	
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
	//private DataSource defaultTargetDataSource;
    private DataSource writeDataSource;
    private Map<String, DataSource> readDataSourceMap;
    private String[] readDataSourceNames;
    private DataSource[] readDataSources;
    private int readDataSourceCount;
    private AtomicInteger counter = new AtomicInteger(1);
    
    public void setReadDataSourceMap(Map<String, DataSource> readDataSourceMap) {
        this.readDataSourceMap = readDataSourceMap;
    }
    public void setWriteDataSource(DataSource writeDataSource) {
        this.writeDataSource = writeDataSource;
    }

	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(writeDataSource == null) {
            throw new IllegalArgumentException("property 'writeDataSource' is required");
        }
        if(CollectionUtils.isEmpty(readDataSourceMap)) {
            throw new IllegalArgumentException("property 'readDataSourceMap' is required");
        }
        readDataSourceCount = readDataSourceMap.size();
        readDataSources = new DataSource[readDataSourceCount];
        readDataSourceNames = new String[readDataSourceCount];
        
        int i = 0;
        for(Entry<String, DataSource> e : readDataSourceMap.entrySet()) {
            readDataSources[i] = e.getValue();
            readDataSourceNames[i] = e.getKey();
            i++;
        }
	}
	
	 public DataSource determineDataSource() {
	        if(ReadWriteDataSourceDecision.isChoiceWrite()) {
	        	logger.info("动态数据源返回数据库类型:{}","isChoiceWrite");
	            return writeDataSource;
	        }
	        if(ReadWriteDataSourceDecision.isChoiceNone()) {
	        	logger.info("动态数据源返回数据库类型:{}","isChoiceNone");
	            return writeDataSource;
	        } 
	        return determineReadDataSource();
	    }

	   private DataSource determineReadDataSource() {
	        //按照顺序选择读库 
	        int index = counter.incrementAndGet() % readDataSourceCount;
	        if(index < 0) {
	            index = - index;
	        }
	        logger.info("动态数据源返回数据库类型:{},第{}个读库","read",index);
	        return readDataSources[index];
	    }

	    @Override
	    public Connection getConnection() throws SQLException {
	        return determineDataSource().getConnection();
	    }
	    
	    @Override
	    public Connection getConnection(String username, String password) throws SQLException {
	        return determineDataSource().getConnection(username, password);
	    }

}
