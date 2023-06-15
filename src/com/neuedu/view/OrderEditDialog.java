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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.neuedu.controller.OrderManageController;
import com.neuedu.controller.ProductManageController;
import com.neuedu.model.ComBoxEntity;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.OrderEntity;
import com.neuedu.model.ProductEntity;

public class OrderEditDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private OrderManage parentWindow ;
	private  OrderEntity parentDate ;
	
	
	private JTextField oeName;
	private JTextField oeReceiver;
	private JTextField oeReceiverTel;
	private JTextField oeReceiverAddress;
	private JTextField odeCount;
	
	private JComboBox<ComBoxEntity> oeState ;
	private JComboBox<ComBoxEntity>  odeProductCode ;
	private JSpinner oeDliverDate;
	private JSpinner oeCutOffDate;
	private JTextPane oeRemark ;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OrderEditDialog dialog = new OrderEditDialog( null , null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OrderEditDialog( OrderManage _parentWindow , OrderEntity _parentDate ) {
		this.parentDate = _parentDate;
		this.parentWindow = _parentWindow;
		setBounds(100, 100, 450, 488);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("订单名称");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(50, 50, 80, 20);
			contentPanel.add(lblNewLabel);
		}
		{
			oeName = new JTextField();
			oeName.setBounds(150, 50, 200, 20);
			contentPanel.add(oeName);
			oeName.setColumns(10);
		}
		
		JLabel label = new JLabel("订单状态");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(50, 80, 80, 20);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("交付时间");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(50, 110, 80, 20);
		contentPanel.add(label_1);
		
		JLabel label_2 = new JLabel("投标截止时间");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(50, 140, 80, 20);
		contentPanel.add(label_2);
		
		JLabel label_3 = new JLabel("接收人");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(50, 170, 80, 20);
		contentPanel.add(label_3);
		
		oeReceiver = new JTextField();
		oeReceiver.setColumns(10);
		oeReceiver.setBounds(150, 170, 200, 20);
		contentPanel.add(oeReceiver);
		
		JLabel label_4 = new JLabel("接收人电话");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(50, 200, 80, 20);
		contentPanel.add(label_4);
		
		oeReceiverTel = new JTextField();
		oeReceiverTel.setColumns(10);
		oeReceiverTel.setBounds(150, 200, 200, 20);
		contentPanel.add(oeReceiverTel);
		
		JLabel label_5 = new JLabel("接收人地址");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(50, 230, 80, 20);
		contentPanel.add(label_5);
		
		oeReceiverAddress = new JTextField();
		oeReceiverAddress.setColumns(10);
		oeReceiverAddress.setBounds(150, 230, 200, 20);
		contentPanel.add(oeReceiverAddress);
		
		JLabel label_6 = new JLabel("订单产品");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(50, 260, 80, 20);
		contentPanel.add(label_6);
		
		JLabel label_7 = new JLabel("订单数量");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(50, 290, 80, 20);
		contentPanel.add(label_7);
		
		odeCount = new JTextField();
		odeCount.setColumns(10);
		odeCount.setBounds(150, 290, 200, 20);
		contentPanel.add(odeCount);
		
		JLabel label_8 = new JLabel("备注");
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setBounds(50, 320, 80, 20);
		contentPanel.add(label_8);
		
		oeState = new JComboBox<ComBoxEntity>();
		oeState.setBounds(150, 80, 200, 20);
		contentPanel.add(oeState);
		
		oeDliverDate = new JSpinner(new SpinnerDateModel());
		oeDliverDate.setBounds(150, 110, 200, 20);
		oeDliverDate.setEditor(new JSpinner.DateEditor(oeDliverDate, "yyyy-MM-dd"));
		contentPanel.add(oeDliverDate);
		
		oeCutOffDate = new JSpinner(new SpinnerDateModel());
		oeCutOffDate.setBounds(150, 140, 200, 20);
		oeCutOffDate.setEditor(new JSpinner.DateEditor(oeCutOffDate, "yyyy-MM-dd"));
		contentPanel.add(oeCutOffDate);
		
		odeProductCode = new JComboBox<ComBoxEntity>();
		odeProductCode.setBounds(150, 260, 200, 20);
		contentPanel.add(odeProductCode);
		
		oeRemark = new JTextPane();
		oeRemark.setBounds(150, 320, 198, 60);
		contentPanel.add(oeRemark);
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
		
		// 下拉框数据加载
		oeState.addItem( new ComBoxEntity("《--------请选择--------》" ,  "" ));
		odeProductCode.addItem( new ComBoxEntity("《--------请选择--------》" ,  "" ));
		new DataDictionaryMainEntity( DataDictionaryMainEntity.DD_ORDER_STATE , oeState) ;
		List<ProductEntity> pList = ProductManageController.getInstance().query(null);
		if( null != pList && !pList.isEmpty() ){
			for( ProductEntity pe : pList){
				odeProductCode.addItem( new ComBoxEntity( pe.getPeName(), pe.getPeCode()));
			}
		}
		if( null == parentDate){
			this.setTitle( "东软智能云制造 - 新建订单" );
		}else{
			this.setTitle( "东软智能云制造 - 修改订单" );
			oeName.setText(parentDate.getOeName());
			oeReceiver.setText(parentDate.getOeReceiver());
			oeReceiverTel.setText( parentDate.getOeReceiverTel());
			oeReceiverAddress.setText( parentDate.getOeReceiverAddress());
			odeCount.setText(parentDate.getOdeCount());
			oeDliverDate.setValue( parentDate.getOeDliverDate());
			oeCutOffDate.setValue( parentDate.getOeCutOffDate());
			oeRemark.setText(parentDate.getOeRemark());
			
			for( int i = 0 ; i < oeState.getItemCount() ; i ++ ){
				if( ((ComBoxEntity)oeState.getItemAt(i)).getComValue().equals(parentDate.getOeState())){
					oeState.setSelectedIndex(i);
					break;
				}
			}
			
			for( int i = 0 ; i < odeProductCode.getItemCount() ; i ++ ){
				if( ((ComBoxEntity)odeProductCode.getItemAt(i)).getComValue().equals(parentDate.getOdeProductCode())){
					odeProductCode.setSelectedIndex(i);
					break;
				}
			}
		}
	}
	
	
	private void saveAction(){
		
		if( null == oeName.getText() || "".equals(oeName.getText())){
			JOptionPane.showMessageDialog( null , "请输入订单名称" ,"提示" ,JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == oeReceiver.getText() || "".equals(oeReceiver.getText())){
			JOptionPane.showMessageDialog( null , "请输入收件人" ,"提示" ,JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == oeReceiverTel.getText() || "".equals(oeReceiverTel.getText())){
			JOptionPane.showMessageDialog( null , "请输入收件人电话" ,"提示" ,JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == oeReceiverAddress.getText() || "".equals(oeReceiverAddress.getText())){
			JOptionPane.showMessageDialog( null , "请输入收件人地址" ,"提示" ,JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == odeCount.getText() || "".equals(odeCount.getText())){
			JOptionPane.showMessageDialog( null , "请输入订单数量" ,"提示" ,JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == oeDliverDate.getValue() || "".equals(oeDliverDate.getValue())){
			JOptionPane.showMessageDialog( null , "请输入交付时间" ,"提示" ,JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == oeCutOffDate.getValue() || "".equals(oeCutOffDate.getValue())){
			JOptionPane.showMessageDialog( null , "请输入投标截止时间" ,"提示" ,JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( 0 == odeProductCode.getSelectedIndex() ){
			JOptionPane.showMessageDialog( null , "请选择订单产品" ,"提示" ,JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == parentDate ){
			OrderEntity entity = new OrderEntity();
			entity.setOeName( oeName.getText());
			entity.setOeReceiver( oeReceiver.getText());
			entity.setOeReceiverTel(oeReceiverTel.getText());
			entity.setOeReceiverAddress(oeReceiverAddress.getText());
			entity.setOdeCount(odeCount.getText());
			entity.setOeDliverDate((Date)oeDliverDate.getValue());
			entity.setOeCutOffDate((Date)oeCutOffDate.getValue());
			entity.setOdeProductCode( ((ComBoxEntity) odeProductCode.getSelectedItem()) .getComValue() );
			entity.setOeState( ((ComBoxEntity) oeState.getSelectedItem()) .getComValue() );
			entity.setOeReceiver(oeReceiver.getText());
			
			OrderManageController.getInstance().create(entity);
			if( "success".equals(entity.getMsgMap().get("state"))){
				JOptionPane.showMessageDialog( null , "保存成功","成功", JOptionPane.INFORMATION_MESSAGE);
				parentWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog( null ,  entity.getMsgMap().get("msg") ,"失败", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			parentDate.setOeName( oeName.getText());
			parentDate.setOeReceiver( oeReceiver.getText());
			parentDate.setOeReceiverTel(oeReceiverTel.getText());
			parentDate.setOeReceiverAddress(oeReceiverAddress.getText());
			parentDate.setOdeCount(odeCount.getText());
			parentDate.setOeDliverDate((Date)oeDliverDate.getValue());
			parentDate.setOeCutOffDate((Date)oeCutOffDate.getValue());
			parentDate.setOdeProductCode( ((ComBoxEntity) odeProductCode.getSelectedItem()) .getComValue() );
			parentDate.setOeState( ((ComBoxEntity) oeState.getSelectedItem()) .getComValue() );
			parentDate.setOeReceiver(oeReceiver.getText());
			OrderManageController.getInstance().modify(parentDate);
			if( "success".equals(parentDate.getMsgMap().get("state"))){
				JOptionPane.showMessageDialog( null , "保存成功","成功", JOptionPane.INFORMATION_MESSAGE);
				parentWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog( null ,  parentDate.getMsgMap().get("msg") ,"失败", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
