package DatabaseMSView;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import DatabaseMS.DatabaseMSController;
import DatabaseMS.DatabaseMSView;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import net.miginfocom.swing.MigLayout;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DatabaseMSMainWindow implements DatabaseMSView{

	private JFrame frmDbmanager;
	private DatabaseMSController msController;
	private JTree dbTree;
	private DatabaseMSTableView tableView;
	
	private ActionListener renameColumnActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(tableView.getColumnCount() <= 0)
				return;
			String [] columnNames = new String[tableView.getColumnCount()];
			for(int i = 0;i < columnNames.length; ++i)
				columnNames[i] = tableView.getColumnName(i);
			DatabaseMSChangeColumnNameWindow dialog = new DatabaseMSChangeColumnNameWindow(frmDbmanager, columnNames);
			dialog.show(new DatabaseMSChangeColumnNameWindow.ColumnNameChangedListener() {

				@Override
				public void columnNameChanged(String oldName, String newName) {
					msController.OnColumnNameChanged(oldName, newName);
					
				}
			});
		}
	};	
	
	private TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
		@Override
		public void valueChanged(TreeSelectionEvent e) {				
			TreePath path = dbTree.getSelectionPath();
			if(path == null)
				return;
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
			if(selectedNode.getLevel()== 1) {
				msController.OnSetActiveDatabase(selectedNode.toString());
			} else if(selectedNode.getLevel()== 2) {
				msController.OnSetActiveTable(selectedNode.toString());
			}			
		}
	};	
	
	
	/**
	 * Create the application.
	 */
	public DatabaseMSMainWindow() {		
		initialize();
	}
	
	public void setController(DatabaseMSController msController) {
		this.msController = msController;
		tableView.setController(msController);
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
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmOpenWorkspace = new JMenuItem("Open Workspace...");
		mntmOpenWorkspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(frmDbmanager);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		           File folder = fc.getSelectedFile();
		           msController.OnWorkspaceChosen(folder);		        
		        }		            
			}
		});
		mnNewMenu.add(mntmOpenWorkspace);
		
		JMenuItem mntmSaveWorkspace = new JMenuItem("Save Workspace...");
		mntmSaveWorkspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				msController.OnSaveWorkspace();				
			}
		});
		mnNewMenu.add(mntmSaveWorkspace);
		
		JMenu mnActions = new JMenu("Actions");
		menuBar.add(mnActions);
		
		JMenuItem mntmAdd = new JMenuItem("Add");
		mntmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tableView.getSelectedRow() >= 0) {
					int row = tableView.getSelectedRow();
					msController.OnRowInserted(row);
				} else if(dbTree.getSelectionCount() >= 0) {
					
				}
			}
		});
		mnActions.add(mntmAdd);
		
		JMenuItem mntmRemove = new JMenuItem("Remove");
		mntmRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableView.getSelectedRow() >= 0) {
					int row = tableView.getSelectedRow();
					msController.OnRowRemoved(row);
				} else if(dbTree.getSelectionCount() > 0) {
					TreePath path = dbTree.getSelectionPath();
					if(path == null)
						return;
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
					if(selectedNode.getLevel()== 2) {
						msController.OnTableRemoved(selectedNode.toString());
					}
				}
			}
		});
		mnActions.add(mntmRemove);
		
		JMenuItem mntmRenamecolumn = new JMenuItem("RenameColumn");
		mntmRenamecolumn.addActionListener(renameColumnActionListener);
		mnActions.add(mntmRenamecolumn);
		
		JMenuItem mntmSearch = new JMenuItem("Search");
		mntmSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnActions.add(mntmSearch);
		frmDbmanager.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		frmDbmanager.getContentPane().add(splitPane);
		
		DefaultMutableTreeNode dbs = new DefaultMutableTreeNode("Databases");
		dbTree = new JTree(dbs);
		dbTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		dbTree.addTreeSelectionListener(treeSelectionListener);
		splitPane.setLeftComponent(dbTree);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		tableView = new DatabaseMSTableView();		
		tableView.registrate(scrollPane);		
	}

	@Override
	public void setVisible(Boolean flag) {
		frmDbmanager.setVisible(flag);	
	}

	@Override
	public void addDatabases(Iterable<String> dbNames) {
		DefaultTreeModel model = (DefaultTreeModel)dbTree.getModel();
		DefaultMutableTreeNode dbs = (DefaultMutableTreeNode)model.getRoot();
		dbs.removeAllChildren();
		
		for(String dbName : dbNames) {
			DefaultMutableTreeNode db = new DefaultMutableTreeNode(dbName);			
			dbs.add(db);			
		}
		model.reload();		
	}

	@Override
	public void fillTable(Object[][] rows, Object[] columnNames) {
		tableView.fileTable(rows, columnNames);		
	}

	@Override
	public void addTablesToDatabase(Iterable<String> tblNames, String dbName) {
		DefaultTreeModel model = (DefaultTreeModel)dbTree.getModel();
		DefaultMutableTreeNode dbs = (DefaultMutableTreeNode)model.getRoot();
		DefaultMutableTreeNode activeDB = null;
		for(int i = 0;i < dbs.getChildCount();++i) {
			DefaultMutableTreeNode db = (DefaultMutableTreeNode)dbs.getChildAt(i);
			db.removeAllChildren();
			if(db.toString().equals(dbName)) {
				for(String tblName : tblNames) {
					DefaultMutableTreeNode tbl = new DefaultMutableTreeNode(tblName);			
					db.add(tbl);
				}
				activeDB = db;
			}
			
		}		
		model.reload();
		dbTree.expandPath(new TreePath(activeDB.getPath()));
		dbTree.removeTreeSelectionListener(treeSelectionListener);
		dbTree.setSelectionPath(new TreePath(activeDB.getPath()));
		dbTree.addTreeSelectionListener(treeSelectionListener);
	}

}
