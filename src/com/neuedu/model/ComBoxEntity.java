package com.neuedu.model;

/**
 * 下拉框实体类
 * @author koala
 *
 */
public class ComBoxEntity {

	private String comText ; 
	private String comValue;
	public String getComText() {
		return comText;
	}
	public void setComText(String comText) {
		this.comText = comText;
	}
	public String getComValue() {
		return comValue;
	}
	public void setComValue(String comValue) {
		this.comValue = comValue;
	}
	@Override
	public String toString() {
		return comText ;
	}
	
	public ComBoxEntity(String comText, String comValue) {
		super();
		this.comText = comText;
		this.comValue = comValue;
	}
	
}
