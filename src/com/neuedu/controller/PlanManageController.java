package com.neuedu.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.neuedu.model.BidEntity;
import com.neuedu.model.DeviceEntity;
import com.neuedu.model.OrderEntity;
import com.neuedu.model.PlanDeviceEntity;
import com.neuedu.model.PlanEntity;
import com.neuedu.model.ProductEntity;
import com.neuedu.model.UserEntity;
import com.neuedu.util.CommonUtil;
import com.neuedu.util.FileOptUtil;

/**
 * 生产管理
 * @author koala
 *
 */
public class PlanManageController {

	// 单例模式
	private PlanManageController() {}
    private static class PlanManageControllerInstance {
        private static final PlanManageController INSTANCE = new PlanManageController();
    }
    public static PlanManageController getInstance() {
        return PlanManageControllerInstance.INSTANCE;
    }
    
    
    /**
     * 创建生产计划， 在生产计划管理页面中， 点击生产计划那妞后， 自动生成计划， 如果点返回按钮， 则删除创建计划
     * @return
     */
    public PlanEntity planCreate(){
    	
    	List<PlanEntity> planList = new ArrayList<PlanEntity>();
		FileOptUtil<PlanEntity> filePlan= new FileOptUtil<PlanEntity>();
		filePlan.fileOption(planList, FileOptUtil.FILE_READ,  PlanEntity.class);

		PlanEntity entity = new PlanEntity();
		UserEntity loginUser = LoginController.getInstance().getLoginUser();
		
		int maxId = 1;
		for( PlanEntity pe: planList){
			if( maxId <= Integer.parseInt(pe.getPeId())){
				maxId = Integer.parseInt(pe.getPeId());
			}
		}
		
		Date d = new Date();		
		entity.setPeId( String.valueOf(maxId));
		entity.setPeCode( CommonUtil.codeGenerator());
		entity.setPeCreater(loginUser.getUeCode());
		entity.setPeUpdater(loginUser.getUeCode());
		entity.setPeCreate(d);
		entity.setPeUpdate(d);
		entity.setPeState("0");
		planList.add(entity);
		String msg = filePlan.fileOption(planList, FileOptUtil.FILE_WRITE,  PlanEntity.class);
		entity.setMsgCode(msg);
		return entity ;
    }
    
    /**
     * 修改生产计划    新建数据的保存操作实际上是修改操作
     * @param entity
     */
    public void planModify(PlanEntity entity ){
    	if( null == entity ){
    		entity = new PlanEntity();
    		entity.setMsgCode(PlanEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	List<PlanEntity> planList = new ArrayList<PlanEntity>();
		FileOptUtil<PlanEntity> filePlan= new FileOptUtil<PlanEntity>();
		filePlan.fileOption(planList, FileOptUtil.FILE_READ,  PlanEntity.class);
		
		PlanEntity dbPe = null;
		for( PlanEntity pe : planList){
			if( pe.getPeCode().equals(entity.getPeCode())){
				dbPe = pe;
			}
		}
    		
		if( null == dbPe){
			entity.setMsgCode(PlanEntity.IOCODE_PARAMERROR);
			return;
		}
		
		boolean saveFlag = true;
		if( null != entity.getPeName() && !"".equals(entity.getPeName()) && !entity.getPeName().equals( dbPe.getPeName())){
			dbPe.setPeName( entity.getPeName());
			saveFlag = true;
		}

		if( null != entity.getPeState() && !"".equals(entity.getPeState()) && !entity.getPeState().equals(dbPe.getPeState())){
			dbPe.setPeState( entity.getPeState());
			saveFlag = true;
		}

		if( (null == entity.getPeRemark() && null  != dbPe.getPeRemark())||
				( null != entity.getPeRemark() && !entity.getPeRemark().equals(dbPe.getPeRemark()))){
			dbPe.setPeRemark( entity.getPeRemark());
			saveFlag = true;
		}
		
		if( null != entity.getPeBidCode() && !"".equals(entity.getPeBidCode())){
			List<BidEntity> bidList = new ArrayList<BidEntity>();
			FileOptUtil<BidEntity> fileBid= new FileOptUtil<BidEntity>();
			fileBid.fileOption(bidList, FileOptUtil.FILE_READ,  BidEntity.class);
			
			String orderCode = null;
			String productCode = null;
			
			BidEntity dbBid = null;
			for( BidEntity bid : bidList){
				if( entity.getPeBidCode().equals(bid.getBeCode())){
					dbBid = bid;
					break;
				}
			}
			
			if( null != dbBid){
				orderCode = dbBid.getBeOrderCode();
			
				List<OrderEntity> orderList = new ArrayList<OrderEntity>();
				FileOptUtil<OrderEntity> fileOrder= new FileOptUtil<OrderEntity>();
				fileOrder.fileOption(orderList, FileOptUtil.FILE_READ,  OrderEntity.class);
			
				for( OrderEntity oe : orderList){
					if( oe.getOeCode().equals( dbBid.getBeOrderCode())){
						productCode = oe.getOdeProductCode();
					}
				}
			}
			
			if( null != orderCode && !"".equals(orderCode) && null != productCode && !"".equals(productCode)){
				saveFlag = true;
				dbPe.setPeOrderCode(orderCode);
				dbPe.setPeProductCode(productCode);
			}
		}
		if( saveFlag){
			if("0".equals(entity.getPeState())){
				List<OrderEntity> orderList = new ArrayList<OrderEntity>();
				FileOptUtil<OrderEntity> fileOrder= new FileOptUtil<OrderEntity>();
				fileOrder.fileOption(orderList, FileOptUtil.FILE_READ,  OrderEntity.class);
				for( OrderEntity oe : orderList){
					if( oe.getOeCode().equals(entity.getPeOrderCode()) && "2".equals(oe.getOeState())){
						oe.setOeState("3");
						fileOrder.fileOption(orderList, FileOptUtil.FILE_WRITE,  OrderEntity.class);
					}
				}
			}
			// 如果状态变更为已发货 ， 则需要变更订单状态为 发货状态
			if( "2".equals(entity.getPeState()) ){
				List<OrderEntity> orderList = new ArrayList<OrderEntity>();
				FileOptUtil<OrderEntity> fileOrder= new FileOptUtil<OrderEntity>();
				fileOrder.fileOption(orderList, FileOptUtil.FILE_READ,  OrderEntity.class);
				for( OrderEntity oe : orderList){
					if( oe.getOeCode().equals(entity.getPeOrderCode()) && "3".equals(oe.getOeState())){
						oe.setOeState("4");
						fileOrder.fileOption(orderList, FileOptUtil.FILE_WRITE,  OrderEntity.class);
					}
				}
			}
			
			String msg = filePlan.fileOption(planList, FileOptUtil.FILE_WRITE,  PlanEntity.class);
			entity.setMsgCode(msg);
		}else{
			entity.setMsgCode( PlanEntity.IOCODE_NOUPDATE);
		}
    }
    
    
    /**
     * 生产计划删除
     * @param removeList
     * @return
     */
    public Map<String, String > planRemove(List<PlanEntity> removeList ){
    	PlanEntity rpe = new PlanEntity();
    	if( null == removeList || removeList.isEmpty() ){
    		rpe.setMsgCode( PlanEntity.IOCODE_PARAMERROR);
    		return rpe.getMsgMap();
    	}
    	
		List<PlanEntity> planList = new ArrayList<PlanEntity>();
		FileOptUtil<PlanEntity> filePlan= new FileOptUtil<PlanEntity>();
		filePlan.fileOption(planList, FileOptUtil.FILE_READ,  PlanEntity.class);
    	
		List<PlanDeviceEntity> pDeviceList = new ArrayList<PlanDeviceEntity>();
		FileOptUtil<PlanDeviceEntity> filePlanDevice= new FileOptUtil<PlanDeviceEntity>();
		filePlanDevice.fileOption(pDeviceList, FileOptUtil.FILE_READ,  PlanDeviceEntity.class);
		
		Iterator< PlanDeviceEntity > iter = pDeviceList.iterator();
		while( iter.hasNext() ){
			PlanDeviceEntity pde = iter.next();
			for( PlanEntity pe : removeList){
				if( pe.getPeCode().equals(pde.getPdePlanCode())){
					iter.remove();
					break;
				}
			}
		}

		String msg = filePlanDevice.fileOption(pDeviceList, FileOptUtil.FILE_WRITE,  PlanDeviceEntity.class);
		if( !"Success".equals(msg)){
			rpe.setMsgCode(msg);
			return rpe.getMsgMap();
		}
		for( PlanEntity pe : removeList){
			Iterator< PlanEntity> iterPlan = planList.iterator();
			while( iterPlan.hasNext()){
				PlanEntity dbPe = iterPlan.next();
				if(dbPe.getPeCode().equals(pe.getPeCode())){
					if( 1== planList.size()){
						planList = new ArrayList<PlanEntity>();
					}else{
						iter.remove();
					}
					break;
				}
			}
		}
		
		msg = filePlan.fileOption(planList, FileOptUtil.FILE_WRITE,  PlanEntity.class);
    	rpe.setMsgCode(msg);
    	
    	return rpe.getMsgMap();
    }

    /**
     * 
     * @param query
     * @return
     */
    public List<PlanEntity> planQuery( PlanEntity query){
      	
		List<PlanEntity> planList = new ArrayList<PlanEntity>();
		FileOptUtil<PlanEntity> filePlan= new FileOptUtil<PlanEntity>();
		filePlan.fileOption(planList, FileOptUtil.FILE_READ,  PlanEntity.class);
		
		UserEntity loginUser = LoginController.getInstance().getLoginUser();
        	
		if( null != query){
			// 查询项包括，主键 计划编号， 计划名称， 计划状态， 订单编号
			if( null != query.getPeName() && !"".equals(query.getPeName())){
				Iterator<PlanEntity> iter = planList.iterator();
				while(iter.hasNext()){
					PlanEntity pe = iter.next();
					if( pe.getPeName().indexOf( query.getPeName()) == -1 ){
						iter.remove();
					}
				}
			}
			
			if( null != query.getPeId() && !"".equals(query.getPeId())){
				Iterator<PlanEntity> iter = planList.iterator();
				while(iter.hasNext()){
					PlanEntity pe = iter.next();
					if(! query.getPeId().equals(pe.getPeId())){
						iter.remove();
					}
				}
			}
			
			if( null != query.getPeCode() && !"".equals(query.getPeCode())){
				Iterator<PlanEntity> iter = planList.iterator();
				while(iter.hasNext()){
					PlanEntity pe = iter.next();
					if( !query.getPeCode().equals(pe.getPeCode() )){
						iter.remove();
					}
				}
			}
			
			if( null != query.getPeOrderCode() && !"".equals(query.getPeOrderCode())){
				Iterator<PlanEntity> iter = planList.iterator();
				while(iter.hasNext()){
					PlanEntity pe = iter.next();
					if(! query.getPeOrderCode().equals(pe.getPeOrderCode())){
						iter.remove();
					}
				}
			}
			
			if( null != query.getPeState() && !"".equals(query.getPeState())){
				Iterator<PlanEntity> iter = planList.iterator();
				while(iter.hasNext()){
					PlanEntity pe = iter.next();
					if( !query.getPeState().equals( pe.getPeState())){
						iter.remove();
					}
				}
			}
		}
		
		
		
		
		// 数据结构拼装
		List<UserEntity> userList = new ArrayList<UserEntity>();
		FileOptUtil<UserEntity> fileUser= new FileOptUtil<UserEntity>();
		fileUser.fileOption(userList, FileOptUtil.FILE_READ,  UserEntity.class);
		
		List<UserEntity> factoryUserList = new ArrayList<UserEntity>();
		for( UserEntity ue : userList){
			if( loginUser.getUeFactoryCode().equals(ue.getUeFactoryCode())){
				factoryUserList.add(ue);
			}
		}
		 
		List< PlanEntity > rpeList = new ArrayList<PlanEntity>();
 		for( PlanEntity pe : planList){
 			boolean flag = true;
 			for( UserEntity ue : factoryUserList){
 				if( pe.getPeCreater().equals(ue.getUeCode())){
 					flag= false;
 				}
 			}
 			if(flag){
 				rpeList.add(pe);
 			}
		}
 		planList.removeAll(rpeList);

		List<OrderEntity> orderList = new ArrayList<OrderEntity>();
		FileOptUtil<OrderEntity> fileOrder= new FileOptUtil<OrderEntity>();
		fileOrder.fileOption(orderList, FileOptUtil.FILE_READ,  OrderEntity.class);
		
		List<ProductEntity> productList = new ArrayList<ProductEntity>();
		FileOptUtil<ProductEntity> fileProduct= new FileOptUtil<ProductEntity>();
		fileProduct.fileOption(productList, FileOptUtil.FILE_READ,  ProductEntity.class);
		
		List<BidEntity> bidList = new ArrayList<BidEntity>();
		FileOptUtil<BidEntity> fileBid = new FileOptUtil<BidEntity>();
		fileBid.fileOption(bidList, FileOptUtil.FILE_READ,  BidEntity.class);
		
		List<PlanDeviceEntity> planDeviceList = new ArrayList<PlanDeviceEntity>();
		FileOptUtil<PlanDeviceEntity> filePlanDevice = new FileOptUtil<PlanDeviceEntity>();
		filePlanDevice.fileOption(planDeviceList, FileOptUtil.FILE_READ,  PlanDeviceEntity.class);
		
		for(PlanEntity pe : planList){
			
			for( BidEntity bid : bidList){
				if( pe.getPeOrderCode().equals(bid.getBeOrderCode()) && "1".equals(bid.getBeState())){
					pe.setBid(bid);
					break;
				}
			}
			
			for( UserEntity ue : userList){
				if( pe.getPeCreater().equals(ue.getUeCode())){
					pe.setCreater( ue);
					break;
				}
			}
			
			for( UserEntity ue : userList){
				if( pe.getPeUpdater().equals(ue.getUeCode())){
					pe.setUpdater(ue);
					break;
				}
			}
			
			for( OrderEntity oe : orderList){
				if( pe.getPeOrderCode().equals(oe.getOeCode())){
					pe.setOrder( oe);
					break;
				}
			}
			
			for( ProductEntity proe : productList){
				if( pe.getPeProductCode().equals(proe.getPeCode())){
					pe.setProduct(proe);
					break;
				}
			}
			
			pe.setPdList(new ArrayList<PlanDeviceEntity>());
			for( PlanDeviceEntity pde : planDeviceList){
				if( pe.getPeCode().equals(pde.getPdePlanCode())){
					pe.getPdList().add(pde);
				}
			}
		}
    	
    	return planList;
    }
    
    /**
     * 查询当前用户可用设备。  用于加载  生产新增设备页面中的下拉框内容
     * @return
     */
    public List<DeviceEntity> queryDevice(PlanEntity plan){
    	List<DeviceEntity> deviceList = DeviceManageController.getInstance().query(null);
    	
      	List<PlanDeviceEntity> pDeviceList = new ArrayList<PlanDeviceEntity>();
		FileOptUtil<PlanDeviceEntity> filePlanDevice= new FileOptUtil<PlanDeviceEntity>();
		filePlanDevice.fileOption(pDeviceList, FileOptUtil.FILE_READ,  PlanDeviceEntity.class);
    	
		
		for( PlanDeviceEntity pde : pDeviceList){
			Iterator< DeviceEntity> iter = deviceList.iterator();
			while(iter.hasNext()){
				DeviceEntity de = iter.next();
				if( pde.getPdePlanCode().equals(plan.getPeCode()) && "0".equals(pde.getPdeState()) && pde.getPdeDeviceCode().equals(de.getDeCode()) ){
					iter.remove();
				}
			}
		}
		
		return deviceList;
    }
    
    /**
     * 添加生产设备
     * @param entity
     */
    public void planDeviceCreate( PlanDeviceEntity entity ){
    	
    	// 所属生产计划编号  需要从页面获取

    	if( null == entity || null == entity.getPdePlanCode() ||"".equals(entity.getPdePlanCode())){
    		entity = new PlanDeviceEntity();
    		entity.setMsgCode( PlanDeviceEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
		List<PlanDeviceEntity> pDeviceList = new ArrayList<PlanDeviceEntity>();
		FileOptUtil<PlanDeviceEntity> filePlanDevice= new FileOptUtil<PlanDeviceEntity>();
		filePlanDevice.fileOption(pDeviceList, FileOptUtil.FILE_READ,  PlanDeviceEntity.class);
    	
		int maxId = 1; 
		for( PlanDeviceEntity pde : pDeviceList){
			if( maxId <= Integer.parseInt( pde.getPdeId())){
				maxId = Integer.parseInt(pde.getPdeId())+1;
			}
		}
		
		entity.setPdeId( String.valueOf( maxId));
    	entity.setPdeCode( CommonUtil.codeGenerator())	;
    	entity.setPdeState( "0");
    	
    	pDeviceList.add(entity);
    	String msg = filePlanDevice.fileOption(pDeviceList, FileOptUtil.FILE_WRITE,  PlanDeviceEntity.class);
    	entity.setMsgCode(msg);
    	
    }
    
    
    public void planDeviceModify( PlanDeviceEntity entity){
    
    	if( null == entity){
    		entity = new PlanDeviceEntity();
    		entity.setMsgCode( PlanDeviceEntity.IOCODE_PARAMERROR);
    		return;
    	}
    	
    	PlanDeviceEntity dbPde = null;
    	List<PlanDeviceEntity> pDeviceList = new ArrayList<PlanDeviceEntity>();
		FileOptUtil<PlanDeviceEntity> filePlanDevice= new FileOptUtil<PlanDeviceEntity>();
		filePlanDevice.fileOption(pDeviceList, FileOptUtil.FILE_READ,  PlanDeviceEntity.class);
		
		for( PlanDeviceEntity pde : pDeviceList){
			if( pde.getPdeCode().equals(entity.getPdeCode())){
				dbPde = pde;
			}
		}
    
		if ( null == dbPde){
			entity.setMsgCode( PlanDeviceEntity.IOCODE_PARAMERROR);
			return;
		}
		
		boolean saveFlag = true;
		if(  null != entity.getPdeProductCount() && !"".equals(entity.getPdeProductCount()) && ! entity.getPdeProductCount().equals(dbPde.getPdeProductCount())){
			dbPde.setPdeProductCount(entity.getPdeProductCount());
			saveFlag = true;
		}
		
		if(  null != entity.getPdeDeviceStart() && 0 != entity.getPdeDeviceStart().compareTo(dbPde.getPdeDeviceStart())) {
			dbPde.setPdeDeviceStart(entity.getPdeDeviceStart());
			saveFlag = true;
		}
		
		if(  null != entity.getPdeDeviceEnd() && 0 != entity.getPdeDeviceEnd().compareTo(dbPde.getPdeDeviceEnd())) {
			dbPde.setPdeDeviceEnd(entity.getPdeDeviceEnd());
			saveFlag = true;
		}
		
		if( null != entity.getPdeState() && !"".equals(entity.getPdeState()) && !entity.getPdeState().equals(dbPde.getPdeState())){
			dbPde.setPdeState( entity.getPdeState());
			saveFlag = true;
		}
		
		if(saveFlag){
			
			boolean checkflag = true;
			for(PlanDeviceEntity pde : pDeviceList){
				 if( pde.getPdePlanCode().equals(entity.getPdePlanCode()) && "0".equals(pde.getPdeState())){
					 checkflag = false;
				 }
			}
			
			if(checkflag){
				List<PlanEntity> planList = new ArrayList<PlanEntity>();
				FileOptUtil<PlanEntity> filePlan= new FileOptUtil<PlanEntity>();
				filePlan.fileOption(planList, FileOptUtil.FILE_READ,  PlanEntity.class);
				
				for(PlanEntity pe : planList){
					if( pe.getPeCode().equals(entity.getPdePlanCode())){
						pe.setPeState("1");
						break;
					}
				}
				filePlan.fileOption(planList, FileOptUtil.FILE_WRITE,  PlanEntity.class);
			}
			
			String msg = 	filePlanDevice.fileOption(pDeviceList, FileOptUtil.FILE_WRITE,  PlanDeviceEntity.class);
			entity.setMsgCode(msg);
		}else{
			entity.setMsgCode( PlanDeviceEntity.IOCODE_PARAMERROR);
		}
    }
    
    public Map<String , String> planDeviceRemove( List<PlanDeviceEntity > removeList){

    	PlanDeviceEntity rpde = new PlanDeviceEntity();
    	
    	if( null == removeList  || removeList.isEmpty()){
    		rpde.setMsgCode( PlanDeviceEntity.IOCODE_PARAMERROR);
    		return rpde.getMsgMap();
    	}
    	
      	List<PlanDeviceEntity> pDeviceList = new ArrayList<PlanDeviceEntity>();
		FileOptUtil<PlanDeviceEntity> filePlanDevice= new FileOptUtil<PlanDeviceEntity>();
		filePlanDevice.fileOption(pDeviceList, FileOptUtil.FILE_READ,  PlanDeviceEntity.class);
    	
		for(PlanDeviceEntity pde : removeList){
			Iterator<PlanDeviceEntity> iter = pDeviceList.iterator();
			while(iter.hasNext()){
				PlanDeviceEntity dbPde = iter.next();
				if( dbPde.getPdeCode().equals( pde.getPdeCode())){
					iter.remove();
					break;
				}
			}
		}
		
		String msg = filePlanDevice.fileOption(pDeviceList, FileOptUtil.FILE_WRITE,  PlanDeviceEntity.class);
    	rpde.setMsgCode(msg);
    	return rpde.getMsgMap();
    }
    
    /**
     * 加载 生产计划编辑页面中的  生产设备表格数据
     * @param query
     * @return
     */
    public List<PlanDeviceEntity> planDeviceQuery( PlanEntity query){

    	if( null == query){
    		return null;
    	}
     	List<PlanDeviceEntity> pDeviceList = new ArrayList<PlanDeviceEntity>();
		FileOptUtil<PlanDeviceEntity> filePlanDevice= new FileOptUtil<PlanDeviceEntity>();
		filePlanDevice.fileOption(pDeviceList, FileOptUtil.FILE_READ,  PlanDeviceEntity.class);
    	
		Iterator<PlanDeviceEntity> iter = pDeviceList.iterator();
		while(iter.hasNext()){
			PlanDeviceEntity pde = iter.next();
			if( !pde.getPdePlanCode().equals(query.getPeCode())){
				iter.remove();
			}
		}
		
		List<DeviceEntity>deviceList = new ArrayList<DeviceEntity>();
		FileOptUtil<DeviceEntity> fileDevice = new FileOptUtil<DeviceEntity>();
		fileDevice.fileOption(deviceList, FileOptUtil.FILE_READ,  DeviceEntity.class);
		
		for( PlanDeviceEntity pde : pDeviceList){
			for( DeviceEntity de : deviceList){
				if( pde.getPdeDeviceCode().equals(de.getDeCode())){
					pde.setDevice(de);
				}
			}
		}
		return pDeviceList;
    }
    
}
