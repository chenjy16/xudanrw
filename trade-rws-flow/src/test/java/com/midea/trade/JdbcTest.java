package com.midea.trade;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.midea.trade.rws.group.TGroupDataSource;
import com.midea.trade.rws.mapper.UserMapper;
import com.midea.trade.rws.service.UserService;
import com.midea.trade.rws.util.GroupDataSourceRouteHelper;

public class JdbcTest extends BaseTest{
	
	@Resource(name="userMapper")
	UserMapper userMapper;
	
	@Resource(name="userService")
	UserService userService;
	
	@Resource(name="groupDataSource")
	TGroupDataSource dataSource;
	

	
	/**
	 * @throws SQLException 
	* @author chejy 
	* @Description: 
	* @return void    杩斿洖绫诲瀷
	* @throws
	 */
	@Test
	public void prase() throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("/testContext.xml");
		dataSource.init();
		JdbcTemplate jt=new JdbcTemplate();
		jt.setDataSource(dataSource);
		String sql = "select * from m_shop_test_rw limit 0,10";
		GroupDataSourceRouteHelper.executeByGroupDataSourceIndex(1);
		List list=jt.queryForList(sql);
		System.out.println(list.size());
		//userService.queryUser("res");
		//userService.add("res");
	}
}

