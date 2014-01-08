package DatabaseMSWebService;

import java.util.ArrayList;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import DatabaseMSCore.DatabaseManager;

@WebService(endpointInterface = "DatabaseMSWebService.IDatabaseMSWebService")
@SOAPBinding(style=Style.DOCUMENT)
public class DatabaseMSWebServiceImpl implements IDatabaseMSWebService {
	private DatabaseManager dbManager;	
	
	public DatabaseMSWebServiceImpl() {
		dbManager = new DatabaseManager();
	}
	
	@Override
	public void setDBManager(DatabaseManager dbManager) {
		this.dbManager = dbManager;
	}

	@Override
	public DatabaseManager getDBManager() {
		return dbManager;
	}

	@Override
	public ArrayList<String> getDBNames() {		
		ArrayList<String> dbNames = new ArrayList<String>();
		for(String dbName : dbManager.databaseNames())
			dbNames.add(dbName);
		return dbNames;
	}

	@Override
	public Boolean addDatabase(String dbName) {
		return dbManager.addDatabase(dbName);
	}
	
	
	
}
