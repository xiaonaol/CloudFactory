package com.neuedu.model;

import java.util.Date;

/**
 * 生产计划设备使用实体类
 * 
 * @author koala
 *
 */
public class PlanDeviceEntity  extends BaseEntity{

	public final static String ENTITY_FILENAME = "planDevice";
	public final static String[] ENTITY_TITLE ={ "","序号" ,"设备名称","设备编号","生产开始时间","生产结束时间","生产数量","生产状态"}; 
	
	private String pdeId ; 									//主键
	private String pdeCode;								//独立编号
	private String pdePlanCode;							// 所属生产计划编号
	private String pdeDeviceCode;						// 设备编码
	private String pdeProductCount;					// 生产数量
	private Date pdeDeviceStart;						// 生产开始时间
	private Date pdeDeviceEnd;							// 生产结束时间
	private String pdeState;								// 设备生产状态

	private DeviceEntity device;
	private PlanEntity plan;
	
	public String getPdePlanCode() {
		return pdePlanCode;
	}

	public void setPdePlanCode(String pdePlanCode) {
		this.pdePlanCode = pdePlanCode;
	}

	public String getPdeProductCount() {
		return pdeProductCount;
	}

	public void setPdeProductCount(String pdeProductCount) {
		this.pdeProductCount = pdeProductCount;
	}

	public PlanEntity getPlan() {
		return plan;
	}

	public void setPlan(PlanEntity plan) {
		this.plan = plan;
	}

	public String getPdeId() {
		return pdeId;
	}

	public void setPdeId(String pdeId) {
		this.pdeId = pdeId;
	}

	public String getPdeCode() {
		return pdeCode;
	}

	public void setPdeCode(String pdeCode) {
		this.pdeCode = pdeCode;
	}

	public String getPdeDeviceCode() {
		return pdeDeviceCode;
	}

	public void setPdeDeviceCode(String pdeDeviceCode) {
		this.pdeDeviceCode = pdeDeviceCode;
	}

	public Date getPdeDeviceStart() {
		return pdeDeviceStart;
	}

	public void setPdeDeviceStart(Date pdeDeviceStart) {
		this.pdeDeviceStart = pdeDeviceStart;
	}

	public Date getPdeDeviceEnd() {
		return pdeDeviceEnd;
	}

	public void setPdeDeviceEnd(Date pdeDeviceEnd) {
		this.pdeDeviceEnd = pdeDeviceEnd;
	}

	public String getPdeState() {
		return pdeState;
	}

	public void setPdeState(String pdeState) {
		this.pdeState = pdeState;
	}

	public DeviceEntity getDevice() {
		return device;
	}

	public void setDevice(DeviceEntity device) {
		this.device = device;
	}
	
}
