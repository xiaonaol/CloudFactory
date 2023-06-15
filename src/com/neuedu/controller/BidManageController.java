package com.neuedu.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.neuedu.model.BidEntity;
import com.neuedu.model.OrderEntity;
import com.neuedu.model.PlanEntity;
import com.neuedu.model.UserEntity;
import com.neuedu.util.CommonUtil;
import com.neuedu.util.FileOptUtil;
import com.neuedu.view.UserManage;

/**
 * 竞标管理
 * @author koala
 *
 */
public class BidManageController {

	private BidManageController() {}
    private static class BidManageControllerInstance {
        private static final BidManageController INSTANCE = new BidManageController();
    }
    public static BidManageController getInstance() {
        return BidManageControllerInstance.INSTANCE;
    }
    
    public void create( BidEntity entity){
    	
    	if( null == entity){
    		entity = new BidEntity();
    		entity.setMsgCode( BidEntity.IOCODE_PARAMERROR);
    		return;
    	}
    	
    	List<BidEntity> bidList = new ArrayList<BidEntity>();
     	FileOptUtil<BidEntity> fileBid= new FileOptUtil<BidEntity>();
     	fileBid.fileOption(bidList, FileOptUtil.FILE_READ,  BidEntity.class);
     	
     	int maxId = 1; 
     	for( BidEntity bid : bidList){
     		if( maxId <= Integer.parseInt( bid.getBeId() )){
     			maxId = Integer.parseInt(bid.getBeId())+1;
     		}
     	}
     	UserEntity loginUser = LoginController.getInstance().getLoginUser();
     	
     	entity.setBeId(String.valueOf(maxId));
     	entity.setBeCode(CommonUtil.codeGenerator());
    	entity.setBeState("0");
    	entity.setBeCreaterCode(loginUser.getUeCode());
    	entity.setBeCreate(new Date());
    	entity.setOrder(null);
    	entity.setBidUser(null);
    	
    	bidList.add(entity);
    	String msg = fileBid.fileOption(bidList, FileOptUtil.FILE_WRITE,  BidEntity.class);
    	entity.setMsgCode(msg);
    	
    }
    
    /**
     * 修改竞标状态
     * @param modifyList
     * @param code
     */
    public Map<String , String > modify( List<BidEntity> modifyList , String code){
    	
    	List<BidEntity> bidList = new ArrayList<BidEntity>();
     	FileOptUtil<BidEntity> fileBid= new FileOptUtil<BidEntity>();
     	fileBid.fileOption(bidList, FileOptUtil.FILE_READ,  BidEntity.class);
     	
     	for( BidEntity dbBe : bidList){
     		for( BidEntity mBe : modifyList ){
     			if( dbBe.getBeCode().equals( mBe.getBeCode() )){
     				dbBe.setBeState(code);
     			}
     		}
     	}
    	
     	String msg = fileBid.fileOption(bidList, FileOptUtil.FILE_WRITE,  BidEntity.class);
     	BidEntity rBe  = new BidEntity();
     	rBe.setMsgCode(msg);
     	return rBe.getMsgMap();
    }
    
    /**
     * 用于检查是否
     * @param query
     * @return
     */
    public Map<String ,String > submitCheck( OrderEntity order){
    	
    	Map<String ,String > rMap = new HashMap<String , String>();
     	if( null == order){
     		rMap.put("state", "fail");
     		rMap.put("msg", "参数错误");
     		return rMap;
     	}
     	
     	UserEntity loginUser = LoginController.getInstance().getLoginUser();
    	
    	List<OrderEntity> orderList = OrderManageController.getInstance().query(null);
     	
    	List<UserEntity> userList = UserManageController.getInstance().query(null);
     	
     	OrderEntity oe = null;
     	List<BidEntity> bList = null;
     	
     	for( OrderEntity dbOe : orderList ){
     		if( dbOe.getOeCode().equals(order.getOeCode())){
     			oe = dbOe;
     		}
     	}
     	
     	if( null == oe){
     		rMap.put("state", "fail");
     		rMap.put("msg", "未查询到对应订单");
     		return rMap;
     	}
     	
     	bList = oe.getBidList();
     	if( null == bList || bList.isEmpty()){
     		rMap.put("state", "success");
     		return rMap;
     	}
     	
     	List<UserEntity> factoryUserList = new ArrayList<UserEntity>();
     	for( UserEntity ue : userList){
     		if( loginUser.getUeFactoryCode().equals(ue.getUeFactoryCode())){
     			factoryUserList.add(ue);
     		}
     	}

     	for( BidEntity bid :bList){
     		for( UserEntity ue : factoryUserList){
     			if( bid.getBeCreaterCode().equals(ue.getUeCode())){
     				rMap.put("state", "fail");
     	     		rMap.put("msg", "该订单已投标");
     	     		return rMap;
     			}
     		}
     	}
     	rMap.put("state", "success");
    	return rMap;
    }
    
    public List<BidEntity> query( OrderEntity order){
    	List<OrderEntity> orderList = OrderManageController.getInstance().query(order);
    	if( null == orderList || orderList.isEmpty() || orderList.size() != 1 ){
    		return null ;
    	}
    	order = orderList.get(0);
    	
    	List<UserEntity> userList = UserManageController.getInstance().query(null);
    	
    	List<BidEntity> bidList = order.getBidList();
    	for( BidEntity bid : bidList){
    		bid.setOrder(order);
    		for( UserEntity ue : userList){
    			if( ue.getUeCode().equals(bid.getBeCreaterCode())){
    				bid.setBidUser(ue);
    			}
    		}
    	}
    	return bidList;
    }
    
    /**
     * 编辑生产计划时 ， 用于加载 中标记录下拉框内容
     * @return
     */
    public List<BidEntity> queryWinBid(){
    	
    	List<UserEntity> userList = UserManageController.getInstance().query(null);
    	
    	UserEntity loginUser = LoginController.getInstance().getLoginUser();
    	
    	List<UserEntity> removeUserList = new ArrayList<UserEntity>();
    	for(  UserEntity ue :userList){
    		if( !loginUser.getUeFactoryCode().equals(ue.getUeFactoryCode())){
    			removeUserList.add(ue);
    		}
    	}
    	userList.removeAll(removeUserList);
    	
    	List<BidEntity> bidList = new ArrayList<BidEntity>();
     	FileOptUtil<BidEntity> fileBid= new FileOptUtil<BidEntity>();
     	fileBid.fileOption(bidList, FileOptUtil.FILE_READ,  BidEntity.class);
     	
 		Iterator<BidEntity> iter = bidList.iterator();
 		while( iter.hasNext()){
 			BidEntity bid = iter.next();
 			boolean flag = true;
 		   	for( UserEntity ue : userList){
	 			if( ue.getUeCode().equals(bid.getBeCreaterCode())){
	 				flag = false;
	 			}
     		}
 		   	if( flag){
 		   		iter.remove();
 		   	}
     	}
 		
 		List<PlanEntity> planList = new ArrayList<PlanEntity>();
     	FileOptUtil<PlanEntity> filePlan= new FileOptUtil<PlanEntity>();
     	filePlan.fileOption(planList, FileOptUtil.FILE_READ,  PlanEntity.class);
 		List<PlanEntity > removePlanList = new ArrayList<PlanEntity>();
     	
     	for( PlanEntity pe : planList){
     		for( UserEntity ue :userList){
     			if( ue.getUeCode().equals(pe.getPeCreater())){
     				if( "0".equals(pe.getPeState())){
     					removePlanList.add(pe);
         				break;
     				}
     			}
     		}
     	}
     	planList.removeAll( removePlanList);
 		
    	List<OrderEntity> orderList = new ArrayList<OrderEntity>();
     	FileOptUtil<OrderEntity> fileOrder= new FileOptUtil<OrderEntity>();
     	fileOrder.fileOption(orderList, FileOptUtil.FILE_READ,  OrderEntity.class);
     	
     	for( BidEntity bid : bidList){
     		for( OrderEntity order : orderList){
     			if( bid.getBeOrderCode().equals(order.getOeCode())){
     				bid.setOrder(order);
     			}
     		}
     	}
 		
 		return bidList;
    }
}
