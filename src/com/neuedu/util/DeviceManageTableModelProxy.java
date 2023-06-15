package com.neuedu.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.neuedu.model.DeviceEntity;

/**
 * 表格代理
 * @author koala
 *
 */
public class DeviceManageTableModelProxy extends AbstractTableModel {

    private static final long serialVersionUID = -3295581072864170310L;
    
    private String[] columnNames;

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    private List<DeviceEntity> data;
    public void setData(List<DeviceEntity> data) {
        this.data = data;
    }
    public List<DeviceEntity> getData(){
    	return this.data;
    }

    public DeviceManageTableModelProxy(String[] columnNames, List<DeviceEntity> data) {
        this.columnNames = columnNames;
        this.data = null == data ? new ArrayList<DeviceEntity>() : data;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return null == getValueAt(0, columnIndex) ? String.class: getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex == 0 ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	switch (columnIndex) {
		case 0:		//CheckBox
			 data.get(rowIndex).setSelectedRow((boolean)aValue);
			 break;
		default:
			 break;
		}
        fireTableCellUpdated(rowIndex, columnIndex);
    }
	
	/**
	 * 获取选中行数据
	 * @return
	 */
    public List<DeviceEntity> getSelectedRowDate(){
    	List<DeviceEntity> rList = new ArrayList<DeviceEntity>();
    	for(int i = 0 ; i< data.size() ; i ++){
    		if(data.get(i).isSelectedRow()){
    			rList.add(data.get(i));
    		}
    	}
    	return rList;
    }
    
    /**
     * 删除选中内容
     * @return
     */
    public List<DeviceEntity> removeSelectedRowDate(){
    	List<DeviceEntity >  rList = new ArrayList<DeviceEntity>();
    	for(int i = 0 ; i< data.size() && i>=0 ; i ++){
    		if(data.get(i).isSelectedRow()){
    			data.remove(i--);
    		}
    	}
    	return rList;
    }
    
    /**
     * 向表格中添加数据
     * @param addList
     */
    public void addSelectedRowDate( List<DeviceEntity> addList){
    	for(DeviceEntity se: addList ){
    		data.add(se);
    	}
    }

    @Override
    public int getRowCount() {
        return null == data ?0: data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    		//"设备名称","设备编号","类型","规格","设备状态","设备所有人","设备租用状态","设备租用人","租用时间"
    	switch (columnIndex) {
		case 0:		//CheckBox
			return data.get(rowIndex).isSelectedRow();
		case 1:		//序号
			return String.valueOf(rowIndex+1);
		case 2:	//设备名称
			return data.get(rowIndex).getDeName();
		case 3:		//设备编码
			return data.get(rowIndex).getDeCode();
		case 4:		// 设备类型
			return data.get(rowIndex).getDeviceType().getDdseText();
		case 5:		//设备规格
			return data.get(rowIndex).getDeSpecifications();
		case 6:		//设备状态
			return data.get(rowIndex).getDeviceState().getDdseText();
		case 7:		//设备所有人
			return data.get(rowIndex).getUserCreate().getUeName();
		case 8:		//租用状态
			if( "admin".equals(data.get(rowIndex).getUserCreate().getUeRole())){
				return  "1".equals(data.get(rowIndex).getDeRentState()) ? "租用中":"空闲";
			}
			return "";
		case 9:		//租用人
			return null != data.get(rowIndex).getUserRent() ? data.get(rowIndex).getUserRent().getUeName() : ""  ;
		case 10:		//租用时间
			return  null != data.get(rowIndex).getDeRentDate()? CommonUtil.date2Str(data.get(rowIndex).getDeRentDate(), new SimpleDateFormat(CommonUtil.DATE_FORMATE_TYPE_NOTIME)): "" ;
		default:
			return data;
		}
    }

    public void selectAllOrNull(boolean value){
        // Select All. The last column
        for(int index = 0; index < getRowCount(); index ++){
            this.setValueAt(value, index, 0);
        }
    }
    
}
