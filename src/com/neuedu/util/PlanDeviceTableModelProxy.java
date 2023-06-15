package com.neuedu.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.neuedu.model.PlanDeviceEntity;

/**
 * 表格代理
 * @author koala
 *
 */
public class PlanDeviceTableModelProxy extends AbstractTableModel {

    private static final long serialVersionUID = -3295581072864170310L;
    
    private String[] columnNames;

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    private List<PlanDeviceEntity> data;
    public void setData(List<PlanDeviceEntity> data) {
        this.data = data;
    }
    public List<PlanDeviceEntity> getData(){
    	return this.data;
    }

    public PlanDeviceTableModelProxy(String[] columnNames, List<PlanDeviceEntity> data) {
        this.columnNames = columnNames;
        this.data = null == data ? new ArrayList<PlanDeviceEntity>() : data;
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
    public List<PlanDeviceEntity> getSelectedRowDate(){
    	List<PlanDeviceEntity> rList = new ArrayList<PlanDeviceEntity>();
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
    public List<PlanDeviceEntity> removeSelectedRowDate(){
    	List<PlanDeviceEntity >  rList = new ArrayList<PlanDeviceEntity>();
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
    public void addSelectedRowDate( List<PlanDeviceEntity> addList){
    	for(PlanDeviceEntity se: addList ){
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
    		//"设备名称","设备编号","生产开始时间","生产结束时间","生产数量","生产状态"}; 
    	switch (columnIndex) {
		case 0:		//CheckBox
			return data.get(rowIndex).isSelectedRow();
		case 1:		//序号
			return String.valueOf(rowIndex+1);
		case 2:	//设备名称
			return data.get(rowIndex).getDevice().getDeName();
		case 3:		//设备编号
			return data.get(rowIndex).getDevice().getDeCode();
		case 4:		// 开始时间
			return CommonUtil.date2Str(data.get(rowIndex).getPdeDeviceStart(), new SimpleDateFormat(CommonUtil.DATE_FORMATE_TYPE_NOTIME));
		case 5:		// 结束时间
			return CommonUtil.date2Str(data.get(rowIndex).getPdeDeviceEnd(), new SimpleDateFormat(CommonUtil.DATE_FORMATE_TYPE_NOTIME));
		case 6:		//生产数量
			return data.get(rowIndex).getPdeProductCount();
		case 7:		//生产状态
			switch (data.get(rowIndex).getPdeState()) {
			case "0":return "生产中";
			case "1":return "生产完成";
			}
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
