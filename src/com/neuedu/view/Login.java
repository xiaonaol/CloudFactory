package com.neuedu.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.neuedu.controller.LoginController;
import com.neuedu.controller.UserManageController;
import com.neuedu.model.UserEntity;

/**
 * 登陆页面
 * @author koala
 *
 */
public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tAccount;
	private JPasswordField tPassword;
	private JButton btnLogin ;
	private JButton btnRegist;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("东软智能制造平台");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 36));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(142, 90, 500, 97);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u8D26\u53F7");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(91, 239, 149, 35);
		contentPane.add(lblNewLabel_1);
		
		tAccount = new JTextField();
		tAccount.setBounds(255, 239, 400, 35);
		contentPane.add(tAccount);
		tAccount.setColumns(10);
		
		JLabel label = new JLabel("\u5BC6\u7801");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		label.setBounds(91, 328, 149, 35);
		contentPane.add(label);
		
		tPassword = new JPasswordField();
		tPassword.setBounds(255, 328, 400, 35);
		contentPane.add(tPassword);
		
		btnLogin = new JButton("\u767B\u9646 ");
		btnLogin.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		btnLogin.setBounds(150, 416, 240, 50);
		contentPane.add(btnLogin);
		
		btnRegist = new JButton("注册");
		btnRegist.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		btnRegist.setBounds(400, 416, 240, 50);
		contentPane.add(btnRegist);
		
		pageInit();
	}
	
	/**
	 * 页面初始化
	 */
	private void pageInit(){
		this.setVisible( true);
		btnLogin.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnLoginAction();
			}
		});
		
		btnRegist.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRegistAction();
			}
		});
		
		// 检查系统中是否存在超级管理员
		UserEntity admin = new UserEntity();
		admin.setUeRole("admin"); 
		List<UserEntity> uList = UserManageController.getInstance().query(admin);
		if( null ==uList || uList.isEmpty() ){
			admin.setUeAccount("admin");
			admin.setUePwd( "123456" );
			admin.setUeName( "超级管理员" );
			admin.setUeBirthday( new Date());
			admin.setUeIdCardNo("111111111111111111");
			admin.setUeGender("1");
			admin.setUeState("1");
			admin.setUeRole("admin");
			admin.setUeEmail("admin@neusoft.com");
			admin.setUeTel("13998765432");
			admin.setUeAddress("东大软件园");
			admin.setUeRemark("系统自动创建管理员");
			UserManageController.getInstance().create(admin);
		}
	}
	
	/**
	 * 登录按钮点击事件
	 */
	private void btnLoginAction(){
		
		if( null == tAccount.getText() || "".equals( tAccount.getText())){
			JOptionPane.showMessageDialog( null , "请输入登陆账号或邮箱", "提示", JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if( null ==tPassword.getPassword() || tPassword.getPassword().length == 0  ){
			JOptionPane.showMessageDialog(null, "请填写登陆密码", "提示", JOptionPane.WARNING_MESSAGE);
			return; 
		}
	
		String account = tAccount.getText() ; 
		String pwd = "";
		for( int i = 0 ; i < tPassword.getPassword().length ; i++){
			pwd+=tPassword.getPassword()[i];
		}
			
		UserEntity loginUser = new UserEntity();
		loginUser.setUeAccount(account);
		loginUser.setUePwd(pwd);
		System.out.println(pwd);
		LoginController.getInstance().doLogin(loginUser);
		if( "success".equals(loginUser.getMsgMap().get("state"))){
			loginUser = LoginController.getInstance().getLoginUser();
			if("admin".equals(loginUser.getUeRole())){
				new AdminWelcomePage();
			}else{
				new WelcomePage();
			}
			dispose();
		}else{
			JOptionPane.showMessageDialog(null, loginUser.getMsgMap().get("msg"), "登陆失败", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	/**
	 * 注册按钮事件
	 */
	private void  btnRegistAction(){
		new UserRegistDialog(null, null);
		dispose();
	}
	
}
