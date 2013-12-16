package DatabaseMS;

public interface DatabaseMSView {
	void setVisible(Boolean flag);
	void setController(DatabaseMSController msController);
	void fillDatabaseTree(Iterable<String> dbNames, String activeDatabaseName, Iterable<String> tblNames);	
	
	void fillTable(Object[][] rows, Object [] columnNames);
}
