package DatabaseMS;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface DatabaseMSModelRMI extends Remote{
	public Boolean loadWorkspace(File folder) throws RemoteException; 
	public Boolean saveWorkspace() throws RemoteException;
	public Boolean setActiveDB(String dbName) throws RemoteException;
	public Boolean setActiveTable(String tableName) throws RemoteException;
	
	public Iterable<String> databaseNames() throws RemoteException;
	
	public void removeActiveTable() throws RemoteException;
	
	public Boolean isWorkspaceLoaded() throws RemoteException;
	
	public Boolean addDatabase(String dbName) throws RemoteException;
	
	public Iterable<String> getActiveDBTableNames() throws RemoteException;
	public int getActiveTableRowsCount() throws RemoteException;
	public int getActiveTableColumnsCount() throws RemoteException;
	public Iterable<String[]> getActiveTableRows() throws RemoteException;
	public Iterable<Integer> getActiveTableRows(Map<String, String> pattern) throws RemoteException;
	public Iterable<String> getActiveTableColumnNames() throws RemoteException;
	
	public Boolean isActiveTableSet() throws RemoteException;
	public String [] getActiveTableRow(int index) throws RemoteException;
	public Boolean setActiveTableValueAt(int rowIndex, int columnIndex, String newValue) throws RemoteException;
	public Boolean addRowToActiveTable(Map<String, String> values) throws RemoteException;
	public void removeRowFromActiveTable(int rowIndex) throws RemoteException;
	public Boolean changeColumnNameInActiveTable(String oldName, String newName) throws RemoteException;
	public String getActiveDbName() throws RemoteException;
	public Boolean addTableToActiveDb(String tableName, Map<String, String> tableScheme) throws RemoteException;
	public Boolean checkTypeInActiveTableAt(String columnName, String value) throws RemoteException;
	public String [] getColumnTypes() throws RemoteException;
}
