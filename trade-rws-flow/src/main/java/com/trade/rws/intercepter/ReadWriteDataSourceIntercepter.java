package com.trade.rws.intercepter;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.trade.rws.ReadWriteDataSourceDecision;
@Intercepts(@Signature(type = Executor.class, method = "query", args = {
	MappedStatement.class, Object.class, RowBounds.class,
	ResultHandler.class }))
public class ReadWriteDataSourceIntercepter implements Interceptor  {
	
//	private static Logger logger = LoggerFactory.getLogger(ReadWriteDataSourceProcessor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if(invocation.getMethod().getName().equals("query")){
			ReadWriteDataSourceDecision.markRead();
		}else{
			ReadWriteDataSourceDecision.markWrite();
		}
//       logger.info("执行时间：{}，执行方法名：{}，使用数据库类型：{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),invocation.getMethod().getName(),"读库");
		Object result=null;
		try{
			result = invocation.proceed(); 
		}finally{
			ReadWriteDataSourceDecision.reset();
		}
		return result;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);   
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
