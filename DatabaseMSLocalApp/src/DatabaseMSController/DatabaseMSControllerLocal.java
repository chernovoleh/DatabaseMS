package DatabaseMSController;

import java.io.File;
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
		
		msView.fillDatabaseTree(dbManager.databaseNames(), null, null);		
		return true;
	}

	@Override
	public Boolean OnSetActiveDatabase(String dbName) {
		if(!dbManager.setActiveDB(dbName))
			return false;
		
		msView.fillDatabaseTree(dbManager.databaseNames(), dbManager.activeDatabase().name(), dbManager.activeDatabase().tableNames());
		return null;
	}
	
	private void fillTable() {
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
		if(!dbManager.activeTable().setValue(rowIndex, columnIndex, newValue))
			return false;
		return null;
	}

	@Override
	public Boolean OnRowInserted(int rowIndex) {
		Table table = dbManager.activeTable();
		Map<String, Object> vals = new HashMap<String, Object>();
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

}
