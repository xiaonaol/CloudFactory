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

import com.neuedu.controller.DeviceManageController;
import com.neuedu.controller.LoginController;
import com.neuedu.model.ComBoxEntity;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.DeviceEntity;
import com.neuedu.model.UserEntity;
import com.neuedu.util.DeviceCheckHeaderCellRenderer;
import com.neuedu.util.DeviceManageTableModelProxy;

public class DeviceManage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTable table;
	private JScrollPane	tablecroll;
	private DeviceManageTableModelProxy tableModel;
	
	private JTextField deName;
	private JTextField deCode;
	private JComboBox<ComBoxEntity> deType ;
	private JComboBox<ComBoxEntity> deState ;
	
	private JButton btnDeviceRent;
	private JButton btnCreate;
	private JButton  btnModify;
	private JButton  btnRemove;
	private JButton  btnQuery;
	private JButton  btnReset;
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
					DeviceManage frame = new DeviceManage();
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
	public DeviceManage() {
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
		
		JLabel lblNewLabel = new JLabel("设备名称");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(50, 50, 80, 20);
		contentPane.add(lblNewLabel);
		
		deName = new JTextField();
		deName.setBounds(150, 50, 200, 20);
		contentPane.add(deName);
		deName.setColumns(10);
		
		JLabel label = new JLabel("设备编码");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(420, 50, 80, 20);
		contentPane.add(label);
		
		deCode = new JTextField();
		deCode.setColumns(10);
		deCode.setBounds(520, 50, 200, 20);
		contentPane.add(deCode);
		
		JLabel label_1 = new JLabel("设备类型");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(50, 100, 80, 20);
		contentPane.add(label_1);
		
		deType = new JComboBox<ComBoxEntity>();
		deType.setBounds(150, 100, 200, 20);
		contentPane.add(deType);
		
		JLabel label_2 = new JLabel("设备状态");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(420, 100, 80, 20);
		contentPane.add(label_2);
		
		deState = new JComboBox<ComBoxEntity>();
		deState.setBounds(520, 100, 200, 20);
		contentPane.add(deState);
		
		btnCreate = new JButton("新增");
		btnCreate.setBounds(320, 150, 80, 30);
		contentPane.add(btnCreate);
		
		btnModify = new JButton("修改");
		btnModify.setBounds(410, 150, 80, 30);
		contentPane.add(btnModify);
		
		btnRemove = new JButton("删除");
		btnRemove.setBounds(500, 150, 80, 30);
		contentPane.add(btnRemove);
		
		btnQuery = new JButton("查询");
		btnQuery.setBounds(590, 150, 80, 30);
		contentPane.add(btnQuery);
		
		btnReset = new JButton("重置");
		btnReset.setBounds(680, 150, 80, 30);
		contentPane.add(btnReset);
		
		table = new JTable();
		tablecroll= new JScrollPane(table);
		tablecroll.setBounds(10, 206, 764, 346);
		contentPane.add(tablecroll);
		
		btnDeviceRent = new JButton("租设备");
		btnDeviceRent.setBounds(230, 150, 80, 30);
		contentPane.add(btnDeviceRent);
		pageInit();
	}
	
	private  void pageInit(){
		
		this.setVisible(true);
		this.setTitle("东软智能云制造-设备管理");
		
		loginUser = LoginController.getInstance().getLoginUser();
		if( null == loginUser ){
			JOptionPane.showMessageDialog(null , "未获取登陆状态， 请重新登陆","错误",JOptionPane.ERROR_MESSAGE);
			new Login();
			dispose();
		}else{
			loginUserName.setText( loginUser.getUeName());
		}
		
		if( "admin".equals(loginUser.getUeRole())){
			btnDeviceRent.setVisible(false);
		}
		
		deType.addItem( new ComBoxEntity("《--------请选择--------》", ""));
		deState.addItem( new ComBoxEntity("《--------请选择--------》",""));
		new DataDictionaryMainEntity(DataDictionaryMainEntity.DD_DEVICE_TYPE, deType);
		new DataDictionaryMainEntity(DataDictionaryMainEntity.DD_DEVICE_STATE, deState);
		
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
		
		btnQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnQueryAction();
			}
		});
		
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnResetAction();
			}
		});
		
		btnDeviceRent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnDeviceRentAction();
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if( "admin".equals(loginUser.getUeRole())){
					new AdminWelcomePage();
				}else{
					new WelcomePage();
				}
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
		
		// 表格信息初始化
		List<DeviceEntity> deviceList = DeviceManageController.getInstance().query(null);
		tableModel = new DeviceManageTableModelProxy(DeviceEntity.ENTITY_TITLE , deviceList );
		table.setModel(tableModel);
		table.getTableHeader().setDefaultRenderer(new DeviceCheckHeaderCellRenderer(table));
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class,   r);
		
	}
	
	private void btnCreateAction(){
		new DeviceEditDialog(this, null);
	}
	
	private void btnModifyAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				new DeviceEditDialog(this, tableModel.getSelectedRowDate().get(0));
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
				Map<String, String>  rMap =DeviceManageController.getInstance().remove(tableModel.getSelectedRowDate());
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
		DeviceEntity query = new DeviceEntity();
		query.setDeName(deName.getText());
		query.setDeCode(deCode.getText());
		
		query.setDeState( ((ComBoxEntity) deState.getSelectedItem()).getComValue());
		query.setDeType( ((ComBoxEntity) deType.getSelectedItem()).getComValue());
		
		tableModel.setData( DeviceManageController.getInstance().query(query));
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
	
	private void btnDeviceRentAction(){
		new DeviceRentDialog( this );
	}
	
	private void btnResetAction(){
		deName.setText("");
		deCode.setText("");
		deState.setSelectedIndex(0);
		deType.setSelectedIndex(0);
	}
	
	public void tableReload(){
		tableModel.setData( DeviceManageController.getInstance().query(null));
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
}
