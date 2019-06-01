package ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * ���а���
 *
 */
public class RankTable extends JTable {
	public RankTable(Object[][] rowData, Object[] columnNames) {
		this(rowData, columnNames, -1);
	}
	
	public RankTable(Object[][] rowData, Object[] columnNames, int markRow) {
		super(rowData, columnNames);

		// ���Զ���ת���
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// �������
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		setDefaultRenderer(Object.class, r);
		
		if(markRow >= 0){
			setOneRowBackgroundColor(markRow, Color.YELLOW);
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	private void setOneRowBackgroundColor(int rowIndex, Color color) {
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (row == rowIndex) {
					setBackground(color);
					setForeground(Color.BLACK);
				}else{
					setBackground(Color.WHITE);
					setForeground(Color.BLACK);
				}

				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		
		tcr.setBackground(Color.BLACK);
		int columnCount = getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			getColumn(getColumnName(i)).setCellRenderer(tcr);
		}
	}
	
}
