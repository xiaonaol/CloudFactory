package com.neuedu.model;

/**
 * 产品类
 * @author koala
 *
 */
public class ProductEntity extends BaseEntity{
	
	public final static String ENTITY_FILENAME = "product";
	public final static String[] ENTITY_TITLE ={ "","序号" ,"产品名称","产品编号","产品规格","产品类型"}; 
	
	private String peId ; 						// 主键
	private String peCode;						// 产品类型编号
	private  String peName;					// 产品名称
	private String Specifications;			// 产品规格
	private String peType;						// 产品类		型
	private String peRemark;					// 备注
	
	private DataDictionarySubEntity pType;

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

	public String getPeName() {
		return peName;
	}

	public void setPeName(String peName) {
		this.peName = peName;
	}

	public String getSpecifications() {
		return Specifications;
	}

	public void setSpecifications(String specifications) {
		Specifications = specifications;
	}

	public String getPeType() {
		return peType;
	}

	public void setPeType(String peType) {
		this.peType = peType;
	}

	public String getPeRemark() {
		return peRemark;
	}

	public void setPeRemark(String peRemark) {
		this.peRemark = peRemark;
	}

	public DataDictionarySubEntity getpType() {
		return pType;
	}

	public void setpType(DataDictionarySubEntity pType) {
		this.pType = pType;
	}
	
	
}
