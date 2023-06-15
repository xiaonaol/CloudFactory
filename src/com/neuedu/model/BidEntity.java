package com.neuedu.model;

import java.util.Date;

/**
 * 投标实体类
 * @author koala
 *
 */
public class BidEntity extends BaseEntity {
	
	public final static String ENTITY_FILENAME = "bid";
	public final static String[] ENTITY_TITLE ={ "","序号" ,"订单编号","订单名称","投标人","投标人工厂","投标时间","单价","承接数量","竞标状态" } ; 

	private String beId ; 					// 主键
	private String beCode; 				// 编号
	private String beOrderCode; 		// 订单编号

	private String bePrice;					// 单价
	private String beCount;				// 承接数量
	private String beProCount;			// 中标数量
	private String beState;					// 竞标状态      0 已投标  1 中标 2 未中标
	
	private String beCreaterCode ;		// 竞标人 
	private Date beCreate;					// 竞标时间
	
	private OrderEntity order;
	private UserEntity bidUser;
	
	public UserEntity getBidUser() {
		return bidUser;
	}

	public void setBidUser(UserEntity bidUser) {
		this.bidUser = bidUser;
	}

	public String getBeProCount() {
		return beProCount;
	}

	public void setBeProCount(String beProCount) {
		this.beProCount = beProCount;
	}

	public String getBeCount() {
		return beCount;
	}

	public void setBeCount(String beCount) {
		this.beCount = beCount;
	}

	public String getBeId() {
		return beId;
	}

	public void setBeId(String beId) {
		this.beId = beId;
	}

	public String getBeCode() {
		return beCode;
	}

	public void setBeCode(String beCode) {
		this.beCode = beCode;
	}

	public String getBeOrderCode() {
		return beOrderCode;
	}

	public void setBeOrderCode(String beOrderCode) {
		this.beOrderCode = beOrderCode;
	}

	public String getBePrice() {
		return bePrice;
	}

	public void setBePrice(String bePrice) {
		this.bePrice = bePrice;
	}

	public String getBeState() {
		return beState;
	}

	public void setBeState(String beState) {
		this.beState = beState;
	}

	public String getBeCreaterCode() {
		return beCreaterCode;
	}

	public void setBeCreaterCode(String beCreaterCode) {
		this.beCreaterCode = beCreaterCode;
	}

	public Date getBeCreate() {
		return beCreate;
	}

	public void setBeCreate(Date beCreate) {
		this.beCreate = beCreate;
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}
	
}
