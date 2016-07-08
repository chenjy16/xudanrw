package com.trade.rws.intercepter;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.trade.rws.util.RouteTableHelper;
import com.trade.table.parsing.method.FreeMakerParser;



/*@Intercepts({@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
RowBounds.class, ResultHandler.class }) })*/
@Intercepts({
    @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class}) })
public class RouteTableIntercepter implements Interceptor {
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler,
		SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
		DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler)metaStatementHandler.getValue("delegate.parameterHandler");  
		String originalSql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");
		Object parameterMap = defaultParameterHandler.getParameterObject();
		RouteTableHelper rth=null;
		if(parameterMap!=null){
			if(parameterMap instanceof RouteTableHelper){
				rth=(RouteTableHelper)parameterMap;
				if(!StringUtils.isEmpty(rth.getRouteTabbleField())
						&&rth.getRouteTabbleValue()!= null){
					
					Map<String,Object> param=new HashMap<String, Object>();
					param.put(rth.getRouteTabbleField(), rth.getRouteTabbleValue());
					
					String sql=FreeMakerParser.process(param, originalSql);
					
					param=null;
					metaStatementHandler.setValue("delegate.boundSql.sql",sql);  
					return invocation.proceed();
	            }
			}
			if(parameterMap instanceof Map){
				String sql=FreeMakerParser.process(parameterMap, originalSql);
				metaStatementHandler.setValue("delegate.boundSql.sql",sql);  
				return invocation.proceed();
			}
		}	
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);   
	}

	@Override
	public void setProperties(Properties properties) {
		
	}
	
	
/*	private  boolean isHandled( Object value ) {

		return (
			value instanceof  Map            ||
			(value instanceof Byte           ||
			value instanceof Boolean         ||
			value instanceof Integer         ||
			value instanceof Long            ||
			value instanceof Character       ||
			value instanceof String          ||
			value instanceof StringBuffer    ||
			value instanceof Float           ||
			value instanceof Short           ||
			value instanceof Double          ||
			value instanceof Date            ||
			value instanceof StringBuilder   )
			)
		? true
		: false;
    }*/
}
