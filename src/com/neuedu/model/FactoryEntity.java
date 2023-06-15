package com.neuedu.model;

import java.util.Date;

/**
 * 
 * @author koala
 *
 */
public class FactoryEntity extends BaseEntity {
	
	public final static String ENTITY_FILENAME = "factory";
	public final static String[] ENTITY_TITLE ={ "","序号" , "名称","编码","联系人","联系人电话","创建者","创建时间","状态"}; 
	
	private String feId; 								// 工厂主键
	private String feName ; 						// 工厂名称
	private String feCode ; 						// 工厂编码
	private String feRemark;						// 工厂说明
	private String feContacts ; 					// 工厂联系人
	private String feContactsTel ; 				// 工厂联系人电话
	private String feTel ;	 						// 工厂电话
	private String feAddress;						// 工厂地址
	
	private String feState;							// 工厂状态  0 关闭， 1 正常
	private String feCreater ; 					// 工厂创建者
	private Date  feCreate;							// 创建时间
	private String feUpdater;						// 更新者
	private Date feUpdate;							// 更新时间
	
/**
 *查询时需要单独加载的属性 
 */
	private DataDictionarySubEntity state;
	private UserEntity creater; 
	private UserEntity updater;
	
	public DataDictionarySubEntity getState() {
		return state;
	}
	public void setState(DataDictionarySubEntity state) {
		this.state = state;
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
	public String getFeId() {
		return feId;
	}
	public void setFeId(String feId) {
		this.feId = feId;
	}
	public String getFeName() {
		return feName;
	}
	public void setFeName(String feName) {
		this.feName = feName;
	}
	public String getFeCode() {
		return feCode;
	}
	public void setFeCode(String feCode) {
		this.feCode = feCode;
	}
	public String getFeRemark() {
		return feRemark;
	}
	public void setFeRemark(String feRemark) {
		this.feRemark = feRemark;
	}
	public String getFeContacts() {
		return feContacts;
	}
	public void setFeContacts(String feContacts) {
		this.feContacts = feContacts;
	}
	public String getFeContactsTel() {
		return feContactsTel;
	}
	public void setFeContactsTel(String feContactsTel) {
		this.feContactsTel = feContactsTel;
	}
	public String getFeTel() {
		return feTel;
	}
	public void setFeTel(String feTel) {
		this.feTel = feTel;
	}
	public String getFeAddress() {
		return feAddress;
	}
	public void setFeAddress(String feAddress) {
		this.feAddress = feAddress;
	}
	public String getFeState() {
		return feState;
	}
	public void setFeState(String feState) {
		this.feState = feState;
	}
	public String getFeCreater() {
		return feCreater;
	}
	public void setFeCreater(String feCreater) {
		this.feCreater = feCreater;
	}
	public Date getFeCreate() {
		return feCreate;
	}
	public void setFeCreate(Date feCreate) {
		this.feCreate = feCreate;
	}
	public String getFeUpdater() {
		return feUpdater;
	}
	public void setFeUpdater(String feUpdater) {
		this.feUpdater = feUpdater;
	}
	public Date getFeUpdate() {
		return feUpdate;
	}
	public void setFeUpdate(Date feUpdate) {
		this.feUpdate = feUpdate;
	}
	
}
