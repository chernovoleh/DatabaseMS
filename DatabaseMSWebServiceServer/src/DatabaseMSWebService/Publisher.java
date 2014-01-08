package DatabaseMSWebService;
import javax.xml.ws.Endpoint;


public class Publisher {

public static void main(String[] args) {
                            
        Endpoint.publish("http://localhost:8080/database_ms", new DatabaseMSWebServiceImpl());
        
        System.out.println("Ready!");
                   
    }
}