package com.neuedu.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.DataDictionarySubEntity;
import com.neuedu.model.DeviceEntity;
import com.neuedu.model.ProductEntity;
import com.neuedu.model.UserEntity;
import com.neuedu.util.CommonUtil;
import com.neuedu.util.FileOptUtil;


public class ProductManageController {

	private ProductManageController() {}
    private static class ProductManageControllerInstance {
        private static final ProductManageController INSTANCE = new ProductManageController();
    }
    public static ProductManageController getInstance() {
        return ProductManageControllerInstance.INSTANCE;
    }
    
    /**
     * 添加产品
     * @param entity
     */
    public void create(ProductEntity entity){
		
		if( null == entity){
			entity = new ProductEntity();
			entity.setMsgCode(ProductEntity.IOCODE_PARAMERROR);
			return;
		}
		
    	List<ProductEntity> productList = new ArrayList<ProductEntity>();
		FileOptUtil<ProductEntity> fileProduct= new FileOptUtil<ProductEntity>();
		fileProduct.fileOption(productList, FileOptUtil.FILE_READ,  ProductEntity.class);
		
		int maxId = 1;
		for(ProductEntity pe : productList){
			if( maxId <= Integer.parseInt( pe.getPeId() )){
				maxId = Integer.parseInt( pe.getPeId()) +1;
			}
		}
		
		entity.setPeId( String.valueOf(maxId ));
		entity.setPeCode( CommonUtil.codeGenerator());
		
		productList.add(entity);
		String msg = fileProduct.fileOption(productList, FileOptUtil.FILE_WRITE,  ProductEntity.class);
		entity.setMsgCode(msg);
    }
    
    /**
     * 产品修改
     * @param entity
     */
    public void modify(ProductEntity entity){
		if( null == entity){
			entity = new ProductEntity();
			entity.setMsgCode(ProductEntity.IOCODE_PARAMERROR);
			return;
		}

    	List<ProductEntity> productList = new ArrayList<ProductEntity>();
		FileOptUtil<ProductEntity> fileProduct= new FileOptUtil<ProductEntity>();
		fileProduct.fileOption(productList, FileOptUtil.FILE_READ,  ProductEntity.class);
		
		ProductEntity dbPe = null;
		for( ProductEntity pe : productList){
			if( pe.getPeId().equals(entity.getPeId())){
				dbPe = pe;
			}
		}
		
		if( null == dbPe){
			entity.setMsgCode(ProductEntity.IOCODE_PARAMERROR);
			return;
		}
		
		boolean saveFlag = false;
		if( null != entity.getPeName() && !"".equals(entity.getPeName()) && !entity.getPeName().equals(dbPe.getPeName()) ){
			dbPe.setPeName(entity.getPeName());
			saveFlag = true;
		}
		
		if( null != entity.getSpecifications() && !"".equals(entity.getSpecifications()) && !entity.getSpecifications().equals(dbPe.getSpecifications()) ){
			dbPe.setSpecifications(entity.getSpecifications());
			saveFlag = true;
		}

		if( null != entity.getPeType() && !"".equals(entity.getPeType()) && !entity.getPeType().equals(dbPe.getPeType()) ){
			dbPe.setPeType(entity.getPeType());
			saveFlag = true;
		}
		
		if( ( null == entity.getPeRemark() && null != dbPe.getPeRemark())||
				( null != entity.getPeRemark() && !entity.getPeRemark().equals(dbPe.getPeRemark())) ){
			dbPe.setPeRemark(entity.getPeRemark());
			saveFlag = true;
		}
		
		if(saveFlag){
			String msg = fileProduct.fileOption(productList, FileOptUtil.FILE_WRITE,  ProductEntity.class);
			entity.setMsgCode(msg);
		}else{
			entity.setMsgCode( ProductEntity.IOCODE_NOUPDATE);
		}
		
    }
    
    /**
     * 产品删除
     * @param rList
     * @return
     */
    public Map<String, String> remove(List<ProductEntity> rList ){
    	
    	if( null == rList || rList.isEmpty()){
    		ProductEntity pe = new ProductEntity();
    		pe.setMsgCode( ProductEntity.IOCODE_PARAMERROR);
    		return pe.getMsgMap();
    	}
    	
    	List<ProductEntity> productList = new ArrayList<ProductEntity>();
		FileOptUtil<ProductEntity> fileProduct= new FileOptUtil<ProductEntity>();
		fileProduct.fileOption(productList, FileOptUtil.FILE_READ,  ProductEntity.class);
		for( ProductEntity pe : rList ){
			Iterator<ProductEntity> iter = productList.iterator();
			while( iter.hasNext()){
				ProductEntity dbPe = iter.next();
				if( pe.getPeId().equals(dbPe.getPeId())){
					iter.remove();
					break;
				}
			}
		}
		
		String msg = fileProduct.fileOption(productList, FileOptUtil.FILE_WRITE,  ProductEntity.class);
		ProductEntity pe = new ProductEntity();
		pe.setMsgCode(msg);
		return pe.getMsgMap();
		
    }
    
    /**
     * 产品查询
     * @param query
     * @return
     */
    public List<ProductEntity> query(ProductEntity query){
    	
    	List<ProductEntity> productList = new ArrayList<ProductEntity>();
		FileOptUtil<ProductEntity> fileProduct= new FileOptUtil<ProductEntity>();
		fileProduct.fileOption(productList, FileOptUtil.FILE_READ,  ProductEntity.class);
		
		if( null != query){
			if( null != query.getPeId() && !"".equals(query.getPeId())){
				Iterator<ProductEntity> iter = productList.iterator();
				while( iter.hasNext()){
					ProductEntity pe = iter.next();
					if( !pe.getPeId().equals(query.getPeId())){
						iter.remove();
					}
				}
			}
			
			if( null != query.getPeCode() && !"".equals(query.getPeCode())){
				Iterator<ProductEntity> iter = productList.iterator();
				while( iter.hasNext()){
					ProductEntity pe = iter.next();
					if( !pe.getPeCode().equals(query.getPeCode())){
						iter.remove();
					}
				}
			}
			
			if( null != query.getPeName() && !"".equals(query.getPeName())){
				Iterator<ProductEntity> iter = productList.iterator();
				while( iter.hasNext()){
					ProductEntity pe = iter.next();
					if( pe.getPeName().indexOf(query.getPeName()) == -1){
						iter.remove();
					}
				}
			}
		}
		
		if( null == productList || productList.isEmpty()){
			return productList;
		}
		DataDictionaryManageController.getInstance();
		Map<String , DataDictionaryMainEntity> dd = DataDictionaryManageController.getDd();
		
		for( ProductEntity pe : productList){
			if( null != pe.getPeType() && !"".equals( pe.getPeType())){
				for( DataDictionarySubEntity sub : dd.get(DataDictionaryMainEntity.DD_PRODUCT_TYPE).getSubList()){
					if(sub.getDdseValue().equals( pe.getPeType())){
						pe.setpType(sub);
					}
				}
			}
		}
    	return productList;
    }
}
