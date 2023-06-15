package com.neuedu.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

import com.neuedu.controller.DataDictionaryManageController;
import com.neuedu.model.DataDictionaryMainEntity;

/**
 * 主项信息维护
 * @author koala
 *
 */
public class DataDictionaryMainEditDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField ddmeKind;
	private JTextField ddmeKindName;
	private JTextPane ddmeRemark ;
	
	private DataDictionaryManage parentWindow ;
	private DataDictionaryMainEntity parentDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DataDictionaryMainEditDialog dialog = new DataDictionaryMainEditDialog( null , null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DataDictionaryMainEditDialog( DataDictionaryManage _parentWindow , DataDictionaryMainEntity _parentDate) {
		this.parentDate = _parentDate;
		this.parentWindow = _parentWindow;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("字典类型代码");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(50, 50, 80, 20);
			contentPanel.add(lblNewLabel);
		}
		{
			ddmeKind = new JTextField();
			ddmeKind.setBounds(150, 50, 200, 20);
			contentPanel.add(ddmeKind);
			ddmeKind.setColumns(10);
		}
		{
			JLabel label = new JLabel("字典类型名称");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 100, 80, 20);
			contentPanel.add(label);
		}
		{
			ddmeKindName = new JTextField();
			ddmeKindName.setColumns(10);
			ddmeKindName.setBounds(150, 100, 200, 20);
			contentPanel.add(ddmeKindName);
		}
		{
			JLabel label = new JLabel("备注");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 150, 80, 20);
			contentPanel.add(label);
		}
		{
			ddmeRemark = new JTextPane();
			ddmeRemark.setBounds(150, 150, 200, 40);
			contentPanel.add(ddmeRemark);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
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
		if( null == parentDate){
			this.setTitle( "东软智能云制造-新建字典项" );
		}else{
			this.setTitle( "东软智能云制造-维护字典项");
			ddmeKind.setText(parentDate.getDdmeKind());
			ddmeKindName .setText( parentDate.getDdmeKindName());
			ddmeRemark.setText(parentDate.getDdmeRemark());
		}
	}

	private void saveAction(){
		
		if( null == ddmeKind.getText() || "".equals(ddmeKind.getText())){
			JOptionPane.showMessageDialog(null, "请输入字典项类型码","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if( null == ddmeKindName.getText() || "".equals(ddmeKindName.getText())){
			JOptionPane.showMessageDialog(null, "请输入字典项类型名称","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if( null == parentDate){
			DataDictionaryMainEntity main = new DataDictionaryMainEntity();
			main.setDdmeKind( ddmeKind.getText());
			main.setDdmeKindName( ddmeKindName.getText());
			main.setDdmeRemark( ddmeRemark.getText());
			
			DataDictionaryManageController.getInstance().create(main);
			if("success".equals(main.getMsgMap().get("state"))){
				JOptionPane.showMessageDialog(null, "保存成功","成功",JOptionPane.INFORMATION_MESSAGE);
				parentWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog(null, main.getMsgMap().get("msg"),"失败",JOptionPane.INFORMATION_MESSAGE);
			}
		}else{
			parentDate.setDdmeKind( ddmeKind.getText());
			parentDate.setDdmeKindName( ddmeKindName.getText());
			parentDate.setDdmeRemark( ddmeRemark.getText());
			DataDictionaryManageController.getInstance().modify(parentDate);
			if("success".equals(parentDate.getMsgMap().get("state"))){
				JOptionPane.showMessageDialog(null, "保存成功","成功",JOptionPane.INFORMATION_MESSAGE);
				parentWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog(null, parentDate.getMsgMap().get("msg"),"失败",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	
}
