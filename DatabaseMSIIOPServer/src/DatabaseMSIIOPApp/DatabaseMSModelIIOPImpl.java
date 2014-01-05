package DatabaseMSIIOPApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;


import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import DatabaseMS.DatabaseMSModelRMI;
import DatabaseMSCore.ColumnScheme;
import DatabaseMSCore.Database;
import DatabaseMSCore.Table;


public class DatabaseMSModelIIOPImpl extends PortableRemoteObject  implements DatabaseMSModelRMI {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Database> databases;
	private Database activeDB;
	private Table activeTable;
	private File workspaceFolder;
	
	public DatabaseMSModelIIOPImpl() throws RemoteException {
		databases = new ArrayList<Database>();
	}
	
	@Override
	public Boolean loadWorkspace(File folder) {
		
		if(folder == null || !folder.isDirectory())
			return false;
		databases.clear();
		
		for (final File fileEntry : folder.listFiles()) {
			if(!fileEntry.isFile())	
				continue;
			if(!fileEntry.toPath().toString().contains(".mydb")) 
				continue;
			try {
				
				FileInputStream fis = new FileInputStream(fileEntry);
				ObjectInputStream oin = new ObjectInputStream(fis);
				Database db = (Database)oin.readObject();
				databases.add(db);
				oin.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}			
	    }
		
		workspaceFolder = folder;
		activeDB = null;
		activeTable = null;		
		return true;
	}
	
	@Override
	public Boolean saveWorkspace() {
		if(workspaceFolder == null)
			return false;
		if(!workspaceFolder.isDirectory())
			return false;
		
		for(Database db : databases) {
			try {
				FileOutputStream fos = new FileOutputStream(workspaceFolder.getAbsolutePath() + "\\" + db.name() + ".mydb");
				ObjectOutputStream oos = new ObjectOutputStream(fos);				
				oos.writeObject(db);
				oos.close();
			} catch(IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	@Override
	public Boolean setActiveDB(String dbName) {
		for(Database db : databases) {
			if(db.name().equals(dbName)) {
				activeDB = db;
				activeTable = null;
				return true;
			}
		}
		return false;		
	}
	
	@Override
	public Boolean setActiveTable(String tableName) {
		if(activeDB == null)
			return false;
		
		for(String tn : activeDB.tableNames()) {
			if(tn.equals(tableName)) {
				activeTable = activeDB.table(tableName);
				return true;
			}
		}
		return false;
	}
	
	private Table activeTable() {
		return activeTable;
	}
	
	private Database activeDatabase() {
		return activeDB;
	}
	
	@Override
	public Iterable<String> databaseNames() {
		ArrayList<String> dbNames = new ArrayList<String>();
		for(Database db : databases) {
			dbNames.add(db.name());
		}
		return dbNames;
	}
	
	@Override
	public void removeActiveTable() {
		if(activeTable == null)
			return;
		
		activeDB.removeTable(activeTable.name());
		activeTable = null;
	}
	
	@Override
	public Boolean isWorkspaceLoaded() {
		return workspaceFolder != null;
	}
	
	@Override
	public Boolean addDatabase(String dbName) {
		for(String dn: databaseNames())
			if(dn.equals(dbName))
				return false;
		
		databases.add(new Database(dbName));
		return true;
	}
	
	@Override
	public Iterable<String> getActiveDBTableNames() {
		return activeDatabase().tableNames();
	}
	
	@Override
	public int getActiveTableRowsCount() {
		return activeTable().rowsCount();
	}
	
	@Override	
	public int getActiveTableColumnsCount() {
		return activeTable().columnsCount();
	}
	
	@Override
	public Iterable<String[]> getActiveTableRows() {		
		return activeTable().rows();
	}
	
	@Override
	public Iterable<Integer> getActiveTableRows(Map<String, String> pattern) {
		return activeTable().rows(pattern);
	}
	
	@Override
	public Iterable<String> getActiveTableColumnNames() {
		return activeTable().columnNames();
	}
	
	@Override
	public Boolean isActiveTableSet() {
		return activeTable() != null;
	}
	
	@Override
	public String [] getActiveTableRow(int index) {
		return activeTable().getRow(index);
	}
	
	@Override
	public Boolean setActiveTableValueAt(int rowIndex, int columnIndex, String newValue) {
		return activeTable().setValue(rowIndex, columnIndex, newValue);
	}
	
	@Override
	public Boolean addRowToActiveTable(Map<String, String> values) {
		return activeTable().addRow(values);
	}
	
	@Override
	public void removeRowFromActiveTable(int rowIndex) {
		activeTable().removeRow(rowIndex);
	}
	
	@Override
	public Boolean changeColumnNameInActiveTable(String oldName, String newName) {
		return activeTable().changeColumnName(oldName, newName);
	}
	
	@Override
	public String getActiveDbName() {
		return activeDatabase().name();
	}
	
	@Override
	public Boolean addTableToActiveDb(String tableName, Map<String, String> tableScheme) {
		return activeDB.addTable(tableName, tableScheme);
	}
	
	@Override
	public Boolean checkTypeInActiveTableAt(String columnName, String value) {
		return activeTable().checkType(columnName, value);
	}
	
	@Override
	public String[] getColumnTypes() {
		return ColumnScheme.getTypeNames();
	}
	
	public static void main(String [] args) throws UnknownHostException {
		System.getProperties().setProperty("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory");
		System.getProperties().setProperty("java.naming.provider.url", "iiop://localhost:1050");

		try {
            // Step 1: Instantiate the Hello servant
			DatabaseMSModelIIOPImpl model = new DatabaseMSModelIIOPImpl();

            // Step 2: Publish the reference in the Naming Service 
            // using JNDI API
            Context initialNamingContext = new InitialContext();
            
            initialNamingContext.rebind("DatabaseMSModelIIOPImpl", model );

            System.out.println("DatabaseMS Server: Ready...");

         } catch (Exception e) {            
            e.printStackTrace();
         } 
	}

	
}
