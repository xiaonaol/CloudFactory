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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.neuedu.controller.BidManageController;
import com.neuedu.model.BidEntity;
import com.neuedu.model.OrderEntity;

public class OrderSubmitDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private OrderEntity parentDate;
	private JTextField beCount;
	private JTextField bePrice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OrderSubmitDialog dialog = new OrderSubmitDialog( null );
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OrderSubmitDialog( OrderEntity _parentDate) {
		this.parentDate = _parentDate;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("承接数量");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(50, 75, 80, 20);
			contentPanel.add(lblNewLabel);
		}
		{
			beCount = new JTextField();
			beCount.setBounds(150, 75, 200, 20);
			contentPanel.add(beCount);
			beCount.setColumns(10);
		}
		{
			JLabel label = new JLabel("单个报价");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 137, 80, 20);
			contentPanel.add(label);
		}
		{
			bePrice = new JTextField();
			bePrice.setColumns(10);
			bePrice.setBounds(150, 137, 200, 20);
			contentPanel.add(bePrice);
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
		this.setTitle( "东软智能云制造-投标");
	}
	
	private void saveAction(){
		
		if( null == bePrice.getText() || "".equals(bePrice.getText())){
			JOptionPane.showMessageDialog(null, "请输入订单单价","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == beCount.getText() || "".equals(beCount.getText())){
			JOptionPane.showMessageDialog(null, "请输入承接数量","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		BidEntity entity = new BidEntity();
		entity.setBeOrderCode( parentDate.getOeCode());
		entity.setBeCount(beCount.getText());
		entity.setBePrice(bePrice.getText());

		BidManageController.getInstance().create(entity);
		if( "success".equals( entity.getMsgMap().get("state"))){
			JOptionPane.showMessageDialog(null, "投标成功","成功",JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}else{
			JOptionPane.showMessageDialog(null,  entity.getMsgMap().get("msg"),"失败",JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
