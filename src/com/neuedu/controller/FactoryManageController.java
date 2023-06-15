package com.neuedu.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.DataDictionarySubEntity;
import com.neuedu.model.FactoryEntity;
import com.neuedu.model.UserEntity;
import com.neuedu.util.CommonUtil;
import com.neuedu.util.FileOptUtil;

/**
 * 工厂管理
 * @author koala
 *
 */
public class FactoryManageController {

	/**
	 * 单例模式
	 */
	private FactoryManageController() {}
    private static class FactoryManageControllerInstance {
        private static final FactoryManageController INSTANCE = new FactoryManageController();
    }
    public static FactoryManageController getInstance() {
        return FactoryManageControllerInstance.INSTANCE;
    }
    
    /*　
     * 新建工厂信息
     * @param entity
     */
    public void create( FactoryEntity entity){
    	
    	if( null == entity ){
    		entity = new FactoryEntity();
    		entity.setMsgCode( FactoryEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	List<FactoryEntity> factoryList = new ArrayList<FactoryEntity>();
		FileOptUtil<FactoryEntity> fileFactory= new FileOptUtil<FactoryEntity>();
		fileFactory.fileOption(factoryList, FileOptUtil.FILE_READ,  FactoryEntity.class);
		
		int maxId = 1; 
		for( FactoryEntity fe : factoryList){
			if( maxId <= Integer.parseInt( fe.getFeId() )){
				maxId = Integer.parseInt( fe.getFeId())+1;
			}
		}
		
		entity.setFeId( String.valueOf( maxId ));
		entity.setFeCode( CommonUtil.codeGenerator() );
		factoryList.add(entity);
		
		String msg = fileFactory.fileOption(factoryList, FileOptUtil.FILE_WRITE,  FactoryEntity.class);
		
		if("Success".equals(msg)){
			UserEntity ue = new UserEntity();
			ue.setUeCode(entity.getFeCreater());
			List<UserEntity> uList = UserManageController.getInstance().query(ue);
			if( null == uList || uList.isEmpty() || uList.size() != 1 ){
				entity.setMsgCode( FactoryEntity.IOCODE_PARAMERROR);
				return ;
			}else{
				
				ue = uList.get(0);
				ue.setUeFactoryCode(entity.getFeCode());
				UserManageController.getInstance().modify(ue);
				if( "success".equals(ue.getMsgMap().get("state"))){
					entity.setMsgCode(msg);
				}else{
					// 用户信息修改失败的情况， 删除新建的工厂信息
					fileFactory.fileOption(factoryList, FileOptUtil.FILE_READ,  FactoryEntity.class);
					Iterator< FactoryEntity> iter = factoryList.iterator();
					while(iter.hasNext()){
						FactoryEntity tfe = iter.next();
						if( tfe.getFeId().equals(entity.getFeId())){
							iter.remove();
							break;
						}
					}
					fileFactory.fileOption(factoryList, FileOptUtil.FILE_WRITE,  FactoryEntity.class);
					entity.getMsgMap().put("state" , ue.getMsgMap().get("state"));
					entity.getMsgMap().put("msg" , ue.getMsgMap().get("msg"));
				}
				
			}
			
		}else{
			entity.setMsgCode(msg);
		}
    
		
		
    }
    
    /**
     * 修改工厂信息
     * @param entity
     */
    public void modify( FactoryEntity entity ){
    	
    	if( null == entity || null == entity.getFeId() || "".equals( entity.getFeId())){
    		entity = new FactoryEntity();
    		entity.setMsgCode( FactoryEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	List<FactoryEntity> factoryList = new ArrayList<FactoryEntity>();
		FileOptUtil<FactoryEntity> fileFactory= new FileOptUtil<FactoryEntity>();
		fileFactory.fileOption(factoryList, FileOptUtil.FILE_READ,  FactoryEntity.class);
		
		FactoryEntity dbFactory = null;
		for(FactoryEntity fe : factoryList){
			if( fe.getFeId().equals(entity.getFeId())){
				dbFactory = fe;
				break;
			}
		}
		
		if( null == dbFactory){
			entity.setMsgCode( FactoryEntity.IOCODE_PARAMERROR);
			return ;
		}
		
		boolean saveFlag = false;
		if( null != entity.getFeName() && !"".equals(entity.getFeName() ) && ! entity.getFeName().equals(dbFactory.getFeName())){
			dbFactory.setFeName( entity.getFeName());
			saveFlag = true;
		}
		
		if( null != entity.getFeContacts() && !"".equals( entity.getFeContacts() ) && ! entity.getFeContacts().equals(dbFactory.getFeContacts())){
			dbFactory.setFeContacts( entity.getFeContacts());
			saveFlag = true;
		}
		
		if( null != entity.getFeContactsTel() && !"".equals( entity.getFeContactsTel() ) && ! entity.getFeContactsTel().equals(dbFactory.getFeContactsTel())){
			dbFactory.setFeContactsTel( entity.getFeContactsTel());
			saveFlag = true;
		}
		
		if( null != entity.getFeTel() && !"".equals( entity.getFeTel() ) && ! entity.getFeTel().equals(dbFactory.getFeTel())){
			dbFactory.setFeTel( entity.getFeTel());
			saveFlag = true;
		}
		
		if( null != entity.getFeAddress() && !"".equals( entity.getFeAddress() ) && ! entity.getFeAddress().equals(dbFactory.getFeAddress())){
			dbFactory.setFeAddress( entity.getFeAddress());
			saveFlag = true;
		}
		
		if( (null == entity.getFeRemark() && null != dbFactory.getFeRemark() )||
				( null != entity.getFeRemark() &&! entity.getFeRemark().equals(dbFactory.getFeRemark()))){
			dbFactory.setFeRemark( entity.getFeRemark());
			saveFlag = true;
		}

		if(saveFlag){
			String msg = fileFactory.fileOption(factoryList, FileOptUtil.FILE_WRITE,  FactoryEntity.class);
			entity.setMsgCode( msg);
		}else{
			entity.setMsgCode( FactoryEntity.IOCODE_NOUPDATE);
		}
    	
    }
    
    /**
     * 批量删除工厂信息
     * @param fList
     * @return
     */
    public Map<String, String> remove( List<FactoryEntity> fList){
    	
    	FactoryEntity entity = new FactoryEntity();
    	if( null == fList || fList.isEmpty()  ){
    		entity.setMsgCode(FactoryEntity.IOCODE_PARAMERROR);
    		return entity.getMsgMap();
    	}
    	
     	List<FactoryEntity> factoryList = new ArrayList<FactoryEntity>();
		FileOptUtil<FactoryEntity> fileFactory= new FileOptUtil<FactoryEntity>();
		fileFactory.fileOption(factoryList, FileOptUtil.FILE_READ,  FactoryEntity.class);
		
		for(FactoryEntity rFe : fList){
			Iterator< FactoryEntity> iter = factoryList.iterator();
			while( iter.hasNext()){
				FactoryEntity fe = iter.next();
				if( rFe.getFeId().equals(fe.getFeId())){
					iter.remove();
					break;
				}
			}
		}
		
		String msg = fileFactory.fileOption(factoryList, FileOptUtil.FILE_WRITE ,  FactoryEntity.class);
		entity.setMsgCode(msg);
    	return entity.getMsgMap();
    }
    
    
    /**
     *  工厂信息查询 ： 支持空查询 ，  主键， 编码， 名称， 状态， 联系人， 联系人电话 查询项
     * @param query
     * @return
     */
    public List<FactoryEntity> query(FactoryEntity query){
    	
    	List<FactoryEntity> factoryList = new ArrayList<FactoryEntity>();
		FileOptUtil<FactoryEntity> fileFactory= new FileOptUtil<FactoryEntity>();
		fileFactory.fileOption(factoryList, FileOptUtil.FILE_READ,  FactoryEntity.class);
    	
		List<FactoryEntity> fList = new ArrayList<FactoryEntity>();
		if( null != query){
			
			// 主键 ， 
			if( null != query.getFeId() && !"".equals(query.getFeId() )){
				for(FactoryEntity fe : factoryList){
					if( fe.getFeId().equals(query.getFeId())){
						fList.add(fe);
						break;
					}
				}
			}
			//编码， 
			if( null != query.getFeCode() && !"".equals(query.getFeCode() )){
				for( FactoryEntity fe: factoryList){
					if( fe.getFeCode().equals(query.getFeCode())){
						fList.add(fe);
						break;
					}
				}
			}
			//名称  ，
			if( null != query.getFeName() && !"".equals(query.getFeName() )){
				for(FactoryEntity fe: factoryList){
					if( fe.getFeName().indexOf( query.getFeName()) != 0){
						fList.add(fe);
					}
				}
			}
			// 状态 ，
			if( null != query.getFeState() && !"".equals(query.getFeState() )){
				for( FactoryEntity fe: factoryList){
					if( fe.getFeState().equals(query.getFeState())){
						fList.add(fe);
					}
				}
			}
			//联系人
			if( null != query.getFeContacts() && !"".equals(query.getFeContacts() )){
				for( FactoryEntity fe: factoryList){
					if( fe.getFeContacts().equals(query.getFeContacts())){
						fList.add(fe);
					}
				}
			}
			//联系电话
			if( null != query.getFeContactsTel() && !"".equals(query.getFeContactsTel() )){
				for( FactoryEntity fe: factoryList){
					if( fe.getFeContactsTel().equals(query.getFeContactsTel())){
						fList.add(fe);
					}
				}
			}
			
		}else{
			fList.addAll( factoryList);
		}
		
		//数据拼装
		
		List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		
		DataDictionaryMainEntity ddme = new DataDictionaryMainEntity();
		ddme.setDdmeKind( DataDictionaryMainEntity.DD_FACTORY_STATE);
		List<DataDictionaryMainEntity> ddList = DataDictionaryManageController.getInstance().queryMain(ddme);
		
		List<DataDictionarySubEntity> subList = null;
		if( null != ddList && ddList.isEmpty() && ddList.size() == 1 ){
			subList = ddList.get(0).getSubList();
		}
		
		for( FactoryEntity fe : fList){
			
			//加载工厂状态数据字典
			if( null != subList){
				for(DataDictionarySubEntity sub : subList){
					if( sub.getDdseCode().equals( fe.getFeState() )){
						fe.setState( sub);
					}
				}
			}
			if( null != userList){
				// 加载创建者信息

				for(UserEntity ue : userList){
					if( fe.getFeCreater().equals( ue.getUeCode())){
						fe.setCreater(ue);
						break;
					}
				}
				// 加载修改者信息
				for( UserEntity ue : userList){
					if( fe.getFeUpdater().equals(ue.getUeCode())){
						fe.setUpdater(ue);
						break;
					}
				}
			}
		}
    	return fList;
    }
    
    
    
}
