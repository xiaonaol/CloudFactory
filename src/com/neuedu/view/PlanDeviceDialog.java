package com.neuedu.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.neuedu.controller.PlanManageController;
import com.neuedu.model.ComBoxEntity;
import com.neuedu.model.DeviceEntity;
import com.neuedu.model.PlanDeviceEntity;
import com.neuedu.model.PlanEntity;

public class PlanDeviceDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField pdeCount;
	private JComboBox<ComBoxEntity> pdeDeviceCode ;
	private JSpinner pdeBegin;
	private JSpinner pdeEnd;
	
	
	private PlanEditDialog parentWindow ;
	private PlanEntity parentDate;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PlanDeviceDialog dialog = new PlanDeviceDialog( null , null );
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PlanDeviceDialog( PlanEditDialog _parentWindow , PlanEntity _parentDate ) {
		this.parentDate = _parentDate;
		this.parentWindow = _parentWindow;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("设备");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(50, 50, 80, 20);
			contentPanel.add(lblNewLabel);
		}
		{
			pdeDeviceCode = new JComboBox<ComBoxEntity>();
			pdeDeviceCode.setBounds(150, 50, 200, 20);
			contentPanel.add(pdeDeviceCode);
		}
		{
			JLabel label = new JLabel("开始时间");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 80, 80, 20);
			contentPanel.add(label);
		}
		{
			pdeBegin = new JSpinner(new SpinnerDateModel());
			pdeBegin.setBounds(150, 80, 200, 22);
			pdeBegin.setEditor(new JSpinner.DateEditor(pdeBegin, "yyyy-MM-dd"));
			contentPanel.add(pdeBegin);
		}
		{
			JLabel label = new JLabel("结束时间");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 110, 80, 20);
			contentPanel.add(label);
		}
		{
			pdeEnd = new JSpinner(new SpinnerDateModel());
			pdeEnd.setBounds(150, 110, 200, 22);
			pdeEnd.setEditor(new JSpinner.DateEditor(pdeEnd, "yyyy-MM-dd"));
			contentPanel.add(pdeEnd);
		}
		{
			JLabel label = new JLabel("生产数量");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 140, 80, 20);
			contentPanel.add(label);
		}
		{
			pdeCount = new JTextField();
			pdeCount.setBounds(150, 140, 200, 20);
			contentPanel.add(pdeCount);
			pdeCount.setColumns(10);
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
		this.setVisible(true);
		this.setTitle("东软智能云制造 - 添加设备");

		pdeDeviceCode.addItem( new ComBoxEntity("《--------请选择--------》",""));
		List<DeviceEntity> dList = PlanManageController.getInstance().queryDevice( parentDate);
		for( DeviceEntity de : dList){
			pdeDeviceCode.addItem( new ComBoxEntity( de.getDeName() ,  de.getDeCode()));
		}
	}
	
	private void saveAction(){
		
		if( 0 == pdeDeviceCode.getSelectedIndex()){
			JOptionPane.showMessageDialog( null , "请选择添加设备","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if( null == pdeBegin.getValue() || "".equals(pdeBegin.getValue())){
			JOptionPane.showMessageDialog( null , "请输入设备使用开始时间","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if( null == pdeEnd.getValue() || "".equals(pdeEnd.getValue())){
			JOptionPane.showMessageDialog( null , "请输入设备使用结束时间","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if( null == pdeCount.getText() || "".equals( pdeCount.getText() )){
			JOptionPane.showMessageDialog( null , "请输入生产数量 ","提示",JOptionPane.WARNING_MESSAGE);
			return ;
		}

		PlanDeviceEntity pde = new PlanDeviceEntity();
		pde.setPdePlanCode( parentDate.getPeCode());
		pde.setPdeDeviceCode( ((ComBoxEntity)pdeDeviceCode.getSelectedItem()).getComValue());
		pde.setPdeDeviceEnd((Date)pdeEnd.getValue());
		pde.setPdeDeviceStart((Date)pdeBegin.getValue());
		pde.setPdeProductCount(pdeCount.getText());
		pde.setPdeState("0");
		PlanManageController.getInstance().planDeviceCreate(pde);
		if( "success".equals(pde.getMsgMap().get("state"))){
			JOptionPane.showMessageDialog( null, "添加成功","成功",JOptionPane.INFORMATION_MESSAGE);
			parentWindow.tableReload();
			dispose();
		}else{
			JOptionPane.showMessageDialog( null,  pde.getMsgMap().get("msg"),"失败",JOptionPane.ERROR_MESSAGE);
		}
	}

}
