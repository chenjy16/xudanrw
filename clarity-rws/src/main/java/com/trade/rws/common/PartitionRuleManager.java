/*(C) 2007-2012 Alibaba Group Holding Limited.	 *This program is free software; you can redistribute it and/or modify	*it under the terms of the GNU General Public License version 2 as	* published by the Free Software Foundation.	* Authors:	*   junyu <junyu@taobao.com> , shenxun <shenxun@taobao.com>,	*   linxuan <linxuan@taobao.com> ,qihao <qihao@taobao.com> 	*/	package com.trade.rws.common;

/**
 * �ֿ�������
 *
 * @author nianbing
 */
public interface PartitionRuleManager {
	/**
	 * ���������ݿ���ͱ���õ�����
	 *
	 * @param masterName ������ݿ���
	 * @param tableName ����
	 * @return �����������û�����ã��򷵻�null��
	 */
	String getPrimaryKey(String masterName, String tableName);

	/**
	 * ���������ݿ���ͱ���õ��ֿ��
	 *
	 * @param masterName ������ݿ���
	 * @param tableName ����
	 * @return ���طֿ�����û�����ã��򷵻�null��
	 */
	String getPartitionKey(String masterName, String tableName);

	/**
	 * ��ݷֿ���򷵻طֿ��б�
	 *
	 * @param masterName ������ݿ���
	 * @param tableName ����
	 * @param value �ֿ��ֵ
	 * @return ���طֿ��б?���ֿ��ֵ��ƥ���κηֿ�����򷵻�null��
	 */
	String[] getSlaves(String masterName, String tableName, Object value);
}
