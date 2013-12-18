package DatabaseMSView;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import DatabaseMS.DatabaseMSController;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DatabaseMSCreateTableWindow {
	private JDialog createTableDialog;
	private JTextField textField;
	private JTable table;
	private DatabaseMSController msController;
	private Map<String, String> enums;
	
	@SuppressWarnings("serial")
	private class TypeColumnCellEditor extends DefaultCellEditor  {	
		private JComboBox<String> editor;
		
		public TypeColumnCellEditor() {
			super(new JComboBox<String>());
			editor = (JComboBox<String>)super.getComponent();
			
			String [] columnTypes = msController.GetDbTypeNames();
			for(String ct : Arrays.asList(columnTypes))
				editor.addItem(ct);	
			
			editor.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox<String> cb = (JComboBox<String>)e.getSource();
					if(cb.getSelectedItem() == null) 
						return;
			        String typeName = (String)cb.getSelectedItem();
			        if(!typeName.equals("dbTypeEnum"))
			        	return;
			        
			        DatabaseMSAddEnumWindow dialog = new DatabaseMSAddEnumWindow(createTableDialog);
			        dialog.show(new DatabaseMSAddEnumWindow.EnumCreatedListener() {
						
						@Override
						public void enumCreated(String[] enumValues) {
							StringBuilder enumType = new StringBuilder();
							for(int i = 0;i < enumValues.length; ++i)
								enumType.append("|").append(enumValues[i]);
							
							enumType.insert(0, "dbTypeEnum");
							String item = enumType.toString();
							editor.addItem(item);
							editor.setSelectedItem(item);
						}
					});
					
				}
			});
		}		
	}
	
	@SuppressWarnings("serial")
	private class NameColumnCellEditor extends DefaultCellEditor  {	
		public NameColumnCellEditor() {
			super(new JTextField());										
		}
		
		@Override
	    public boolean stopCellEditing() {
			JTextField textField = (JTextField)super.editorComponent;
	        String value = textField.getText();
	        Boolean ok = true;
	        for(int i = 0;i < table.getRowCount();++i) {
	           	if(table.getValueAt(i, 0) == null) continue;
	           	String columnName = (String)table.getValueAt(i, 0);
	           	if(columnName.trim().equals(value.trim()))
	           		ok = false;
	        }
	        
	        return ok && super.stopCellEditing();
	    }
	}

	/**
	 * Create the application.
	 */
	public DatabaseMSCreateTableWindow(JFrame owner) {
		enums = new HashMap<String, String>();
		createTableDialog = new JDialog(owner, Dialog.ModalityType.APPLICATION_MODAL);
		initialize();
	}
	
	public void setController(DatabaseMSController msController) {
		this.msController = msController;
	}	
	
	public void show() {		
		createTableDialog.setVisible(true);
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		createTableDialog.setTitle("Create table dialog");
		createTableDialog.setBounds(100, 100, 322, 296);
		createTableDialog.getContentPane().setLayout(null);
		
		JLabel lblTableName = new JLabel("Table name:");
		lblTableName.setBounds(10, 11, 67, 14);
		createTableDialog.getContentPane().add(lblTableName);
		
		textField = new JTextField();
		textField.setBounds(73, 8, 100, 20);
		createTableDialog.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblRowsCount = new JLabel("Rows count:");
		lblRowsCount.setBounds(183, 11, 67, 14);
		createTableDialog.getContentPane().add(lblRowsCount);
		
		JScrollPane scrollPane  = new JScrollPane();
		scrollPane.setBounds(10, 36, 291, 184);
		createTableDialog.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JComboBox<Integer> comboBox = new JComboBox<Integer>();
		comboBox.setBounds(252, 8, 49, 20);
		for(Integer i = 0;i < 10; ++i)
			comboBox.addItem(i);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<Integer> cb = (JComboBox<Integer>)e.getSource();
		        Integer columnsNum = (Integer)cb.getSelectedItem();
		        
		        Object [] columnNames = {"Column name", "Column type"};
		        Object [][] rows = new Object[columnsNum][2];
		        DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.setDataVector(rows, columnNames);	        
				table.getColumn("Column type").setCellEditor(new TypeColumnCellEditor());
				table.getColumn("Column name").setCellEditor(new NameColumnCellEditor());
			}			
		});
		createTableDialog.getContentPane().add(comboBox);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String tableName = textField.getText().trim(); 
				if(tableName.isEmpty())
					return;
				
				Map<String, String> tableScheme = new HashMap<String, String>();
				for(int i = 0;i < table.getRowCount();++i) {
					
					if(table.getValueAt(i, 0) == null || table.getValueAt(i, 1) == null)
						return;
					
					String columnName = table.getValueAt(i, 0).toString().trim();
					String columnType = table.getValueAt(i, 1).toString().trim();
					
					if(columnName.isEmpty() || columnType.isEmpty())
						return;
					
					tableScheme.put(columnName, columnType);					
				}
				if(!msController.OnTableAdded(tableName, tableScheme))
					return;
				
				createTableDialog.setVisible(false);
			}
		});
		btnOk.setBounds(44, 231, 89, 23);
		createTableDialog.getContentPane().add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createTableDialog.setVisible(false);
			}
		});
		btnCancel.setBounds(161, 231, 89, 23);
		createTableDialog.getContentPane().add(btnCancel);
	}
}
