package com.midea.trade.rws.dbselect;

import java.sql.SQLException;

/** * 当一组的数据库都试过，都不可用了，并且没有更多的数据源了，抛出该错误 */public class NoMoreDataSourceException extends SQLException {	private static final long serialVersionUID = 1L;	public NoMoreDataSourceException(String reason) {		super(reason);	}}
