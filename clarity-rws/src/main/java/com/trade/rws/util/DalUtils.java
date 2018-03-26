/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: DalUtils.java
 * Author:   12010065
 * Date:     2013-5-13 下午4:19:10
 * Description: DAL工具类     
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.trade.rws.util;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 功能描述：DAL工具类
 */
public class DalUtils {
    private static Logger logger = LoggerFactory.getLogger(DalUtils.class);
    
    private static final int NUM0 = 0;
    private static final int NUM1 = 1;
    private static final int NUM2 = 2;
    private static final int NUM3 = 3;
    private static final int NUM4 = 4;
    private static final int NUM8 = 8;
    private static final int NUM16 = 16;
    private static final int NUM24 = 24;
    private static final int NUM_0XFF = 0xFF;

    /**
     * 功能描述：把参数转成对象数组
     * 输入参数：Object类型
     * @param parameter
     * 返回值:  Object数组 <说明> 
     * @return Object[] 
     * @throw 异常描述
     * @see 需要参见的其它内容
     */
    public static Object[] convertToObjectArray(Object parameter) {
        Object[] retObject = null;
        if (parameter instanceof Object[]) {
            retObject = (Object[]) parameter;
        } else {
            retObject = new Object[] { parameter };
        }
        return retObject;
    }

    

    public static Map<String, Object> mapIfNull(Map<String, Object> map) {
        if (map == null) {
            return new HashMap<String, Object>();
        }
        return map;
    }

    public static void setProperty(Object targetObject, String propertyName, Object propertyValue) {
        try {
            Field field = targetObject.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            if (propertyValue instanceof BigDecimal) {
                if (field.getType().isAssignableFrom(Integer.class) || field.getType().isAssignableFrom(int.class)) {
                    field.set(targetObject, ((BigDecimal) propertyValue).intValue());
                }
                if (field.getType().isAssignableFrom(Long.class) || field.getType().isAssignableFrom(long.class)) {
                    field.set(targetObject, ((BigDecimal) propertyValue).longValue());
                }
                if (field.getType().isAssignableFrom(Double.class) || field.getType().isAssignableFrom(double.class)) {
                    field.set(targetObject, ((BigDecimal) propertyValue).doubleValue());
                }
                if (field.getType().isAssignableFrom(Float.class) || field.getType().isAssignableFrom(float.class)) {
                    field.set(targetObject, ((BigDecimal) propertyValue).floatValue());
                }
                if (field.getType().isAssignableFrom(Byte.class) || field.getType().isAssignableFrom(byte.class)) {
                    field.set(targetObject, ((BigDecimal) propertyValue).byteValue());
                }
            } else {
                field.set(targetObject, propertyValue);
            }
        } catch (SecurityException e) {
            logger.warn(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage(), e);
        } catch (NoSuchFieldException e) {
            logger.warn(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.warn(e.getMessage(), e);
        }
    }
    
    public static byte[] computeMd5(String k) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(k.getBytes("UTF-8"));
            return md5.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unknown string :" + k, e);
        }
    }
    
    /**
     * 描述：hash算法
     */
    public static enum HashAlgorithm {
        /**
         * MD5-based hash algorithm used by ketama.
         */
        KETAMA_HASH;
        public long hash(byte[] digest, int nTime) {
            long rv = ((long) (digest[NUM3 + nTime * NUM4] & NUM_0XFF) << NUM24)
                    | ((long) (digest[NUM2 + nTime * NUM4] & NUM_0XFF) << NUM16)
                    | ((long) (digest[NUM1 + nTime * NUM4] & NUM_0XFF) << NUM8)
                    | (digest[NUM0 + nTime * NUM4] & NUM_0XFF);

            return rv & 0xffffffffL; /* Truncate to 32-bits */
        }
    }
}
