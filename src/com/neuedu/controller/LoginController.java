package com.neuedu.controller;

import java.util.ArrayList;
import java.util.List;

import com.neuedu.model.UserEntity;
import com.neuedu.util.FileOptUtil;
import com.neuedu.util.MD5Util;


/**
 * 登陆
 * @author koala
 *
 */
public class LoginController {

	/**
	 * 单例模式
	 */
	private LoginController() {}
    private static class LoginControllerInstance {
        private static final LoginController INSTANCE = new LoginController();
    }
    public static LoginController getInstance() {
        return LoginControllerInstance.INSTANCE;
    }
    
    private UserEntity loginUser ; 
    public UserEntity getLoginUser() {
		return loginUser;
	}

	/**
     * 登陆验证
     * @param user
     * @return
     */
    public void doLogin(UserEntity user){
    	
    	if( null == user ){
    		user = new UserEntity();
    		user.setMsgCode( UserEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		
		UserEntity tarUser = null; 
		
		for( UserEntity ue : userList){
			if(  ue.getUeAccount().equals(user.getUeAccount())){
				tarUser = ue;
				break;
			}
		}
		
		if( null == tarUser ){
			for( UserEntity ue : userList){
				if(  ue.getUeEmail().equals(user.getUeAccount())){
					tarUser = ue;
					break;
				}
			}
		}
		
		if( null == tarUser){
			user.setMsgCode(UserEntity.IOCODE_LOGIN_NOACCOUNT);
			return ;
		}
    	
		if( MD5Util.validatePassword( tarUser.getUePwd(), user.getUePwd())){
			loginUser = tarUser;
			user.setMsgCode(UserEntity.IOCODE_SUCCESS);
		}else{
			user.setMsgCode( UserEntity.IOCODE_VALIPWD_ERROR);
		}
    }
    
    /**
     * 注销
     */
    public void doLogout(){
    	this.loginUser = null;
    }
    
    /**
     * 重新加载当前登陆用户信息
     */
    public void reloadLoginUser(){
    	List<UserEntity> list = UserManageController.getInstance().query(loginUser);
    	if( null == list || list.isEmpty()){
    		System.out.println("reloadLoginUser  未查询到当前登陆用户 ");
    		return;
    	}
    	this.loginUser =  list.get(0);
    }
    
    /**
     * 用户注册
     */
    public void registUser( UserEntity regist){
    	
    	UserManageController.getInstance().create(regist);
    	if( "success".equals(regist.getMsgMap().get("state"))){
    		
    	}else{
    		
    	}
    	
    }
    
}
