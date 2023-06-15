package com.neuedu.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.neuedu.model.PlanEntity;

/**
 * 表格代理
 * @author koala
 *
 */
public class PlanManageTableModelProxy extends AbstractTableModel {

    private static final long serialVersionUID = -3295581072864170310L;
    
    private String[] columnNames;

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    private List<PlanEntity> data;
    public void setData(List<PlanEntity> data) {
        this.data = data;
    }
    public List<PlanEntity> getData(){
    	return this.data;
    }

    public PlanManageTableModelProxy(String[] columnNames, List<PlanEntity> data) {
        this.columnNames = columnNames;
        this.data = null == data ? new ArrayList<PlanEntity>() : data;
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
    public List<PlanEntity> getSelectedRowDate(){
    	List<PlanEntity> rList = new ArrayList<PlanEntity>();
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
    public List<PlanEntity> removeSelectedRowDate(){
    	List<PlanEntity >  rList = new ArrayList<PlanEntity>();
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
    public void addSelectedRowDate( List<PlanEntity> addList){
    	for(PlanEntity se: addList ){
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
    	//"计划名称","计划编号","产品名称","订单名称","订单编号","创建人","创建时间","计划状态"}; 
    	switch (columnIndex) {
		case 0:		//CheckBox
			return data.get(rowIndex).isSelectedRow();
		case 1:		//序号
			return String.valueOf(rowIndex+1);
		case 2:	//计划名称
			return data.get(rowIndex).getPeName();
		case 3:		//计划编号
			return data.get(rowIndex).getPeCode();
		case 4:		// 产品名称
			return data.get(rowIndex).getProduct().getPeName();
		case 5:		//订单名称
			return data.get(rowIndex).getOrder().getOeName();
		case 6:		//订单编号
			return data.get(rowIndex).getOrder().getOeCode();
		case 7:		//创建人
			return data.get(rowIndex).getCreater().getUeName();
		case 8:		//创建时间
			return  CommonUtil.date2Str(data.get(rowIndex).getPeCreate(), new SimpleDateFormat(CommonUtil.DATE_FORMATE_TYPE_NOTIME));
		case 9:		//计划状态
			switch (data.get(rowIndex).getPeState()) {
			case "0":return "生产中";
			case "1":return "生产完成";
			case "2":return "已发货";
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
