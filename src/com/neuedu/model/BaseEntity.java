package com.neuedu.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  基本实体类
 * @author koala
 */
public class BaseEntity  {
	public final static String IOCODE_SUCCESS = "Success";
	public final static String IOCODE_CREATEFILE_ERROR = "CreateFileError";
	public final static String IOCODE_OPTTYPE_ERROR = "OptTypeError";
	public final static String IOCODE_IO_ERROR = "IOError";
	public final static String IOCODE_NOUPDATE = "noUpdate";
	public final static String IOCODE_PARAMERROR = "paramError";
	public final static String IOCODE_VALIPWD_ERROR = "valiPwdError";
	public final static String IOCODE_LOGIN_NOACCOUNT = "loginNoAccount";
	
	private Map<String , String > msgMap ; 
	
	private boolean isSelectedRow;	// 列表选择

     public boolean isSelectedRow() {
		return isSelectedRow;
	}

	public void setSelectedRow(boolean isSelectedRow) {
		this.isSelectedRow = isSelectedRow;
	}

	public Map<String, String> getMsgMap() {
		if( null == msgMap ){
			this.msgMap = new HashMap<String, String>() ;
		}
		return this.msgMap ;
	}

	public void setMsgMap(Map<String, String> msgMap) {
		this.msgMap = msgMap;
	}

	/**
	 * 传入读写结果码 ， 自动填充错误类型说明
	 * Success 成功
	 * CreateFileError：创建文件错误
	 * OptTypeError：读写类型错误
	 * IOError： 读写错误
	 * noUpdate : 无更新内容
	 * @param code
	 */
	public void setMsgCode(String code ){
		
		switch (code) {
		case IOCODE_SUCCESS:
			this.getMsgMap().put("state", "success");
			this.getMsgMap().put("msg", "成功");
			break;
		case IOCODE_CREATEFILE_ERROR:
			this.getMsgMap().put("state", "fail");
			this.getMsgMap().put("msg", "创建文件错误");
			break;
		case IOCODE_OPTTYPE_ERROR:
			this.getMsgMap().put("state", "fail");
			this.getMsgMap().put("msg", "读写操作类型错误");
			break;
		case IOCODE_IO_ERROR:
			this.getMsgMap().put("state", "fail");
			this.getMsgMap().put("msg", "文件写入错误");
			break;
		case IOCODE_NOUPDATE:	
			this.getMsgMap().put("state", "noUpdate");
			this.getMsgMap().put("msg", "无更新内容");
			break;
		case  IOCODE_PARAMERROR:
			this.getMsgMap().put("state", "fail");
			this.getMsgMap().put("msg", "无对应数据或参数错误");
			break;
		case IOCODE_VALIPWD_ERROR:
			this.getMsgMap().put("state", "fail");
			this.getMsgMap().put("msg", "账号或密码错误");
			break;
		case IOCODE_LOGIN_NOACCOUNT:
			this.getMsgMap().put("state", "fail");
			this.getMsgMap().put("msg", "账号不存在");
			break;
		default:
			this.getMsgMap().put("state", "fail");
			this.getMsgMap().put("msg", "未知错误");
			break;
		}
		
	}
	
}
