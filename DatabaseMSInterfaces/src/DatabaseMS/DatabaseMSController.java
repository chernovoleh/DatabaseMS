package DatabaseMS;

import java.io.File;

public interface DatabaseMSController {
	Boolean OnWorkspaceChosen(File folder);	
	Boolean OnSaveWorkspace();
	Boolean OnSetActiveDatabase(String dbName);
	Boolean OnSetActiveTable(String tableName);
	
	Boolean OnUpdateValue(int rowIndex, int columnIndex, Object newValue);
	
	Boolean OnRowInserted(int rowIndex);
	Boolean OnRowRemoved(int rowIndex);
	
	Boolean OnTableRemoved(String tableName);
	Boolean OnColumnNameChanged(String oldName, String newName);
	
	Boolean IsValueValid(String columnName, String value);
}
