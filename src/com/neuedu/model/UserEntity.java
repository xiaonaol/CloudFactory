package com.neuedu.model;

import java.util.Date;

/**
 * 
 * @author koala
 *
 */
public class UserEntity extends BaseEntity {
	
	public final static String ENTITY_FILENAME = "user";
	public final static String[] ENTITY_TITLE ={ "","序号" , "姓名","账号","编码","性别","年龄","角色","邮箱","电话","账号状态"}; 

	private String ueId ; 								// 主键
	private String ueAccount;						// 账号
	private String uePwd;								// 密码
	private String ueCode;								// 编号
	
	private String ueName;							// 展示名称
	private Date ueBirthday;							// 生日
	private String ueIdCardNo;						// 身份证号码
	private String ueGender; 							// 性别   
	private String ueRole;								// 角色： 
	private String ueEmail ; 							// 用户email  可使用email登陆系统
	private String ueTel;								// 电话
	private String ueAddress;							// 地址
	
	private String ueFactoryCode;					// 用户所属工厂编号
	private String ueRemark;							// 用户备注
	
	private String ueState;								// 账号状态  
	private String ueCreater;							// 创建者code
	private Date ueCreate;								// 创建时间
	private String ueUpdater;						//  更新者code
	private Date ueUpdate;							// 更新时间
	
/**
 * 查询事需要单独加载内容
 */
	private UserEntity creater ; 
	private UserEntity updater;
	private FactoryEntity factory ; 					// 用户所属工厂信息
	private DataDictionarySubEntity gender; 	// 用户性别数据字典信息
	private DataDictionarySubEntity state;			// 账号状态数据字典信息
	private DataDictionarySubEntity role;			// 角色 数据字典信息
	
/**
 * 功能性属性
 */
	private String newPwd ; 							// 用于重置密码
	
	
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
	public FactoryEntity getFactory() {
		return factory;
	}
	public void setFactory(FactoryEntity factory) {
		this.factory = factory;
	}
	public DataDictionarySubEntity getGender() {
		return gender;
	}
	public void setGender(DataDictionarySubEntity gender) {
		this.gender = gender;
	}
	public DataDictionarySubEntity getState() {
		return state;
	}
	public void setState(DataDictionarySubEntity state) {
		this.state = state;
	}
	public DataDictionarySubEntity getRole() {
		return role;
	}
	public void setRole(DataDictionarySubEntity role) {
		this.role = role;
	}
	public String getUeRemark() {
		return ueRemark;
	}
	public void setUeRemark(String ueRemark) {
		this.ueRemark = ueRemark;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getUeFactoryCode() {
		return ueFactoryCode;
	}
	public void setUeFactoryCode(String ueFactoryCode) {
		this.ueFactoryCode = ueFactoryCode;
	}
	public String getUeId() {
		return ueId;
	}
	public void setUeId(String ueId) {
		this.ueId = ueId;
	}
	public String getUeAccount() {
		return ueAccount;
	}
	public void setUeAccount(String ueAccount) {
		this.ueAccount = ueAccount;
	}
	public String getUePwd() {
		return uePwd;
	}
	public void setUePwd(String uePwd) {
		this.uePwd = uePwd;
	}
	public String getUeCode() {
		return ueCode;
	}
	public void setUeCode(String ueCode) {
		this.ueCode = ueCode;
	}
	public String getUeName() {
		return ueName;
	}
	public void setUeName(String ueName) {
		this.ueName = ueName;
	}
	public Date getUeBirthday() {
		return ueBirthday;
	}
	public void setUeBirthday(Date ueBirthday) {
		this.ueBirthday = ueBirthday;
	}
	public String getUeIdCardNo() {
		return ueIdCardNo;
	}
	public void setUeIdCardNo(String ueIdCardNo) {
		this.ueIdCardNo = ueIdCardNo;
	}
	public String getUeGender() {
		return ueGender;
	}
	public void setUeGender(String ueGender) {
		this.ueGender = ueGender;
	}
	public String getUeRole() {
		return ueRole;
	}
	public void setUeRole(String ueRole) {
		this.ueRole = ueRole;
	}
	public String getUeEmail() {
		return ueEmail;
	}
	public void setUeEmail(String ueEmail) {
		this.ueEmail = ueEmail;
	}
	public String getUeTel() {
		return ueTel;
	}
	public void setUeTel(String ueTel) {
		this.ueTel = ueTel;
	}
	public String getUeAddress() {
		return ueAddress;
	}
	public void setUeAddress(String ueAddress) {
		this.ueAddress = ueAddress;
	}
	public String getUeState() {
		return ueState;
	}
	public void setUeState(String ueState) {
		this.ueState = ueState;
	}
	public String getUeCreater() {
		return ueCreater;
	}
	public void setUeCreater(String ueCreater) {
		this.ueCreater = ueCreater;
	}
	public Date getUeCreate() {
		return ueCreate;
	}
	public void setUeCreate(Date ueCreate) {
		this.ueCreate = ueCreate;
	}
	public String getUeUpdater() {
		return ueUpdater;
	}
	public void setUeUpdater(String ueUpdater) {
		this.ueUpdater = ueUpdater;
	}
	public Date getUeUpdate() {
		return ueUpdate;
	}
	public void setUeUpdate(Date ueUpdate) {
		this.ueUpdate = ueUpdate;
	}
	
}
