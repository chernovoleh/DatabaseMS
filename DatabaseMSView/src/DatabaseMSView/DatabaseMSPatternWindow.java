package DatabaseMSView;

import java.awt.Dialog;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DatabaseMS.DatabaseMSController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DatabaseMSPatternWindow {

	private JDialog searchPatternDialog;
	private JTable table;
	private String[] columnNames;
	private DatabaseMSController msController;
	
	@SuppressWarnings("serial")
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

	
	/**
	 * Create the application.
	 */
	public DatabaseMSPatternWindow(JFrame owner, String [] columnNames) {
		searchPatternDialog = new JDialog(owner, Dialog.ModalityType.APPLICATION_MODAL);
		this.columnNames = columnNames;
		initialize();
	}
	
	public void show() {
		searchPatternDialog.setVisible(true);
	}
	
	public void setController(DatabaseMSController controller) {
		msController = controller;		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		searchPatternDialog.setResizable(false);
		searchPatternDialog.setTitle("Search pattern dialog");
		searchPatternDialog.setBounds(100, 100, 446, 128);	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 62);
		searchPatternDialog.getContentPane().add(scrollPane);
				
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<String, String> pattern = new HashMap<String, String>();
				for(int i = 0;i < columnNames.length; ++i) {
					if(table.getValueAt(0, i) != null)
						pattern.put(columnNames[i], table.getValueAt(0, i).toString());
				}
				
				if(!msController.OnSearchByPattern(pattern))
					return;
				searchPatternDialog.setVisible(false);
			}
		});
		
		searchPatternDialog.getContentPane().setLayout(null);
		btnOk.setBounds(113, 66, 89, 23);
		searchPatternDialog.getContentPane().add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchPatternDialog.setVisible(false);
			}
		});
		btnCancel.setBounds(233, 66, 89, 23);
		searchPatternDialog.getContentPane().add(btnCancel);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		Object [][] patternRow = new Object[1][columnNames.length];
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setDataVector(patternRow, columnNames);
		
		for(Object columnName : columnNames)
			table.getColumn(columnName).setCellEditor(new DbCellEditor(columnName.toString()));
	}
}
