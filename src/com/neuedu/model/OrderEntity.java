package com.neuedu.model;

import java.util.Date;
import java.util.List;

/**
 * 订单实体类
 * @author koala
 *
 */
public class OrderEntity  extends BaseEntity{
	
	public final static String ENTITY_FILENAME = "order";
	public final static String[] ENTITY_TITLE ={ "","序号" , "订单名称","订单编码","产品名称","产品数量","交付时间","投标截止时间","接收人","接收人电话","订单状态" }; 
	
	private String oeId ;							// 主键
	private String oeCode; 					// 订单编号
	private String oeName;					// 订单名称
	
	private String oeState;						// 订单状态  1 竞标发布 ：2 中标确认： 3 生产中 ：4 已发货： 5 结束 6 无投标
	
	private Date oeDliverDate;					//	 交付时间
	private Date oeCutOffDate;				// 投标截止时间
	
	private String oeReceiver;					// 接收人
	private String oeReceiverTel;				// 接收人电话
	private String oeReceiverAddress;		// 接受地址
	
	private String odeProductCode;			// 产品编码
	private String odeCount;						// 订单数量
	private String oeRemark;
	
	private String oeCreater;
	private Date  oeCreate;
	private String oeUpdater;
	private Date oeUpdate;
	
	private ProductEntity product;			// 订单产品.
	private UserEntity creater ; 
	private UserEntity updater;
	private DataDictionarySubEntity state;
	private List<BidEntity> bidList ; 
	
	public String getOeRemark() {
		return oeRemark;
	}
	public void setOeRemark(String oeRemark) {
		this.oeRemark = oeRemark;
	}
	public DataDictionarySubEntity getState() {
		return state;
	}
	public void setState(DataDictionarySubEntity state) {
		this.state = state;
	}
	public List<BidEntity> getBidList() {
		return bidList;
	}
	public void setBidList(List<BidEntity> bidList) {
		this.bidList = bidList;
	}
	public String getOeId() {
		return oeId;
	}
	public void setOeId(String oeId) {
		this.oeId = oeId;
	}
	public String getOeCode() {
		return oeCode;
	}
	public void setOeCode(String oeCode) {
		this.oeCode = oeCode;
	}
	public String getOeName() {
		return oeName;
	}
	public void setOeName(String oeName) {
		this.oeName = oeName;
	}
	public String getOeState() {
		return oeState;
	}
	public void setOeState(String oeState) {
		this.oeState = oeState;
	}
	public Date getOeDliverDate() {
		return oeDliverDate;
	}
	public void setOeDliverDate(Date oeDliverDate) {
		this.oeDliverDate = oeDliverDate;
	}
	public Date getOeCutOffDate() {
		return oeCutOffDate;
	}
	public void setOeCutOffDate(Date oeCutOffDate) {
		this.oeCutOffDate = oeCutOffDate;
	}
	public String getOeReceiver() {
		return oeReceiver;
	}
	public void setOeReceiver(String oeReceiver) {
		this.oeReceiver = oeReceiver;
	}
	public String getOeReceiverTel() {
		return oeReceiverTel;
	}
	public void setOeReceiverTel(String oeReceiverTel) {
		this.oeReceiverTel = oeReceiverTel;
	}
	public String getOeReceiverAddress() {
		return oeReceiverAddress;
	}
	public void setOeReceiverAddress(String oeReceiverAddress) {
		this.oeReceiverAddress = oeReceiverAddress;
	}
	public String getOdeProductCode() {
		return odeProductCode;
	}
	public void setOdeProductCode(String odeProductCode) {
		this.odeProductCode = odeProductCode;
	}
	public String getOdeCount() {
		return odeCount;
	}
	public void setOdeCount(String odeCount) {
		this.odeCount = odeCount;
	}
	public ProductEntity getProduct() {
		return product;
	}
	public void setProduct(ProductEntity product) {
		this.product = product;
	}
	public String getOeCreater() {
		return oeCreater;
	}
	public void setOeCreater(String oeCreater) {
		this.oeCreater = oeCreater;
	}
	public Date getOeCreate() {
		return oeCreate;
	}
	public void setOeCreate(Date oeCreate) {
		this.oeCreate = oeCreate;
	}
	public String getOeUpdater() {
		return oeUpdater;
	}
	public void setOeUpdater(String oeUpdater) {
		this.oeUpdater = oeUpdater;
	}
	public Date getOeUpdate() {
		return oeUpdate;
	}
	public void setOeUpdate(Date oeUpdate) {
		this.oeUpdate = oeUpdate;
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

}
