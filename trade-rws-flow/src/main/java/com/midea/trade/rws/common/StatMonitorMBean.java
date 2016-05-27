/*(C) 2007-2012 Alibaba Group Holding Limited.	 *This program is free software; you can redistribute it and/or modify	*it under the terms of the GNU General Public License version 2 as	* published by the Free Software Foundation.	* Authors:	*   junyu <junyu@taobao.com> , shenxun <shenxun@taobao.com>,	*   linxuan <linxuan@taobao.com> ,qihao <qihao@taobao.com> 	*/	package com.midea.trade.rws.common;


/*
 * @author guangxia
 * @since 1.0, 2010-2-9 ����03:40:20
 */
public interface StatMonitorMBean {
	
    /**
     * ���¿�ʼʵʱͳ��
     */
    void resetStat();
    /**
     * ����ͳ�Ƶ�ʱ���
     * 
     * @return
     */
    long getStatDuration();
    /**
     * ��ȡʵʱͳ�ƽ��
     * 
     * @param key1
     * @param key2
     * @param key3
     * @return
     */
    String getStatResult(String key1, String key2, String key3);
    long getDuration();

}
