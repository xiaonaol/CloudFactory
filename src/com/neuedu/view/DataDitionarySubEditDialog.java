package com.neuedu.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.neuedu.controller.DataDictionaryManageController;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.DataDictionarySubEntity;

public class DataDitionarySubEditDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private DataDictionarySubManageDialog parentiWindow ;
	private DataDictionaryMainEntity parentDate;
	private DataDictionarySubEntity subEntity ;

	private final JPanel contentPanel = new JPanel();
	private JTextField ddseText;
	private JTextField ddseValue;
	private JTextPane ddseRemark ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDitionarySubEditDialog dialog = new DataDitionarySubEditDialog ( null , null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDitionarySubEditDialog( DataDictionarySubManageDialog _parentiWindow , DataDictionaryMainEntity _parentDate) {
		this.parentDate = _parentDate;
		this.parentiWindow = _parentiWindow;
		
		if(null !=  parentDate && null != parentDate.getSubList() && 1== parentDate.getSubList().size()  ){
			this.subEntity = parentDate.getSubList().get(0);
		}
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("子项展示名称");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(50, 50, 80, 20);
			contentPanel.add(lblNewLabel);
		}
		{
			ddseText = new JTextField();
			ddseText.setBounds(150, 50, 200, 20);
			contentPanel.add(ddseText);
			ddseText.setColumns(10);
		}
		{
			JLabel label = new JLabel("子项值");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 100, 80, 20);
			contentPanel.add(label);
		}
		{
			ddseValue = new JTextField();
			ddseValue.setColumns(10);
			ddseValue.setBounds(150, 100, 200, 20);
			contentPanel.add(ddseValue);
		}
		{
			JLabel label = new JLabel("备注");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 150, 80, 20);
			contentPanel.add(label);
		}
		{
			ddseRemark = new JTextPane();
			ddseRemark.setBounds(150, 150, 200, 47);
			contentPanel.add(ddseRemark);
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
	
	/**
	 * 页面初始化
	 */
	private void pageInit(){
		this.setVisible( true);
		if( null == subEntity ){
			this.setTitle( "东软智能云工厂-新增数据字典子项信息");
		}else{
			this.setTitle( "东软智能云工厂-维护数据字典子项信息");
			this.ddseText.setText( subEntity.getDdseText());
			this.ddseValue.setText( subEntity.getDdseValue());
			this.ddseRemark.setText( subEntity.getDdseRemark());
		}
	}
	
	/**
	 * 保存动作
	 */
	private void saveAction(){
		if( null == ddseText.getText() || "".equals(ddseText.getText())){
			JOptionPane.showMessageDialog( null ,  "请输入字典项子项展示名称" , "提示" , JOptionPane.WARNING_MESSAGE);
			 return;
		}
		
		if( null == ddseValue.getText() || "".equals(ddseValue.getText())){
			JOptionPane.showMessageDialog( null ,  "请输入字典项子项值" , "提示" , JOptionPane.WARNING_MESSAGE);
			 return;
		}
		
		if( null == subEntity){
			DataDictionarySubEntity sub = new DataDictionarySubEntity();
			sub.setDdsePCode(this.parentDate.getDdmeCode());
			sub.setDdseText( ddseText.getText());
			sub.setDdseValue( ddseValue.getText());
			sub.setDdseRemark( ddseRemark.getText());
			
			DataDictionaryManageController.getInstance().create(sub);
			if("success".equals(sub.getMsgMap().get("state"))){
				JOptionPane.showMessageDialog( null , "保存成功" ,"成功" , JOptionPane.INFORMATION_MESSAGE);
				parentiWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog( null , sub.getMsgMap().get("msg"),"失败",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			subEntity.setDdsePCode(this.parentDate.getDdmeCode());
			subEntity.setDdseText( ddseText.getText());
			subEntity.setDdseValue( ddseValue.getText());
			subEntity.setDdseRemark( ddseRemark.getText());
			
			DataDictionaryManageController.getInstance().modify(subEntity);
			if("success".equals(subEntity.getMsgMap().get("state"))){
				JOptionPane.showMessageDialog( null , "修改成功" ,"成功" , JOptionPane.INFORMATION_MESSAGE);
				parentiWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog( null , subEntity.getMsgMap().get("msg"),"失败",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
