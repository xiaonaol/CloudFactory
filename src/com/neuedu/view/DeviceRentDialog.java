package com.neuedu.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.neuedu.controller.DeviceManageController;
import com.neuedu.model.ComBoxEntity;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.DeviceEntity;
import com.neuedu.util.DeviceCheckHeaderCellRenderer;
import com.neuedu.util.DeviceManageTableModelProxy;

public class DeviceRentDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField deName;
	private JTextField deCode;
	private JComboBox<ComBoxEntity> deType;
	
	private JTable table;
	private JScrollPane	tablecroll;
	private DeviceManageTableModelProxy tableModel;

	private JButton btnQuery;
	
	private DeviceManage parentWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DeviceRentDialog dialog = new DeviceRentDialog( null );
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DeviceRentDialog(DeviceManage _parentWindow) {
		this.parentWindow = _parentWindow;
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lable = new JLabel("设备名称");
			lable.setHorizontalAlignment(SwingConstants.CENTER);
			lable.setBounds(50, 50, 80, 20);
			contentPanel.add(lable);
		}
		{
			deName = new JTextField();
			deName.setBounds(150, 50, 200, 20);
			contentPanel.add(deName);
			deName.setColumns(10);
		}
		{
			JLabel lable1 = new JLabel("设备编码");
			lable1.setHorizontalAlignment(SwingConstants.CENTER);
			lable1.setBounds(416, 50, 80, 20);
			contentPanel.add(lable1);
		}
		{
			deCode = new JTextField();
			deCode.setColumns(10);
			deCode.setBounds(516, 50, 200, 20);
			contentPanel.add(deCode);
		}
		{
			btnQuery = new JButton("查询");
			btnQuery.setBounds(636, 100, 80, 30);
			contentPanel.add(btnQuery);
		}
		{
			JLabel label = new JLabel("设备名称");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 100, 80, 20);
			contentPanel.add(label);
		}
		{
			deType = new JComboBox<ComBoxEntity>();
			deType.setBounds(150, 100, 200, 20);
			contentPanel.add(deType);
		}
		{
			table = new JTable();
			tablecroll= new JScrollPane(table);
			tablecroll.setBounds(10, 153, 764, 366);
			contentPanel.add(tablecroll);
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
		this.setTitle( "东软智能云制造-设备租用" );
		
		deType.addItem( new ComBoxEntity("《--------请选择--------》",""));
		new DataDictionaryMainEntity( DataDictionaryMainEntity.DD_DEVICE_TYPE, deType);
		
		btnQuery.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnQueryAction();
			}
		});
		
		DeviceEntity query = new DeviceEntity();
		query.setDeRentState("0");
		List<DeviceEntity> deviceList = DeviceManageController.getInstance().query(query);
		tableModel = new DeviceManageTableModelProxy(DataDictionaryMainEntity.ENTITY_TITLE , deviceList );
		table.setModel(tableModel);
		table.getTableHeader().setDefaultRenderer(new DeviceCheckHeaderCellRenderer(table));
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class,   r);
	}
	
	private void saveAction(){
		if(null != tableModel.getSelectedRowDate() && ! tableModel.getSelectedRowDate().isEmpty() ){
			Map<String , String> rMap = DeviceManageController.getInstance().deviceRent(tableModel.getSelectedRowDate() );
			if( "success".equals(rMap.get("state"))){
				JOptionPane.showMessageDialog(null, "设备租用成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				parentWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog(null, rMap.get("msg"), "提示", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "至少选择一条记录进行编辑", "提示", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void btnQueryAction(){
		DeviceEntity query = new DeviceEntity();
		query.setDeRentState("0");
		query.setDeName(deName.getText());
		query.setDeCode(deCode.getText());
		query.setDeType( ((ComBoxEntity) deType.getSelectedItem()) .getComValue() );
		tableModel.setData(DeviceManageController.getInstance().query(query));
		tableModel.fireTableDataChanged();
		table.setModel(tableModel);
	}
	
}
