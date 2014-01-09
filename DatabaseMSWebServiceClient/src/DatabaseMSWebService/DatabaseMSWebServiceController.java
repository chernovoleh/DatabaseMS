package DatabaseMSWebService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DatabaseMS.DatabaseMSController;
import DatabaseMSCore.ColumnScheme;
import DatabaseMSCore.DatabaseManager;
import DatabaseMS.DatabaseMSView;

public class DatabaseMSWebServiceController implements DatabaseMSController {
	private IDatabaseMSWebService dbService;
	private DatabaseMSView msView;
	
	public DatabaseMSWebServiceController(DatabaseMSView msView, IDatabaseMSWebService dbService) {
		this.dbService = dbService;
		this.msView = msView;
	}

	@Override
	public Boolean OnWorkspaceChosen(File folder) {				
		if(!dbService.loadWorkspace(folder))
			return false;
		
		msView.addDatabases(dbService.databaseNames());	
		fillTable();
		return true;		
	}

	@Override
	public Boolean OnSetActiveDatabase(String dbName) {
		if(!dbService.setActiveDB(dbName))
			return false;
		
		msView.addTablesToDatabase(dbService.getActiveDBTableNames(), dbName);
		fillTable();
		return true;
	}
	
	private void fillTable() {
		if(!dbService.isActiveTableSet()) {
			msView.fillTable(null, null);
		}
		else {
			Object[][] rows = new Object[dbService.getActiveTableRowsCount()][];
			int i = 0;
			for(Object[] row : dbService.getActiveTableRows())
				rows[i++] = row;
			
			Object [] columnNames = new Object[dbService.getActiveTableColumnsCount()];
			int j = 0;
			for(String columnName : dbService.getActiveTableColumnNames())
				columnNames[j++] = columnName;
			
			msView.fillTable(rows, columnNames);
		}
	}
	
	private void fillTable(ArrayList<Integer> indexes) {		
		Object[][] rows = new Object[indexes.size()][];
		
		int i = 0;
		for(Integer index : indexes) {
			rows[i++] = dbService.getActiveTableRow(index);
		}
		
		Object [] columnNames = new Object[dbService.getActiveTableColumnsCount()];
		int j = 0;
		for(String columnName : dbService.getActiveTableColumnNames())
			columnNames[j++] = columnName;
		
		msView.fillTable(rows, columnNames);
	}

	@Override
	public Boolean OnSetActiveTable(String tableName) {
		if(!dbService.setActiveTable(tableName))
			return false;
		
		fillTable();
		return true;
	}

	@Override
	public Boolean OnSaveWorkspace() {		
		if(!dbService.saveWorkspace())
			return false;
		
		return true;
	}

	@Override
	public Boolean OnUpdateValue(int rowIndex, int columnIndex, Object newValue) {
		if(!dbService.setActiveTableValueAt(rowIndex, columnIndex, newValue.toString()))
			return false;
		return null;
	}

	@Override
	public Boolean OnRowInserted(int rowIndex) {
		Map<String, String> vals = new HashMap<String, String>();
		dbService.addRowToActiveTable(vals);
		fillTable();
		return true;
	}

	@Override
	public Boolean OnRowRemoved(int rowIndex) {
		dbService.removeRowFromActiveTable(rowIndex);
		fillTable();
		return null;		
	}

	@Override
	public Boolean OnTableRemoved(String tableName) {
		dbService.removeActiveTable();
		OnSetActiveDatabase(dbService.getActiveDbName());
		fillTable();
		
		return true;
	}

	@Override
	public Boolean OnColumnNameChanged(String oldName, String newName) {
		if(!dbService.isActiveTableSet())
			return false;
		if(!dbService.changeColumnNameInActiveTable(oldName, newName))
			return false;
		
		fillTable();
		return true;
	}

	@Override
	public Boolean IsValueValid(String columnName, String value) {
		return dbService.checkTypeInActiveTableAt(columnName, value);
	}

	@Override
	public Boolean OnSearchByPattern(Map<String, String> pattern) {
		if(pattern.size() == 0)
			return true;
		
		ArrayList<Integer> foundRows = new ArrayList<Integer>();
		for(Integer i : dbService.getActiveTableRowsByPattern(pattern))
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
		if(!dbService.addTableToActiveDb(tableName, tableScheme))
			return false;
		
		OnSetActiveDatabase(dbService.getActiveDbName());
		OnSetActiveTable(tableName);		
		return true;
	}

	@Override
	public Boolean OnDatabaseCreated(String dbName) {
		if(!dbService.isWorkspaceLoaded())
			return false;
		
		if(!dbService.addDatabase(dbName))
			return false;
		
		msView.addDatabases(dbService.databaseNames());
		return true;
	}
}
