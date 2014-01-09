package DatabaseMSWebService;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import DatabaseMSCore.DatabaseManager;



@WebService
@SOAPBinding(style=Style.DOCUMENT)
public interface IDatabaseMSWebService {	
	@WebMethod
	public Boolean setActiveDB(String dbName);
	@WebMethod
	public Boolean setActiveTable(String tableName);
	
	@WebMethod
	public ArrayList<String> databaseNames();
	
	@WebMethod
	public void removeActiveTable();
	
	@WebMethod
	public Boolean isWorkspaceLoaded();
	
	@WebMethod
	public Boolean addDatabase(String dbName);
	
	@WebMethod
	public ArrayList<String> getActiveDBTableNames();
	@WebMethod
	public int getActiveTableRowsCount();
	@WebMethod
	public int getActiveTableColumnsCount();
	@WebMethod
	public ArrayList<String[]> getActiveTableRows();
	@WebMethod
	public ArrayList<Integer> getActiveTableRowsByPattern(Map<String, String> pattern);
	@WebMethod
	public ArrayList<String> getActiveTableColumnNames();
	
	@WebMethod
	public Boolean isActiveTableSet();
	@WebMethod
	public String [] getActiveTableRow(int index);
	@WebMethod
	public Boolean setActiveTableValueAt(int rowIndex, int columnIndex, String newValue);
	@WebMethod
	public Boolean addRowToActiveTable(Map<String, String> values);
	@WebMethod
	public void removeRowFromActiveTable(int rowIndex);
	@WebMethod
	public Boolean changeColumnNameInActiveTable(String oldName, String newName);
	@WebMethod
	public String getActiveDbName();
	@WebMethod
	public Boolean addTableToActiveDb(String tableName, Map<String, String> tableScheme);
	@WebMethod
	public Boolean checkTypeInActiveTableAt(String columnName, String value);
	@WebMethod
	public String [] getColumnTypes();
	@WebMethod
	public Boolean saveWorkspace();
	@WebMethod
	public Boolean loadWorkspace(File folder);
}
