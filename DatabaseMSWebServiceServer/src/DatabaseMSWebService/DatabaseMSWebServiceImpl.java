package DatabaseMSWebService;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import DatabaseMSCore.ColumnScheme;
import DatabaseMSCore.DatabaseManager;

@WebService(endpointInterface = "DatabaseMSWebService.IDatabaseMSWebService")
@SOAPBinding(style=Style.DOCUMENT)
public class DatabaseMSWebServiceImpl implements IDatabaseMSWebService {
	private DatabaseManager dbManager;	
	
	public DatabaseMSWebServiceImpl() {
		dbManager = new DatabaseManager();
		//dbManager.loadWorkspace(new File("C:/Users/Oleg/Documents"));
	}

	@Override
	public Boolean addDatabase(String dbName) {
		return dbManager.addDatabase(dbName);
	}

	@Override
	public Boolean setActiveDB(String dbName) {
		return dbManager.setActiveDB(dbName);
	}

	@Override
	public Boolean setActiveTable(String tableName) {
		return dbManager.setActiveTable(tableName);
	}

	@Override
	public ArrayList<String> databaseNames() {
		return dbManager.databaseNames();
	}

	@Override
	public void removeActiveTable() {
		dbManager.removeActiveTable();		
	}

	@Override
	public Boolean isWorkspaceLoaded() {
		return dbManager.isWorkspaceLoaded();
	}

	@Override
	public ArrayList<String> getActiveDBTableNames() {		
		return dbManager.getActiveDBTableNames();
	}

	@Override
	public int getActiveTableRowsCount() {
		return dbManager.getActiveTableRowsCount();
	}

	@Override
	public int getActiveTableColumnsCount() {
		return dbManager.getActiveTableColumnsCount();
	}

	@Override
	public ArrayList<String[]> getActiveTableRows() {
		return dbManager.getActiveTableRows();
	}

	@Override
	public ArrayList<Integer> getActiveTableRowsByPattern(Map<String, String> pattern) {		
		return dbManager.getActiveTableRows(pattern);
	}

	@Override
	public ArrayList<String> getActiveTableColumnNames() {		
		return dbManager.getActiveTableColumnNames();
	}

	@Override
	public Boolean isActiveTableSet() {
		return dbManager.isActiveTableSet();
	}

	@Override
	public String[] getActiveTableRow(int index) {		
		return dbManager.getActiveTableRow(index);
	}

	@Override
	public Boolean setActiveTableValueAt(int rowIndex, int columnIndex, String newValue) {		
		return dbManager.setActiveTableValueAt(rowIndex, columnIndex, newValue);
	}

	@Override
	public Boolean addRowToActiveTable(Map<String, String> values) {		
		return dbManager.addRowToActiveTable(values);
	}

	@Override
	public void removeRowFromActiveTable(int rowIndex) {		
		dbManager.removeRowFromActiveTable(rowIndex);
	}

	@Override
	public Boolean changeColumnNameInActiveTable(String oldName, String newName) {
		return dbManager.changeColumnNameInActiveTable(oldName, newName);
	}

	@Override
	public String getActiveDbName() {		
		return dbManager.getActiveDbName();
	}

	@Override
	public Boolean addTableToActiveDb(String tableName, Map<String, String> tableScheme) {		
		return dbManager.addTableToActiveDb(tableName, tableScheme);
	}

	@Override
	public Boolean checkTypeInActiveTableAt(String columnName, String value) {	
		return dbManager.checkTypeInActiveTableAt(columnName, value);
	}

	@Override
	public String[] getColumnTypes() {		
		return ColumnScheme.getTypeNames();
	}

	@Override
	public Boolean saveWorkspace() {		
		return dbManager.saveWorkspace();
	}

	@Override
	public Boolean loadWorkspace(File folder) {		
		return dbManager.loadWorkspace(folder);
	}	
}
