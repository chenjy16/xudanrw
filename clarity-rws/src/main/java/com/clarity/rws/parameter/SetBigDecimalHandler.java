/*(C) 2007-2012 Alibaba Group Holding Limited.	 *This program is free software; you can redistribute it and/or modify	*it under the terms of the GNU General Public License version 2 as	* published by the Free Software Foundation.	* Authors:	*   junyu <junyu@taobao.com> , shenxun <shenxun@taobao.com>,	*   linxuan <linxuan@taobao.com> ,qihao <qihao@taobao.com> 	*/	package com.clarity.rws.parameter;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetBigDecimalHandler implements ParameterHandler {
	public void setParameter(PreparedStatement stmt, Object[] args)
			throws SQLException {
		stmt.setBigDecimal((Integer) args[0], (BigDecimal) args[1]);
	}
}
