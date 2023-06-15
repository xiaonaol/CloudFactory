package com.neuedu.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import com.neuedu.util.FileOptUtil;

/**
 * 数据字典主数据项实体类
 * @author koala
 *
 */
public class DataDictionaryMainEntity  extends BaseEntity {

	public final static String ENTITY_FILENAME = "dataDicationaryMain";
	public final static String[] ENTITY_TITLE ={ "","序号" ,"编码","字典类型码","类型名称" , "子项数量" , "备注" } ; 
	
	public final static String DD_FACTORY_STATE = "factoryState";
	
	public final static String DD_USER_GENDER = "userGender";
	public final static String DD_USER_STATE	= "userState";
	public final static String DD_USER_ROLE	= "userRole" ;
	
	public final static String DD_DEVICE_TYPE	= "deviceType";
	public final static String DD_DEVICE_STATE	= "deviceState";
	
	public final static String DD_PRODUCT_TYPE = "productType";
	
	public final static String DD_ORDER_STATE = "orderState";
	
	public final static String DD_BID_STATE	= "bidState";
	
	public final static String DD_PLAN_STATE= "planState" ; 										//  生产计划状态
	public final static String DD_PLAN_DEVICESTATE = "planDeviceState";					// 生产设备状态
	
	
	
	private String ddmeId ; 							// 主键
	private String ddmeCode;						// 独立编码
	private String ddmeKind;							// 主项代码
	private String ddmeKindName;				// 数据字典类型名称
	private String ddmeRemark;					// 字典项说明
	
	private List<DataDictionarySubEntity> subList ; // 子项.
	
	public DataDictionaryMainEntity(){};
	/**
	 * 数据字典构建方法 ， 传入 字典关键字以及combox ， 可自动加载
	 * @param kind
	 * @param combox
	 */
	public DataDictionaryMainEntity(String kind , JComboBox<ComBoxEntity> combox) {
		super();

		if( null == kind || "".equals(kind)){
			return;
		}
		
    	List<DataDictionaryMainEntity> ddmList = new ArrayList<DataDictionaryMainEntity>();
		FileOptUtil<DataDictionaryMainEntity> fileddm = new FileOptUtil<DataDictionaryMainEntity>();
		fileddm.fileOption(ddmList, FileOptUtil.FILE_READ,  DataDictionaryMainEntity.class);
		
    	List<DataDictionarySubEntity> ddsList = new ArrayList<DataDictionarySubEntity>();
		FileOptUtil<DataDictionarySubEntity> filedds = new FileOptUtil<DataDictionarySubEntity>();
		filedds.fileOption(ddsList, FileOptUtil.FILE_READ,  DataDictionarySubEntity.class);
		
		boolean flag = true;
		
		for(DataDictionaryMainEntity ddm : ddmList ){
			if( kind.equals(ddm.getDdmeKind())){
				this.setDdmeId( ddm.getDdmeId());
				this.setDdmeCode( ddm.getDdmeCode());
				this.setDdmeKind( ddm.getDdmeKind() );
				this.setDdmeKindName( ddm.getDdmeKindName());
				this.setDdmeRemark( ddm.getDdmeRemark());
				flag = false;
				break;
			}
		}
		
		if( flag ){
			return ;
		}
		
		this.setSubList( new ArrayList<DataDictionarySubEntity>());
		for(DataDictionarySubEntity dds : ddsList ){
			if( this.ddmeCode.equals(dds.getDdsePCode())){
				this.subList.add(dds);
				if( null != combox){
					combox.addItem(new ComBoxEntity ( dds.getDdseText() , dds.getDdseValue() ) );
				}
			}
		}
	}
	
	public String getDdmeId() {
		return ddmeId;
	}

	public void setDdmeId(String ddmeId) {
		this.ddmeId = ddmeId;
	}

	public String getDdmeCode() {
		return ddmeCode;
	}

	public void setDdmeCode(String ddmeCode) {
		this.ddmeCode = ddmeCode;
	}

	public String getDdmeKind() {
		return ddmeKind;
	}

	public void setDdmeKind(String ddmeKind) {
		this.ddmeKind = ddmeKind;
	}

	public String getDdmeKindName() {
		return ddmeKindName;
	}

	public void setDdmeKindName(String ddmeKindName) {
		this.ddmeKindName = ddmeKindName;
	}

	public String getDdmeRemark() {
		return ddmeRemark;
	}

	public void setDdmeRemark(String ddmeRemark) {
		this.ddmeRemark = ddmeRemark;
	}

	public List<DataDictionarySubEntity> getSubList() {
		return subList;
	}

	public void setSubList(List<DataDictionarySubEntity> subList) {
		this.subList = subList;
	}
}
