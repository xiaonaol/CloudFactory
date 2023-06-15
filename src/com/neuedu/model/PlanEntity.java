package com.neuedu.model;

import java.util.Date;
import java.util.List;

/**
 * 生产计划实体类
 * 
 * @author koala
 *
 */
public class PlanEntity  extends BaseEntity{

	public final static String ENTITY_FILENAME = "plan";
	public final static String[] ENTITY_TITLE ={ "","序号" ,"计划名称","计划编号","产品名称","订单名称","订单编号","创建人","创建时间","计划状态"}; 
	
	private String peId ; 								//  主键
	private String peName;							//  计划名称
	private String peCode;								// 计划编号
	private String peOrderCode;					// 订单编号
	private String peBidCode ;						// 标单编号
	private String peProductCode;					// 生产产品编号
	private String peRemark;							// 计划备注
	private String peState;								//计划状态   0 生产中 1 生产完成
	
	private Date peCreate;								
	private String peCreater;
	private Date peUpdate;
	private String peUpdater;
	
	private UserEntity creater;
	private UserEntity updater;
	private OrderEntity order;
	private ProductEntity product;
	private BidEntity bid;
	private List<PlanDeviceEntity> pdList;				// 生产计划下 设备列表
	
	public List<PlanDeviceEntity> getPdList() {
		return pdList;
	}
	public void setPdList(List<PlanDeviceEntity> pdList) {
		this.pdList = pdList;
	}
	public String getPeOrderCode() {
		return peOrderCode;
	}
	public void setPeOrderCode(String peOrderCode) {
		this.peOrderCode = peOrderCode;
	}
	public String getPeBidCode() {
		return peBidCode;
	}
	public void setPeBidCode(String peBidCode) {
		this.peBidCode = peBidCode;
	}
	public String getPeProductCode() {
		return peProductCode;
	}
	public void setPeProductCode(String peProductCode) {
		this.peProductCode = peProductCode;
	}
	public ProductEntity getProduct() {
		return product;
	}
	public void setProduct(ProductEntity product) {
		this.product = product;
	}
	public BidEntity getBid() {
		return bid;
	}
	public void setBid(BidEntity bid) {
		this.bid = bid;
	}
	public String getPeName() {
		return peName;
	}
	public void setPeName(String peName) {
		this.peName = peName;
	}
	private List<PlanDeviceEntity> deviceList;
	public String getPeId() {
		return peId;
	}
	public void setPeId(String peId) {
		this.peId = peId;
	}
	public String getPeCode() {
		return peCode;
	}
	public void setPeCode(String peCode) {
		this.peCode = peCode;
	}
	public String getPeRemark() {
		return peRemark;
	}
	public void setPeRemark(String peRemark) {
		this.peRemark = peRemark;
	}
	public Date getPeCreate() {
		return peCreate;
	}
	public void setPeCreate(Date peCreate) {
		this.peCreate = peCreate;
	}
	public String getPeCreater() {
		return peCreater;
	}
	public void setPeCreater(String peCreater) {
		this.peCreater = peCreater;
	}
	public Date getPeUpdate() {
		return peUpdate;
	}
	public void setPeUpdate(Date peUpdate) {
		this.peUpdate = peUpdate;
	}
	public String getPeUpdater() {
		return peUpdater;
	}
	public void setPeUpdater(String peUpdater) {
		this.peUpdater = peUpdater;
	}
	public String getPeState() {
		return peState;
	}
	public void setPeState(String peState) {
		this.peState = peState;
	}
	public UserEntity getCreater() {
		return creater;
	}
	public void setCreater(UserEntity creater) {
		this.creater = creater;
	}
	public UserEntity getUpdater() {
		return updater;
	}
	public void setUpdater(UserEntity updater) {
		this.updater = updater;
	}
	public OrderEntity getOrder() {
		return order;
	}
	public void setOrder(OrderEntity order) {
		this.order = order;
	}
	public List<PlanDeviceEntity> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<PlanDeviceEntity> deviceList) {
		this.deviceList = deviceList;
	}
	
}
