package com.midea.trade.rws.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.midea.trade.rws.mapper.annotation.IRepository;

@IRepository
public interface MShopMapper {
	int countByExample(MShopExample example);

	int deleteByExample(MShopExample example);

	int deleteByPrimaryKey(Integer shopId);
	
	int insert(MShop record);

	int insertSelective(MShop record);

	List<MShop> selectByExample(MShopExample example);

	MShop selectByPrimaryKey(Integer shopId);

	int updateByExampleSelective(@Param("record") MShop record,
			@Param("example") MShopExample example);

	int updateByExample(@Param("record") MShop record,
			@Param("example") MShopExample example);

	int updateByPrimaryKeySelective(MShop record);

	int updateByPrimaryKey(MShop record);

	List<MShop> selectShop(Map map);

	List<MShop> selectShopById(Integer id);
	
	List<MShop> selectShopByShard(@Param("splitDBName") String splitDBName);
	
}