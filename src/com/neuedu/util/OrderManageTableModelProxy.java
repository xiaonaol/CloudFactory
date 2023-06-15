package com.neuedu.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.neuedu.model.OrderEntity;


/**
 * 用户管理表格代理
 * @author koala
 *
 */
public class OrderManageTableModelProxy extends AbstractTableModel {

    private static final long serialVersionUID = -3295581072864170310L;
    
    private String[] columnNames;

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    private List<OrderEntity> data;
    public void setData(List<OrderEntity> data) {
        this.data = data;
    }
    public List<OrderEntity> getData(){
    	return this.data;
    }

    public OrderManageTableModelProxy(String[] columnNames, List<OrderEntity> data) {
        this.columnNames = columnNames;
        this.data = null == data ? new ArrayList<OrderEntity>() : data;
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
    public List<OrderEntity> getSelectedRowDate(){
    	List<OrderEntity> rList = new ArrayList<OrderEntity>();
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
    public List<OrderEntity> removeSelectedRowDate(){
    	List<OrderEntity >  rList = new ArrayList<OrderEntity>();
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
    public void addSelectedRowDate( List<OrderEntity> addList){
    	for(OrderEntity se: addList ){
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
    		//"订单名称","订单编码","产品名称","交付时间","投标截止时间","接收人","接收人电话",,"订单状态"
    	switch (columnIndex) {
		case 0:		//CheckBox
			return data.get(rowIndex).isSelectedRow();
		case 1:		//序号
			return String.valueOf(rowIndex+1);
		case 2:	//订单名称
			return data.get(rowIndex).getOeName();
		case 3:		//订单编码
			return data.get(rowIndex).getOeCode();
		case 4:		// 产品名称
			return data.get(rowIndex).getProduct().getPeName();
		case 5:		// 订单数量
			return data.get(rowIndex).getOdeCount();
		case 6:		//交付时间
			return CommonUtil.date2Str(data.get(rowIndex).getOeDliverDate(), new  SimpleDateFormat(CommonUtil.DATE_FORMATE_TYPE_NOTIME));
		case 7:		//投标截止时间
			return CommonUtil.date2Str(data.get(rowIndex).getOeCutOffDate(), new  SimpleDateFormat(CommonUtil.DATE_FORMATE_TYPE_NOTIME));
		case 8:		//接收人
			return data.get(rowIndex).getOeReceiver();
		case 9:		//电话
			return data.get(rowIndex).getOeReceiverTel();
		case 10:		//订单状态
			return data.get(rowIndex).getState().getDdseText();
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
