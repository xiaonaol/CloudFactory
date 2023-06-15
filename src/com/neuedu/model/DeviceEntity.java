package com.neuedu.model;

import java.util.Date;

public class DeviceEntity extends BaseEntity{
	
	public final static String ENTITY_FILENAME = "device";
	public final static String[] ENTITY_TITLE ={ "","序号" , "设备名称","设备编号","类型","规格","设备状态","设备所有人","设备租用状态","设备租用人","租用时间"}; 
	
	private String deId;									// 主键
	private String deName;							// 名称
	private String deCode;								// 设备编码
	private String deType;								// 类型  
	private String deSpecifications;				// 规格
	private boolean isRentDevice;					// 是否是租用设备 ：  通过创建者类型， 可滤掉此类设备
	
	private String deState;								// 设备状态
	private String deRemark;							// 设备说明
	
	private String deRentState;						// 租用状态
	private String deRentUserCode;				// 租用人编码
	private Date deRentDate;							// 租用日期
	
	private String deCreater;							// 设备创建者
	private Date deCreate;								// 设备创建时	间
	private String deUpdater;						// 设备更新者
	private Date deUpdate;							// 设备更新时间
	
	private UserEntity userRent;
	private UserEntity userCreate;
	private UserEntity userUpdate;
	private DataDictionarySubEntity deviceType ;
	private DataDictionarySubEntity deviceState ;
	
	
	public DataDictionarySubEntity getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(DataDictionarySubEntity deviceState) {
		this.deviceState = deviceState;
	}

	public String getDeCode() {
		return deCode;
	}

	public void setDeCode(String deCode) {
		this.deCode = deCode;
	}

	public boolean isRentDevice() {
		return isRentDevice;
	}

	public void setRentDevice(boolean isRentDevice) {
		this.isRentDevice = isRentDevice;
	}

	public String getDeId() {
		return deId;
	}

	public void setDeId(String deId) {
		this.deId = deId;
	}

	public String getDeName() {
		return deName;
	}

	public void setDeName(String deName) {
		this.deName = deName;
	}

	public String getDeType() {
		return deType;
	}

	public void setDeType(String deType) {
		this.deType = deType;
	}

	public String getDeSpecifications() {
		return deSpecifications;
	}

	public void setDeSpecifications(String deSpecifications) {
		this.deSpecifications = deSpecifications;
	}

	public String getDeState() {
		return deState;
	}

	public void setDeState(String deState) {
		this.deState = deState;
	}

	public String getDeRemark() {
		return deRemark;
	}

	public void setDeRemark(String deRemark) {
		this.deRemark = deRemark;
	}

	public String getDeRentState() {
		return deRentState;
	}

	public void setDeRentState(String deRentState) {
		this.deRentState = deRentState;
	}

	public String getDeRentUserCode() {
		return deRentUserCode;
	}

	public void setDeRentUserCode(String deRentUserCode) {
		this.deRentUserCode = deRentUserCode;
	}

	public Date getDeRentDate() {
		return deRentDate;
	}

	public void setDeRentDate(Date deRentDate) {
		this.deRentDate = deRentDate;
	}

	public String getDeCreater() {
		return deCreater;
	}

	public void setDeCreater(String deCreater) {
		this.deCreater = deCreater;
	}

	public Date getDeCreate() {
		return deCreate;
	}

	public void setDeCreate(Date deCreate) {
		this.deCreate = deCreate;
	}

	public String getDeUpdater() {
		return deUpdater;
	}

	public void setDeUpdater(String deUpdater) {
		this.deUpdater = deUpdater;
	}

	public Date getDeUpdate() {
		return deUpdate;
	}

	public void setDeUpdate(Date deUpdate) {
		this.deUpdate = deUpdate;
	}

	public UserEntity getUserRent() {
		return userRent;
	}

	public void setUserRent(UserEntity userRent) {
		this.userRent = userRent;
	}

	public UserEntity getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(UserEntity userCreate) {
		this.userCreate = userCreate;
	}

	public UserEntity getUserUpdate() {
		return userUpdate;
	}

	public void setUserUpdate(UserEntity userUpdate) {
		this.userUpdate = userUpdate;
	}

	public DataDictionarySubEntity getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DataDictionarySubEntity deviceType) {
		this.deviceType = deviceType;
	} 
	
}
