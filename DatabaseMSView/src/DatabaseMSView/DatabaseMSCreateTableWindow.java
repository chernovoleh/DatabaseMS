package DatabaseMSView;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class DatabaseMSCreateTableWindow {
	private JDialog createTableDialog;
	private JTextField textField;
	private JScrollPane scrollPane;
	private ArrayList<JComboBox> typeSelectors;
	private ArrayList<JTextField> nameSelectors;

	/**
	 * Create the application.
	 */
	public DatabaseMSCreateTableWindow(JFrame owner) {
		createTableDialog = new JDialog(owner, Dialog.ModalityType.APPLICATION_MODAL);
		initialize();
	}
	
	public interface TableCreatedListener {
		void tableCreated(String tableName, Map<String, Class<?>> columnSchemes);
	}
	
	public TableCreatedListener tableCreatedListener;
	public void show(TableCreatedListener tableCreatedListener) {
		this.tableCreatedListener = tableCreatedListener;
		createTableDialog.setVisible(true);
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		typeSelectors = new ArrayList<JComboBox>();
		nameSelectors = new ArrayList<JTextField>();
		
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
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 291, 184);
		createTableDialog.getContentPane().add(scrollPane);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(252, 8, 49, 20);
		for(Integer i = 0;i < 10; ++i)
			comboBox.addItem(i.toString());
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
		        String item = (String)cb.getSelectedItem();
		        Integer columnsNum = Integer.parseInt(item);
		        
		        typeSelectors.clear();
		        nameSelectors.clear();
		        scrollPane.removeAll();
		        //ScrollPane gridLayout = new GridLayout(0,2);
		        //scrollPane.setLayout(gridLayout);
		        for(int i = 0;i < columnsNum;++i) {
		        	JComboBox typeSelector = new JComboBox();
		        	JTextField nameSelector = new JTextField();
		        	typeSelectors.add(typeSelector);
		        	nameSelectors.add(nameSelector);
		        	scrollPane.add(typeSelector);
		        	scrollPane.add(nameSelector);      	
		        	
		        }        
		        
			}
			
		});
		createTableDialog.getContentPane().add(comboBox);
		
		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(44, 231, 89, 23);
		createTableDialog.getContentPane().add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(161, 231, 89, 23);
		createTableDialog.getContentPane().add(btnCancel);
	}

}
