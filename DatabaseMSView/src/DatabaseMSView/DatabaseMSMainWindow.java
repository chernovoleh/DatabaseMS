package DatabaseMSView;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class DatabaseMSMainWindow {

	private JFrame frmDbmanager;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatabaseMSMainWindow window = new DatabaseMSMainWindow();
					window.frmDbmanager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DatabaseMSMainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDbmanager = new JFrame();
		frmDbmanager.setTitle("DBManager");
		frmDbmanager.setBounds(100, 100, 512, 336);
		frmDbmanager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmDbmanager.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmOpenWorkspace = new JMenuItem("Open Workspace...");
		mnNewMenu.add(mntmOpenWorkspace);
		frmDbmanager.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		frmDbmanager.getContentPane().add(splitPane);
		
		table = new JTable();
		splitPane.setRightComponent(table);
		
		JTree tree = new JTree();
		splitPane.setLeftComponent(tree);
	}

}
