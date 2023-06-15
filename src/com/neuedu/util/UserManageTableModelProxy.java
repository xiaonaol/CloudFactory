package com.neuedu.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.neuedu.model.UserEntity;

/**
 * 用户管理表格代理
 * @author koala
 *
 */
public class UserManageTableModelProxy extends AbstractTableModel {

    private static final long serialVersionUID = -3295581072864170310L;
    
    private String[] columnNames;

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    private List<UserEntity> data;
    public void setData(List<UserEntity> data) {
        this.data = data;
    }
    public List<UserEntity> getData(){
    	return this.data;
    }

    public UserManageTableModelProxy(String[] columnNames, List<UserEntity> data) {
        this.columnNames = columnNames;
        this.data = null == data ? new ArrayList<UserEntity>() : data;
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
    public List<UserEntity> getSelectedRowDate(){
    	List<UserEntity> rList = new ArrayList<UserEntity>();
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
    public List<UserEntity> removeSelectedRowDate(){
    	List<UserEntity >  rList = new ArrayList<UserEntity>();
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
    public void addSelectedRowDate( List<UserEntity> addList){
    	for(UserEntity se: addList ){
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
    		//"姓名","编码","性别","年龄","角色","邮箱","电话","账号状态"
    	switch (columnIndex) {
		case 0:		//CheckBox
			return data.get(rowIndex).isSelectedRow();
		case 1:		//序号
			return String.valueOf(rowIndex+1);
		case 2:	//姓名
			return data.get(rowIndex).getUeName();
		case 3:	//账号
			return data.get(rowIndex).getUeAccount();
		case 4:		//编码
			return data.get(rowIndex).getUeCode();
		case 5:		// 性别
			return data.get(rowIndex).getGender().getDdseText();
		case 6:		//年龄
			return CommonUtil.calculatAge(data.get(rowIndex).getUeBirthday());
		case 7:		//角色
			return data.get(rowIndex).getRole().getDdseText();
		case 8:		//邮箱
			return data.get(rowIndex).getUeEmail();
		case 9:		//电话
			return data.get(rowIndex).getUeTel();
		case 10:		//账号状态
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
