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
import com.neuedu.util.MD5Util;

/**
 * 用户管理
 * @author koala
 * create 新建用户
 * modify  修改用户
 *remove 批量删除用户
 *userIDCardNoCheck 身份证编号唯一性校验
 *userTelCheck电话号码唯一性校验
 *userEmailCheck 电子邮件唯一性校验
 *query 用户信息查询
 *resetDefaultPwd 重置默认密码
 *resetPwd 修改密码
 */
public class UserManageController {

	/**
	 * 单例模式
	 */
	private UserManageController() {}
    private static class UserManageControllerInstance {
        private static final UserManageController INSTANCE = new UserManageController();
    }
    public static UserManageController getInstance() {
        return UserManageControllerInstance.INSTANCE;
    }
    
    /**
     * 新建用户
     * @param entity
     */
    public void create(UserEntity entity){
    	
    	if( null == entity ){
    		entity = new UserEntity();
    		entity.setMsgCode(UserEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		
		int maxId = 1; 
		for( UserEntity ue : userList){
			if( maxId <= Integer.parseInt(ue.getUeId() )){
				maxId = Integer.parseInt(ue.getUeId()) +1; 
			}
		}
		
		entity.setUeId(String.valueOf( maxId));
		entity.setUeCode( CommonUtil.codeGenerator() );
		Date d = new Date();
		entity.setUeCreate(d);
		entity.setUeUpdate( d);
		entity.setUeCreater( null != LoginController.getInstance().getLoginUser() ? LoginController.getInstance().getLoginUser().getUeCode() : entity.getUeCode());
		entity.setUeUpdater( null != LoginController.getInstance().getLoginUser() ? LoginController.getInstance().getLoginUser().getUeCode() : entity.getUeCode());
		if( null == entity.getUePwd() || "".equals(entity.getUePwd())){
			entity.setUePwd(  MD5Util.generatePassword("123456"));
		}else{
			entity.setUePwd( MD5Util.generatePassword(entity.getUePwd()));
		}
		userList.add(entity);
		
		String msg = fileUser.fileOption(userList, FileOptUtil.FILE_WRITE,  UserEntity.class);
		entity.setMsgCode(msg);
    }
    
    /**
     * 修改用户信息
     * @param entity
     */
    public void modify(UserEntity  entity ){
    	if( null == entity ){
    		entity = new UserEntity();
    		entity.setMsgCode(UserEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		UserEntity dbUser = null;
    	for( UserEntity ue : userList ){
    		if( ue.getUeId().equals(entity.getUeId() )){
    			dbUser = ue;
    			break;
    		}
    	}
    	
    	// 姓名
    	boolean saveFlag = false;
    	if( null != entity.getUeName() && !"".equals(entity.getUeName()) && !entity.getUeName().equals(dbUser.getUeName())){
    		dbUser.setUeName( entity.getUeName());
    		saveFlag = true;
    	}
    	
    	if( null != entity.getUeBirthday() && entity.getUeBirthday().compareTo(dbUser.getUeBirthday()) != 0){
    		dbUser.setUeBirthday( entity.getUeBirthday());
    		saveFlag = true;
    	}
    	
    	if( null != entity.getUeIdCardNo() && !"".equals(entity.getUeIdCardNo()) && !entity.getUeIdCardNo().equals(dbUser.getUeIdCardNo())){
    		dbUser.setUeIdCardNo( entity.getUeIdCardNo());
    		saveFlag = true;
    	}
    	
    	if( null != entity.getUeGender() && !"".equals(entity.getUeGender()) && !entity.getUeGender().equals(dbUser.getUeGender())){
    		dbUser.setUeGender( entity.getUeGender());
    		saveFlag = true;
    	}
    	
     	if( null != entity.getUeRole() && !"".equals(entity.getUeRole()) && !entity.getUeRole().equals(dbUser.getUeRole())){
    		dbUser.setUeRole( entity.getUeRole());
    		saveFlag = true;
    	}
     	
     	if( null != entity.getUeEmail() && !"".equals(entity.getUeEmail()) && !entity.getUeEmail().equals(dbUser.getUeEmail())){
    		dbUser.setUeEmail( entity.getUeEmail());
    		saveFlag = true;
    	}
     	
    	if( null != entity.getUeTel() && !"".equals(entity.getUeTel()) && !entity.getUeTel().equals(dbUser.getUeTel())){
    		dbUser.setUeTel( entity.getUeTel());
    		saveFlag = true;
    	}
     	
    	if( null != entity.getUeAddress() && !"".equals(entity.getUeAddress()) && !entity.getUeAddress().equals(dbUser.getUeAddress())){
    		dbUser.setUeAddress( entity.getUeAddress());
    		saveFlag = true;
    	}
    	
    	if( null != entity.getUeFactoryCode() && !"".equals(entity.getUeFactoryCode()) && !entity.getUeFactoryCode().equals(dbUser.getUeFactoryCode())){
    		dbUser.setUeFactoryCode( entity.getUeFactoryCode());
    		saveFlag = true;
    	}
    	
      	if( null != entity.getUeState() && !"".equals(entity.getUeState()) && !entity.getUeState().equals(dbUser.getUeState())){
    		dbUser.setUeState( entity.getUeState());
    		saveFlag = true;
    	}
      	
      	if( (null == entity.getUeRemark() && null !=  dbUser.getUeRemark()) || 
      			null != entity.getUeRemark() && !entity.getUeRemark().equals(dbUser.getUeRemark())){
      		dbUser.setUeRemark( entity.getUeRemark());
    		saveFlag = true;
      	}
      	
      	if(saveFlag){
      		dbUser.setUeUpdate(new Date());
      		dbUser.setUeUpdater( null != LoginController.getInstance().getLoginUser() ? LoginController.getInstance().getLoginUser().getUeCode() : dbUser.getUeCode());
      		String msg = fileUser.fileOption(userList, FileOptUtil.FILE_WRITE,  UserEntity.class);
      		entity.setMsgCode(msg);
      	}else{
      		entity.setMsgCode( UserEntity.IOCODE_NOUPDATE);
      	}
    }
    
    /**
     * 批量删除用户
     * @param removeList
     * @return
     */
    public Map<String , String >  remove(List<UserEntity> removeList){
    
    	UserEntity entity = new UserEntity();
    	if( null == removeList || removeList.isEmpty()){
    		entity.setMsgCode( UserEntity.IOCODE_PARAMERROR);
    		return  entity.getMsgMap();
    	}
    	
    	List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		
		// 需要考虑  如果是工厂所有者 ， 同时删除所属工厂以及 ， 工厂内员工 
    	
		Iterator<UserEntity> iter  = userList.iterator();
		while( iter.hasNext() ){
			UserEntity tue = iter.next() ;
			for( UserEntity ue : removeList ){
				if( ue.getUeId().equals( tue.getUeId() )){
					iter.remove();
					break;
				}
			}
		}
		String msg = fileUser.fileOption(userList, FileOptUtil.FILE_WRITE,  UserEntity.class);
		entity.setMsgCode( msg );
    	return entity.getMsgMap();
    }
    
    /**
     * 身份证号码唯一性校验
     * @param idCardNo
     * @return
     */
    public boolean userIDCardNoCheck(String idCardNo){
    	if( null == idCardNo || "".equals(idCardNo)){
    		return false;
    	}
    	
    	List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		
		for( UserEntity ue : userList ){
			if( idCardNo.equals(ue.getUeIdCardNo())){
				return false;
			}
		}
    	return true;
    }
    
    /**
     * 电话号码唯一性校验
     * @param tel
     * @return
     */
    public boolean userTelCheck(String tel){
    	if( null == tel || "".equals(tel)){
    		return false;
    	}
    	
    	List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		
		for( UserEntity ue : userList ){
			if( tel.equals(ue.getUeTel())){
				return false;
			}
		}
    	return true;
    }
    
    /**
     * 邮箱唯一性校验
     * @param eMail
     * @return
     */
    public boolean userEmailCheck(String eMail){
    	if( null == eMail || "".equals(eMail)){
    		return false;
    	}
    	
    	List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		
		for( UserEntity ue : userList ){
			if( eMail.equals(ue.getUeEmail())){
				return false;
			}
		}
    	return true;
    }
    
    /**
     * 账号唯一性检查
     * @param eMail
     * @return
     */
    public boolean userAccountCheck(String account){
    	if( null == account || "".equals(account)){
    		return false;
    	}
    	
    	List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		
		for( UserEntity ue : userList ){
			if( account.equals(ue.getUeAccount())){
				return false;
			}
		}
    	return true;
    }
    
    /**
     * 用户信息查询
     * @param query
     * @return
     */
    public List<UserEntity> query( UserEntity query){
    	
    	List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		
		if( null != query){
			// 查询项 ：  姓名， ， 工厂code ， 角色  ， 账号状态
			//主键 ， 编号，身份证号， 手机号， email 
			if( null != query.getUeId() && !"".equals(query.getUeId()) ){
				Iterator<UserEntity> iter = userList.iterator();
				while(iter.hasNext()){
					UserEntity ue = iter.next();
					if(! query.getUeId().equals(ue.getUeId())){
						iter.remove();
					}
				}
			}else if( null != query.getUeCode() && !"".equals(query.getUeCode())){
				Iterator<UserEntity> iter = userList.iterator();
				while(iter.hasNext()){
					UserEntity ue = iter.next();
					if( !query.getUeCode().equals(ue.getUeCode())){
						iter.remove();
					}
				}
			}else if( null != query.getUeIdCardNo() && !"".equals( query.getUeIdCardNo())){
				Iterator<UserEntity> iter = userList.iterator();
				while(iter.hasNext()){
					UserEntity ue = iter.next();
					if( !query.getUeIdCardNo().equals(ue.getUeIdCardNo())){
						iter.remove();
					}
				}
			}else if( null != query.getUeTel() && !"".equals(query.getUeTel())){
				Iterator<UserEntity> iter = userList.iterator();
				while(iter.hasNext()){
					UserEntity ue = iter.next();
					if(! query.getUeTel().equals(ue.getUeTel())){
						iter.remove();
					}
				}
			}else if( null != query.getUeEmail() && !"".equals( query.getUeEmail())){
				Iterator<UserEntity> iter = userList.iterator();
				while(iter.hasNext()){
					UserEntity ue = iter.next();
					if( !query.getUeEmail().equals(ue.getUeEmail())){
						iter.remove();
					}
				}
			}else{
				//  姓名， ， 工厂code ， 角色  ， 账号状态
				if( null != query.getUeName() && !"".equals(query.getUeName())){
					Iterator<UserEntity> iter = userList.iterator();
					while(iter.hasNext()){
						UserEntity ue = iter.next();
						if( ue.getUeName().indexOf(query.getUeName()) == -1){
							iter.remove();
						}
					}
				}
				
				if( null != query.getUeFactoryCode() && !"".equals( query.getUeFactoryCode() )){
					Iterator<UserEntity> iter = userList.iterator();
					while(iter.hasNext()){
						UserEntity ue = iter.next();
						if(! query.getUeFactoryCode().equals(ue.getUeFactoryCode())){
							iter.remove();
						}
					}
				}
				
				if( null != query.getUeRole() && !"".equals(query.getUeRole())){
					
					Iterator<UserEntity> iter = userList.iterator();
					while(iter.hasNext()){
						UserEntity ue = iter.next();
						if( !query.getUeRole().equals(ue.getUeRole())){
							iter.remove();
						}
					}
				}
				
				if( null != query.getUeState() && !"".equals(query.getUeState())){
					Iterator<UserEntity> iter = userList.iterator();
					while(iter.hasNext()){
						UserEntity ue = iter.next();
						if( !query.getUeState().equals(ue.getUeState())){
							iter.remove();
						}
					}
				}
			}
			
			if( null == userList || userList.isEmpty() ){
				return userList;
			}
		}
		
    	List<FactoryEntity> factoryList = new ArrayList<FactoryEntity>();
		FileOptUtil<FactoryEntity> fileFactory= new FileOptUtil<FactoryEntity>();
		fileFactory.fileOption(factoryList, FileOptUtil.FILE_READ,  FactoryEntity.class);
		
		for( UserEntity user : userList){
			// 创建者填充
			for( UserEntity ue : userList){
				if( user.getUeCreater().equals(ue.getUeCode() )){
					user.setCreater(ue);
					break;
				}
			}
			// 更新者填充
			for( UserEntity ue : userList ){
				if( user.getUeUpdater().equals(ue.getUeCode())){
					user.setUpdater(ue);
					break;
				}
			}
			
			// 工厂填充
			for(FactoryEntity fe : factoryList){
				if( fe.getFeCode().equals(user.getUeFactoryCode())){
					user.setFactory(fe);
					break;
				}
			}
			DataDictionaryManageController.getInstance();
			Map<String , DataDictionaryMainEntity> dd = DataDictionaryManageController.getDd();
			// 性别填充
			for( DataDictionarySubEntity sub : dd.get(DataDictionaryMainEntity.DD_USER_GENDER).getSubList()){
				if(sub.getDdseValue().equals(user.getUeGender())){
					user.setGender(sub);
				}
			}
			// 状态填充
			for( DataDictionarySubEntity sub : dd.get(DataDictionaryMainEntity.DD_USER_STATE).getSubList()){
				if( sub.getDdseValue().equals(user.getUeState())){
					user.setState(sub);
				}
			}
			
			// 角色填充
			for( DataDictionarySubEntity sub : dd.get(DataDictionaryMainEntity.DD_USER_ROLE).getSubList()){
				if(sub.getDdseValue().equals( user.getUeRole())){
					user.setRole(sub);
					break;
				}
			}
		}
    	return userList;
    }
    
    /**
     * 重置用户密码为默认密码
     * @param entity
     */
    public void resetDefaultPwd(UserEntity entity){
    	if( null == entity ){
    		entity = new UserEntity();
    		entity.setMsgCode(UserEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		UserEntity dbUser = null;
    	for( UserEntity ue : userList ){
    		if( ue.getUeId().equals(entity.getUeId() )){
    			dbUser = ue;
    			break;
    		}
    	}
    	
    	// 未查询到对应的数据
    	if( null == dbUser ){
    		entity.setMsgCode(UserEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	dbUser.setUePwd( MD5Util.generatePassword( "123456"));
  		dbUser.setUeUpdate( new Date());
		dbUser.setUeUpdater( LoginController.getInstance().getLoginUser().getUeCode() );
		String msg = fileUser.fileOption(userList, FileOptUtil.FILE_WRITE,  UserEntity.class);
		entity.setMsgCode(msg);
    	
    }
    
    /**
     *  修改用户密码
     * @param entity
     */
    public void resetPwd (UserEntity entity ){
    	
    	if( null == entity ){
    		entity = new UserEntity();
    		entity.setMsgCode(UserEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		UserEntity dbUser = null;
    	for( UserEntity ue : userList ){
    		if( ue.getUeId().equals(entity.getUeId() )){
    			dbUser = ue;
    			break;
    		}
    	}
    	
    	// 未查询到对应的数据
    	if( null == dbUser ){
    		entity.setMsgCode(UserEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	if( MD5Util.validatePassword( dbUser.getUePwd(), entity.getUePwd())){
    		// 原始密码校验正确
    		dbUser.setUePwd( MD5Util.generatePassword( entity.getNewPwd() ));
    		dbUser.setUeUpdate( new Date());
    		dbUser.setUeUpdater( LoginController.getInstance().getLoginUser().getUeCode() );
			String msg = fileUser.fileOption(userList, FileOptUtil.FILE_WRITE,  UserEntity.class);
			entity.setMsgCode(msg);
		}else{
			// 原始密码校验错误
			entity.setMsgCode(UserEntity.IOCODE_VALIPWD_ERROR);
		}
    }
    
}
