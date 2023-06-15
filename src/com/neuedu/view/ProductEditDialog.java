package com.neuedu.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.neuedu.controller.ProductManageController;
import com.neuedu.model.ComBoxEntity;
import com.neuedu.model.DataDictionaryMainEntity;
import com.neuedu.model.ProductEntity;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;

public class ProductEditDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private ProductManage parentWindow ;
	private ProductEntity parentDate ;
	private JTextField peName;
	private JTextField peSpe;
	private JComboBox<ComBoxEntity> peType;
	private JTextPane peRemark ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ProductEditDialog dialog = new ProductEditDialog( null , null );
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ProductEditDialog( ProductManage _parentWindow , ProductEntity _parentDate ) {
		this.parentDate = _parentDate;
		this.parentWindow = _parentWindow;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("产品名称");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(50, 40, 80, 20);
			contentPanel.add(lblNewLabel);
		}
		{
			peName = new JTextField();
			peName.setBounds(150, 40, 200, 20);
			contentPanel.add(peName);
			peName.setColumns(10);
		}
		{
			JLabel label = new JLabel("产品规格");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 70, 80, 20);
			contentPanel.add(label);
		}
		{
			peSpe = new JTextField();
			peSpe.setColumns(10);
			peSpe.setBounds(150, 70, 200, 20);
			contentPanel.add(peSpe);
		}
		{
			JLabel label = new JLabel("产品类型");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 100, 80, 20);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("备注");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 130, 80, 20);
			contentPanel.add(label);
		}
		{
			peRemark = new JTextPane();
			peRemark.setBounds(150, 130, 200, 51);
			contentPanel.add(peRemark);
		}
		{
			peType = new JComboBox<ComBoxEntity>();
			peType.setBounds(150, 100, 200, 20);
			contentPanel.add(peType);
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
		
		peType.addItem( new ComBoxEntity("《--------请选择--------》",""));
		new DataDictionaryMainEntity(DataDictionaryMainEntity.DD_PRODUCT_TYPE, peType);
		
		if( null == parentDate){
			this.setTitle( "东软智能云工厂 - 新建产品" );
		}else{
			this.setTitle( "东软智能云工厂 - 修改产品");
			
			peName.setText( parentDate.getPeName());
			peSpe.setText( parentDate.getSpecifications());
			peRemark.setText( parentDate.getPeRemark());
			
			for( int i = 0 ; i < peType.getItemCount() ; i ++ ){
				if( ((ComBoxEntity) peType.getItemAt(i)).getComValue().equals(parentDate.getPeType())){
					peType.setSelectedIndex(i);
					break;
				}
			}
		}
		
	}
	
	private void saveAction(){
		
		if( null == peName.getText() || "".equals(peName.getText())){
			JOptionPane.showMessageDialog( null, "请输入产品名称","提示", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == peSpe.getText() || "".equals(peSpe.getText())){
			JOptionPane.showMessageDialog( null, "请输入产品规格","提示", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( 0 == peType.getSelectedIndex() ){
			JOptionPane.showMessageDialog( null, "请选择产品类型","提示", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if( null == parentDate){
			ProductEntity pe = new ProductEntity();
			pe.setPeName( peName.getText());
			pe.setSpecifications( peSpe.getText());
			pe.setPeType( ((ComBoxEntity) peType.getSelectedItem()).getComValue());
			pe.setPeRemark(peRemark.getText());
			
			ProductManageController.getInstance().create(pe);
			if( "success".equals( pe.getMsgMap().get("state"))){
				JOptionPane.showMessageDialog(null, "保存成功","成功",JOptionPane.INFORMATION_MESSAGE);
				parentWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog(null, pe.getMsgMap().get("msg"),"失败",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			parentDate.setPeName( peName.getText());
			parentDate.setSpecifications( peSpe.getText());
			parentDate.setPeType( ((ComBoxEntity) peType.getSelectedItem()).getComValue());
			parentDate.setPeRemark(peRemark.getText());
			
			ProductManageController.getInstance().modify(parentDate);
			if( "success".equals( parentDate.getMsgMap().get("state"))){
				JOptionPane.showMessageDialog(null, "保存成功","成功",JOptionPane.INFORMATION_MESSAGE);
				parentWindow.tableReload();
				dispose();
			}else{
				JOptionPane.showMessageDialog(null, parentDate.getMsgMap().get("msg"),"失败",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
