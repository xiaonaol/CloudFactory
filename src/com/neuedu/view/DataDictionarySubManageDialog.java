package com.neuedu.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.neuedu.controller.DataDictionaryManageController;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.DataDictionarySubEntity;
import com.neuedu.util.DataDictionarySubCheckHeaderCellRenderer;
import com.neuedu.util.DataDictionarySubTableModelProxy;

/**
 * 数据字典编辑页面
 * 
 * @author koala
 *
 */
public class DataDictionarySubManageDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	
	private JLabel ddmeKindName ;
	private JLabel ddmeKind ;
	
	private JTable table;
	private JScrollPane	tablecroll;
	private DataDictionarySubTableModelProxy tableModel;
	
	private JButton btnCreate;
	private JButton btnModify;
	private JButton btnRemove;
	private JButton btnBack;
	
	private  DataDictionaryManage parentWindow ;
	private DataDictionaryMainEntity parentData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDictionarySubManageDialog dialog = new DataDictionarySubManageDialog( null , null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDictionarySubManageDialog( DataDictionaryManage _parentWindow , DataDictionaryMainEntity _parentData) {
		this.parentWindow = _parentWindow;
		this.parentData = _parentData;
		setBounds(100, 100, 550, 445);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("字典名称");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(50, 30, 80, 20);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel label = new JLabel("字典类型码");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(270, 30, 80, 20);
			contentPanel.add(label);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("字典项子项");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setBounds(10, 85, 100, 20);
			contentPanel.add(lblNewLabel_1);
		}
		{
			table = new JTable();
			tablecroll= new JScrollPane(table);
			tablecroll.setBounds(10, 131, 514, 270);
			contentPanel.add(tablecroll);
		}
		{
			btnCreate = new JButton("新增");
			btnCreate.setBounds(170, 80, 80, 30);
			contentPanel.add(btnCreate);
		}
		{
			btnModify = new JButton("修改");
			btnModify.setBounds(350, 80, 80, 30);
			contentPanel.add(btnModify);
		}
		{
			btnRemove = new JButton("删除");
			btnRemove.setBounds(260, 80, 80, 30);
			contentPanel.add(btnRemove);
		}
		{
			ddmeKindName = new JLabel("");
			ddmeKindName.setBounds(130, 30, 130, 20);
			contentPanel.add(ddmeKindName);
		}
		{
			ddmeKind = new JLabel("");
			ddmeKind.setBounds(350, 30, 130, 20);
			contentPanel.add(ddmeKind);
		}
		{
			btnBack = new JButton("返回");
			btnBack.setBounds(440, 80, 80, 30);
			contentPanel.add(btnBack);
		}
		pageInit();
	}
	
	private void pageInit(){
		this.setVisible(true);
		
		btnCreate.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCreateAction();
			}
		});
		
		btnModify.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnModifyAction();
			}
		});
		
		btnRemove.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRemoveAction();
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnBackAction();
			}
		});
		
		
		this.setTitle( "东软只能云制造-数据字典项子项管理 ");
		ddmeKindName.setText( parentData.getDdmeKindName());
		ddmeKind.setText(parentData.getDdmeKind());
		
		// 表格加载
		
		DataDictionarySubEntity query = new DataDictionarySubEntity();
		query.setDdsePCode( parentData.getDdmeCode());
		List<DataDictionarySubEntity > rList  = DataDictionaryManageController.getInstance().querySub(query);
        tableModel = new DataDictionarySubTableModelProxy(DataDictionarySubEntity.ENTITY_TITLE , rList );
        table.setModel(tableModel);
        table.getTableHeader().setDefaultRenderer(new DataDictionarySubCheckHeaderCellRenderer(table));
        DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
        r.setHorizontalAlignment(JLabel.CENTER);   
        table.setDefaultRenderer(Object.class,   r);
	}
	
	private void btnCreateAction(){
		parentData.setSubList(null);
		new DataDitionarySubEditDialog(this , parentData);
	}
	
	private void btnModifyAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				parentData.setSubList( new ArrayList<DataDictionarySubEntity>());
				parentData.getSubList().add(tableModel.getSelectedRowDate().get(0));
				new DataDitionarySubEditDialog(this,  parentData);
			}else{
				JOptionPane.showMessageDialog(null, "只能选择一条记录维护信息", "提示", JOptionPane.WARNING_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录维护信息", "提示", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void btnRemoveAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "是否删除选中数据字典子项", "删除确认", JOptionPane.YES_NO_OPTION)){
				Map<String, String>  rMap =  DataDictionaryManageController.getInstance().removeSub(tableModel.getSelectedRowDate());
				if( "success".equals(rMap.get("state"))){
					JOptionPane.showMessageDialog(null,"删除成功", "删除成功", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, rMap.get("msg"), "删除失败", JOptionPane.ERROR_MESSAGE);
				}
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录进行删除", "提示", JOptionPane.WARNING_MESSAGE);
		}
		tableReload();
	}
	
	private void btnBackAction(){
		parentWindow.tableReload();
		dispose();
	}
	
	public void tableReload(){
		DataDictionarySubEntity query = new DataDictionarySubEntity();
		query.setDdsePCode( parentData.getDdmeCode());
		tableModel.setData( DataDictionaryManageController.getInstance().querySub(query ));
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
}
