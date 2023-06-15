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

import com.neuedu.controller.BidManageController;
import com.neuedu.controller.LoginController;
import com.neuedu.controller.OrderManageController;
import com.neuedu.controller.ProductManageController;
import com.neuedu.model.ComBoxEntity;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.OrderEntity;
import com.neuedu.model.ProductEntity;
import com.neuedu.model.UserEntity;
import com.neuedu.util.OrderManageCheckHeaderCellRenderer;
import com.neuedu.util.OrderManageTableModelProxy;

public class OrderManage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField oeName;
	private JTextField oeCode;
	private JComboBox<ComBoxEntity> oeState;
	private JComboBox<ComBoxEntity> oeProduct ;
	private JTable table;
	private JScrollPane	tablecroll;
	private OrderManageTableModelProxy tableModel;
	
	private JButton btnQuery;
	private JButton btnReset;
	
	private UserEntity loginUser;
	
	private JButton btnBack;
	private JButton btnLogout;
	private JLabel loginUserName;
 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderManage frame = new OrderManage();
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
	public OrderManage() {
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
		
		JLabel lblNewLabel = new JLabel("订单名称");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(50, 50, 80, 20);
		contentPane.add(lblNewLabel);
		
		oeName = new JTextField();
		oeName.setBounds(150, 50, 200, 20);
		contentPane.add(oeName);
		oeName.setColumns(10);
		
		JLabel label = new JLabel("订单编号");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(420, 50, 80, 20);
		contentPane.add(label);
		
		oeCode = new JTextField();
		oeCode.setColumns(10);
		oeCode.setBounds(520, 50, 200, 20);
		contentPane.add(oeCode);
		
		JLabel label_1 = new JLabel("订单状态");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(50, 100, 80, 20);
		contentPane.add(label_1);
		
		oeState = new JComboBox<ComBoxEntity>();
		oeState.setBounds(150, 100, 200, 20);
		contentPane.add(oeState);
		
		JLabel label11 = new JLabel("订单产品");
		label11.setHorizontalAlignment(SwingConstants.CENTER);
		label11.setBounds(420, 100, 80, 20);
		contentPane.add(label11);
		
		oeProduct = new JComboBox<ComBoxEntity>();
		oeProduct.setBounds(520, 100, 200, 20);
		contentPane.add(oeProduct);
		
		btnQuery = new JButton("查询");
		btnQuery.setBounds(560, 150, 80, 30);
		contentPane.add(btnQuery);
		
		btnReset = new JButton("重置");
		btnReset.setBounds(650, 150, 80, 30);
		contentPane.add(btnReset);
		
		table = new JTable();
		tablecroll= new JScrollPane(table);
		tablecroll.setBounds(10, 201, 764, 351);
		contentPane.add(tablecroll);
		
		pageInit();
		
	}
	
	private void pageInit(){
		this.setVisible( true);
		this.setTitle("东软智能云制造-订单管理");
		
		loginUser = LoginController.getInstance().getLoginUser();
		if( null == loginUser){
			JOptionPane.showMessageDialog( null , "未获取当前登陆状态，请重新登陆","错误", JOptionPane.ERROR_MESSAGE);
			new Login();
			dispose();
		}else{
			loginUserName.setText( loginUser.getUeName() );
		}
		
		oeState.addItem( new ComBoxEntity("《--------请选择--------》", "" ));
		oeProduct.addItem( new ComBoxEntity("《--------请选择--------》", "" ));
		
		new DataDictionaryMainEntity(DataDictionaryMainEntity.DD_ORDER_STATE	, oeState);
		List<ProductEntity> pList = ProductManageController.getInstance().query(null);
		for( ProductEntity pe : pList ){
			oeProduct.addItem( new ComBoxEntity( pe.getPeName(), pe.getPeCode()));
		}
		
		// 如果是管理员登陆， 则展示 收货确认以及 投标查看按钮
		if( "admin".equals(loginUser.getUeRole())){
			JButton btnOrderConfirm = new JButton("确认");
			btnOrderConfirm.setBounds(200, 150, 80, 30);
			contentPane.add(btnOrderConfirm);
			btnOrderConfirm.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btnOrderConfirmAction();
				}
			});
			
			JButton btnbidView = new JButton("投标查看");
			btnbidView.setBounds(110, 150, 80, 30);
			contentPane.add(btnbidView);
			
			btnbidView.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btnBidViewAction();
				}
			});
			
			JButton btnCreate = new JButton("新增");
			btnCreate.setBounds(290, 150, 80, 30);
			contentPane.add(btnCreate);
			btnCreate.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btnCreateAction();
				}
			});
			
			JButton btnModify = new JButton("修改");
			btnModify.setBounds(380, 150, 80, 30);
			contentPane.add(btnModify);
			btnModify.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btnModifyAction();
				}
			});
			
			JButton btnRemove = new JButton("删除");
			btnRemove.setBounds(470, 150, 80, 30);
			contentPane.add(btnRemove);
			btnRemove.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btnRemoveAction();
				}
			});
			
		}else{
			// 如果是非管理员查看， 则展示 参与竞标  
			JButton btnOrderConfig = new JButton("投标");
			btnOrderConfig.setBounds(470, 150, 80, 30);
			contentPane.add(btnOrderConfig);
			
			btnOrderConfig.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btnSubmintOrder();
				}
			});
		}
		
		btnQuery.addActionListener(new ActionListener() {
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
		
		btnBack.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if( "admin".equals( loginUser.getUeRole()) ){
					new AdminWelcomePage();
				}else{
					new WelcomePage();
				}
				dispose();
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginController.getInstance().doLogout();
				new Login();
				dispose();
			}
		});
		
		List<OrderEntity> orderList = OrderManageController.getInstance().query(null);
		tableModel = new OrderManageTableModelProxy(OrderEntity.ENTITY_TITLE , orderList );
		table.setModel(tableModel);
		table.getTableHeader().setDefaultRenderer(new OrderManageCheckHeaderCellRenderer(table));
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class,   r);
		
	}
	
	private void btnCreateAction(){
		new OrderEditDialog( this , null);
	}
	
	private void btnModifyAction(){
		// 判断   除 竞标发布状态外 ， 其他状态不可修改
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				OrderEntity order = tableModel.getSelectedRowDate().get(0);
				if( "1".equals(order.getOeState())){
					new OrderEditDialog(this, order );
				}else{
					JOptionPane.showMessageDialog(null, "订单已完成竞标不可更改", "提示", JOptionPane.WARNING_MESSAGE);
				}
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
				Map<String, String>  rMap =OrderManageController.getInstance().remove(tableModel.getSelectedRowDate());
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
		OrderEntity query = new OrderEntity();
		query.setOeName( oeName.getText()	);
		query.setOeCode( oeCode.getText());
		query.setOeState( ((ComBoxEntity) oeState.getSelectedItem()) .getComValue() );
		query.setOdeProductCode( ((ComBoxEntity) oeProduct.getSelectedItem())  .getComValue() );
		tableModel.setData( OrderManageController.getInstance().query(query));
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
	
	private void btnResetAction(){
		oeName.setText("");
		oeCode.setText("");
		oeProduct.setSelectedIndex(0);
		oeState.setSelectedIndex(0);
	}
	
	private void btnOrderConfirmAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				OrderEntity order = tableModel.getSelectedRowDate().get(0);
				if( "4".equals(order.getOeState())){
					
					order.setOeState("5");
					OrderManageController.getInstance().modify(order);
					if( "success".equals(order.getMsgMap().get("state"))){
						JOptionPane.showMessageDialog(null, "收货成功", "成功", JOptionPane.INFORMATION_MESSAGE);
						tableReload();
					}else{
						JOptionPane.showMessageDialog(null, order.getMsgMap().get("msg"), "失败", JOptionPane.ERROR_MESSAGE);
					}
					
				}else{
					JOptionPane.showMessageDialog(null, "订单当前状态不能确认收货", "提示", JOptionPane.WARNING_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "只能选择一条记录确认收货", "提示", JOptionPane.WARNING_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录确认收货", "提示", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void btnBidViewAction(){
		// 判断   除 竞标发布状态外 ， 其他状态不可修改
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				new OrderBidViewDialog( tableModel.getSelectedRowDate().get(0));
			}else{
				JOptionPane.showMessageDialog(null, "只能选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 *   工厂投标
	 */
	private void btnSubmintOrder(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				
				OrderEntity order =  tableModel.getSelectedRowDate().get(0);
				if( "1".equals(order.getOeState())){
					
					Map<String , String > checkResult = BidManageController.getInstance().submitCheck(order);
					if( "success".equals(checkResult.get("state"))){
						new OrderSubmitDialog( order);
					}else{
						JOptionPane.showMessageDialog(null, checkResult.get("msg"), "提示", JOptionPane.WARNING_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "订单投标已结束", "提示", JOptionPane.WARNING_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "只能选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void tableReload(){
		tableModel.setData( OrderManageController.getInstance().query(null));
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
}
