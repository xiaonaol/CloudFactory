package com.neuedu.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.neuedu.model.DataDictionaryMainEntity;

/**
 * 用户管理表格代理
 * @author koala
 *
 */
public class DataDictionaryMainTableModelProxy extends AbstractTableModel {

    private static final long serialVersionUID = -3295581072864170310L;
    
    private String[] columnNames;

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    private List<DataDictionaryMainEntity> data;
    public void setData(List<DataDictionaryMainEntity> data) {
        this.data = data;
    }
    public List<DataDictionaryMainEntity> getData(){
    	return this.data;
    }

    public DataDictionaryMainTableModelProxy(String[] columnNames, List<DataDictionaryMainEntity> data) {
        this.columnNames = columnNames;
        this.data = null == data ? new ArrayList<DataDictionaryMainEntity>() : data;
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
    public List<DataDictionaryMainEntity> getSelectedRowDate(){
    	List<DataDictionaryMainEntity> rList = new ArrayList<DataDictionaryMainEntity>();
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
    public List<DataDictionaryMainEntity> removeSelectedRowDate(){
    	List<DataDictionaryMainEntity >  rList = new ArrayList<DataDictionaryMainEntity>();
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
    public void addSelectedRowDate( List<DataDictionaryMainEntity> addList){
    	for(DataDictionaryMainEntity se: addList ){
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
//   ,"编码","字典类型码","类型代码" , "子项数量" , "备注"
    	switch (columnIndex) {
		case 0:		//CheckBox
			return data.get(rowIndex).isSelectedRow();
		case 1:		//序号
			return String.valueOf(rowIndex+1);
		case 2:	//编码
			return data.get(rowIndex).getDdmeCode();
		case 3:		//字典类型码
			return data.get(rowIndex).getDdmeKind();
		case 4:		//类型名称
			return data.get(rowIndex).getDdmeKindName();
		case 5:		//子项数量
			return null != data.get(rowIndex).getSubList() ? data.get(rowIndex).getSubList().size(): 0 ;
		case 6:		//备注
			return data.get(rowIndex).getDdmeRemark();
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
