package com.midea.trade.rws.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.midea.trade.rws.mapper.UserMapper;
import com.trade.rws.annotation.DataSource;


@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource(name="userMapper")
	UserMapper userMapper;

	@Transactional
	public void add(String user) {
		userMapper.add(user);
	}
	
	
	@DataSource("read")
	public void queryUser(String user) {
		userMapper.query(user);
	}

}
