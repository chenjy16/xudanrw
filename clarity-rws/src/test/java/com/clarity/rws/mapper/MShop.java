package com.clarity.rws.mapper;

import java.util.Date;



public class MShop extends BasePojo{
    private Integer shopId;

    private Byte platformId;

    private String shopName;

    private String owner;

    private String ownerMobile;

    private String email;

    private Byte status;

    private Integer guaranteeStoreId;

    private String logisticsComCode;

    private Byte shopType;

    private Integer returnStoreId;

    private String outerShopId;

    private Long shopCate;

    private Date shopJoinTime;

    private String businessUnits;

    private Byte operationAttri;

    private Byte isOpertion;

    private String itemScore;

    private String serviceScore;

    private String deliveryScore;
    
    private String splitDBName;

    public String getSplitDBName() {
		return splitDBName;
	}

	public void setSplitDBName(String splitDBName) {
		this.splitDBName = splitDBName;
	}

	public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Byte getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Byte platformId) {
        this.platformId = platformId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        this.ownerMobile = ownerMobile == null ? null : ownerMobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getGuaranteeStoreId() {
        return guaranteeStoreId;
    }

    public void setGuaranteeStoreId(Integer guaranteeStoreId) {
        this.guaranteeStoreId = guaranteeStoreId;
    }

    public String getLogisticsComCode() {
        return logisticsComCode;
    }

    public void setLogisticsComCode(String logisticsComCode) {
        this.logisticsComCode = logisticsComCode == null ? null : logisticsComCode.trim();
    }

    public Byte getShopType() {
        return shopType;
    }

    public void setShopType(Byte shopType) {
        this.shopType = shopType;
    }

    public Integer getReturnStoreId() {
        return returnStoreId;
    }

    public void setReturnStoreId(Integer returnStoreId) {
        this.returnStoreId = returnStoreId;
    }

    public String getOuterShopId() {
        return outerShopId;
    }

    public void setOuterShopId(String outerShopId) {
        this.outerShopId = outerShopId == null ? null : outerShopId.trim();
    }

    public Long getShopCate() {
        return shopCate;
    }

    public void setShopCate(Long shopCate) {
        this.shopCate = shopCate;
    }

    public Date getShopJoinTime() {
        return shopJoinTime;
    }

    public void setShopJoinTime(Date shopJoinTime) {
        this.shopJoinTime = shopJoinTime;
    }

    public String getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(String businessUnits) {
        this.businessUnits = businessUnits == null ? null : businessUnits.trim();
    }

    public Byte getOperationAttri() {
        return operationAttri;
    }

    public void setOperationAttri(Byte operationAttri) {
        this.operationAttri = operationAttri;
    }

    public Byte getIsOpertion() {
        return isOpertion;
    }

    public void setIsOpertion(Byte isOpertion) {
        this.isOpertion = isOpertion;
    }

    public String getItemScore() {
        return itemScore;
    }

    public void setItemScore(String itemScore) {
        this.itemScore = itemScore == null ? null : itemScore.trim();
    }

    public String getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(String serviceScore) {
        this.serviceScore = serviceScore == null ? null : serviceScore.trim();
    }

    public String getDeliveryScore() {
        return deliveryScore;
    }

    public void setDeliveryScore(String deliveryScore) {
        this.deliveryScore = deliveryScore == null ? null : deliveryScore.trim();
    }
}