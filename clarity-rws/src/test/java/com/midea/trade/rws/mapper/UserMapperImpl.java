package com.midea.trade.rws.mapper;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;

import com.trade.rws.DynamicDataSource;


public class UserMapperImpl implements UserMapper {
	
	
	
    public void add(String user){
    /*	try {
			Connection conn=ds.getConnection();
			System.out.println(conn.getMetaData().getUserName());
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
    	
		System.out.println(user);
	}

	public void query(String user) {
	/*	try {
			Connection conn=ds.getConnection();
			System.out.println(conn.getMetaData().getUserName());
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
	
	
}
