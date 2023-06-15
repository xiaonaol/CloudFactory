package com.neuedu.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.neuedu.controller.BidManageController;
import com.neuedu.controller.PlanManageController;
import com.neuedu.model.BidEntity;
import com.neuedu.model.ComBoxEntity;
import com.neuedu.model.PlanDeviceEntity;
import com.neuedu.model.PlanEntity;
import com.neuedu.util.PlanDeviceCheckHeaderCellRenderer;
import com.neuedu.util.PlanDeviceTableModelProxy;

public class PlanEditDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTable table;
	private JScrollPane	tablecroll;
	private PlanDeviceTableModelProxy tableModel;
	
	private JTextField peName;
	private JComboBox<ComBoxEntity> peBidCode ;
	
	private JButton btnUndone;
	private JButton btnDone;
	private JButton btnCreate;
	private JButton btnRemove;
	
	private boolean createFlag = false;
	
	private PlanManage parentWindow ;
	private PlanEntity parentDate;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PlanEditDialog dialog = new PlanEditDialog( null , null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PlanEditDialog( PlanManage _parentWindow , PlanEntity _parentDate) {
		this.parentDate = _parentDate;
		this.parentWindow = _parentWindow;
		
		setBounds(100, 100, 450, 389);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("计划名称");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(50, 50, 80, 20);
			contentPanel.add(lblNewLabel);
		}
		{
			peName = new JTextField();
			peName.setBounds(150, 50, 200, 20);
			contentPanel.add(peName);
			peName.setColumns(10);
		}
		{
			JLabel label = new JLabel("对应中标");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 80, 80, 20);
			contentPanel.add(label);
		}
		{
			peBidCode = new JComboBox<ComBoxEntity>();
			peBidCode.setBounds(150, 80, 200, 20);
			contentPanel.add(peBidCode);
		}
		{
			btnCreate = new JButton("新增");
			btnCreate.setBounds(240, 110, 80, 30);
			contentPanel.add(btnCreate);
		}
		{
			btnRemove = new JButton("删除");
			btnRemove.setBounds(330, 110, 80, 30);
			contentPanel.add(btnRemove);
		}
		{
			table = new JTable();
			tablecroll= new JScrollPane(table);
			tablecroll.setBounds(10, 161, 414, 147);
			contentPanel.add(tablecroll);
		}
		{
			btnDone = new JButton("完成");
			btnDone.setBounds(150, 110, 80, 30);
			contentPanel.add(btnDone);
		}
		
		btnUndone = new JButton("未完成");
		btnUndone.setBounds(60, 110, 80, 30);
		contentPanel.add(btnUndone);
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
						backAction();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		pageInit();
	}
	
	private void pageInit(){
		this.setVisible( true);
		
		peBidCode.addItem( new ComBoxEntity( "《--------请选择--------》",""));
		List<BidEntity> bidList = BidManageController.getInstance().queryWinBid();
		for( BidEntity bid : bidList){
			peBidCode.addItem( new ComBoxEntity( bid.getOrder().getOeName(), bid.getBeCode()));
		}
		
		if( null == parentDate){
			createFlag = true;
			this.setTitle( "东软智能云制造 - 新建生产计划" );
			parentDate= PlanManageController.getInstance().planCreate();
			if( !"success".equals(parentDate.getMsgMap().get("state"))){
				JOptionPane.showMessageDialog(null , parentDate.getMsgMap().get("msg"),"创建失败", JOptionPane.ERROR_MESSAGE);
				dispose();
			}
		}else{
			this.setTitle("东软智能云制造 - 修改生产计划");
			peName.setText( parentDate.getPeName());
			for( int i = 0 ; i < peBidCode.getItemCount() ; i++ ){
				if( parentDate.getBid().getBeCode().equals(((ComBoxEntity) peBidCode.getItemAt(i)).getComValue())){
					peBidCode.setSelectedIndex(i);
					break;
				}
			}
			peBidCode.setEnabled(false);
		}
		
		btnUndone.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnpdeModifyAction("0");
			}
		});
		
		btnDone.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnpdeModifyAction("1");
			}
		});
		
		btnCreate.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCreateAction();
			}
		});
		
		btnRemove.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRemoveAction();
			}
		});

		// 表格初始化
		List<PlanDeviceEntity> deviceList = PlanManageController.getInstance().planDeviceQuery(parentDate);
		tableModel = new PlanDeviceTableModelProxy(PlanDeviceEntity.ENTITY_TITLE , deviceList );
		table.setModel(tableModel);
		table.getTableHeader().setDefaultRenderer(new PlanDeviceCheckHeaderCellRenderer(table));
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class,   r);
	
	}
	
	private void saveAction(){
		parentDate.setPeName( peName.getText());
		parentDate.setPeBidCode( ((ComBoxEntity) peBidCode.getSelectedItem()) .getComValue());
	
		PlanManageController.getInstance().planModify(parentDate);
		if( "success".equals(parentDate.getMsgMap().get("state"))){
			JOptionPane.showMessageDialog(null, "保存成功","成功",JOptionPane.INFORMATION_MESSAGE);
			parentWindow.tableReload();
			dispose();
		}else{
			JOptionPane.showMessageDialog( null , parentDate.getMsgMap().get("msg") , "" ,JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 返回按钮， 当页面数据为新增情况， 删除新建的数据内容
	 */
	private void backAction(){
		if( createFlag ){
			List<PlanEntity> rList = new ArrayList<PlanEntity>();
			rList.add(parentDate);
			PlanManageController.getInstance().planRemove(rList);
		}
		parentWindow.tableReload();
		dispose();
	}
	
	private void btnCreateAction(){
		new PlanDeviceDialog( this, parentDate);
	}
	
	private void btnRemoveAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "是否确认删除", "删除确认", JOptionPane.YES_NO_OPTION)){
				Map<String, String>  rMap =PlanManageController.getInstance().planDeviceRemove(tableModel.getSelectedRowDate());
				if( "success".equals(rMap.get("state"))){
					JOptionPane.showMessageDialog(null,"删除成功", "删除成功", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, rMap.get("msg"), "删除失败", JOptionPane.ERROR_MESSAGE);
				}
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录进行删除", "提示", JOptionPane.WARNING_MESSAGE);
		}
		tableReload();
	}
	
	private  void btnpdeModifyAction(String state){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			if( 1 == tableModel.getSelectedRowDate().size() ){
				PlanDeviceEntity pde = tableModel.getSelectedRowDate().get(0);
				pde.setPdeState(state);
				PlanManageController.getInstance().planDeviceModify(pde);
				if("fail".equals(pde.getMsgMap().get("state"))){
					JOptionPane.showMessageDialog(null, pde.getMsgMap().get("msg"), "提示", JOptionPane.ERROR_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "生产状态已变更", "提示", JOptionPane.INFORMATION_MESSAGE);
					tableReload();
				}
			}else{
				JOptionPane.showMessageDialog(null, "只能选择一条记录修改状态", "提示", JOptionPane.WARNING_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录修改状态", "提示", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void tableReload(){
		tableModel.setData(  PlanManageController.getInstance().planDeviceQuery(parentDate) );
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
	
}
