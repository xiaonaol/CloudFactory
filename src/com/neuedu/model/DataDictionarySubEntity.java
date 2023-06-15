package com.neuedu.model;
/**
 *  数据字典子数据项实体类
 * @author koala
 *
 */
public class DataDictionarySubEntity extends BaseEntity {

	public final static String ENTITY_FILENAME = "dataDicationarySub";
	public final static String[] ENTITY_TITLE ={ "","序号" ,"编码","所属主项代码","所属主项名称","展示名称" , "项值"  } ; 
	
	
	private String ddseId;
	private String ddseCode;
	private String ddsePCode;
	private String ddseText;
	private String ddseValue; 
	private String ddseRemark;
	
	private DataDictionaryMainEntity main;
	
	public DataDictionaryMainEntity getMain() {
		return main;
	}
	public void setMain(DataDictionaryMainEntity main) {
		this.main = main;
	}
	public String getDdseId() {
		return ddseId;
	}
	public void setDdseId(String ddseId) {
		this.ddseId = ddseId;
	}
	public String getDdseCode() {
		return ddseCode;
	}
	public void setDdseCode(String ddseCode) {
		this.ddseCode = ddseCode;
	}
	public String getDdsePCode() {
		return ddsePCode;
	}
	public void setDdsePCode(String ddsePCode) {
		this.ddsePCode = ddsePCode;
	}
	public String getDdseText() {
		return ddseText;
	}
	public void setDdseText(String ddseText) {
		this.ddseText = ddseText;
	}
	public String getDdseValue() {
		return ddseValue;
	}
	public void setDdseValue(String ddseValue) {
		this.ddseValue = ddseValue;
	}
	public String getDdseRemark() {
		return ddseRemark;
	}
	public void setDdseRemark(String ddseRemark) {
		this.ddseRemark = ddseRemark;
	}
}
