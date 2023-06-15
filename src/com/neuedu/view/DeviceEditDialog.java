package com.neuedu.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.neuedu.controller.DeviceManageController;
import com.neuedu.model.ComBoxEntity;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.DeviceEntity;

public class DeviceEditDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField deName;
	private JTextField deSpecifications;
	private JComboBox<ComBoxEntity> deType;
	private JComboBox<ComBoxEntity> deState;
	private JTextPane deRemark ;
	
	private DeviceManage parentWindow;
	private DeviceEntity  parentDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DeviceEditDialog dialog = new DeviceEditDialog( null , null );
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DeviceEditDialog(DeviceManage _parentWindow , DeviceEntity _parentDate) {
		this.parentDate = _parentDate;
		this.parentWindow = _parentWindow;
		
		setBounds(100, 100, 450, 329);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("设备名称");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(50, 40, 80, 20);
			contentPanel.add(lblNewLabel);
		}
		{
			deName = new JTextField();
			deName.setBounds(150, 40, 200, 20);
			contentPanel.add(deName);
			deName.setColumns(10);
		}
		{
			JLabel label = new JLabel("设备类型");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 70, 80, 20);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("设备规格");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 100, 80, 20);
			contentPanel.add(label);
		}
		{
			deSpecifications = new JTextField();
			deSpecifications.setColumns(10);
			deSpecifications.setBounds(150, 100, 200, 20);
			contentPanel.add(deSpecifications);
		}
		{
			deType = new JComboBox<ComBoxEntity>();
			deType.setBounds(150, 70, 200, 20);
			contentPanel.add(deType);
		}
		{
			JLabel label = new JLabel("设备说明");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 160, 80, 20);
			contentPanel.add(label);
		}
		
		deRemark = new JTextPane();
		deRemark.setBounds(150, 160, 200, 63);
		contentPanel.add(deRemark);
		{
			JLabel label = new JLabel("设备状态");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 130, 80, 20);
			contentPanel.add(label);
		}
		{
			deState = new JComboBox<ComBoxEntity>();
			deState.setBounds(150, 130, 200, 20);
			contentPanel.add(deState);
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
				cancelButton.addActionListener(new ActionListener() {
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
		this.setVisible(true);
		this.deType.addItem( new ComBoxEntity("《--------请选择--------》" ,""));
		this.deState.addItem( new ComBoxEntity("《--------请选择--------》" ,""));
		new DataDictionaryMainEntity( DataDictionaryMainEntity.DD_DEVICE_TYPE, deType);
		new DataDictionaryMainEntity( DataDictionaryMainEntity.DD_DEVICE_STATE, deState);
		
		if( null == parentDate){
			deState.setSelectedIndex(1);
			deState.setEnabled(false);
			this.setTitle("东软智能云制造-新建设备");	
		}else{
			this.setTitle( "东软智能云制造-修改设备" );
			deName.setText(parentDate.getDeName());
			deSpecifications.setText(parentDate.getDeSpecifications());
			deRemark.setText( parentDate.getDeRemark());
			for( int i = 0 ; i < deType.getItemCount() ; i ++ ){
				if( parentDate.getDeType().equals(((ComBoxEntity) deType.getItemAt(i)).getComValue())){
					deType.setSelectedIndex(i);
					break;
				}
			}
			
			for( int i = 0 ; i < deState.getItemCount() ; i ++ ){
				if( parentDate.getDeState().equals(((ComBoxEntity) deState.getItemAt(i)).getComValue())){
					deState.setSelectedIndex(i);
					break;
				}
			}
		}
	}
	
	private void saveAction(){
		
		if( null == deName.getText() || "".equals( deName.getText())){
			JOptionPane.showMessageDialog(null , "请输入设备名称","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == deSpecifications.getText() || "".equals( deSpecifications.getText())){
			JOptionPane.showMessageDialog(null , "请输入设备规格","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( 0 == deType.getSelectedIndex()  ){
			JOptionPane.showMessageDialog(null , "请选择设备类型","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == parentDate ){
			DeviceEntity de = new DeviceEntity();
			de.setDeName(deName.getText());
			de.setDeSpecifications(deSpecifications.getText());
			de.setDeType(((ComBoxEntity)deType.getSelectedItem()).getComValue());
			de.setDeRemark(deRemark.getText());
			
			DeviceManageController.getInstance().create(de);
			if("success".equals( de.getMsgMap().get("state") )){
				JOptionPane.showMessageDialog(null , "添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
				parentWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog(null , de.getMsgMap().get("msg"),"提示",JOptionPane.WARNING_MESSAGE);
			}
		}else{
			parentDate.setDeName(deName.getText());
			parentDate.setDeSpecifications(deSpecifications.getText());
			parentDate.setDeType(((ComBoxEntity)deType.getSelectedItem()).getComValue());
			parentDate.setDeRemark(deRemark.getText());
			parentDate.setDeState(((ComBoxEntity)deState.getSelectedItem()).getComValue());
			DeviceManageController.getInstance().modify(parentDate);
			if("success".equals( parentDate.getMsgMap().get("state") )){
				JOptionPane.showMessageDialog(null , "修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
				parentWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog(null , parentDate.getMsgMap().get("msg"),"提示",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
