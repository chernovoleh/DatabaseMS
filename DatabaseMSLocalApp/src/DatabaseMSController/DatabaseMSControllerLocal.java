package DatabaseMSController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DatabaseMS.DatabaseMSController;
import DatabaseMSCore.DatabaseManager;
import DatabaseMSCore.Table;
import DatabaseMS.DatabaseMSView;

public class DatabaseMSControllerLocal implements DatabaseMSController {
	private DatabaseManager dbManager;
	private DatabaseMSView msView;
	
	public DatabaseMSControllerLocal(DatabaseMSView msView) {
		this.dbManager = new DatabaseManager();
		this.msView = msView;
	}

	@Override
	public Boolean OnWorkspaceChosen(File folder) {
		if(!dbManager.loadWorkspace(folder))
			return false;
		
		msView.addDatabases(dbManager.databaseNames());	
		fillTable();
		return true;
	}

	@Override
	public Boolean OnSetActiveDatabase(String dbName) {
		if(!dbManager.setActiveDB(dbName))
			return false;
		
		msView.addTablesToDatabase(dbManager.activeDatabase().tableNames(), dbName);
		return true;
	}
	
	private void fillTable() {
		if(dbManager.activeTable() == null)
		{
			msView.fillTable(null, null);
		}
		else
		{
			Object[][] rows = new Object[dbManager.activeTable().rowsCount()][];
			int i = 0;
			for(Object[] row : dbManager.activeTable().rows())
				rows[i++] = row;
			
			Object [] columnNames = new Object[dbManager.activeTable().columnsCount()];
			int j = 0;
			for(String columnName : dbManager.activeTable().columnNames())
				columnNames[j++] = columnName;
			
			msView.fillTable(rows, columnNames);
		}
	}
	
	private void fillTable(ArrayList<Integer> indexes) {		
		Object[][] rows = new Object[indexes.size()][];
		
		int i = 0;
		for(Integer index : indexes) {
			rows[i++] = dbManager.activeTable().getRow(index);
		}
		
		Object [] columnNames = new Object[dbManager.activeTable().columnsCount()];
		int j = 0;
		for(String columnName : dbManager.activeTable().columnNames())
			columnNames[j++] = columnName;
		
		msView.fillTable(rows, columnNames);
	}

	@Override
	public Boolean OnSetActiveTable(String tableName) {
		if(!dbManager.setActiveTable(tableName))
			return false;
		
		fillTable();
		return true;
	}

	@Override
	public Boolean OnSaveWorkspace() {
		if(!dbManager.saveWorkspace())
			return false;
		
		return true;
	}

	@Override
	public Boolean OnUpdateValue(int rowIndex, int columnIndex, Object newValue) {
		if(!dbManager.activeTable().setValue(rowIndex, columnIndex, newValue.toString()))
			return false;
		return null;
	}

	@Override
	public Boolean OnRowInserted(int rowIndex) {
		Table table = dbManager.activeTable();
		Map<String, String> vals = new HashMap<String, String>();
		table.addRow(vals);
		fillTable();
		return true;
	}

	@Override
	public Boolean OnRowRemoved(int rowIndex) {
		Table table = dbManager.activeTable();
		
		table.removeRow(rowIndex);
		fillTable();
		return null;		
	}

	@Override
	public Boolean OnTableRemoved(String tableName) {
		dbManager.removeActiveTable();
		OnSetActiveDatabase(dbManager.activeDatabase().name());
		fillTable();
		
		return true;
	}

	@Override
	public Boolean OnColumnNameChanged(String oldName, String newName) {
		if(dbManager.activeTable() == null)
			return false;
		if(!dbManager.activeTable().changeColumnName(oldName, newName))
			return false;
		
		fillTable();
		return true;
	}

	@Override
	public Boolean IsValueValid(String columnName, String value) {
		Table activeTable = dbManager.activeTable();
		return activeTable.checkType(columnName, value);
	}

	@Override
	public Boolean OnSearchByPattern(Map<String, String> pattern) {
		if(pattern.size() == 0)
			return true;
		
		ArrayList<Integer> foundRows = new ArrayList<Integer>();
		for(Integer i : dbManager.activeTable().rows(pattern))
			foundRows.add(i);
		
		fillTable(foundRows);
		return null;
	}

}
