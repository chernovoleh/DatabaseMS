package DatabaseMS;

import java.awt.EventQueue;

import DatabaseMSController.DatabaseMSControllerLocal;
import DatabaseMSView.DatabaseMSMainWindow;

public class DatabaseMSLocalApp {
	private DatabaseMSView msMainWindow;
	private DatabaseMSController msControllerLocal;
	
	public void run() {
		msMainWindow = new DatabaseMSMainWindow();
		msControllerLocal = new DatabaseMSControllerLocal(msMainWindow);
		
		msMainWindow.setController(msControllerLocal);
		msMainWindow.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatabaseMSLocalApp app = new DatabaseMSLocalApp();
					app.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
