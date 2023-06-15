package com.neuedu.util;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.neuedu.model.PlanEntity;


/**
 * 列表表头checkbox实现
 * @author koala
 *
 */
public class PlanManageCheckHeaderCellRenderer implements TableCellRenderer {
	PlanManageTableModelProxy tableModel;
    JTableHeader tableHeader;
    final JCheckBox selectBox;

    public PlanManageCheckHeaderCellRenderer(final JTable table) {
        this.tableModel = (PlanManageTableModelProxy) table.getModel();
        this.tableHeader = table.getTableHeader();
        selectBox = new JCheckBox(tableModel.getColumnName(0));
        selectBox.setSelected(false);
        tableHeader.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    // 获得选中列
                    int selectColumn = tableHeader.columnAtPoint(e.getPoint());
                    if (selectColumn == 0) {
                        boolean value = !selectBox.isSelected();
                        selectBox.setSelected(value);
                        tableModel.selectAllOrNull(value);
                        tableHeader.repaint();
                    }
                }
            }
        });
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
    	
        JLabel label = new JLabel(PlanEntity.ENTITY_TITLE[column]);
        label.setHorizontalAlignment(SwingConstants.CENTER); // 表头标签剧中
        selectBox.setHorizontalAlignment(SwingConstants.CENTER);// 表头标签剧中
        selectBox.setBorderPainted(true);
        JComponent component = (column ==0 ) ? selectBox : label;
        component.setForeground(tableHeader.getForeground());
        component.setBackground(tableHeader.getBackground());
        component.setFont(tableHeader.getFont());
        component.setBorder(UIManager.getBorder("TableHeader.cellBorder"));

        return component;
    }

}
