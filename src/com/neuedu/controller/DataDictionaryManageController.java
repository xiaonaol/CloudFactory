package com.neuedu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.DataDictionarySubEntity;
import com.neuedu.util.CommonUtil;
import com.neuedu.util.FileOptUtil;

/**
 * 数据字典管理
 * @author koala
 *
 */
public class DataDictionaryManageController {

	private static  Map<String , DataDictionaryMainEntity>  dd ;
	/**
	 * 单例模式
	 */
	private DataDictionaryManageController() {}
    private static class DataDictionaryManageControllerInstance {
        private static final DataDictionaryManageController INSTANCE = new DataDictionaryManageController();
    }
    public static DataDictionaryManageController getInstance() {
        return DataDictionaryManageControllerInstance.INSTANCE;
    }
    

    private void reloadDataDictonaryMainList( ){

    	List<DataDictionaryMainEntity> mainList = new ArrayList<DataDictionaryMainEntity>();
		FileOptUtil<DataDictionaryMainEntity> fileMain= new FileOptUtil<DataDictionaryMainEntity>();
		fileMain.fileOption(mainList, FileOptUtil.FILE_READ,  DataDictionaryMainEntity.class);
		
    	List<DataDictionarySubEntity> subList = new ArrayList<DataDictionarySubEntity>();
		FileOptUtil<DataDictionarySubEntity> fileSub= new FileOptUtil<DataDictionarySubEntity>();
		fileSub.fileOption(subList, FileOptUtil.FILE_READ,  DataDictionarySubEntity.class);
		
		for(DataDictionaryMainEntity main : mainList){
			main.setSubList( new ArrayList<DataDictionarySubEntity>());
			for(DataDictionarySubEntity sub : subList){
				if( main.getDdmeCode().equals(sub.getDdsePCode())){
					main.getSubList().add(sub);
				}
			}
		}
		
		dd.clear();
		for( DataDictionaryMainEntity main : mainList){
			dd.put(main.getDdmeKind(), main);
		}
		
    }
    public static Map<String, DataDictionaryMainEntity> getDd() {
    	if(dd == null ){
    		List<DataDictionaryMainEntity> mainList = new ArrayList<DataDictionaryMainEntity>();
    		FileOptUtil<DataDictionaryMainEntity> fileMain= new FileOptUtil<DataDictionaryMainEntity>();
    		fileMain.fileOption(mainList, FileOptUtil.FILE_READ,  DataDictionaryMainEntity.class);
    		
        	List<DataDictionarySubEntity> subList = new ArrayList<DataDictionarySubEntity>();
    		FileOptUtil<DataDictionarySubEntity> fileSub= new FileOptUtil<DataDictionarySubEntity>();
    		fileSub.fileOption(subList, FileOptUtil.FILE_READ,  DataDictionarySubEntity.class);
    		
    		for(DataDictionaryMainEntity main : mainList){
    			main.setSubList( new ArrayList<DataDictionarySubEntity>());
    			for(DataDictionarySubEntity sub : subList){
    				if( main.getDdmeCode().equals(sub.getDdsePCode())){
    					main.getSubList().add(sub);
    				}
    			}
    		}
    		
    		dd  = new HashMap<String, DataDictionaryMainEntity>();
    		for( DataDictionaryMainEntity main : mainList){
    			dd.put(main.getDdmeKind(), main);
    		}
    	}
    	
    	return dd;
	}

	/**
     * 新建 数据字典主项
     * @param entity
     */
    public void create(DataDictionaryMainEntity entity){
    	if( null == entity ){
    		entity = new DataDictionaryMainEntity();
    		entity.setMsgCode( DataDictionaryMainEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
       	List<DataDictionaryMainEntity> mainList = new ArrayList<DataDictionaryMainEntity>();
		FileOptUtil<DataDictionaryMainEntity> fileMain= new FileOptUtil<DataDictionaryMainEntity>();
		fileMain.fileOption(mainList, FileOptUtil.FILE_READ,  DataDictionaryMainEntity.class);
    		
		int maxId = 1; 
		for( DataDictionaryMainEntity main : mainList){
			if( maxId <= Integer.parseInt( main.getDdmeId() )){
				maxId = Integer.parseInt( main.getDdmeId()) +1;
			}
		}
		entity.setDdmeId( String.valueOf( maxId ));
		entity.setDdmeCode( CommonUtil.codeGenerator() );
		mainList.add(entity);
		String msg = fileMain.fileOption(mainList, FileOptUtil.FILE_WRITE,  DataDictionaryMainEntity.class);
		entity.setMsgCode(msg);
		reloadDataDictonaryMainList();
    }
    
    /**
     * 新建数据字典子项
     * @param entity
     */
    public void create( DataDictionarySubEntity entity){
    	if( null == entity || null == entity.getDdsePCode()|| "".equals(entity.getDdsePCode()) ){
    		entity = new DataDictionarySubEntity();
    		entity.setMsgCode( DataDictionarySubEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
    	List<DataDictionarySubEntity> subList = new ArrayList<DataDictionarySubEntity>();
		FileOptUtil<DataDictionarySubEntity> fileSub= new FileOptUtil<DataDictionarySubEntity>();
		fileSub.fileOption(subList, FileOptUtil.FILE_READ,  DataDictionarySubEntity.class);
		
		int maxId = 1;
		for( DataDictionarySubEntity sub : subList){
			if( maxId <= Integer.parseInt( sub.getDdseId() )){
				maxId = Integer.parseInt( sub.getDdseId()) +1; 
			}
		}
		entity.setDdseId( String.valueOf( maxId));
		entity.setDdseCode( CommonUtil.codeGenerator() );
		subList.add(entity);
		String msg = fileSub.fileOption(subList, FileOptUtil.FILE_WRITE,  DataDictionarySubEntity.class);
		entity.setMsgCode( msg);
		reloadDataDictonaryMainList();
    }
    
    /**
     * 数据字典 主项信息修改
     * @param entity
     */
    public void modify (DataDictionaryMainEntity entity){
    	if( null == entity ){
    		entity = new DataDictionaryMainEntity();
    		entity.setMsgCode( DataDictionaryMainEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
       	List<DataDictionaryMainEntity> mainList = new ArrayList<DataDictionaryMainEntity>();
		FileOptUtil<DataDictionaryMainEntity> fileMain= new FileOptUtil<DataDictionaryMainEntity>();
		fileMain.fileOption(mainList, FileOptUtil.FILE_READ,  DataDictionaryMainEntity.class);

		DataDictionaryMainEntity dbMain = null;
		for( DataDictionaryMainEntity main : mainList){
			if( entity.getDdmeId().equals(main.getDdmeId() )){
				dbMain = main;
			}
		}
		
		// 未找到要求该的数据
		if( null == dbMain){
			entity.setMsgCode( DataDictionaryMainEntity.IOCODE_PARAMERROR);
			return;
		}
		
		boolean saveFlag = false;
		// 可更改项   主项代码 ，主项名称 ， 说明
		if(  null != entity.getDdmeKind() && !"".equals( entity.getDdmeKind() ) && !entity.getDdmeKind().equals(dbMain.getDdmeKind())){
			dbMain.setDdmeKind( entity.getDdmeKind() );
			saveFlag = true;
		}
    	
		if(  null != entity.getDdmeKindName() && !"".equals( entity.getDdmeKindName() ) && !entity.getDdmeKindName().equals(dbMain.getDdmeKindName())){
			dbMain.setDdmeKindName( entity.getDdmeKindName() );
			saveFlag = true;
		}
		
		if( ( null == entity.getDdmeRemark() && null != dbMain.getDdmeRemark() ) ||
				( null != entity.getDdmeRemark() && ! entity.getDdmeRemark().equals(dbMain.getDdmeRemark())) ){
			dbMain.setDdmeRemark( entity.getDdmeRemark() );
			saveFlag = true;
		}
		
		if( saveFlag ){
			String msg = fileMain.fileOption(mainList, FileOptUtil.FILE_WRITE,  DataDictionaryMainEntity.class);
			entity.setMsgCode( msg);
		}else{
			entity.setMsgCode( DataDictionaryMainEntity.IOCODE_NOUPDATE);
		}
		reloadDataDictonaryMainList();
    }
    
    /**
     * 数据字典  子项信息修改
     * @param entity
     */
    public void modify (DataDictionarySubEntity entity){
    	
    	if( null == entity || null == entity.getDdsePCode()|| "".equals(entity.getDdsePCode()) ){
    		entity = new DataDictionarySubEntity();
    		entity.setMsgCode( DataDictionarySubEntity.IOCODE_PARAMERROR);
    		return ;
    	}
    	
      	List<DataDictionarySubEntity> subList = new ArrayList<DataDictionarySubEntity>();
		FileOptUtil<DataDictionarySubEntity> fileSub= new FileOptUtil<DataDictionarySubEntity>();
		fileSub.fileOption(subList, FileOptUtil.FILE_READ,  DataDictionarySubEntity.class);
        	
		DataDictionarySubEntity dbSub = null; 
		for(DataDictionarySubEntity sub : subList){
			if( sub.getDdseId().equals( entity.getDdseId() )){
				dbSub = sub ;
				break;
			}
		}
		
		// 无对应修改数据
		if( null == dbSub ){
			entity.setMsgCode( DataDictionarySubEntity.IOCODE_PARAMERROR);
    		return ;
		}
		
		boolean saveFlag = false;
		if( null != entity .getDdseText() && !"".equals(entity.getDdseText()) &&! entity.getDdseText() .equals( dbSub.getDdseText())){
			dbSub.setDdseText( entity.getDdseText() );
			saveFlag = true;
		}
		
		if( null != entity .getDdseValue() && !"".equals(entity.getDdseValue()) &&! entity.getDdseValue() .equals( dbSub.getDdseValue())){
			dbSub.setDdseValue( entity.getDdseValue() );
			saveFlag = true;
		}
		
		if(  ( null == entity.getDdseRemark() && null !=  dbSub.getDdseRemark() ) ||
				( null != entity.getDdseRemark() && !entity.getDdseRemark().equals( dbSub.getDdseRemark() ))){
			dbSub.setDdseRemark( entity.getDdseRemark() );
			saveFlag = true;
		}
		
		if(saveFlag ){
			String msg = fileSub.fileOption(subList, FileOptUtil.FILE_WRITE,  DataDictionarySubEntity.class);
			entity.setMsgCode( msg );
		}else{
			entity.setMsgCode( DataDictionarySubEntity.IOCODE_NOUPDATE);
		}
		reloadDataDictonaryMainList();
    }
    
    /**
     * 删除数据字典主项
     * @param mList
     * @return
     */
    public Map<String , String > removeMain(List<DataDictionaryMainEntity> mList){
    	
    	DataDictionaryMainEntity entity = new DataDictionaryMainEntity();
    	if( null == mList || mList.isEmpty() ){
    		entity.setMsgCode( DataDictionaryMainEntity.IOCODE_PARAMERROR);
    		return entity.getMsgMap();
    	}
    	
    	List<DataDictionaryMainEntity> mainList = new ArrayList<DataDictionaryMainEntity>();
		FileOptUtil<DataDictionaryMainEntity> fileMain= new FileOptUtil<DataDictionaryMainEntity>();
		fileMain.fileOption(mainList, FileOptUtil.FILE_READ,  DataDictionaryMainEntity.class);
		
    	List<DataDictionarySubEntity> subList = new ArrayList<DataDictionarySubEntity>();
		FileOptUtil<DataDictionarySubEntity> fileSub= new FileOptUtil<DataDictionarySubEntity>();
		fileSub.fileOption(subList, FileOptUtil.FILE_READ,  DataDictionarySubEntity.class);
		
    	for( DataDictionaryMainEntity main : mList){
    		Iterator< DataDictionarySubEntity> subIter  = subList.iterator();
        	while( subIter.hasNext() ){
        		DataDictionarySubEntity sub = subIter.next() ;
        		if( main.getDdmeCode() .equals( sub.getDdsePCode()) ){
        			subIter.remove();
        		}
        	}
		}
    	
    	Iterator< DataDictionaryMainEntity> mainIter  = mainList.iterator();
    	while( mainIter.hasNext() ){
    		DataDictionaryMainEntity main = mainIter.next() ;
    		for(  DataDictionaryMainEntity rMain : mList ){
    			if( main.getDdmeId().equals( rMain.getDdmeId())){
    				mainIter.remove();
    			}
    		}
    	}
    	
    	String msgSub = fileSub.fileOption(subList, FileOptUtil.FILE_WRITE,  DataDictionarySubEntity.class);
    	String msgMain = fileMain.fileOption(mainList, FileOptUtil.FILE_WRITE,  DataDictionaryMainEntity.class);
    	
    	
    	if( "Success".equals(msgSub) && "Success".equals(msgMain) ){
    		entity.setMsgCode( msgMain);
    	}else{
    		entity.setMsgCode(  (! "Success".equals(msgSub)) ? msgSub:  msgMain) ;
    	}
    	reloadDataDictonaryMainList();
    	return entity.getMsgMap();
    }
    
    /**
     * 删除数据字典子项
     * @param subList
     * @return
     */
    public Map<String , String > removeSub( List<DataDictionarySubEntity> rList){
    	DataDictionarySubEntity entity = new DataDictionarySubEntity();
    	if( null == rList || rList.isEmpty() ){
    		entity.setMsgCode( DataDictionarySubEntity.IOCODE_NOUPDATE);
    		return entity.getMsgMap();
    	}
    	
    	List<DataDictionarySubEntity> subList = new ArrayList<DataDictionarySubEntity>();
		FileOptUtil<DataDictionarySubEntity> fileSub= new FileOptUtil<DataDictionarySubEntity>();
		fileSub.fileOption(subList, FileOptUtil.FILE_READ,  DataDictionarySubEntity.class);
		
		Iterator< DataDictionarySubEntity> subIter = subList.iterator();
		while( subIter.hasNext() ){
			DataDictionarySubEntity sub = subIter.next();
			for( DataDictionarySubEntity rSub : rList){
				if( sub.getDdseId().equals(rSub.getDdseId() )){
					subIter.remove();
				}
			}
		}
		
		String msg = fileSub.fileOption(subList, FileOptUtil.FILE_WRITE,  DataDictionarySubEntity.class);
		entity.setMsgCode(msg);
		reloadDataDictonaryMainList();
    	return entity.getMsgMap();
    }
    
    /**
     *  根据 数据字典主项  主键  编码， 类型编码  查询 数据字典完整结构信息
     * @param query
     * @return
     */
    public List<DataDictionaryMainEntity> queryMain( DataDictionaryMainEntity query){
    	
    	List<DataDictionaryMainEntity> mainList = new ArrayList<DataDictionaryMainEntity>();
		FileOptUtil<DataDictionaryMainEntity> fileMain= new FileOptUtil<DataDictionaryMainEntity>();
		fileMain.fileOption(mainList, FileOptUtil.FILE_READ,  DataDictionaryMainEntity.class);
		
    	List<DataDictionarySubEntity> subList = new ArrayList<DataDictionarySubEntity>();
		FileOptUtil<DataDictionarySubEntity> fileSub= new FileOptUtil<DataDictionarySubEntity>();
		fileSub.fileOption(subList, FileOptUtil.FILE_READ,  DataDictionarySubEntity.class);
    	
		List<DataDictionaryMainEntity> returnMainList = new ArrayList<DataDictionaryMainEntity>(); 
		// 查询项包括  主键， code， kind
		if( null != query ){
			
			if( null !=query.getDdmeId() && !"".equals(query.getDdmeId() ) ){
				for( DataDictionaryMainEntity main : mainList){
					if( query.getDdmeId().equals( main.getDdmeId() )){
						returnMainList.add(main);
						break;
					}
				}
			}
			
			if( null != query.getDdmeCode() && !"".equals(query.getDdmeCode() )){
				for( DataDictionaryMainEntity main : mainList){
					if( query.getDdmeCode().equals( main.getDdmeCode())){
						returnMainList.add( main);
						break;
					}
				}
			}
			
			if( null != query.getDdmeKind() && !"".equals( query.getDdmeKind() )){
				for( DataDictionaryMainEntity main : mainList){
					if( query.getDdmeKind() .equals( main.getDdmeKind() )) {
						returnMainList.add(main);
						break;
					}
				}
			}
			
			if( null != query.getDdmeKindName() && !"".equals( query.getDdmeKindName() )){
				for( DataDictionaryMainEntity main : mainList){
					if( main.getDdmeKindName().indexOf( query.getDdmeKindName()) != 0 ) {
						returnMainList.add(main);
						break;
					}
				}
			}
		}else{
			returnMainList.addAll( mainList);
		}
		
		// 根据结构进行拼装
		for( DataDictionaryMainEntity main : returnMainList ){
			main.setSubList( new ArrayList<DataDictionarySubEntity>());
			for( DataDictionarySubEntity sub : subList){
				if( sub.getDdsePCode().equals(main.getDdmeCode())){
					main.getSubList().add(sub);
				}
			}
		}
		
    	return returnMainList;
    }
    
    /**
     *   根据 数据字典子项  主键 ， 编码 ， 父节点， 查询 子项信息 
     * @param query
     * @return
     */
    public List<DataDictionarySubEntity> querySub( DataDictionarySubEntity query){
    	
    	List<DataDictionarySubEntity> subList = new ArrayList<DataDictionarySubEntity>();
		FileOptUtil<DataDictionarySubEntity> fileSub= new FileOptUtil<DataDictionarySubEntity>();
		fileSub.fileOption(subList, FileOptUtil.FILE_READ,  DataDictionarySubEntity.class);
		
		List<DataDictionarySubEntity> rList = new ArrayList<DataDictionarySubEntity>();
		
		if( query != null ){
			// 查询项包括 ：  	ddseId   ddseCode   ddsePCode
			if( null != query.getDdseId() && !"".equals( query.getDdseId() )){
				for( DataDictionarySubEntity sub : subList){
					if( query.getDdseId() .equals( sub.getDdseId() )){
						rList.add(sub);
						return rList;
					}
				}
			}
			
			if( null != query.getDdseCode() &&!"".equals( query.getDdseCode() )){
				for( DataDictionarySubEntity sub : subList){
					if( query.getDdseCode().equals( sub.getDdseCode() )){
						rList.add(sub);
						return rList;
					}
				}
			}
			
			if( null != query.getDdsePCode() && !"".equals( query.getDdsePCode() )){
				for( DataDictionarySubEntity sub : subList){
					if( query.getDdsePCode().equals( sub.getDdsePCode() )){
						rList.add(sub);
					}
				}
			}
		}else{
			rList.addAll( subList);
		}
		
		// 主结构拼装
		
		if( null == rList || rList.isEmpty()){
			return rList;
		}
		
		List<DataDictionaryMainEntity> mainList = new ArrayList<DataDictionaryMainEntity>();
		FileOptUtil<DataDictionaryMainEntity> fileMain= new FileOptUtil<DataDictionaryMainEntity>();
		fileMain.fileOption(mainList, FileOptUtil.FILE_READ,  DataDictionaryMainEntity.class);
		
		for(DataDictionarySubEntity sub : rList){
			for( DataDictionaryMainEntity main : mainList){
				if( sub.getDdsePCode().equals(main.getDdmeCode())){
					sub.setMain( main);
				}
			}
		}
    	
    	return rList;
    }
    
    
}
