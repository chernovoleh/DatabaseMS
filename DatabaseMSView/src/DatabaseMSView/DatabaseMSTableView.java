package DatabaseMSView;

import java.util.EventObject;

import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import DatabaseMS.DatabaseMSController;

public class DatabaseMSTableView {
	private JTable table;
	private DatabaseMSController msController;	
	
	private class DbCellEditor extends DefaultCellEditor  {	
		private String columnName;
		
		public DbCellEditor(String columnName) {
			super(new JTextField());
			this.columnName = columnName;			
		}

		@Override
	    public boolean stopCellEditing() {
			JTextField textField = (JTextField)super.editorComponent;
	        String value = textField.getText();
	        
	        Boolean verified = msController.IsValueValid(columnName, value);
			return verified && super.stopCellEditing();
	    }
		
	}

	
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
		
		if(columnNames == null)
			return;
		
		for(Object columnName : columnNames) {
			table.getColumn(columnName).setCellEditor(new DbCellEditor(columnName.toString()));
		}
	}
}
