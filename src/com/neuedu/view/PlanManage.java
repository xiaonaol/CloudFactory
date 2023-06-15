package com.neuedu.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;


import com.neuedu.controller.LoginController;
import com.neuedu.controller.PlanManageController;
import com.neuedu.model.ComBoxEntity;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.PlanEntity;
import com.neuedu.model.UserEntity;
import com.neuedu.util.PlanManageCheckHeaderCellRenderer;
import com.neuedu.util.PlanManageTableModelProxy;

public class PlanManage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField peName;
	private JTextField peCode;
	private JTextField peOrderCode;
	private JComboBox<ComBoxEntity> peState ;
	
	private JTable table;
	private JScrollPane	tablecroll;
	private PlanManageTableModelProxy tableModel;
	
	private JButton btnCreate;
	private JButton btnModify;
	private JButton btnRemove;
	private JButton btnQuery;
	private JButton btnReset;
	private JButton btnDeliver;
	private JButton btnBack;
	private JButton btnLogout;
	private JLabel loginUserName;
	
	private UserEntity loginUser;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlanManage frame = new PlanManage();
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
	public PlanManage() {
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
		
		JLabel lblNewLabel = new JLabel("计划名称");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(50, 50, 80, 20);
		contentPane.add(lblNewLabel);
		
		peName = new JTextField();
		peName.setBounds(150, 50, 200, 20);
		contentPane.add(peName);
		peName.setColumns(10);
		
		JLabel label = new JLabel("计划编号");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(420, 50, 80, 20);
		contentPane.add(label);
		
		peCode = new JTextField();
		peCode.setColumns(10);
		peCode.setBounds(520, 50, 200, 20);
		contentPane.add(peCode);
		
		JLabel label_1 = new JLabel("订单编号");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(50, 90, 80, 20);
		contentPane.add(label_1);
		
		peOrderCode = new JTextField();
		peOrderCode.setColumns(10);
		peOrderCode.setBounds(150, 90, 200, 20);
		contentPane.add(peOrderCode);
		
		JLabel label_2 = new JLabel("计划状态");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(420, 90, 80, 20);
		contentPane.add(label_2);
		
		peState = new JComboBox<ComBoxEntity>();
		peState.setBounds(520, 90, 200, 20);
		contentPane.add(peState);
		
		btnCreate = new JButton("新增");
		btnCreate.setBounds(270, 130, 80, 30);
		contentPane.add(btnCreate);
		
		btnModify = new JButton("修改");
		btnModify.setBounds(360, 130, 80, 30);
		contentPane.add(btnModify);
		
		btnRemove = new JButton("删除");
		btnRemove.setBounds(450, 130, 80, 30);
		contentPane.add(btnRemove);
		
		btnQuery = new JButton("查询");
		btnQuery.setBounds(540, 130, 80, 30);
		contentPane.add(btnQuery);
		
		btnReset = new JButton("重置");
		btnReset.setBounds(630, 130, 80, 30);
		contentPane.add(btnReset);
		
		btnDeliver = new JButton("发货");
		btnDeliver.setBounds(180, 130, 80, 30);
		contentPane.add(btnDeliver);
		
		table = new JTable();
		tablecroll= new JScrollPane(table);
		tablecroll.setBounds(10, 184, 764, 368);
		contentPane.add(tablecroll);
		
		 pageInit();
	}

	private void pageInit(){
		this.setVisible(true);
		this.setTitle( "东软智能云制造 - 生产计划管理" );
		
		loginUser = LoginController.getInstance().getLoginUser();
		if( null == loginUser ){
			JOptionPane.showMessageDialog(null , "未获取登陆状态， 请重新登陆","错误",JOptionPane.ERROR_MESSAGE);
			new Login();
			dispose();
		}else{
			loginUserName.setText( loginUser.getUeName());
		}
		
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
		
		btnDeliver.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnDeliverAction();
			}
		});
		
		btnBack.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new WelcomePage();
				dispose();
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginController.getInstance().doLogout();
				new Login() ; 
				dispose();
			}
		});
		
		peState.addItem( new ComBoxEntity("《--------请选择--------》",""));
		new DataDictionaryMainEntity( DataDictionaryMainEntity.DD_PLAN_STATE, peState);
		
		// 表格信息初始化
		List<PlanEntity> deviceList = PlanManageController.getInstance().planQuery(null);
		tableModel = new PlanManageTableModelProxy(PlanEntity.ENTITY_TITLE , deviceList );
		table.setModel(tableModel);
		table.getTableHeader().setDefaultRenderer(new PlanManageCheckHeaderCellRenderer(table));
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class,   r);
		
	}
	
	private void btnCreateAction(){
		new PlanEditDialog(this , null );
	}
	
	private void btnModifyAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				new PlanEditDialog(this, tableModel.getSelectedRowDate().get(0));
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
				Map<String, String>  rMap =PlanManageController.getInstance().planRemove(tableModel.getSelectedRowDate());
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
		PlanEntity query = new PlanEntity();
		query.setPeName(peName.getText());
		query.setPeCode(peCode.getText());
		query.setPeOrderCode(peOrderCode.getText());
		query.setPeState(((ComBoxEntity) peState.getSelectedItem()).getComValue());
		tableModel.setData(  PlanManageController.getInstance().planQuery(query));
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
	
	private void btnResetAction(){
		peName.setText( "");
		peCode.setText( "");
		peOrderCode.setText( "" );
		peState.setSelectedIndex(0);
	}
	
	private void btnDeliverAction(){
		
			// 判断 生产状态， 如果生产状态均为生产完成， 则可进行状态变更
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				
				PlanEntity pe = tableModel.getSelectedRowDate().get(0);
				if( "1".equals(pe.getPeState())){
					pe.setPeState("2");
					PlanManageController.getInstance().planModify(pe);
					if( "success".equals(pe.getMsgMap().get("state"))){
						JOptionPane.showMessageDialog(null, "完成发货", "提示", JOptionPane.INFORMATION_MESSAGE);
						tableReload();
					}else{
						JOptionPane.showMessageDialog(null, pe.getMsgMap().get("msg"), "提示", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "未完成生产， 不能发货", "提示", JOptionPane.WARNING_MESSAGE);
				}
				
			}else{
				JOptionPane.showMessageDialog(null, "只能选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
		}
		
		
	}
	
	public void tableReload(){
		tableModel.setData(  PlanManageController.getInstance().planQuery(null) );
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
}
