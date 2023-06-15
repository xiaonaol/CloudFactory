package com.neuedu.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.DataDictionarySubEntity;
import com.neuedu.model.DeviceEntity;
import com.neuedu.model.UserEntity;
import com.neuedu.util.CommonUtil;
import com.neuedu.util.FileOptUtil;


/**
 * 设备管理
 * @author koala
 *
 */
public class DeviceManageController {
	/**
	 * 单例模式
	 */
	private DeviceManageController() {}
    private static class DeviceManageControllerInstance {
        private static final DeviceManageController INSTANCE = new DeviceManageController();
    }
    public static DeviceManageController getInstance() {
        return DeviceManageControllerInstance.INSTANCE;
    }
    
    /**
     * 新建设备
     * @param device
     */
    public void create(DeviceEntity device){
		UserEntity loginUser = LoginController.getInstance().getLoginUser();
		if( null == loginUser ){
			device.setMsgCode(DeviceEntity.IOCODE_PARAMERROR);
			return;
		}
    	List<DeviceEntity> deviceList = new ArrayList<DeviceEntity>();
		FileOptUtil<DeviceEntity> fileDevice= new FileOptUtil<DeviceEntity>();
		fileDevice.fileOption(deviceList, FileOptUtil.FILE_READ,  DeviceEntity.class);
		int maxId = 1;
		for( DeviceEntity de : deviceList ){
			if( maxId <= Integer.parseInt( de.getDeId() )){
				maxId = Integer.parseInt(de.getDeId()) +1 ;
			}
		}
		device.setDeId(String.valueOf(maxId));
		device.setDeCode( CommonUtil.codeGenerator());
		device.setDeState("1");
		device.setDeRentState("0");
		Date d  = new Date();
		device.setDeCreater(loginUser.getUeCode());
		device.setDeUpdater( loginUser.getUeCode());
		device.setDeCreate(d);
		device.setDeUpdate(d);
		deviceList.add(device);
		String msg = fileDevice.fileOption(deviceList, FileOptUtil.FILE_WRITE,  DeviceEntity.class);
		device.setMsgCode(msg);
    }
    
    /**
     * 设备信息修改
     * @param device
     */
    public void modify(DeviceEntity device){
    	
    	if( null == device){
    		device = new DeviceEntity();
    		device.setMsgCode(DeviceEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	UserEntity loginUser = LoginController.getInstance().getLoginUser();
    	if( null == loginUser){
    		device.setMsgCode(DeviceEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	List<DeviceEntity> deviceList = new ArrayList<DeviceEntity>();
		FileOptUtil<DeviceEntity> fileDevice= new FileOptUtil<DeviceEntity>();
		fileDevice.fileOption(deviceList, FileOptUtil.FILE_READ,  DeviceEntity.class);
		
		DeviceEntity tarDevice = null;
		for(DeviceEntity de : deviceList){
			if( de.getDeId().equals(device.getDeId())){
				tarDevice = de;
				break;
			}
		}
		
		// 未查询到相关设备信息
		if( null == tarDevice){
			device.setMsgCode(DeviceEntity.IOCODE_PARAMERROR);
    		return ;
		}
		
		boolean saveFlag = false;
		
		if( null != device.getDeName() && !"".equals(device.getDeName()) && !device.getDeName().equals( tarDevice.getDeName())){
			tarDevice.setDeName(device.getDeName());
			saveFlag = true;
		}
		
		if( null != device.getDeType() && !"".equals(device.getDeType()) && !device.getDeType().equals( tarDevice.getDeType())){
			tarDevice.setDeType(device.getDeType());
			saveFlag = true;
		}
		
		if( null != device.getDeSpecifications() && !"".equals(device.getDeSpecifications()) && !device.getDeSpecifications().equals( tarDevice.getDeSpecifications())){
			tarDevice.setDeSpecifications(device.getDeSpecifications());
			saveFlag = true;
		}
		
		if( null != device.getDeState() && !"".equals(device.getDeState()) && !device.getDeState().equals( tarDevice.getDeState())){
			tarDevice.setDeState(device.getDeState());
			saveFlag = true;
		}
    	
		if(  ( null == device.getDeRemark() && null != tarDevice.getDeRemark())||
				( null != device.getDeRemark() && !device.getDeRemark().equals( tarDevice.getDeRemark()))){
			tarDevice.setDeRemark(device.getDeRemark());
			saveFlag = true;
		}
		
		if( null != device.getDeRentState()){
			//  取消租用
			if( "0".equals( device.getDeRentState()) && "1".equals(tarDevice.getDeRentState() ) ){
				tarDevice.setDeRentState("0");
				tarDevice.setDeRentDate(null);
				tarDevice.setDeRentUserCode(null);
			}
			
			// 申请租用
			if( "0".equals( tarDevice.getDeRentState()) && "1".equals(device.getDeRentState() ) ){
				tarDevice.setDeRentState("1");
				tarDevice.setDeRentDate(new Date());
				tarDevice.setDeRentUserCode(loginUser.getUeCode());
			}
		}
		
		if( saveFlag ){
			tarDevice.setDeUpdater( loginUser.getUeCode());
			tarDevice.setDeUpdate(new Date());
		}
		
		String msg = 	fileDevice.fileOption(deviceList, FileOptUtil.FILE_WRITE,  DeviceEntity.class);
		device.setMsgCode(msg);
		
    }
    
    /**
     * 删除设备信息
     * @param deviceList
     * @return
     */
    public Map<String , String > remove (List<DeviceEntity> removeList){
    	
    	UserEntity loginUser = LoginController.getInstance().getLoginUser();
    	
    	if( null == loginUser ){
    		DeviceEntity entity = new DeviceEntity();
    		entity.setMsgCode(DeviceEntity.IOCODE_PARAMERROR);
    		return entity.getMsgMap();
    	}
    	
    	if( null == removeList || removeList.isEmpty()){
    		DeviceEntity entity = new DeviceEntity();
    		entity.setMsgCode(DeviceEntity.IOCODE_PARAMERROR);
    		return entity.getMsgMap();
    	}
    	
    	
    	List<DeviceEntity> deviceList = new ArrayList<DeviceEntity>();
		FileOptUtil<DeviceEntity> fileDevice= new FileOptUtil<DeviceEntity>();
		fileDevice.fileOption(deviceList, FileOptUtil.FILE_READ,  DeviceEntity.class);
		
    	List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		
		for( DeviceEntity de : removeList){
			
			Iterator<DeviceEntity> iter = deviceList.iterator();
	    	while(iter.hasNext()){
	    		DeviceEntity dbDe = iter.next();
	    		if( dbDe.getDeId().equals(de.getDeId())){
	    				
	    			boolean isRentDevice = true;
	    	    	// 判断设备类型   
	    			for( UserEntity ue : userList ){
	    				if( ue.getUeCode().equals(dbDe.getDeCreater())){
	    					if( "admin".equals(ue.getUeRole()) ){
	    						//当前设备类型为租用设备 ， 去掉租用状态
	    						isRentDevice = false;
	    						dbDe.setDeRentDate(null);
	    						dbDe.setDeRentState("0");
	    						dbDe.setDeRentUserCode(null);
	    						dbDe.setDeUpdate( new Date());
	    						dbDe.setDeUpdater(loginUser.getUeCode());
	    						break;
	    					}
	    				}
	    			}
	    			if(isRentDevice){
	    				// 如果是非租用设备， 删除设备信息
	    				iter.remove();
	    			}
	    		}
	    	}
		}
		
		String msg = fileDevice.fileOption(deviceList, FileOptUtil.FILE_WRITE,  DeviceEntity.class);
    	DeviceEntity rde = new DeviceEntity();
    	rde.setMsgCode(msg);
    	return rde.getMsgMap();
    }
    
    public List<DeviceEntity> query(DeviceEntity query){
    	
    	List<DeviceEntity> dList = new ArrayList<DeviceEntity>();
    	
     	List<DeviceEntity> deviceList = new ArrayList<DeviceEntity>();
		FileOptUtil<DeviceEntity> fileDevice= new FileOptUtil<DeviceEntity>();
		fileDevice.fileOption(deviceList, FileOptUtil.FILE_READ,  DeviceEntity.class);

		List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		
		UserEntity loginUser = LoginController.getInstance().getLoginUser();
		
		if( "admin".equals(loginUser.getUeRole())){
			for( DeviceEntity de : deviceList ){
				for( UserEntity ue : userList ){
					if( de.getDeCreater().equals( ue.getUeCode() )&& "admin".equals(ue.getUeRole())){
						dList.add(de);
					}
				}
			}
		}else{
			
			//  查询 可租用设备专用
			if( null != query && "0".equals(query.getDeRentState())){
				dList.addAll(deviceList);
			}else{
				
				List<UserEntity >  factoryUserList = new ArrayList<UserEntity>();
				for( UserEntity ue : userList ){
					if( loginUser.getUeFactoryCode().equals(ue.getUeFactoryCode())){
						factoryUserList.add(ue);
					}
				}
				
				for( DeviceEntity de : deviceList ){
					for( UserEntity ue : factoryUserList){
						if( de.getDeCreater().equals(ue.getUeCode())){
							// 添加工厂自有设备
							dList.add(de);
						}else if( ue.getUeCode().equals(de.getDeRentUserCode())){
							// 添加租用设备
							dList.add(de);
						}
					}
				}
				
			}
		}
		
		if( null == dList || dList.isEmpty()){
			return dList;
		}
		
		if( null != query){
			Iterator<DeviceEntity> iter = dList.iterator();
			while( iter.hasNext()){
				DeviceEntity de = iter.next();
				// 主键
				if( null != query.getDeId() && !"".equals(query.getDeId()) && !query.getDeId().equals(de.getDeId())){
					iter.remove();
				}
				// 名称
				if( null != query.getDeName() && !"".equals(query.getDeName()) && de.getDeName().indexOf(query.getDeName()) == -1 ){
				 	iter.remove();
				}
				// 编号
				if( null != query.getDeCode() && !"".equals(query.getDeCode()) && !query.getDeCode().equals(de.getDeCode())){
					iter.remove();
				}
				// 设备类型
				if( null != query.getDeType() && !"".equals(query.getDeType()) && !query.getDeType().equals(de.getDeType())){
					iter.remove();
				}
				// 设备状态
				if( null != query.getDeState() && !"".equals(query.getDeState()) && !query.getDeState().equals(de.getDeState())){
					iter.remove();
				}
				
				// 设备租用装填
				if( null != query.getDeRentState() && !"".equals(query.getDeRentState() ) && !query.getDeRentState().equals(de.getDeRentState())){
					iter.remove();
				}
			}
		}
//		else{
//			dList.addAll(deviceList);
//		}
		
		// 结构拼装
		for(DeviceEntity de : dList ){
			if( null != de.getDeRentUserCode() && !"".equals(de.getDeRentUserCode())){
				for( UserEntity ue : userList){
					if( de.getDeRentUserCode().equals(ue.getUeCode())){
						de.setUserRent(ue);
					}
				}
			}
			
			if( null != de.getDeCreater() && !"".equals(de.getDeCreater())){
				for( UserEntity ue : userList){
					if( de.getDeCreater().equals(ue.getUeCode())){
						de.setUserCreate(ue);
					}
				}
			}
			
			if( null != de.getDeUpdater() && !"".equals(de.getDeUpdater())){
				for( UserEntity ue : userList){
					if( de.getDeUpdater().equals(ue.getUeCode())){
						de.setUserUpdate(ue);
					}
				}
			}
			
			DataDictionaryManageController.getInstance();
			Map<String , DataDictionaryMainEntity> dd = DataDictionaryManageController.getDd();
			
			if( null != de.getDeType() && !"".equals( de.getDeType())){
				for( DataDictionarySubEntity sub : dd.get(DataDictionaryMainEntity.DD_DEVICE_TYPE).getSubList()){
					if(sub.getDdseValue().equals( de.getDeType())){
						de.setDeviceType(sub);
					}
				}
			}
			
			if( null != de.getDeState() && !"".equals(de.getDeState())){
				for( DataDictionarySubEntity sub : dd.get(DataDictionaryMainEntity.DD_DEVICE_STATE).getSubList()){
					if(sub.getDdseValue().equals( de.getDeState())){
						de.setDeviceState(sub);
					}
				}
			}
		}
    	return dList;
    }
    
    
    /**
     * 租用设备
     * @param dList
     * @return
     */
    public Map<String , String> deviceRent(List<DeviceEntity> dList ){
    	
    	List<DeviceEntity> deviceList = new ArrayList<DeviceEntity>();
		FileOptUtil<DeviceEntity> fileDevice= new FileOptUtil<DeviceEntity>();
		fileDevice.fileOption(deviceList, FileOptUtil.FILE_READ,  DeviceEntity.class);
		
		UserEntity loginUser = LoginController.getInstance().getLoginUser();
		if( null == loginUser){
			DeviceEntity de = new DeviceEntity();
			de.setMsgCode( DeviceEntity.IOCODE_PARAMERROR);
			return de.getMsgMap();
		}
		
		for( DeviceEntity de : deviceList){
			for( DeviceEntity rde : dList){
				if( de.getDeId().equals(rde.getDeId())){
					de.setDeRentDate( new Date());
					de.setDeRentState("1");
					de.setDeRentUserCode( loginUser.getUeCode());
					break;
				}
			}
		}
		
		String msg = fileDevice.fileOption(deviceList, FileOptUtil.FILE_WRITE,  DeviceEntity.class);
		DeviceEntity de = new DeviceEntity();
		de.setMsgCode(msg	);
		return de.getMsgMap();
    }
}
