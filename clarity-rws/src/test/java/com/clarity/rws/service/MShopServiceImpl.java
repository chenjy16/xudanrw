package com.clarity.rws.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.clarity.rws.mapper.MShop;
import com.clarity.rws.mapper.MShopExample;
import com.clarity.rws.mapper.MShopMapper;





@Service("mShopService")
public class MShopServiceImpl implements MShopService {
	
	@Autowired
	private MShopMapper mShopMapper;
	
	
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class,isolation=Isolation.DEFAULT)
	public void insert() throws Exception{
		MShop shop=new MShop();
		shop.setShopName("fe4");
		shop.setPlatformId(new Byte("1"));
		shop.setOwnerMobile("123213");
		shop.setEmail("12312313");
		shop.setStatus(new Byte("1"));
		shop.setOwner("44444");
		shop.setShopType(new Byte("1"));
		shop.setOuterShopId("12111");
		shop.setShopJoinTime(new Date());
		shop.setSplitDBName("2014-01-03");
		mShopMapper.insert(shop);
		
		 
		
		
		MShop shop1=new MShop();
		shop1.setShopName("fe4");
		shop1.setPlatformId(new Byte("1"));
		shop1.setOwnerMobile("123213");
		shop1.setEmail("12312313");
		shop1.setStatus(new Byte("1"));
		shop1.setOwner("44444");
		shop1.setShopType(new Byte("1"));
		shop1.setOuterShopId("12111");
		shop1.setShopJoinTime(new Date());
		shop1.setSplitDBName("2015-01-01");
		mShopMapper.insert(shop1);
		
		//throw new Exception();
		//GroupDataSourceRouteHelper.executeByGroupDataSourceIndex(2);
		//mShopMapper.selectByExample(null);
		//((MShopService) AopContext.currentProxy()).update();
	/*	MShop record=mShopMapper.selectByPrimaryKey(18140);
		record.setShopName("8888888888");
		mShopMapper.updateByPrimaryKey(record);*/
		
		//System.out.println("事务内:"+mShopMapper.selectByExample(null).size());
		//throw new RuntimeException("----------");
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class,isolation=Isolation.DEFAULT)
	public void update(){
		MShop shop=new MShop();
		shop.setShopId(101519);
		shop.setShopName("testcjy");
		shop.setPlatformId(new Byte("1"));
		shop.setOwnerMobile("123213");
		shop.setEmail("cjy");
		shop.setStatus(new Byte("1"));
		shop.setOwner("44444");
		shop.setShopType(new Byte("1"));
		shop.setOuterShopId("12111");
		shop.setShopJoinTime(new Date());
		shop.setSplitDBName("2");
		mShopMapper.updateByPrimaryKey(shop);
		
		//System.out.println("update事务内:"+mShopMapper.selectByExample(null).size());
	}
	

	public void select(){
		/*MShopExample example=new MShopExample();
		example.createCriteria().andShopIdEqualTo(5);
		example.setRouteTabbleField("shop_id");
		example.setRouteTabbleValue(3);
		List<MShop> list=mShopMapper.selectByExample(example);
		System.out.println(list.size());*/
		
		List<MShop> list=mShopMapper.selectShopByShard("2");
		System.out.println(list.size());
/*		Map param=new HashMap();
		param.put("shop_id", 3);
		List<MShop> list=mShopMapper.selectShop(param);*/
		//List<MShop> list=mShopMapper.selectShopById(3);
	}

	@Override
	public void delete() {
		
	}

}
