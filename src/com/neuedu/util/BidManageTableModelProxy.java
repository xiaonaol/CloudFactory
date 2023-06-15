package com.neuedu.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.neuedu.model.BidEntity;

/**
 * 表格代理
 * @author koala
 *
 */
public class BidManageTableModelProxy extends AbstractTableModel {

    private static final long serialVersionUID = -3295581072864170310L;
    
    private String[] columnNames;

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    private List<BidEntity> data;
    public void setData(List<BidEntity> data) {
        this.data = data;
    }
    public List<BidEntity> getData(){
    	return this.data;
    }

    public BidManageTableModelProxy(String[] columnNames, List<BidEntity> data) {
        this.columnNames = columnNames;
        this.data = null == data ? new ArrayList<BidEntity>() : data;
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
    public List<BidEntity> getSelectedRowDate(){
    	List<BidEntity> rList = new ArrayList<BidEntity>();
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
    public List<BidEntity> removeSelectedRowDate(){
    	List<BidEntity >  rList = new ArrayList<BidEntity>();
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
    public void addSelectedRowDate( List<BidEntity> addList){
    	for(BidEntity se: addList ){
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
    		//订单编号","订单名称","投标人","投标人工厂","投标时间","单价","承接数量","竞标状态" } ; 
    	switch (columnIndex) {
		case 0:		//CheckBox
			return data.get(rowIndex).isSelectedRow();
		case 1:		//序号
			return String.valueOf(rowIndex+1);
		case 2:	//订单编号
			return data.get(rowIndex).getOrder().getOeCode();
		case 3:		//订单名称
			return data.get(rowIndex).getOrder().getOeName();
		case 4:		// 投标人
			return data.get(rowIndex).getBidUser().getUeName();
		case 5:		//投标人工厂
			return data.get(rowIndex).getBidUser().getFactory().getFeName();
		case 6:		//投标时间
			return CommonUtil.date2Str(data.get(rowIndex).getBeCreate(), new SimpleDateFormat(CommonUtil.DATE_FORMATE_TYPE_NOTIME));
		case 7:		//单价
			return data.get(rowIndex).getBePrice();
		case 8:		//承接数量
			return data.get(rowIndex).getBeCount();
		case 9:		//竞标状态
			switch (data.get(rowIndex).getBeState()) {
			case "0":return "已投标";
			case "1":return "中标";
			case "2":return "未中标";
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
