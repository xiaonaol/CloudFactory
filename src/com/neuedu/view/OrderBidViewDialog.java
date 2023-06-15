package com.neuedu.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.neuedu.controller.BidManageController;
import com.neuedu.model.BidEntity;
import com.neuedu.model.OrderEntity;
import com.neuedu.util.BidCheckHeaderCellRenderer;
import com.neuedu.util.BidManageTableModelProxy;
import com.neuedu.util.OrderManageCheckHeaderCellRenderer;

public class OrderBidViewDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JScrollPane	tablecroll;
	private BidManageTableModelProxy tableModel;
	
	private JButton btnWinBid ;
	private JButton btnLoseBid;

	private OrderEntity order;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OrderBidViewDialog dialog = new OrderBidViewDialog( null );
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OrderBidViewDialog( OrderEntity _order ) {
		this.order = _order;
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			btnWinBid = new JButton("中标");
			btnWinBid.setBounds(481, 50, 80, 30);
			contentPanel.add(btnWinBid);
		}
		{
			btnLoseBid = new JButton("取消");
			btnLoseBid.setBounds(581, 50, 80, 30);
			contentPanel.add(btnLoseBid);
		}
		{
			table = new JTable();
			tablecroll= new JScrollPane(table);
			tablecroll.setBounds(10, 116, 764, 403);
			contentPanel.add(tablecroll);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("返回");
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
		this.setTitle( "东软智能云制造 - 竞标信息" );
		
		btnWinBid.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnBidAction("1");
			}
		});
		
		btnLoseBid.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnBidAction("2");
			}
		});
		
		List<BidEntity> bidList = BidManageController.getInstance().query(this.order);
		tableModel = new BidManageTableModelProxy(BidEntity.ENTITY_TITLE , bidList );
		table.setModel(tableModel);
		table.getTableHeader().setDefaultRenderer(new BidCheckHeaderCellRenderer(table));
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class,   r);
	}
	
	private void btnBidAction( String code){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			Map<String , String> msgMap = BidManageController.getInstance().modify( tableModel.getSelectedRowDate(), code);
			if( "success".equals(msgMap.get("state"))){
				JOptionPane.showMessageDialog(null, "状态已变更", "提示", JOptionPane.INFORMATION_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(null, msgMap.get("msg"), "错误", JOptionPane.ERROR_MESSAGE);
			}
		}else{
 			JOptionPane.showMessageDialog(null, "至少选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
		}
		tableReload();
	}
	
	private void tableReload(){
		tableModel.setData( BidManageController.getInstance().query(this.order) );
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}

}
