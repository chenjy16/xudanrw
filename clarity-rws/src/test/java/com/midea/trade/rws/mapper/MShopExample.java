package com.midea.trade.rws.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.trade.rws.util.RouteTableHelper;

public class MShopExample extends RouteTableHelper{
	
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MShopExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andShopIdIsNull() {
            addCriterion("shop_id is null");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNotNull() {
            addCriterion("shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopIdEqualTo(Integer value) {
            addCriterion("shop_id =", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotEqualTo(Integer value) {
            addCriterion("shop_id <>", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThan(Integer value) {
            addCriterion("shop_id >", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("shop_id >=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThan(Integer value) {
            addCriterion("shop_id <", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThanOrEqualTo(Integer value) {
            addCriterion("shop_id <=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdIn(List<Integer> values) {
            addCriterion("shop_id in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotIn(List<Integer> values) {
            addCriterion("shop_id not in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdBetween(Integer value1, Integer value2) {
            addCriterion("shop_id between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotBetween(Integer value1, Integer value2) {
            addCriterion("shop_id not between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIsNull() {
            addCriterion("platform_id is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIsNotNull() {
            addCriterion("platform_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformIdEqualTo(Byte value) {
            addCriterion("platform_id =", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotEqualTo(Byte value) {
            addCriterion("platform_id <>", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdGreaterThan(Byte value) {
            addCriterion("platform_id >", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdGreaterThanOrEqualTo(Byte value) {
            addCriterion("platform_id >=", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLessThan(Byte value) {
            addCriterion("platform_id <", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLessThanOrEqualTo(Byte value) {
            addCriterion("platform_id <=", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIn(List<Byte> values) {
            addCriterion("platform_id in", values, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotIn(List<Byte> values) {
            addCriterion("platform_id not in", values, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdBetween(Byte value1, Byte value2) {
            addCriterion("platform_id between", value1, value2, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotBetween(Byte value1, Byte value2) {
            addCriterion("platform_id not between", value1, value2, "platformId");
            return (Criteria) this;
        }

        public Criteria andShopNameIsNull() {
            addCriterion("shop_name is null");
            return (Criteria) this;
        }

        public Criteria andShopNameIsNotNull() {
            addCriterion("shop_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopNameEqualTo(String value) {
            addCriterion("shop_name =", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotEqualTo(String value) {
            addCriterion("shop_name <>", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameGreaterThan(String value) {
            addCriterion("shop_name >", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_name >=", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLessThan(String value) {
            addCriterion("shop_name <", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLessThanOrEqualTo(String value) {
            addCriterion("shop_name <=", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLike(String value) {
            addCriterion("shop_name like", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotLike(String value) {
            addCriterion("shop_name not like", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameIn(List<String> values) {
            addCriterion("shop_name in", values, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotIn(List<String> values) {
            addCriterion("shop_name not in", values, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameBetween(String value1, String value2) {
            addCriterion("shop_name between", value1, value2, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotBetween(String value1, String value2) {
            addCriterion("shop_name not between", value1, value2, "shopName");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNull() {
            addCriterion("owner is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNotNull() {
            addCriterion("owner is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerEqualTo(String value) {
            addCriterion("owner =", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotEqualTo(String value) {
            addCriterion("owner <>", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThan(String value) {
            addCriterion("owner >", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("owner >=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThan(String value) {
            addCriterion("owner <", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThanOrEqualTo(String value) {
            addCriterion("owner <=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLike(String value) {
            addCriterion("owner like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotLike(String value) {
            addCriterion("owner not like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerIn(List<String> values) {
            addCriterion("owner in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotIn(List<String> values) {
            addCriterion("owner not in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerBetween(String value1, String value2) {
            addCriterion("owner between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotBetween(String value1, String value2) {
            addCriterion("owner not between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileIsNull() {
            addCriterion("owner_mobile is null");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileIsNotNull() {
            addCriterion("owner_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileEqualTo(String value) {
            addCriterion("owner_mobile =", value, "ownerMobile");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileNotEqualTo(String value) {
            addCriterion("owner_mobile <>", value, "ownerMobile");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileGreaterThan(String value) {
            addCriterion("owner_mobile >", value, "ownerMobile");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileGreaterThanOrEqualTo(String value) {
            addCriterion("owner_mobile >=", value, "ownerMobile");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileLessThan(String value) {
            addCriterion("owner_mobile <", value, "ownerMobile");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileLessThanOrEqualTo(String value) {
            addCriterion("owner_mobile <=", value, "ownerMobile");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileLike(String value) {
            addCriterion("owner_mobile like", value, "ownerMobile");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileNotLike(String value) {
            addCriterion("owner_mobile not like", value, "ownerMobile");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileIn(List<String> values) {
            addCriterion("owner_mobile in", values, "ownerMobile");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileNotIn(List<String> values) {
            addCriterion("owner_mobile not in", values, "ownerMobile");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileBetween(String value1, String value2) {
            addCriterion("owner_mobile between", value1, value2, "ownerMobile");
            return (Criteria) this;
        }

        public Criteria andOwnerMobileNotBetween(String value1, String value2) {
            addCriterion("owner_mobile not between", value1, value2, "ownerMobile");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andGuaranteeStoreIdIsNull() {
            addCriterion("guarantee_store_id is null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeStoreIdIsNotNull() {
            addCriterion("guarantee_store_id is not null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeStoreIdEqualTo(Integer value) {
            addCriterion("guarantee_store_id =", value, "guaranteeStoreId");
            return (Criteria) this;
        }

        public Criteria andGuaranteeStoreIdNotEqualTo(Integer value) {
            addCriterion("guarantee_store_id <>", value, "guaranteeStoreId");
            return (Criteria) this;
        }

        public Criteria andGuaranteeStoreIdGreaterThan(Integer value) {
            addCriterion("guarantee_store_id >", value, "guaranteeStoreId");
            return (Criteria) this;
        }

        public Criteria andGuaranteeStoreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("guarantee_store_id >=", value, "guaranteeStoreId");
            return (Criteria) this;
        }

        public Criteria andGuaranteeStoreIdLessThan(Integer value) {
            addCriterion("guarantee_store_id <", value, "guaranteeStoreId");
            return (Criteria) this;
        }

        public Criteria andGuaranteeStoreIdLessThanOrEqualTo(Integer value) {
            addCriterion("guarantee_store_id <=", value, "guaranteeStoreId");
            return (Criteria) this;
        }

        public Criteria andGuaranteeStoreIdIn(List<Integer> values) {
            addCriterion("guarantee_store_id in", values, "guaranteeStoreId");
            return (Criteria) this;
        }

        public Criteria andGuaranteeStoreIdNotIn(List<Integer> values) {
            addCriterion("guarantee_store_id not in", values, "guaranteeStoreId");
            return (Criteria) this;
        }

        public Criteria andGuaranteeStoreIdBetween(Integer value1, Integer value2) {
            addCriterion("guarantee_store_id between", value1, value2, "guaranteeStoreId");
            return (Criteria) this;
        }

        public Criteria andGuaranteeStoreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("guarantee_store_id not between", value1, value2, "guaranteeStoreId");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeIsNull() {
            addCriterion("logistics_com_code is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeIsNotNull() {
            addCriterion("logistics_com_code is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeEqualTo(String value) {
            addCriterion("logistics_com_code =", value, "logisticsComCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeNotEqualTo(String value) {
            addCriterion("logistics_com_code <>", value, "logisticsComCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeGreaterThan(String value) {
            addCriterion("logistics_com_code >", value, "logisticsComCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_com_code >=", value, "logisticsComCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeLessThan(String value) {
            addCriterion("logistics_com_code <", value, "logisticsComCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeLessThanOrEqualTo(String value) {
            addCriterion("logistics_com_code <=", value, "logisticsComCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeLike(String value) {
            addCriterion("logistics_com_code like", value, "logisticsComCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeNotLike(String value) {
            addCriterion("logistics_com_code not like", value, "logisticsComCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeIn(List<String> values) {
            addCriterion("logistics_com_code in", values, "logisticsComCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeNotIn(List<String> values) {
            addCriterion("logistics_com_code not in", values, "logisticsComCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeBetween(String value1, String value2) {
            addCriterion("logistics_com_code between", value1, value2, "logisticsComCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsComCodeNotBetween(String value1, String value2) {
            addCriterion("logistics_com_code not between", value1, value2, "logisticsComCode");
            return (Criteria) this;
        }

        public Criteria andShopTypeIsNull() {
            addCriterion("shop_type is null");
            return (Criteria) this;
        }

        public Criteria andShopTypeIsNotNull() {
            addCriterion("shop_type is not null");
            return (Criteria) this;
        }

        public Criteria andShopTypeEqualTo(Byte value) {
            addCriterion("shop_type =", value, "shopType");
            return (Criteria) this;
        }

        public Criteria andShopTypeNotEqualTo(Byte value) {
            addCriterion("shop_type <>", value, "shopType");
            return (Criteria) this;
        }

        public Criteria andShopTypeGreaterThan(Byte value) {
            addCriterion("shop_type >", value, "shopType");
            return (Criteria) this;
        }

        public Criteria andShopTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("shop_type >=", value, "shopType");
            return (Criteria) this;
        }

        public Criteria andShopTypeLessThan(Byte value) {
            addCriterion("shop_type <", value, "shopType");
            return (Criteria) this;
        }

        public Criteria andShopTypeLessThanOrEqualTo(Byte value) {
            addCriterion("shop_type <=", value, "shopType");
            return (Criteria) this;
        }

        public Criteria andShopTypeIn(List<Byte> values) {
            addCriterion("shop_type in", values, "shopType");
            return (Criteria) this;
        }

        public Criteria andShopTypeNotIn(List<Byte> values) {
            addCriterion("shop_type not in", values, "shopType");
            return (Criteria) this;
        }

        public Criteria andShopTypeBetween(Byte value1, Byte value2) {
            addCriterion("shop_type between", value1, value2, "shopType");
            return (Criteria) this;
        }

        public Criteria andShopTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("shop_type not between", value1, value2, "shopType");
            return (Criteria) this;
        }

        public Criteria andReturnStoreIdIsNull() {
            addCriterion("return_store_id is null");
            return (Criteria) this;
        }

        public Criteria andReturnStoreIdIsNotNull() {
            addCriterion("return_store_id is not null");
            return (Criteria) this;
        }

        public Criteria andReturnStoreIdEqualTo(Integer value) {
            addCriterion("return_store_id =", value, "returnStoreId");
            return (Criteria) this;
        }

        public Criteria andReturnStoreIdNotEqualTo(Integer value) {
            addCriterion("return_store_id <>", value, "returnStoreId");
            return (Criteria) this;
        }

        public Criteria andReturnStoreIdGreaterThan(Integer value) {
            addCriterion("return_store_id >", value, "returnStoreId");
            return (Criteria) this;
        }

        public Criteria andReturnStoreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("return_store_id >=", value, "returnStoreId");
            return (Criteria) this;
        }

        public Criteria andReturnStoreIdLessThan(Integer value) {
            addCriterion("return_store_id <", value, "returnStoreId");
            return (Criteria) this;
        }

        public Criteria andReturnStoreIdLessThanOrEqualTo(Integer value) {
            addCriterion("return_store_id <=", value, "returnStoreId");
            return (Criteria) this;
        }

        public Criteria andReturnStoreIdIn(List<Integer> values) {
            addCriterion("return_store_id in", values, "returnStoreId");
            return (Criteria) this;
        }

        public Criteria andReturnStoreIdNotIn(List<Integer> values) {
            addCriterion("return_store_id not in", values, "returnStoreId");
            return (Criteria) this;
        }

        public Criteria andReturnStoreIdBetween(Integer value1, Integer value2) {
            addCriterion("return_store_id between", value1, value2, "returnStoreId");
            return (Criteria) this;
        }

        public Criteria andReturnStoreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("return_store_id not between", value1, value2, "returnStoreId");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdIsNull() {
            addCriterion("outer_shop_id is null");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdIsNotNull() {
            addCriterion("outer_shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdEqualTo(String value) {
            addCriterion("outer_shop_id =", value, "outerShopId");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdNotEqualTo(String value) {
            addCriterion("outer_shop_id <>", value, "outerShopId");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdGreaterThan(String value) {
            addCriterion("outer_shop_id >", value, "outerShopId");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdGreaterThanOrEqualTo(String value) {
            addCriterion("outer_shop_id >=", value, "outerShopId");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdLessThan(String value) {
            addCriterion("outer_shop_id <", value, "outerShopId");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdLessThanOrEqualTo(String value) {
            addCriterion("outer_shop_id <=", value, "outerShopId");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdLike(String value) {
            addCriterion("outer_shop_id like", value, "outerShopId");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdNotLike(String value) {
            addCriterion("outer_shop_id not like", value, "outerShopId");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdIn(List<String> values) {
            addCriterion("outer_shop_id in", values, "outerShopId");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdNotIn(List<String> values) {
            addCriterion("outer_shop_id not in", values, "outerShopId");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdBetween(String value1, String value2) {
            addCriterion("outer_shop_id between", value1, value2, "outerShopId");
            return (Criteria) this;
        }

        public Criteria andOuterShopIdNotBetween(String value1, String value2) {
            addCriterion("outer_shop_id not between", value1, value2, "outerShopId");
            return (Criteria) this;
        }

        public Criteria andShopCateIsNull() {
            addCriterion("shop_cate is null");
            return (Criteria) this;
        }

        public Criteria andShopCateIsNotNull() {
            addCriterion("shop_cate is not null");
            return (Criteria) this;
        }

        public Criteria andShopCateEqualTo(Long value) {
            addCriterion("shop_cate =", value, "shopCate");
            return (Criteria) this;
        }

        public Criteria andShopCateNotEqualTo(Long value) {
            addCriterion("shop_cate <>", value, "shopCate");
            return (Criteria) this;
        }

        public Criteria andShopCateGreaterThan(Long value) {
            addCriterion("shop_cate >", value, "shopCate");
            return (Criteria) this;
        }

        public Criteria andShopCateGreaterThanOrEqualTo(Long value) {
            addCriterion("shop_cate >=", value, "shopCate");
            return (Criteria) this;
        }

        public Criteria andShopCateLessThan(Long value) {
            addCriterion("shop_cate <", value, "shopCate");
            return (Criteria) this;
        }

        public Criteria andShopCateLessThanOrEqualTo(Long value) {
            addCriterion("shop_cate <=", value, "shopCate");
            return (Criteria) this;
        }

        public Criteria andShopCateIn(List<Long> values) {
            addCriterion("shop_cate in", values, "shopCate");
            return (Criteria) this;
        }

        public Criteria andShopCateNotIn(List<Long> values) {
            addCriterion("shop_cate not in", values, "shopCate");
            return (Criteria) this;
        }

        public Criteria andShopCateBetween(Long value1, Long value2) {
            addCriterion("shop_cate between", value1, value2, "shopCate");
            return (Criteria) this;
        }

        public Criteria andShopCateNotBetween(Long value1, Long value2) {
            addCriterion("shop_cate not between", value1, value2, "shopCate");
            return (Criteria) this;
        }

        public Criteria andShopJoinTimeIsNull() {
            addCriterion("shop_join_time is null");
            return (Criteria) this;
        }

        public Criteria andShopJoinTimeIsNotNull() {
            addCriterion("shop_join_time is not null");
            return (Criteria) this;
        }

        public Criteria andShopJoinTimeEqualTo(Date value) {
            addCriterion("shop_join_time =", value, "shopJoinTime");
            return (Criteria) this;
        }

        public Criteria andShopJoinTimeNotEqualTo(Date value) {
            addCriterion("shop_join_time <>", value, "shopJoinTime");
            return (Criteria) this;
        }

        public Criteria andShopJoinTimeGreaterThan(Date value) {
            addCriterion("shop_join_time >", value, "shopJoinTime");
            return (Criteria) this;
        }

        public Criteria andShopJoinTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("shop_join_time >=", value, "shopJoinTime");
            return (Criteria) this;
        }

        public Criteria andShopJoinTimeLessThan(Date value) {
            addCriterion("shop_join_time <", value, "shopJoinTime");
            return (Criteria) this;
        }

        public Criteria andShopJoinTimeLessThanOrEqualTo(Date value) {
            addCriterion("shop_join_time <=", value, "shopJoinTime");
            return (Criteria) this;
        }

        public Criteria andShopJoinTimeIn(List<Date> values) {
            addCriterion("shop_join_time in", values, "shopJoinTime");
            return (Criteria) this;
        }

        public Criteria andShopJoinTimeNotIn(List<Date> values) {
            addCriterion("shop_join_time not in", values, "shopJoinTime");
            return (Criteria) this;
        }

        public Criteria andShopJoinTimeBetween(Date value1, Date value2) {
            addCriterion("shop_join_time between", value1, value2, "shopJoinTime");
            return (Criteria) this;
        }

        public Criteria andShopJoinTimeNotBetween(Date value1, Date value2) {
            addCriterion("shop_join_time not between", value1, value2, "shopJoinTime");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsIsNull() {
            addCriterion("business_units is null");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsIsNotNull() {
            addCriterion("business_units is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsEqualTo(String value) {
            addCriterion("business_units =", value, "businessUnits");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsNotEqualTo(String value) {
            addCriterion("business_units <>", value, "businessUnits");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsGreaterThan(String value) {
            addCriterion("business_units >", value, "businessUnits");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsGreaterThanOrEqualTo(String value) {
            addCriterion("business_units >=", value, "businessUnits");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsLessThan(String value) {
            addCriterion("business_units <", value, "businessUnits");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsLessThanOrEqualTo(String value) {
            addCriterion("business_units <=", value, "businessUnits");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsLike(String value) {
            addCriterion("business_units like", value, "businessUnits");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsNotLike(String value) {
            addCriterion("business_units not like", value, "businessUnits");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsIn(List<String> values) {
            addCriterion("business_units in", values, "businessUnits");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsNotIn(List<String> values) {
            addCriterion("business_units not in", values, "businessUnits");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsBetween(String value1, String value2) {
            addCriterion("business_units between", value1, value2, "businessUnits");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitsNotBetween(String value1, String value2) {
            addCriterion("business_units not between", value1, value2, "businessUnits");
            return (Criteria) this;
        }

        public Criteria andOperationAttriIsNull() {
            addCriterion("operation_attri is null");
            return (Criteria) this;
        }

        public Criteria andOperationAttriIsNotNull() {
            addCriterion("operation_attri is not null");
            return (Criteria) this;
        }

        public Criteria andOperationAttriEqualTo(Byte value) {
            addCriterion("operation_attri =", value, "operationAttri");
            return (Criteria) this;
        }

        public Criteria andOperationAttriNotEqualTo(Byte value) {
            addCriterion("operation_attri <>", value, "operationAttri");
            return (Criteria) this;
        }

        public Criteria andOperationAttriGreaterThan(Byte value) {
            addCriterion("operation_attri >", value, "operationAttri");
            return (Criteria) this;
        }

        public Criteria andOperationAttriGreaterThanOrEqualTo(Byte value) {
            addCriterion("operation_attri >=", value, "operationAttri");
            return (Criteria) this;
        }

        public Criteria andOperationAttriLessThan(Byte value) {
            addCriterion("operation_attri <", value, "operationAttri");
            return (Criteria) this;
        }

        public Criteria andOperationAttriLessThanOrEqualTo(Byte value) {
            addCriterion("operation_attri <=", value, "operationAttri");
            return (Criteria) this;
        }

        public Criteria andOperationAttriIn(List<Byte> values) {
            addCriterion("operation_attri in", values, "operationAttri");
            return (Criteria) this;
        }

        public Criteria andOperationAttriNotIn(List<Byte> values) {
            addCriterion("operation_attri not in", values, "operationAttri");
            return (Criteria) this;
        }

        public Criteria andOperationAttriBetween(Byte value1, Byte value2) {
            addCriterion("operation_attri between", value1, value2, "operationAttri");
            return (Criteria) this;
        }

        public Criteria andOperationAttriNotBetween(Byte value1, Byte value2) {
            addCriterion("operation_attri not between", value1, value2, "operationAttri");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}