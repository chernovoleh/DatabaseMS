package DatabaseMSView;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;


import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DatabaseMSAddEnumWindow {
	private JDialog createEnumDialog;
	private JTable table;

	
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
	public DatabaseMSAddEnumWindow(JDialog createTableDialog) {
		createEnumDialog = new JDialog(createTableDialog, Dialog.ModalityType.APPLICATION_MODAL);
		createEnumDialog.setResizable(false);
		initialize();
	}
	
	public interface EnumCreatedListener {
		void enumCreated(String [] enumValues);
	}
	private EnumCreatedListener enumCreatedListener;
	
	public void show(EnumCreatedListener enumCreatedListener) {		
		this.enumCreatedListener = enumCreatedListener;
		createEnumDialog.setVisible(true);		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		createEnumDialog.setTitle("Create enum dialog");
		createEnumDialog.setBounds(100, 100, 172, 296);
		createEnumDialog.getContentPane().setLayout(null);
		
		JLabel lblRowsCount = new JLabel("Values count:");
		lblRowsCount.setBounds(10, 11, 67, 14);
		createEnumDialog.getContentPane().add(lblRowsCount);
		
		JScrollPane scrollPane  = new JScrollPane();
		scrollPane.setBounds(10, 36, 146, 184);
		createEnumDialog.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JComboBox<Integer> comboBox = new JComboBox<Integer>();
		comboBox.setBounds(85, 8, 71, 20);
		for(Integer i = 0;i < 10; ++i)
			comboBox.addItem(i);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<Integer> cb = (JComboBox<Integer>)e.getSource();
		        Integer columnsNum = (Integer)cb.getSelectedItem();
		        
		        Object [] columnNames = {"Enum value"};
		        Object [][] rows = new Object[columnsNum][1];
		        DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.setDataVector(rows, columnNames);	        
				table.getColumn("Enum value").setCellEditor(new NameColumnCellEditor());
			}			
		});
		createEnumDialog.getContentPane().add(comboBox);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getRowCount() == 0)
					return;
				
				String [] enumValues = new String[table.getRowCount()];
				for(int i = 0;i < table.getRowCount();++i) {
					
					if(table.getValueAt(i, 0) == null)
						return;
					
					String enumValue = table.getValueAt(i, 0).toString().trim();
										
					if(enumValue.isEmpty())
						return;
					
					enumValues[i] = enumValue;				
				}
				enumCreatedListener.enumCreated(enumValues);
				
				createEnumDialog.setVisible(false);
			}
		});
		btnOk.setBounds(10, 231, 67, 23);
		createEnumDialog.getContentPane().add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createEnumDialog.setVisible(false);
			}
		});
		btnCancel.setBounds(89, 231, 67, 23);
		createEnumDialog.getContentPane().add(btnCancel);
	}
}
