package com.neuedu.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.neuedu.controller.FactoryManageController;
import com.neuedu.model.FactoryEntity;
import com.neuedu.model.UserEntity;

public class FactoryEidtDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField feName;
	private JTextField feContacts;
	private JTextField feContactsTel;
	private JTextField feTel;
	private JTextField feAddress;
	private 	JTextPane feRemark ;
	
	private UserRegistDialog parentWindow ;
	private UserEntity parentDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FactoryEidtDialog dialog = new FactoryEidtDialog(null , null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FactoryEidtDialog( UserRegistDialog _parentWindow , UserEntity _parentDate) {
		this.parentDate = _parentDate;
		this.parentWindow = _parentWindow;
		setBounds(100, 100, 450, 324);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("工厂名称");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(50, 30, 80, 20);
			contentPanel.add(lblNewLabel);
		}
		{
			feName = new JTextField();
			feName.setBounds(158, 30, 200, 20);
			contentPanel.add(feName);
			feName.setColumns(10);
		}
		{
			feContacts = new JTextField();
			feContacts.setColumns(10);
			feContacts.setBounds(158, 60, 200, 20);
			contentPanel.add(feContacts);
		}
		{
			JLabel label = new JLabel("工厂联系人");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 60, 80, 20);
			contentPanel.add(label);
		}
		{
			feContactsTel = new JTextField();
			feContactsTel.setColumns(10);
			feContactsTel.setBounds(158, 90, 200, 20);
			contentPanel.add(feContactsTel);
		}
		{
			JLabel label = new JLabel("联系人电话");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 90, 80, 20);
			contentPanel.add(label);
		}
		{
			feTel = new JTextField();
			feTel.setColumns(10);
			feTel.setBounds(158, 120, 200, 20);
			contentPanel.add(feTel);
		}
		{
			JLabel label = new JLabel("工厂电话");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 120, 80, 20);
			contentPanel.add(label);
		}
		{
			feAddress = new JTextField();
			feAddress.setColumns(10);
			feAddress.setBounds(158, 150, 200, 20);
			contentPanel.add(feAddress);
		}
		{
			JLabel label = new JLabel("工厂地址");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 150, 80, 20);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("备注");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 180, 80, 20);
			contentPanel.add(label);
		}
		{
			feRemark = new JTextPane();
			feRemark.setBounds(158, 180, 200, 40);
			contentPanel.add(feRemark);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
		this.setTitle("东软智能云制造-注册工厂");
	}
	
	private void saveAction(){
		
		if( null == feName.getText() || "".equals(feName.getText()) ){
			JOptionPane.showMessageDialog(null, "请输入工厂名称","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == feContacts.getText() || "".equals(feContacts.getText()) ){
			JOptionPane.showMessageDialog(null, "请输入工厂联系人","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == feContactsTel.getText() || "".equals(feContactsTel.getText()) ){
			JOptionPane.showMessageDialog(null, "请输入工厂联系人电话","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == feTel.getText() || "".equals(feTel.getText()) ){
			JOptionPane.showMessageDialog(null, "请输入工厂电话","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == feAddress.getText() || "".equals(feAddress.getText()) ){
			JOptionPane.showMessageDialog(null, "请输入工厂地址","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		FactoryEntity fe = new FactoryEntity();
		fe.setFeName(feName.getText());
		fe.setFeContacts(feContacts.getText());
		fe.setFeContactsTel(feContactsTel.getText());
		fe.setFeTel(feTel.getText());
		fe.setFeAddress(feAddress.getText());
		fe.setFeRemark(feRemark.getText());
		Date d = new Date();
		fe.setFeCreate(d);
		fe.setFeUpdate(d);
		fe.setFeCreater(parentDate.getUeCode());
		fe.setFeUpdater(parentDate.getUeCode());
		
		FactoryManageController.getInstance().create(fe);
		if("success".equals(fe.getMsgMap().get("state"))){
			JOptionPane.showMessageDialog(null, "注册成功，请重新登陆","成功",JOptionPane.INFORMATION_MESSAGE);
			new Login();
			dispose();
		}else{
			JOptionPane.showMessageDialog(null, fe.getMsgMap().get("msg"),"失败",JOptionPane.ERROR_MESSAGE);
		}
	}

}
