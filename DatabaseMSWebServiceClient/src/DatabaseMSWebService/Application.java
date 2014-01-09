package DatabaseMSWebService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import DatabaseMS.DatabaseMSView;
import DatabaseMSCore.DatabaseManager;
import DatabaseMSView.DatabaseMSMainWindow;

public class Application {

	public static void main(String[] args) {
		URL url = null;
		try {
			url = new URL("http://localhost:8080/database_ms?wsdl");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        QName name = new QName("http://DatabaseMSWebService/", "DatabaseMSWebServiceImplService");
        
        Service service = Service.create(url, name);
        
        IDatabaseMSWebService dbService = service.getPort(IDatabaseMSWebService.class);        
        DatabaseMSView dbView = new DatabaseMSMainWindow();
        
        DatabaseMSWebServiceController dbController = new DatabaseMSWebServiceController(dbView, dbService); 
        dbView.setController(dbController);
        
        dbView.setVisible(true);
	}

}

