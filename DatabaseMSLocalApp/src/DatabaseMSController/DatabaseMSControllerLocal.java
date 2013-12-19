package DatabaseMSController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DatabaseMS.DatabaseMSController;
import DatabaseMSCore.ColumnScheme;
import DatabaseMSCore.DatabaseManager;
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
		
		msView.addTablesToDatabase(dbManager.getActiveDBTableNames(), dbName);
		fillTable();
		return true;
	}
	
	private void fillTable() {
		if(!dbManager.isActiveTableSet()) {
			msView.fillTable(null, null);
		}
		else {
			Object[][] rows = new Object[dbManager.getActiveTableRowsCount()][];
			int i = 0;
			for(Object[] row : dbManager.getActiveTableRows())
				rows[i++] = row;
			
			Object [] columnNames = new Object[dbManager.getActiveTableColumnsCount()];
			int j = 0;
			for(String columnName : dbManager.getActiveTableColumnNames())
				columnNames[j++] = columnName;
			
			msView.fillTable(rows, columnNames);
		}
	}
	
	private void fillTable(ArrayList<Integer> indexes) {		
		Object[][] rows = new Object[indexes.size()][];
		
		int i = 0;
		for(Integer index : indexes) {
			rows[i++] = dbManager.getActiveTableRow(index);
		}
		
		Object [] columnNames = new Object[dbManager.getActiveTableColumnsCount()];
		int j = 0;
		for(String columnName : dbManager.getActiveTableColumnNames())
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
		if(!dbManager.setActiveTableValueAt(rowIndex, columnIndex, newValue.toString()))
			return false;
		return null;
	}

	@Override
	public Boolean OnRowInserted(int rowIndex) {
		Map<String, String> vals = new HashMap<String, String>();
		dbManager.addRowToActiveTable(vals);
		fillTable();
		return true;
	}

	@Override
	public Boolean OnRowRemoved(int rowIndex) {
		dbManager.removeRowFromActiveTable(rowIndex);
		fillTable();
		return null;		
	}

	@Override
	public Boolean OnTableRemoved(String tableName) {
		dbManager.removeActiveTable();
		OnSetActiveDatabase(dbManager.getActiveDbName());
		fillTable();
		
		return true;
	}

	@Override
	public Boolean OnColumnNameChanged(String oldName, String newName) {
		if(!dbManager.isActiveTableSet())
			return false;
		if(!dbManager.changeColumnNameInActiveTable(oldName, newName))
			return false;
		
		fillTable();
		return true;
	}

	@Override
	public Boolean IsValueValid(String columnName, String value) {
		return dbManager.checkTypeInActiveTableAt(columnName, value);
	}

	@Override
	public Boolean OnSearchByPattern(Map<String, String> pattern) {
		if(pattern.size() == 0)
			return true;
		
		ArrayList<Integer> foundRows = new ArrayList<Integer>();
		for(Integer i : dbManager.getActiveTableRows(pattern))
			foundRows.add(i);
		
		fillTable(foundRows);
		return true;
	}

	@Override
	public String[] GetDbTypeNames() {
		return ColumnScheme.getTypeNames();
	}

	@Override
	public Boolean OnTableAdded(String tableName, Map<String, String> tableScheme) {
		if(!dbManager.addTableToActiveDb(tableName, tableScheme))
			return false;
		
		OnSetActiveDatabase(dbManager.getActiveDbName());
		OnSetActiveTable(tableName);		
		return true;
	}

	@Override
	public Boolean OnDatabaseCreated(String dbName) {
		if(!dbManager.isWorkspaceLoaded())
			return false;
		
		if(!dbManager.addDatabase(dbName))
			return false;
		
		msView.addDatabases(dbManager.databaseNames());
		return true;
	}
}
