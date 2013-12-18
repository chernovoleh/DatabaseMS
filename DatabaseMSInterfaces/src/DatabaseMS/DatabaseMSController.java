package DatabaseMS;

import java.io.File;
import java.util.Map;

public interface DatabaseMSController {
	Boolean OnWorkspaceChosen(File folder);	
	Boolean OnSaveWorkspace();
	
	Boolean OnSetActiveDatabase(String dbName);
	Boolean OnSetActiveTable(String tableName);		
	
	Boolean OnRowInserted(int rowIndex);
	Boolean OnRowRemoved(int rowIndex);	
	
	Boolean OnTableAdded(String tableName, Map<String, String> tableScheme);
	Boolean OnTableRemoved(String tableName);
	
	Boolean OnUpdateValue(int rowIndex, int columnIndex, Object newValue);
	Boolean OnColumnNameChanged(String oldName, String newName);
	Boolean OnSearchByPattern(Map<String, String> pattern);
	
	Boolean OnDatabaseCreated(String dbName);
	
	String [] GetDbTypeNames();
	Boolean IsValueValid(String columnName, String value);
}
