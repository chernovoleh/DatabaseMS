package DatabaseMSIIOPApp;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import DatabaseMS.DatabaseMSController;
import DatabaseMS.DatabaseMSModelRMI;
import DatabaseMS.DatabaseMSView;
import DatabaseMSView.DatabaseMSMainWindow;

public class DatabaseMSIIOPController implements DatabaseMSController {
	private DatabaseMSModelRMI dbManager;
	private DatabaseMSView msView;
	
	public DatabaseMSIIOPController(DatabaseMSView msView) {
		this.msView = msView;
		
		try {
            Context ic = new InitialContext();
         
        // STEP 1: Get the Object reference from the Name Service
        // using JNDI call.
            Object objref = ic.lookup("DatabaseMSModelIIOPImpl");
            System.out.println("Client: Obtained a ref. to DatabaseMS model.");

        // STEP 2: Narrow the object reference to the concrete type and
        // invoke the method.
            this.dbManager = (DatabaseMSModelRMI) PortableRemoteObject.narrow(
                objref, DatabaseMSModelRMI.class);            

        } catch( Exception e ) {
            System.err.println( "Exception " + e + "Caught" );
            e.printStackTrace( );
            return;
        }
		
	}

	@Override
	public Boolean OnWorkspaceChosen(File folder) {
		try {
			if(!dbManager.loadWorkspace(folder))
				return false;
			msView.addDatabases(dbManager.databaseNames());	
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		fillTable();
		return true;
	}

	@Override
	public Boolean OnSetActiveDatabase(String dbName) {
		try {
			if(!dbManager.setActiveDB(dbName))
				return false;
			
			msView.addTablesToDatabase(dbManager.getActiveDBTableNames(), dbName);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		fillTable();
		return true;
	}
	
	private void fillTable() {
		try {
			if(!dbManager.isActiveTableSet()) {
				msView.fillTable(null, null);
			}
			else {
				Object[][] rows = new Object[dbManager.getActiveTableRowsCount()][];
				int i = 0;
				for(Object[] row : dbManager.getActiveTableRows())
					rows[i++] = row;
				
				Object [] columnNames = new Object[dbManager.getActiveTableColumnsCount()];
				int j = 0;
				for(String columnName : dbManager.getActiveTableColumnNames())
					columnNames[j++] = columnName;
				
				msView.fillTable(rows, columnNames);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void fillTable(ArrayList<Integer> indexes) {		
		Object[][] rows = new Object[indexes.size()][];
		
		try {		
			int i = 0;
			for(Integer index : indexes) {
				rows[i++] = dbManager.getActiveTableRow(index);
			}
			
			Object [] columnNames = new Object[dbManager.getActiveTableColumnsCount()];
			int j = 0;
			for(String columnName : dbManager.getActiveTableColumnNames())
				columnNames[j++] = columnName;
			msView.fillTable(rows, columnNames);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

	@Override
	public Boolean OnSetActiveTable(String tableName) {
		try {
			if(!dbManager.setActiveTable(tableName))
				return false;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fillTable();
		return true;
	}

	@Override
	public Boolean OnSaveWorkspace() {
		try {
			if(!dbManager.saveWorkspace())
				return false;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public Boolean OnUpdateValue(int rowIndex, int columnIndex, Object newValue) {
		try {
			if(!dbManager.setActiveTableValueAt(rowIndex, columnIndex, newValue.toString()))
				return false;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean OnRowInserted(int rowIndex) {
		Map<String, String> vals = new HashMap<String, String>();
		try {
			dbManager.addRowToActiveTable(vals);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fillTable();
		return true;
	}

	@Override
	public Boolean OnRowRemoved(int rowIndex) {
		try {
			dbManager.removeRowFromActiveTable(rowIndex);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fillTable();
		return null;		
	}

	@Override
	public Boolean OnTableRemoved(String tableName) {
		try {
			dbManager.removeActiveTable();
			OnSetActiveDatabase(dbManager.getActiveDbName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fillTable();
		
		return true;
	}

	@Override
	public Boolean OnColumnNameChanged(String oldName, String newName) {
		try {
			if(!dbManager.isActiveTableSet())
				return false;
			if(!dbManager.changeColumnNameInActiveTable(oldName, newName))
				return false;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		fillTable();
		return true;
	}

	@Override
	public Boolean IsValueValid(String columnName, String value) {
		try {
			return dbManager.checkTypeInActiveTableAt(columnName, value);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean OnSearchByPattern(Map<String, String> pattern) {
		if(pattern.size() == 0)
			return true;
		
		ArrayList<Integer> foundRows = new ArrayList<Integer>();
		try {
			for(Integer i : dbManager.getActiveTableRows(pattern))
				foundRows.add(i);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fillTable(foundRows);
		return true;
	}

	@Override
	public String[] GetDbTypeNames() {
		try {
			return dbManager.getColumnTypes();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean OnTableAdded(String tableName, Map<String, String> tableScheme) {
		try {
			if(!dbManager.addTableToActiveDb(tableName, tableScheme))
				return false;
			OnSetActiveDatabase(dbManager.getActiveDbName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		OnSetActiveTable(tableName);		
		return true;
	}

	@Override
	public Boolean OnDatabaseCreated(String dbName) {
		try {
			if(!dbManager.isWorkspaceLoaded())
				return false;
			if(!dbManager.addDatabase(dbName))
				return false;
			msView.addDatabases(dbManager.databaseNames());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return true;
	}
	
	public static void main(String[] args) {
		System.getProperties().setProperty("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory");
		System.getProperties().setProperty("java.naming.provider.url", "iiop://localhost:1050");
		
		DatabaseMSView msMainWindow = new DatabaseMSMainWindow();
		DatabaseMSIIOPController msControllerLocal = new DatabaseMSIIOPController(msMainWindow);
		
		msMainWindow.setController(msControllerLocal);
		msMainWindow.setVisible(true);
	}


}
