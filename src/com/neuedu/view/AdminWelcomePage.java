package com.neuedu.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.neuedu.controller.LoginController;
import com.neuedu.model.UserEntity;

/**
 * 管理员首页
 * @author koala
 *
 */
public class AdminWelcomePage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnLogout; 
	private JButton  btnBack;
	private JButton  btnUserManage;
	private JButton  btnDeviceManage;
	private JButton  btnDDManage;
	private JButton  btnOrderManage;
	private JButton  btnProductManage;
	
	private JLabel loginUserName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminWelcomePage frame = new AdminWelcomePage();
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
	public AdminWelcomePage() {
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
		
		btnUserManage = new JButton("用户管理");
		btnUserManage.setBounds(232, 110, 326, 40);
		contentPane.add(btnUserManage);
		
		btnDeviceManage = new JButton("设备管理");
		btnDeviceManage.setBounds(232, 180, 326, 40);
		contentPane.add(btnDeviceManage);
		
		btnDDManage = new JButton("数据字典管理");
		btnDDManage.setBounds(232, 250, 326, 40);
		contentPane.add(btnDDManage);
		
		btnOrderManage = new JButton("订单管理");
		btnOrderManage.setBounds(232, 320, 326, 40);
		contentPane.add(btnOrderManage);
		
		btnProductManage = new JButton("产品管理");
		btnProductManage.setBounds(232, 390, 326, 40);
		contentPane.add(btnProductManage);
		
		pageInit();
	}
	
	private void pageInit(){
		this.setVisible(true);
		this.setTitle("东软智能云制造-管理员首页");
		UserEntity loginUser = LoginController.getInstance().getLoginUser();
		if( null == loginUser){
			JOptionPane.showMessageDialog(null, "未获取到登陆状态， 请重新登陆", "无状态", JOptionPane.ERROR_MESSAGE);
			new Login();
			dispose();
			return;
		}
		loginUserName.setText( loginUser.getUeName() );
		
		btnBack.setEnabled(false);
		btnLogout.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginController.getInstance().doLogout();
				new Login();
				dispose();
			}
		});
		
		btnUserManage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UserManage();
			}
		});
		
		btnDeviceManage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DeviceManage();
				dispose();
			}
		});
		
		btnDDManage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DataDictionaryManage();
				dispose();
			}
		});
		
		btnOrderManage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new OrderManage();
				dispose();
			}
		});
		
		btnProductManage.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ProductManage();
				dispose();
			}
		});
	}

}
