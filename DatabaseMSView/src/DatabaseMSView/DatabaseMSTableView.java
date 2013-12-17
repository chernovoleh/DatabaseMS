package DatabaseMSView;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import DatabaseMS.DatabaseMSController;

public class DatabaseMSTableView {
	private JTable table;
	private DatabaseMSController msController;	
	
	private TableModelListener tableModelListener = new TableModelListener() {

		@Override
		public void tableChanged(TableModelEvent e) {
			int row = e.getFirstRow();
	        int column = e.getColumn();
	        if(row < 0 || column < 0)
	        	return;
	        
	        if(e.getType() == TableModelEvent.UPDATE) {
	        	msController.OnUpdateValue(row, column, table.getModel().getValueAt(row, column));
	        }   
			
		}
		
	};
	
	public DatabaseMSTableView() {
		table = new JTable();
		table.getModel().addTableModelListener(tableModelListener);
	}
	
	public void setController(DatabaseMSController msController) {
		this.msController = msController;
	}
	
	public void registrate(JScrollPane scrollPane ) {
		scrollPane.setViewportView(table);		
	}
	
	public int getColumnCount() {
		return table.getColumnCount();
	}
	
	public int getRowCount() {
		return table.getRowCount();
	}
	
	public String getColumnName(int columnIndex) {
		return table.getModel().getColumnName(columnIndex);
	}
	
	public int getSelectedRow() {
		return table.getSelectedRow();
	}
	
	public void fileTable(Object[][] rows, Object[] columnNames) {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setDataVector(rows, columnNames);
	}
}
