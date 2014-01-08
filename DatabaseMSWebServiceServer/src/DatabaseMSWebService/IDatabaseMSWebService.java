package DatabaseMSWebService;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import DatabaseMSCore.DatabaseManager;



@WebService
@SOAPBinding(style=Style.DOCUMENT)
public interface IDatabaseMSWebService {
	
    @WebMethod
    public void setDBManager(DatabaseManager dbManager);
    
    @WebMethod
    public DatabaseManager getDBManager();
    
    @WebMethod
    public Boolean addDatabase(String dbName);
    
    @WebMethod
    public ArrayList<String> getDBNames();
}
