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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTable;

import com.neuedu.controller.LoginController;
import com.neuedu.controller.ProductManageController;
import com.neuedu.model.ProductEntity;
import com.neuedu.model.UserEntity;
import com.neuedu.util.ProductCheckHeaderCellRenderer;
import com.neuedu.util.ProductTableModelProxy;

public class ProductManage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField peName;
	private JTextField peCode;
	
	private JButton btnCreate;
	private JButton btnModify;
	private JButton btnRemove;
	private JButton btnQuery;
	private JButton btnReset;
	
	private JButton btnBack ; 
	private JButton btnLogout;
	private JLabel loginUserName;
	
	private JTable table;
	private JScrollPane	tablecroll;
	private ProductTableModelProxy tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductManage frame = new ProductManage();
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
	public ProductManage() {
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
		
		JLabel lblNewLabel = new JLabel("产品名称");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(50, 50, 80, 20);
		contentPane.add(lblNewLabel);
		
		peName = new JTextField();
		peName.setBounds(150, 50, 200, 20);
		contentPane.add(peName);
		peName.setColumns(10);
		
		JLabel label = new JLabel("产品编码");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(411, 50, 80, 20);
		contentPane.add(label);
		
		peCode = new JTextField();
		peCode.setColumns(10);
		peCode.setBounds(511, 50, 200, 20);
		contentPane.add(peCode);
		
		btnCreate = new JButton("新增");
		btnCreate.setBounds(330, 100, 80, 30);
		contentPane.add(btnCreate);
		
		btnModify = new JButton("修改");
		btnModify.setBounds(420, 100, 80, 30);
		contentPane.add(btnModify);
		
		btnRemove = new JButton("删除");
		btnRemove.setBounds(511, 100, 80, 30);
		contentPane.add(btnRemove);
		
		btnQuery = new JButton("查询");
		btnQuery.setBounds(601, 100, 80, 30);
		contentPane.add(btnQuery);
		
		btnReset = new JButton("重置");
		btnReset.setBounds(691, 100, 80, 30);
		contentPane.add(btnReset);
		
		table = new JTable();
		tablecroll= new JScrollPane(table);
		tablecroll.setBounds(10, 159, 764, 393);
		contentPane.add(tablecroll);
		
		pageInit();
	}

	private void pageInit(){
		this.setVisible(true);
		this.setTitle( " 东软智能云制造-产品管理");
		
		UserEntity loginUser = LoginController.getInstance().getLoginUser();
		if( null == loginUser ){
			JOptionPane.showMessageDialog(null , "未获取登陆状态， 请重新登陆","错误",JOptionPane.ERROR_MESSAGE);
			new Login();
			dispose();
		}else{
			loginUserName.setText( loginUser.getUeName());
		}
		
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
		
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRemoveAction();
			}
		});
		
		btnQuery.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnQueryAction();
			}
		});
		
		btnReset.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnResetAction();
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AdminWelcomePage();
				dispose();
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
		
		List<ProductEntity> deviceList = ProductManageController.getInstance().query(null);
		tableModel = new ProductTableModelProxy(ProductEntity.ENTITY_TITLE , deviceList );
		table.setModel(tableModel);
		table.getTableHeader().setDefaultRenderer(new ProductCheckHeaderCellRenderer(table));
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class,   r);
		
	}
	
	private void btnCreateAction(){
		new ProductEditDialog( this,  null );
	}
	
	private void btnModifyAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				new ProductEditDialog(this, tableModel.getSelectedRowDate().get(0));
			}else{
				JOptionPane.showMessageDialog(null, "只能选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void btnRemoveAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "是否确认删除", "删除确认", JOptionPane.YES_NO_OPTION)){
				Map<String, String>  rMap = ProductManageController.getInstance().remove(tableModel.getSelectedRowDate());
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
		ProductEntity query = new ProductEntity();
		query.setPeName( peName.getText());
		query.setPeCode(peCode.getText());
		tableModel.setData(ProductManageController.getInstance().query(query));
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
	
	private void btnResetAction(){
		peName.setText("");
		peCode.setText("");
	}
	
	public void tableReload(){
		tableModel.setData(ProductManageController.getInstance().query(null));
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
}
