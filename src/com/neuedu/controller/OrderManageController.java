package com.neuedu.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.neuedu.model.BidEntity;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.DataDictionarySubEntity;
import com.neuedu.model.OrderEntity;
import com.neuedu.model.ProductEntity;
import com.neuedu.model.UserEntity;
import com.neuedu.util.CommonUtil;
import com.neuedu.util.FileOptUtil;

/**
 * 订单管理
 * @author koala
 *
 */
public class OrderManageController {

	private OrderManageController() {}
    private static class OrderManageControllerInstance {
        private static final OrderManageController INSTANCE = new OrderManageController();
    }
    public static OrderManageController getInstance() {
        return OrderManageControllerInstance.INSTANCE;
    }
    
    /**
     * 创建订单
     * @param entity
     */
    public void create(OrderEntity entity ){
    	// 参数错误  返回
    	if( null == entity ){
    		entity = new OrderEntity();
    		entity.setMsgCode( OrderEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	// 无登陆状态  返回
    	UserEntity loginUser = LoginController.getInstance().getLoginUser();
    	if( null == loginUser ){
    		entity.setMsgCode( OrderEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	List<OrderEntity> orderList = new ArrayList<OrderEntity>();
     	FileOptUtil<OrderEntity> fileOrder= new FileOptUtil<OrderEntity>();
     	fileOrder.fileOption(orderList, FileOptUtil.FILE_READ,  OrderEntity.class);
     	
     	int maxId = 1 ;
     	for( OrderEntity oe : orderList){
     		if( maxId <= Integer.parseInt( oe.getOeId() )){
     			maxId = Integer.parseInt(oe.getOeId()) +1;
     		}
     	}

     	entity.setOeId(String.valueOf(maxId));
     	entity.setOeCode( CommonUtil.codeGenerator());
     	entity.setOeState("1");
     	Date d = new Date();
     	entity.setOeCreate( d);
     	entity.setOeCreater( loginUser.getUeCode());
     	entity.setOeUpdate( d);
     	entity.setOeUpdater( loginUser.getUeCode());
     	
     	entity.setProduct(null);
     	entity.setCreater(null);
     	entity.setUpdater(null);
     	entity.setState( null);
     	entity.setBidList(null);
     	orderList.add(entity);
    	String msg = fileOrder.fileOption(orderList, FileOptUtil.FILE_WRITE,  OrderEntity.class);
    	entity.setMsgCode(msg);
    }
    
    /**
     * 修改
     * @param entity
     */
    public void modify(OrderEntity entity ){
    	// 参数错误  返回
    	if( null == entity ){
    		entity = new OrderEntity();
    		entity.setMsgCode( OrderEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	// 无登陆状态  返回
    	UserEntity loginUser = LoginController.getInstance().getLoginUser();
    	if( null == loginUser ){
    		entity.setMsgCode( OrderEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
      	List<OrderEntity> orderList = new ArrayList<OrderEntity>();
     	FileOptUtil<OrderEntity> fileOrder= new FileOptUtil<OrderEntity>();
     	fileOrder.fileOption(orderList, FileOptUtil.FILE_READ,  OrderEntity.class);
     	
     	OrderEntity dbOe = null;
     	for( OrderEntity oe : orderList){
     		if( oe.getOeId().equals( entity.getOeId())){
     			dbOe = oe;
     		}
     	}
     	
     	// 未找到要求改的数据   返回
     	if( null == dbOe ){
     		entity.setMsgCode( OrderEntity.IOCODE_PARAMERROR);
    		return ;
     	}
     	
     	boolean saveFlag = true;
     	if( null != entity.getOeName() && !"".equals(entity.getOeName()) && !entity.getOeName().equals(dbOe.getOeName())){
     		dbOe.setOeName( entity.getOeName());
     		saveFlag = true;
     	}
     	
    	if( null != entity.getOeState() && !"".equals(entity.getOeState()) && !entity.getOeState().equals(dbOe.getOeState())){
     		dbOe.setOeState( entity.getOeState());
     		saveFlag = true;
     	}
     	
    	if( null != entity.getOeDliverDate() && entity.getOeDliverDate().compareTo(dbOe.getOeDliverDate()) != 0 ){
     		dbOe.setOeDliverDate( entity.getOeDliverDate());
     		saveFlag = true;
     	}
    	
    	if( null != entity.getOeCutOffDate() && entity.getOeCutOffDate().compareTo(dbOe.getOeCutOffDate()) != 0 ){
     		dbOe.setOeCutOffDate( entity.getOeCutOffDate());
     		saveFlag = true;
     	}
     	
    	if( null != entity.getOeReceiver() && !"".equals(entity.getOeReceiver()) && !entity.getOeReceiver().equals(dbOe.getOeReceiver())){
     		dbOe.setOeReceiver( entity.getOeReceiver());
     		saveFlag = true;
     	}
    	
    	if( null != entity.getOeReceiverTel() && !"".equals(entity.getOeReceiverTel()) && !entity.getOeReceiverTel().equals(dbOe.getOeReceiverTel())){
     		dbOe.setOeReceiverTel( entity.getOeReceiverTel());
     		saveFlag = true;
     	}
    	
    	if( null != entity.getOeReceiverAddress() && !"".equals(entity.getOeReceiverAddress()) && !entity.getOeReceiverAddress().equals(dbOe.getOeReceiverAddress())){
     		dbOe.setOeReceiverAddress( entity.getOeReceiverAddress());
     		saveFlag = true;
     	}
    	
    	if( null != entity.getOdeProductCode() && !"".equals(entity.getOdeProductCode()) && !entity.getOdeProductCode().equals(dbOe.getOdeProductCode())){
     		dbOe.setOdeProductCode( entity.getOdeProductCode());
     		saveFlag = true;
     	}
    	
    	if( null != entity.getOdeCount() && !"".equals(entity.getOdeCount()) && !entity.getOdeCount().equals(dbOe.getOdeCount())){
     		dbOe.setOdeCount( entity.getOdeCount());
     		saveFlag = true;
     	}
    	
    	if(saveFlag){
    	  	entity.setProduct(null);
         	entity.setCreater(null);
         	entity.setUpdater(null);
         	entity.setState( null);
         	entity.setBidList(null);
    		String msg = 	fileOrder.fileOption(orderList, FileOptUtil.FILE_WRITE,  OrderEntity.class);
    		entity.setMsgCode(msg);
    	}else{
    		entity.setMsgCode( OrderEntity.IOCODE_NOUPDATE);
    	}
    }
    
    public Map<String , String > remove(List<OrderEntity> oList){
    	
    	OrderEntity roe = new OrderEntity();
    	if( null == oList || oList.isEmpty()){
    		roe.setMsgCode( OrderEntity.IOCODE_PARAMERROR);
    		return roe.getMsgMap();
    	}
    	
      	List<OrderEntity> orderList = new ArrayList<OrderEntity>();
     	FileOptUtil<OrderEntity> fileOrder= new FileOptUtil<OrderEntity>();
     	fileOrder.fileOption(orderList, FileOptUtil.FILE_READ,  OrderEntity.class);
    	
    	List<BidEntity> bidList = new ArrayList<BidEntity>();
     	FileOptUtil<BidEntity> fileBid= new FileOptUtil<BidEntity>();
     	fileBid.fileOption(bidList, FileOptUtil.FILE_READ,  BidEntity.class);
     	
     	
     	// 删除所有 竞标记录
     	for( OrderEntity oe : oList){
     		Iterator<BidEntity> iter = bidList.iterator();
     		while ( iter.hasNext()){
     			BidEntity bid = iter.next();
     			if( bid.getBeOrderCode().equals(oe.getOeCode())){
     				iter.remove();
     				break;
     			}
     		}
     	}
     	
     	String msg = fileBid.fileOption(bidList, FileOptUtil.FILE_WRITE,  BidEntity.class);
     	if( !"Success".equals(msg)){
     		roe.setMsgCode(msg);
     		return roe.getMsgMap();
     	}
     	
    	for( OrderEntity oe : oList){
    		Iterator<OrderEntity> iter = orderList.iterator();
    		while(iter.hasNext()) {
    			OrderEntity dboe = iter.next();
    			if( oe.getOeCode().equals( dboe.getOeCode() )){
    				iter.remove();
    				break;
    			}
    		}
     	}
    	
    	msg = fileOrder.fileOption(orderList, FileOptUtil.FILE_WRITE,  OrderEntity.class);
    	roe.setMsgCode( msg);
    	return roe.getMsgMap();
    }
    
    /**
     * 管理员查询 订单页面
     * 管理员可查看所有订单信息
     * @param query
     * @return
     */
    public List<OrderEntity> query(OrderEntity query){
    	
     	List<OrderEntity> orderList = new ArrayList<OrderEntity>();
     	FileOptUtil<OrderEntity> fileOrder= new FileOptUtil<OrderEntity>();
     	fileOrder.fileOption(orderList, FileOptUtil.FILE_READ,  OrderEntity.class);
     	
     	if( null != query ){
     		
     		if( null != query.getOeId() && !"".equals(query.getOeId())){
     			Iterator<OrderEntity> iter = orderList.iterator();
     			while( iter.hasNext()){
     				OrderEntity oe = iter.next();
     				if( ! query.getOeId().equals(oe.getOeId())){
     					iter.remove();
     				}
     			}
     		}
     		
     		if( null != query.getOeCode() && !"".equals(query.getOeCode())){
     			Iterator<OrderEntity> iter = orderList.iterator();
     			while( iter.hasNext()){
     				OrderEntity oe = iter.next();
     				if( ! query.getOeCode().equals(oe.getOeCode())){
     					iter.remove();
     				}
     			}
     		}
     		
     		if( null != query.getOeName() && !"".equals(query.getOeName())){
     			Iterator<OrderEntity> iter = orderList.iterator();
     			while( iter.hasNext()){
     				OrderEntity oe = iter.next();
     				if(  oe.getOeName().indexOf(query.getOeName()) == -1 ){
     					iter.remove();
     				}
     			}
     		}
     		
      		if( null != query.getOeState() && !"".equals(query.getOeState())){
     			Iterator<OrderEntity> iter = orderList.iterator();
     			while( iter.hasNext()){
     				OrderEntity oe = iter.next();
     				if( ! query.getOeState().equals(oe.getOeState())){
     					iter.remove();
     				}
     			}
     		}
      		
      		if( null != query.getOdeProductCode() && !"".equals(query.getOdeProductCode())){
     			Iterator<OrderEntity> iter = orderList.iterator();
     			while( iter.hasNext()){
     				OrderEntity oe = iter.next();
     				if( ! query.getOdeProductCode().equals(oe.getOdeProductCode())){
     					iter.remove();
     				}
     			}
     		}
     	}
     	
    	
    	// 订单结构填充
     	
     	List<BidEntity> bidList = new ArrayList<BidEntity>();
     	FileOptUtil<BidEntity> fileBid= new FileOptUtil<BidEntity>();
     	fileBid.fileOption(bidList, FileOptUtil.FILE_READ,  BidEntity.class);
     	
     	List<ProductEntity> productList = ProductManageController.getInstance().query(null);
     	List<UserEntity> userList = UserManageController.getInstance().query(null);
    	
    	DataDictionaryManageController.getInstance();
		Map<String , DataDictionaryMainEntity> dd = DataDictionaryManageController.getDd();
		
		for( OrderEntity  oe : orderList){
			
			// 状态填充
			if( null != oe.getOeState() && !"".equals( oe.getOeState())){
				for( DataDictionarySubEntity sub : dd.get(DataDictionaryMainEntity.DD_ORDER_STATE).getSubList()){
					if(sub.getDdseValue().equals( oe.getOeState())){
						oe.setState(sub);
						break;
					}
				}
			}
			// 产品填充
			for( ProductEntity pe : productList){
				if( pe.getPeCode().equals(oe.getOdeProductCode())){
					oe.setProduct(pe);
					break;
				}
			}
			// 创建填充
			for( UserEntity ue : userList){
				if( ue.getUeCode().equals(oe.getOeCreater())){
					oe.setCreater(ue);
					break;
				}
			}
			// 更新填充
			for( UserEntity ue : userList){
				if( ue.getUeCode().equals(oe.getOeUpdater())){
					oe.setUpdater( ue);
					break;
				}
			}
			
			oe.setBidList(new ArrayList<BidEntity>());
			for( BidEntity bid : bidList){
				if( bid.getBeOrderCode().equals(oe.getOeCode())){
					oe.getBidList().add(bid);
				}
			}
		}
		
    	
    	// 订单检查
    	// 当订单状态为 竞标发布状态 且 超过竞标截止日期， 对竞标内容进行计算 ， 选择最有竞标结果
		boolean saveFlag = false;
		for( OrderEntity oe : orderList){
			if( "1".equals(oe.getOeState()) &&  (new Date()).compareTo( oe.getOeCutOffDate()) > 0  ){
				saveFlag = true;
				List<BidEntity> bList = oe.getBidList();
				// 如果没有人投标， 订单状态变更结束 ，继续判断吓一跳记录
				if( null == bList || bList.isEmpty()){
					oe.setOeState( "6");
					continue;
				}
				// 根据价格进行排序
				for( int i = 0 ; i < bList.size()-i ; i++ ){
					BidEntity aBid = bList.get(i);
					for( int j = i  ; j < bList.size() ; j++){
						BidEntity bBid = bList.get(j);
						if( Integer.parseInt(aBid.getBePrice()) > Integer.parseInt(bBid.getBePrice())){
							Collections.swap(bList, i, j);
						}
					}
				}
				
				int oeCount = Integer.parseInt(oe.getOdeCount());			// 订单需要数量
				oe.setOeState( "2" );
				
				for( BidEntity bid :bList){
					if(oeCount > 0 &&   oeCount >= Integer.parseInt( bid.getBeCount())  ){
						bid.setBeState( "1" );
						bid.setBeProCount(  bid.getBeCount() );
						oeCount = oeCount - Integer.parseInt( bid.getBeCount()) ;
					}else if( oeCount > 0 &&  oeCount <  Integer.parseInt( bid.getBeCount()) ){
						bid.setBeState( "1" );
						bid.setBeProCount(  String.valueOf(oeCount));
						oeCount = oeCount - Integer.parseInt( bid.getBeCount()) ;
					}else{
						bid.setBeState( "0" );
					}
				}
			}
		}
		
		if(saveFlag){
			try {
				fileBid.fileOption(bidList, FileOptUtil.FILE_WRITE,  BidEntity.class);
				fileOrder.fileOption(orderList, FileOptUtil.FILE_WRITE,  OrderEntity.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		UserEntity loginUser = LoginController.getInstance().getLoginUser();
		
		if( !"admin".equals(loginUser.getUeRole())){
			
			List<OrderEntity> rList = new ArrayList<OrderEntity>();
			
			// 查询当前登录人员所在工厂内的所有工作人员
			UserEntity uQuery = new UserEntity();
			uQuery.setUeFactoryCode( loginUser.getUeFactoryCode());
			List<UserEntity> uList = UserManageController .getInstance().query(uQuery);
			
			if( null !=  uList &&! uList.isEmpty()){
				// 遍历检查  工厂内人员 是否参与竞标 ， 并且竞标结果为中标的订单记录。
				for( OrderEntity oe : orderList){
					List<BidEntity > oeBidList = oe.getBidList();
					boolean addFlag = false;		
					Iterator<BidEntity> iter = oeBidList.iterator();
					while(iter.hasNext()){
						BidEntity bid = iter.next();
						for( UserEntity ue : uList){
							if( bid.getBeCreaterCode().equals(ue.getUeCode()) ){
								addFlag = true;
								break;
							}
						}
						if(! addFlag){
							iter.remove();
						}
					}
					if(addFlag ){
						rList.add(oe);
					}
				}
			}
			//  只展示   状态为竞标发布的订单 ， 以及中标的订单 
			for(  OrderEntity oe : orderList){
				if( "1".equals(oe.getOeState())){
					
					boolean flag = true;
					for( OrderEntity roe : rList){
						if( oe.getOeCode().equals(roe.getOeCode())){
							flag = false;
						}
					}
					if(flag){
						rList.add(oe);
					}
				}
			}
			return rList;
		}
    	return orderList;
    }
    
    /**
     * 订单确认
     * @param order
     */
    public void orderConfirm ( OrderEntity order){
    	
    }
    
    
}
