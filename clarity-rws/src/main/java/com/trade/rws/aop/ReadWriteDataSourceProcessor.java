package com.trade.rws.aop;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.trade.rws.ReadWriteDataSourceDecision;
import com.trade.rws.annotation.DataSource;
import com.trade.rws.enums.Config;



/*@Aspect
@Order(-1000000000)
@Component("readWriteDataSourceTransactionProcessor")*/
public class ReadWriteDataSourceProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(ReadWriteDataSourceProcessor.class);
	
/*	execution(* *..service*..*(..))
	@Pointcut("execution(* com.midea.trade..service*..*(..))")
	public void serviceMethod() {
	};*/
	
	
	@Around("serviceMethod()")
    public Object determineReadOrWriteDB(ProceedingJoinPoint pjp) throws Throwable {
	    	 Object target = pjp.getTarget();
	         String method = pjp.getSignature().getName();
	         Class<?> classz = target.getClass();
	         Class<?>[] parameterTypes = ((MethodSignature) pjp.getSignature())
	                 .getMethod().getParameterTypes();
	         Method m = classz.getMethod(method, parameterTypes);
	         String usedDB=null;
	         if (m != null && m.isAnnotationPresent(DataSource.class)){
	        	 DataSource data = m.getAnnotation(DataSource.class);
	        	 if(Config.Slave.getDesc().equals(data.value())
	        			 ||Config.Read.getDesc().equals(data.value())){
	        		 ReadWriteDataSourceDecision.markRead();
	        		 usedDB="读库";
	        	 }else{
	        		 ReadWriteDataSourceDecision.markWrite();
	        		 usedDB="写库";
	        	 }
	         }else {
	             ReadWriteDataSourceDecision.markWrite();
	             usedDB="写库";
	         }
	         logger.info("执行时间：{}，执行方法名：{}，使用数据库类型：{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),m.getDeclaringClass()+"."+m.getName(),usedDB);
	         try {
	             return pjp.proceed();
	         } finally {
	             ReadWriteDataSourceDecision.reset();
	         }
    }

}
