package com.neuedu.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.neuedu.controller.LoginController;
import com.neuedu.controller.UserManageController;
import com.neuedu.model.ComBoxEntity;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.UserEntity;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JSpinner;

public class UserEditDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private UserManage parentWindow ;
	private UserEntity parentDate;
	private JTextField ueName;
	private JTextField ueIdCardNo;
	private JTextField ueTel;
	private JTextField ueAddress;
	private JSpinner ueBirthday;
	private JTextField ueEmail;
	private JTextField ueAccount;
	private JComboBox<ComBoxEntity> ueGender;
	private JComboBox<ComBoxEntity> ueState;
	private JTextPane ueRemark;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UserEditDialog dialog = new UserEditDialog( null , null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public UserEditDialog( UserManage _parentWindow , UserEntity _parentDate) {
		this.parentDate = _parentDate;
		this.parentWindow = _parentWindow;
		setBounds(100, 100, 450, 511);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("姓名");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(50, 30, 80, 20);
			contentPanel.add(lblNewLabel);
		}
		{
			ueName = new JTextField();
			ueName.setBounds(150, 30, 200, 20);
			contentPanel.add(ueName);
			ueName.setColumns(10);
		}
		{
			JLabel label = new JLabel("性别");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 60, 80, 20);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("身份证号码");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 90, 80, 20);
			contentPanel.add(label);
		}
		{
			ueIdCardNo = new JTextField();
			ueIdCardNo.setColumns(10);
			ueIdCardNo.setBounds(150, 90, 200, 20);
			contentPanel.add(ueIdCardNo);
		}
		{
			JLabel label = new JLabel("联系电话");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 120, 80, 20);
			contentPanel.add(label);
		}
		{
			ueTel = new JTextField();
			ueTel.setColumns(10);
			ueTel.setBounds(150, 120, 200, 20);
			contentPanel.add(ueTel);
		}
		{
			JLabel label = new JLabel("地址");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 150, 80, 20);
			contentPanel.add(label);
		}
		{
			ueAddress = new JTextField();
			ueAddress.setColumns(10);
			ueAddress.setBounds(150, 150, 200, 20);
			contentPanel.add(ueAddress);
		}
		{
			JLabel label = new JLabel("出生日期");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 180, 80, 20);
			contentPanel.add(label);
		}
		{
			ueGender = new JComboBox<ComBoxEntity>();
			ueGender.setBounds(150, 60, 200, 20);
			contentPanel.add(ueGender);
		}
		{
			JLabel lblEmail = new JLabel("eMail");
			lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
			lblEmail.setBounds(50, 210, 80, 20);
			contentPanel.add(lblEmail);
		}
		{
			ueEmail = new JTextField();
			ueEmail.setColumns(10);
			ueEmail.setBounds(150, 210, 200, 20);
			contentPanel.add(ueEmail);
		}
		{
			JLabel label = new JLabel("备注");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 300, 80, 20);
			contentPanel.add(label);
		}
		{
			ueRemark = new JTextPane();
			ueRemark.setBounds(150, 300, 200, 60);
			contentPanel.add(ueRemark);
		}
		
		ueBirthday = new JSpinner(new SpinnerDateModel());
		ueBirthday.setBounds(150, 180, 200, 20);
		ueBirthday.setEditor(new JSpinner.DateEditor(ueBirthday, "yyyy-MM-dd"));
		ueBirthday.setValue(new Date());		
		contentPanel.add(ueBirthday);
		
		JLabel label = new JLabel("账号");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(50, 240, 80, 20);
		contentPanel.add(label);
		
		ueAccount = new JTextField();
		ueAccount.setColumns(10);
		ueAccount.setBounds(150, 240, 200, 20);
		contentPanel.add(ueAccount);
		{
			JLabel label_1 = new JLabel("账号状态");
			label_1.setHorizontalAlignment(SwingConstants.CENTER);
			label_1.setBounds(50, 270, 80, 20);
			contentPanel.add(label_1);
		}
		{
			ueState = new JComboBox<ComBoxEntity>();
			ueState.setBounds(150, 270, 200, 20);
			contentPanel.add(ueState);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener( new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						saveAction();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener( new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		 pageInit();
	}
	
	private void pageInit(){
		this.setVisible( true);
		
		ueGender.addItem( new ComBoxEntity ("《--------请选择--------》",""));
		ueState.addItem( new ComBoxEntity ("《--------请选择--------》",""));
		new DataDictionaryMainEntity( DataDictionaryMainEntity.DD_USER_GENDER , ueGender);
		new DataDictionaryMainEntity(DataDictionaryMainEntity.DD_USER_STATE, ueState);
		
		if( null == parentDate){
			this.setTitle("东软智能云制造-新建用户");
			ueState.setSelectedIndex(1);
			ueState.setEnabled(false);
		}else{
			this.setTitle("东软智能云制造-修改用户");
			ueAccount.setEnabled( false);
			
			ueName.setText(parentDate.getUeName());
			ueIdCardNo.setText(parentDate.getUeIdCardNo());
			ueTel.setText(parentDate.getUeTel());
			ueAddress.setText( parentDate.getUeAddress());
			ueBirthday.setValue( parentDate.getUeBirthday());
			ueEmail.setText( parentDate.getUeEmail());
			ueRemark.setText(parentDate.getUeRemark());
			
			for( int i = 0 ; i < ueGender.getItemCount() ; i ++ ){
				if( ((ComBoxEntity) ueGender.getItemAt(i)).getComValue().equals(parentDate.getUeGender())){
					ueGender.setSelectedIndex(i);
					break;
				}
			}
			
			for( int i = 0 ; i < ueState.getItemCount() ; i ++ ){
				if( ((ComBoxEntity) ueState.getItemAt(i)).getComValue().equals(parentDate.getUeState())){
					ueState.setSelectedIndex(i);
					break;
				}
			}
		}
	}
	
	private void saveAction(){

		if( null == ueName.getText() || "".equals(ueName.getText())){
			JOptionPane.showMessageDialog( null, "请输入用户名称","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if( null == ueIdCardNo.getText() || "".equals(ueIdCardNo.getText())){
			JOptionPane.showMessageDialog( null, "请输入用户身份证号码","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if( null == ueTel.getText() || "".equals(ueTel.getText())){
			JOptionPane.showMessageDialog( null, "请输入用户联系电话","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if( null == ueAddress.getText() || "".equals(ueAddress.getText())){
			JOptionPane.showMessageDialog( null, "请输入用户地址","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if( null == ueEmail.getText() || "".equals(ueEmail.getText())){
			JOptionPane.showMessageDialog( null, "请输入用户eMail","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if( null == ueBirthday.getValue() || "".equals(ueBirthday.getValue())){
			JOptionPane.showMessageDialog( null, "请输入用户出生日期","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if(  0 == ueGender.getSelectedIndex()){
			JOptionPane.showMessageDialog( null, "请选择用户性别","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if( null == parentDate){
			
			if( null == ueAccount.getText() || "".equals(ueAccount.getText())){
				JOptionPane.showMessageDialog( null, "请输入用户登陆账号","提示",JOptionPane.WARNING_MESSAGE);
				return ;
			}
			
			if( ! UserManageController.getInstance().userIDCardNoCheck(ueIdCardNo.getText())){
				JOptionPane.showMessageDialog( null, "身份证号码已存在， 请重新输入","提示",JOptionPane.WARNING_MESSAGE);
				ueIdCardNo.setText("");
				return ;
			}
			
			if( ! UserManageController.getInstance().userTelCheck(ueTel.getText())){
				JOptionPane.showMessageDialog( null, "联系电话已存在， 请重新输入","提示",JOptionPane.WARNING_MESSAGE);
				ueTel.setText("");
				return ;
			}
			
			if( ! UserManageController.getInstance().userEmailCheck(ueEmail.getText())){
				JOptionPane.showMessageDialog( null, "eMail已存在， 请重新输入","提示",JOptionPane.WARNING_MESSAGE);
				ueEmail.setText("");
				return ;
			}
			
			if( ! UserManageController.getInstance().userAccountCheck(ueAccount.getText())){
				JOptionPane.showMessageDialog( null, "账号已存在， 请重新输入","提示",JOptionPane.WARNING_MESSAGE);
				ueAccount.setText("");
				return ;
			}
			
			UserEntity loginUser = LoginController.getInstance().getLoginUser();
			UserEntity user = new UserEntity();
			user.setUeFactoryCode( loginUser.getUeFactoryCode());
			user.setUeName(ueName.getText());
			user.setUeIdCardNo(ueIdCardNo.getText());
			user.setUeTel(ueTel.getText());
			user.setUeAddress(ueAddress.getText());
			user.setUeBirthday( (Date) ueBirthday.getValue());
			user.setUeEmail( ueEmail.getText());
			user.setUeAccount(ueAccount.getText());
			user.setUeGender(((ComBoxEntity) ueGender.getSelectedItem()).getComValue());
			user.setUeState(((ComBoxEntity)ueState.getItemAt(0)).getComValue());
			user.setUeRole("worker");
			user.setUeRemark(ueRemark.getText());

			UserManageController.getInstance().create(user);
			if( "success".equals(user.getMsgMap().get("state"))){
				JOptionPane.showMessageDialog(null, "保存成功" , "成功", JOptionPane.INFORMATION_MESSAGE);
				parentWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog(null, user.getMsgMap().get("msg") , "注册失败", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			parentDate.setUeName(ueName.getText());
			parentDate.setUeIdCardNo(ueIdCardNo.getText());
			parentDate.setUeTel(ueTel.getText());
			parentDate.setUeAddress(ueAddress.getText());
			parentDate.setUeBirthday( (Date) ueBirthday.getValue());
			parentDate.setUeEmail( ueEmail.getText());
			parentDate.setUeGender(((ComBoxEntity) ueGender.getSelectedItem()).getComValue());
			parentDate.setUeState(((ComBoxEntity)ueState.getSelectedItem()).getComValue());
			parentDate.setUeRemark(ueRemark.getText());
			UserManageController.getInstance().modify(parentDate);
			if( "success".equals(parentDate.getMsgMap().get("state"))){
				JOptionPane.showMessageDialog(null, "更新成功" , "成功", JOptionPane.INFORMATION_MESSAGE);
				parentWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog(null, parentDate.getMsgMap().get("msg") , "失败", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
