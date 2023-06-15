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

import com.neuedu.controller.FactoryManageController;
import com.neuedu.controller.LoginController;
import com.neuedu.controller.UserManageController;
import com.neuedu.model.ComBoxEntity;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.FactoryEntity;
import com.neuedu.model.UserEntity;
import com.neuedu.util.UserManageCheckHeaderCellRenderer;
import com.neuedu.util.UserManageTableModelProxy;

public class UserManage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ueName;
	private JTextField ueCode;
	private JTextField ueTel;
	
	private JTable table;
	private JScrollPane	tablecroll;
	private UserManageTableModelProxy tableModel;
	
	private JComboBox<ComBoxEntity> ueFactoryCode;
	private JComboBox<ComBoxEntity> ueRole;
	private JComboBox<ComBoxEntity> ueState;
	private JButton btnResetPwd;
	private JButton btnRemove;
	private JButton btnQuery;
	private JButton btnReset;
	
	private JButton btnBack;
	private JButton btnLogout;
	private JLabel loginUserName;
	private JButton btnModify;
	private JButton btnCreate;
	
	private UserEntity loginUser ;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserManage frame = new UserManage();
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
	public UserManage() {
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
		
		JLabel lblNewLabel = new JLabel("姓名");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(50, 50, 80, 20);
		contentPane.add(lblNewLabel);
		
		ueName = new JTextField();
		ueName.setBounds(150, 50, 200, 20);
		contentPane.add(ueName);
		ueName.setColumns(10);
		
		ueCode = new JTextField();
		ueCode.setColumns(10);
		ueCode.setBounds(520, 50, 200, 20);
		contentPane.add(ueCode);
		
		JLabel label = new JLabel("用户编码");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(420, 50, 80, 20);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("所属工厂");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(50, 80, 80, 20);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("用户角色");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(420, 80, 80, 20);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("账号状态");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(50, 110, 80, 20);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("手机号码");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(420, 110, 80, 20);
		contentPane.add(label_4);
		
		ueTel = new JTextField();
		ueTel.setColumns(10);
		ueTel.setBounds(520, 110, 200, 20);
		contentPane.add(ueTel);
		
		ueFactoryCode = new JComboBox<ComBoxEntity>();
		ueFactoryCode.setBounds(150, 80, 200, 20);
		contentPane.add(ueFactoryCode);
		
		ueRole = new JComboBox<ComBoxEntity>();
		ueRole.setBounds(520, 80, 200, 20);
		contentPane.add(ueRole);
		
		ueState = new JComboBox<ComBoxEntity>();
		ueState.setBounds(150, 110, 200, 20);
		contentPane.add(ueState);
		
		btnResetPwd = new JButton("重置密码");
		btnResetPwd.setBounds(360, 160, 80, 30);
		contentPane.add(btnResetPwd);
		
		btnRemove = new JButton("删除");
		btnRemove.setBounds(450, 160, 80, 30);
		contentPane.add(btnRemove);
		
		btnQuery = new JButton("查询");
		btnQuery.setBounds(540, 160, 80, 30);
		contentPane.add(btnQuery);
		
		btnReset = new JButton("重置");
		btnReset.setBounds(630, 160, 80, 30);
		contentPane.add(btnReset);
		
		table = new JTable();
		tablecroll= new JScrollPane(table);
		tablecroll.setBounds(10, 219, 764, 333);
		contentPane.add(tablecroll);
		
		btnModify = new JButton("修改");
		btnModify.setBounds(270, 160, 80, 30);
		contentPane.add(btnModify);
		
		btnCreate = new JButton("新增");
		btnCreate.setBounds(180, 160, 80, 30);
		contentPane.add(btnCreate);
		
		pageInit();
	}
	
	private void pageInit(){
		this.setVisible( true);
		this.setTitle( "东软智能云制造-用户管理" );
		loginUser = LoginController.getInstance().getLoginUser();
		if( null == loginUser){
			JOptionPane.showMessageDialog(null, "未获取到登陆状态， 请重新登陆", "无状态", JOptionPane.ERROR_MESSAGE);
			new Login();
			dispose();
			return;
		}
		loginUserName.setText( loginUser.getUeName() );
		
		ueRole.addItem( new ComBoxEntity ("《--------请选择-------->",""));
		ueState.addItem( new ComBoxEntity ("《--------请选择-------->",""));
		
		new DataDictionaryMainEntity(DataDictionaryMainEntity.DD_USER_ROLE, ueRole);
		new DataDictionaryMainEntity(DataDictionaryMainEntity.DD_USER_STATE, ueState);
		
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
		
		btnResetPwd.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnResetPwdAction();
			}
		});
		
		btnRemove.addActionListener( new ActionListener() {
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
	
		btnBack.addActionListener( new ActionListener() {
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
		
		// 判断当前登录人员角色
		// 超级管理员， 只能重置密码 ， 查询   不可新增和编辑 
		// 工厂所有者   可进行完整功能
		// 工厂工人  可查询
		
		// 工厂所有所有者 拥有新建用户的权限 ， 新建用户时 ， 新建用户默认所属工厂与创建者一直
		// 超级管理员 ， 系统初始化时创建
		// 工厂所有者  ， 注册用户后， 直接跳转创建工厂页面
		// 工人， 由工厂所有者创建的人员均是工人角色
		
		List<UserEntity> userList = null;  
		
		if( "admin".equals(loginUser.getUeRole())){
			//工厂下拉框
			ueFactoryCode.addItem( new ComBoxEntity ("《--------请选择-------->",""));
			List<FactoryEntity> factoryList = FactoryManageController.getInstance().query(null);
			for( FactoryEntity fe : factoryList){
				ueFactoryCode.addItem( new ComBoxEntity( fe.getFeName(), fe.getFeCode()));
			}
			
			btnCreate.setEnabled( false);
			btnModify.setEnabled( false);
			btnRemove.setEnabled(false);
			
			userList = UserManageController.getInstance().query(null);
		}else{
			FactoryEntity fe = new FactoryEntity();
			fe.setFeCode(loginUser.getUeFactoryCode());
			List<FactoryEntity> factoryList = FactoryManageController.getInstance().query(fe);
			if( null != factoryList && !factoryList.isEmpty()&& factoryList.size() == 1 ){
				ueFactoryCode.addItem( new ComBoxEntity( factoryList.get(0).getFeName(), factoryList.get(0).getFeCode()));
				ueFactoryCode.setEditable(false);
			}

			if( "factoryWorker".equals(loginUser.getUeRole())){
				btnCreate.setEnabled( false);
				btnModify.setEnabled(false);
				btnResetPwd.setEnabled(false);
				btnRemove.setEnabled( false);
			}
			
			UserEntity query = new UserEntity();
			query.setUeFactoryCode( loginUser.getUeFactoryCode());
			userList = UserManageController.getInstance().query(query);
		}
		
        tableModel = new UserManageTableModelProxy(DataDictionaryMainEntity.ENTITY_TITLE , userList );
        table.setModel(tableModel);
        table.getTableHeader().setDefaultRenderer(new UserManageCheckHeaderCellRenderer(table));
        DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
        r.setHorizontalAlignment(JLabel.CENTER);   
        table.setDefaultRenderer(Object.class,   r);
        
	}

	/**
	 * 重置密码
	 */
	private void btnResetPwdAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				UserEntity user = tableModel.getSelectedRowDate().get(0);
				UserManageController.getInstance().resetDefaultPwd(user);
				if( "success".equals(user.getMsgMap().get("state"))){
					JOptionPane.showMessageDialog(null, "已重置默认密码", "提示", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, user.getMsgMap().get("msg"), "提示", JOptionPane.ERROR_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "只能选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void btnCreateAction(){
		new UserEditDialog(this, null);
	}
	
	private void btnModifyAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				new UserEditDialog(this, tableModel.getSelectedRowDate().get(0));
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
				Map<String, String>  rMap = UserManageController.getInstance().remove(tableModel.getSelectedRowDate() );
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

	/**
	 * 查询按钮事件
	 */
	private void btnQueryAction(){
		UserEntity query = new UserEntity();
		if(! "admin".equals(loginUser.getUeRole())){
			query.setUeFactoryCode( loginUser.getUeFactoryCode());
		}
		
		query.setUeName(ueName.getText());
		query.setUeCode(ueCode.getText());
		query.setUeTel(ueTel.getText());
		if( 0 != ueFactoryCode.getSelectedIndex()){
			query.setUeFactoryCode( ((ComBoxEntity) ueFactoryCode.getSelectedItem()).getComValue());
		}
		if( 0 != ueRole.getSelectedIndex()){
			query.setUeRole( ((ComBoxEntity) ueRole.getSelectedItem()).getComValue());
		}
		if( 0 != ueState.getSelectedIndex()){
			query.setUeState( ((ComBoxEntity) ueState.getSelectedItem()).getComValue());
		}
		List<UserEntity> userList = UserManageController.getInstance().query(query);
		tableModel.setData( userList);
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
		
	}
	
	private void btnResetAction(){
		ueName.setText( "");
		ueCode.setText( "");
		ueFactoryCode.setSelectedIndex(0);
		ueState.setSelectedIndex(0);
		ueRole.setSelectedIndex(0);
		ueTel.setText("");
	}

	public void tableReload(){
		if("admin".equals( loginUser.getUeRole())){
			tableModel.setData( UserManageController.getInstance().query(null));
		}else{
			UserEntity query = new UserEntity();
			query.setUeFactoryCode( loginUser.getUeFactoryCode());
			tableModel.setData( UserManageController.getInstance().query(query));
		}	
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
}
