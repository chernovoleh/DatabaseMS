package DatabaseMSCore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseManager {
	final static Class<?> [] allowedTypes = {Integer.class, Double.class, Character.class, String.class};
	
	private ArrayList<Database> databases;
	private Database activeDB;
	private Table activeTable;
	private String workspacePath;
	
	public DatabaseManager() {
		databases = new ArrayList<Database>();
	}
	
	public Boolean loadWorkspace(String path) {
		File folder = new File(path);
		if(folder == null || !folder.isDirectory())
			return false;
		
		for (final File fileEntry : folder.listFiles()) {
			if(!fileEntry.isFile())	
				continue;
			if(!fileEntry.toPath().toString().contains(".db")) 
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
		
		workspacePath = path;
		activeDB = null;
		activeTable = null;		
		return true;
	}
	
	public Boolean saveWorkspace() {
		if(workspacePath == null)
			return false;
		File folder = new File(workspacePath);
		if(folder == null || !folder.isDirectory())
			return false;
		
		for(Database db : databases) {
			try {
				FileOutputStream fos = new FileOutputStream(db.name() + ".db");
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
	
	public Table activeTable() {
		return activeTable;
	}
	
	public Database activeDatabase() {
		return activeDB;
	}
	
	public Iterable<Class<?> > allowedTypes() {
		return Arrays.asList(allowedTypes);
	}
	
	public Iterable<String> databaseNames() {
		ArrayList<String> dbNames = new ArrayList<String>();
		for(Database db : databases) {
			dbNames.add(db.name());
		}
		return dbNames;
	}
}
