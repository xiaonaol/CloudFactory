package com.neuedu.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTable;

import com.neuedu.controller.DataDictionaryManageController;
import com.neuedu.controller.LoginController;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.UserEntity;
import com.neuedu.util.DataDictionaryMainCheckHeaderCellRenderer;
import com.neuedu.util.DataDictionaryMainTableModelProxy;

/**
 * 数据字典管理页面
 * @author koala
 *
 */
public class DataDictionaryManage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ddName;
	private JTextField ddKind;
	
	private JTable table;
	private JScrollPane	tablecroll;
	private DataDictionaryMainTableModelProxy tableModel;
	
	private JButton btnCreate ;
	private JButton btnModify;
	private JButton btnRemove;
	private JButton btnQuery;
	private JButton btnReset;
	
	private JButton btnBack;
	private JButton btnLogout;
	private JLabel loginUserName;
	private JButton btnSubManage;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataDictionaryManage frame = new DataDictionaryManage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DataDictionaryManage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnLogout = new JButton("注销");
		btnLogout.setBounds(724, 0, 60, 20);
		contentPane.add(btnLogout);
		
		btnBack = new JButton("返回");
		btnBack.setBounds(664, 0, 60, 20);
		contentPane.add(btnBack);
		
		loginUserName = new JLabel("");
		loginUserName.setBounds(557, 0, 109, 20);
		contentPane.add(loginUserName);
		
		ddName = new JTextField();
		ddName.setBounds(150, 50, 200, 20);
		contentPane.add(ddName);
		ddName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("字典项名称");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(50, 50, 80, 20);
		contentPane.add(lblNewLabel);
		
		ddKind = new JTextField();
		ddKind.setColumns(10);
		ddKind.setBounds(510, 50, 200, 20);
		contentPane.add(ddKind);
		
		JLabel label = new JLabel("字典项代码");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(410, 50, 80, 20);
		contentPane.add(label);
		
		btnCreate = new JButton("新增");
		btnCreate.setBounds(310, 100, 80, 30);
		contentPane.add(btnCreate);
		
		btnModify = new JButton("修改");
		btnModify.setBounds(400, 100, 80, 30);
		contentPane.add(btnModify);
		
		btnRemove = new JButton("删除");
		btnRemove.setBounds(490, 100, 80, 30);
		contentPane.add(btnRemove);
		
		btnQuery = new JButton("查询");
		btnQuery.setBounds(580, 100, 80, 30);
		contentPane.add(btnQuery);
		
		btnReset = new JButton("重置");
		btnReset.setBounds(670, 100, 80, 30);
		contentPane.add(btnReset);
		
		table = new JTable();
		tablecroll= new JScrollPane(table);
		tablecroll.setBounds(10, 164, 764, 388);
		contentPane.add(tablecroll);
		
		btnSubManage = new JButton("子项管理");
		btnSubManage.setBounds(220, 100, 80, 30);
		contentPane.add(btnSubManage);
		
		pageInit();
	}
	
	private void pageInit(){
		this.setVisible(true);
		this.setTitle("东软智能云制造-数据字典管理");
		UserEntity loginUser = LoginController.getInstance().getLoginUser();
		if( null == loginUser){
			JOptionPane.showMessageDialog(null, "未获取到登陆状态， 请重新登陆", "无状态", JOptionPane.ERROR_MESSAGE);
			new Login();
			dispose();
			return;
		}
		loginUserName.setText( loginUser.getUeName() );
		
		btnCreate.addActionListener(new ActionListener() {
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
		
		btnQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnQueryAction();
			}
		});
		
		btnSubManage.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSubManageAction();
			}
		});
		
		btnBack.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AdminWelcomePage();
				dispose();
			}
		});
		
		btnReset.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnResetAction();
			}
		});
		
		btnLogout.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginController.getInstance().doLogout();
				new Login();
				dispose();
			}
		});
		
		List<DataDictionaryMainEntity > rList  = DataDictionaryManageController.getInstance().queryMain(null);
        tableModel = new DataDictionaryMainTableModelProxy(DataDictionaryMainEntity.ENTITY_TITLE , rList );
        table.setModel(tableModel);
        table.getTableHeader().setDefaultRenderer(new DataDictionaryMainCheckHeaderCellRenderer(table));
        DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
        r.setHorizontalAlignment(JLabel.CENTER);   
        table.setDefaultRenderer(Object.class,   r);
		
	}
	
	private void btnSubManageAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				new DataDictionarySubManageDialog(this , tableModel.getSelectedRowDate().get(0) );
			}else{
				JOptionPane.showMessageDialog(null, "只能选择一条记录维护子项信息", "提示", JOptionPane.WARNING_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录维护子项信息", "提示", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void btnCreateAction(){
		new DataDictionaryMainEditDialog( this , null );
	}
	
	private void btnModifyAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				new DataDictionaryMainEditDialog(this,   tableModel.getSelectedRowDate().get(0));
			}else{
				JOptionPane.showMessageDialog(null, "只能选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void btnRemoveAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "即将删除数据字典项以及其所有子项", "删除确认", JOptionPane.YES_NO_OPTION)){
				Map<String, String>  rMap =  DataDictionaryManageController.getInstance().removeMain(tableModel.getSelectedRowDate());
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
	
	private void btnQueryAction(){
		DataDictionaryMainEntity main = null;
		
		if( (null != ddKind.getText() && !"".equals(ddKind.getText() )) || ( null != ddName.getText() && !"".equals(ddName.getText()))){
			main = new DataDictionaryMainEntity();
			main.setDdmeKind( ddKind.getText());
			main.setDdmeKindName( ddName.getText());
		}
		tableModel.setData( DataDictionaryManageController.getInstance().queryMain(main));
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
	
	private void btnResetAction(){
		this.ddKind.setText("");
		this.ddName.setText("");
	}
	
	/**
	 * 表格重载
	 */
	public void tableReload(){
		tableModel.setData( DataDictionaryManageController.getInstance().queryMain(null));
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
	
}
