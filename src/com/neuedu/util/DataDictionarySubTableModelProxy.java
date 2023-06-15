package com.neuedu.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.neuedu.model.DataDictionarySubEntity;

/**
 * 用户管理表格代理
 * @author koala
 *
 */
public class DataDictionarySubTableModelProxy extends AbstractTableModel {

    private static final long serialVersionUID = -3295581072864170310L;
    
    private String[] columnNames;

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    private List<DataDictionarySubEntity> data;
    public void setData(List<DataDictionarySubEntity> data) {
        this.data = data;
    }
    public List<DataDictionarySubEntity> getData(){
    	return this.data;
    }

    public DataDictionarySubTableModelProxy(String[] columnNames, List<DataDictionarySubEntity> data) {
        this.columnNames = columnNames;
        this.data = null == data ? new ArrayList<DataDictionarySubEntity>() : data;
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
    public List<DataDictionarySubEntity> getSelectedRowDate(){
    	List<DataDictionarySubEntity> rList = new ArrayList<DataDictionarySubEntity>();
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
    public List<DataDictionarySubEntity> removeSelectedRowDate(){
    	List<DataDictionarySubEntity >  rList = new ArrayList<DataDictionarySubEntity>();
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
    public void addSelectedRowDate( List<DataDictionarySubEntity> addList){
    	for(DataDictionarySubEntity se: addList ){
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
//,"编码","所属主项代码","所属主项名称","展示名称" , "项值" 
    	switch (columnIndex) {
		case 0:		//CheckBox
			return data.get(rowIndex).isSelectedRow();
		case 1:		//序号
			return String.valueOf(rowIndex+1);
		case 2:	//编码
			return data.get(rowIndex).getDdseCode();
		case 3:		//所属主项代码
			return data.get(rowIndex).getMain().getDdmeKind();
		case 4:		//所属主项名称
			return data.get(rowIndex).getMain().getDdmeKindName();
		case 5:		//展示名称
			return data.get(rowIndex).getDdseText();
		case 6:		//项值
			return data.get(rowIndex).getDdseValue();
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
