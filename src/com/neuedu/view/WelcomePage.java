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

public class WelcomePage extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private JButton btnBack;
	private JButton btnLogout;
	private JLabel loginUserName;
	
	private JButton  btnUserManage;
	private JButton btnDeviceMange;
	private JButton btnOrderMange;
	private JButton btnPlanManage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomePage frame = new WelcomePage();
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
	public WelcomePage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
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
		
		btnDeviceMange = new JButton("设备管理");
		btnDeviceMange.setBounds(232, 190, 326, 40);
		contentPane.add(btnDeviceMange);
		
		btnOrderMange = new JButton("可接订单");
		btnOrderMange.setBounds(232, 270, 326, 40);
		contentPane.add(btnOrderMange);
		
		btnPlanManage = new JButton("生产管理");
		btnPlanManage.setBounds(232, 350, 326, 40);
		contentPane.add(btnPlanManage);
		pageInit();
	}
	
	private void pageInit(){
		
		this.setVisible(true);
		this.setTitle("东软智能云制造-工厂首页");
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
		
		btnUserManage.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnUserManageAction();
			}
		});
		
		btnDeviceMange.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnDeviceMangeAction();
			}
		});
		
		btnOrderMange.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnOrderMangeAction();
			}
		});
		
		btnPlanManage.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPlanManageAction();
			}
		});
		
	}
	
	private void btnUserManageAction(){
		new UserManage();
		dispose();
	}
	
	private void btnDeviceMangeAction(){
		new DeviceManage();
		dispose();
	}

	private void btnOrderMangeAction(){
		new OrderManage();
		dispose();
	}
	
	private void btnPlanManageAction(){
		new PlanManage();
		dispose();
	}
}
