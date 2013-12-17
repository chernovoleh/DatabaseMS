package DatabaseMSView;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Dialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DatabaseMSChangeColumnNameWindow {

	private JDialog frmChangeColumnDialog;	
	private JTextField textField;
	private String[] columnNames;

	/**
	 * Create the application.
	 */
	public DatabaseMSChangeColumnNameWindow(JFrame owner, String [] columnNames) {
		frmChangeColumnDialog = new JDialog(owner, Dialog.ModalityType.APPLICATION_MODAL);
		this.columnNames = columnNames;
		initialize();
	}	
	
	public interface ColumnNameChangedListener {
		void columnNameChanged(String oldName, String newName);
	}
	private ColumnNameChangedListener columnNameChangedListener; 
	
	public void show(ColumnNameChangedListener listener) {
		columnNameChangedListener = listener;		
		frmChangeColumnDialog.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		frmChangeColumnDialog.setResizable(false);
		frmChangeColumnDialog.setTitle("Change column name dialog");
		frmChangeColumnDialog.setBounds(100, 100, 300, 133);
		//frmChangeColumnDialog.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		frmChangeColumnDialog.getContentPane().setLayout(null);
		
		JLabel lblColumn = new JLabel("Column");
		lblColumn.setBounds(10, 11, 46, 14);
		frmChangeColumnDialog.getContentPane().add(lblColumn);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setBounds(76, 8, 208, 20);
		frmChangeColumnDialog.getContentPane().add(comboBox);
		
		for(String clmnName : columnNames) {
			comboBox.addItem(clmnName);
		}
		
		JLabel lblNewName = new JLabel("New name:");
		lblNewName.setBounds(10, 36, 64, 14);
		frmChangeColumnDialog.getContentPane().add(lblNewName);
		
		textField = new JTextField();
		textField.setBounds(76, 33, 208, 20);
		frmChangeColumnDialog.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Change");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String oldName = comboBox.getSelectedItem().toString();	
				String newName = textField.getText().trim();
				if(newName.isEmpty())
					return;
				
				frmChangeColumnDialog.setVisible(false);
				columnNameChangedListener.columnNameChanged(oldName, newName);
			}
		});
		btnNewButton.setBounds(63, 61, 89, 23);
		frmChangeColumnDialog.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChangeColumnDialog.setVisible(false);
			}
		});
		btnNewButton_1.setBounds(162, 61, 89, 23);
		frmChangeColumnDialog.getContentPane().add(btnNewButton_1);
	}
}
