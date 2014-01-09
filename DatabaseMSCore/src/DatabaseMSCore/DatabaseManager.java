package DatabaseMSCore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DatabaseManager {
	final static Class<?> [] allowedTypes = {Integer.class, Double.class, Character.class, String.class};
	
	private ArrayList<Database> databases;
	private Database activeDB;
	private Table activeTable;
	private File workspaceFolder;
	
	public DatabaseManager() {
		databases = new ArrayList<Database>();
	}
	
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
	
	public Iterable<Class<?> > allowedTypes() {
		return Arrays.asList(allowedTypes);
	}
	
	public ArrayList<String> databaseNames() {
		ArrayList<String> dbNames = new ArrayList<String>();
		for(Database db : databases) {
			dbNames.add(db.name());
		}
		return dbNames;
	}
	
	public void removeActiveTable() {
		if(activeTable == null)
			return;
		
		activeDB.removeTable(activeTable.name());
		activeTable = null;
	}
	
	public Boolean isWorkspaceLoaded() {
		return workspaceFolder != null;
	}
	
	public Boolean addDatabase(String dbName) {
		for(String dn: databaseNames())
			if(dn.equals(dbName))
				return false;
		
		databases.add(new Database(dbName));
		return true;
	}
	
	public ArrayList<String> getActiveDBTableNames() {
		return activeDatabase().tableNames();
	}
	
	public int getActiveTableRowsCount() {
		return activeTable().rowsCount();
	}
	
	public int getActiveTableColumnsCount() {
		return activeTable().columnsCount();
	}
	
	public ArrayList<String[]> getActiveTableRows() {
		return activeTable().rows();
	}
	
	public ArrayList<Integer> getActiveTableRows(Map<String, String> pattern) {
		return activeTable().rows(pattern);
	}
	
	public ArrayList<String> getActiveTableColumnNames() {
		return activeTable().columnNames();
	}
	
	public Boolean isActiveTableSet() {
		return activeTable() != null;
	}
	
	public String [] getActiveTableRow(int index) {
		return activeTable().getRow(index);
	}
	
	public Boolean setActiveTableValueAt(int rowIndex, int columnIndex, String newValue) {
		return activeTable().setValue(rowIndex, columnIndex, newValue);
	}
	
	public Boolean addRowToActiveTable(Map<String, String> values) {
		return activeTable().addRow(values);
	}
	
	public void removeRowFromActiveTable(int rowIndex) {
		activeTable().removeRow(rowIndex);
	}
	
	public Boolean changeColumnNameInActiveTable(String oldName, String newName) {
		return activeTable().changeColumnName(oldName, newName);
	}
	
	public String getActiveDbName() {
		return activeDatabase().name();
	}
	
	public Boolean addTableToActiveDb(String tableName, Map<String, String> tableScheme) {
		return activeDB.addTable(tableName, tableScheme);
	}
	
	public Boolean checkTypeInActiveTableAt(String columnName, String value) {
		return activeTable().checkType(columnName, value);
	}
}
