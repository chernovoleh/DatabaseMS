package DatabaseMS;

public interface DatabaseMSView {
	void setVisible(Boolean flag);
	void setController(DatabaseMSController msController);
	
	void addDatabases(Iterable<String> dbNames);	
	void addTablesToDatabase(Iterable<String> tblNames, String dbName);
	
	void fillTable(Object[][] rows, Object [] columnNames);
}
