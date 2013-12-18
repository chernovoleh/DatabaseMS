package DatabaseMSCore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Database implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Table> tables;
	private String name;
	
	private class TableNamesIterator implements Iterator<String> {
		private int index = 0;
		@Override
		public boolean hasNext() {			
			return index < tables.size();
		}

		@Override
		public String next() {			
			return tables.get(index++).name();
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub			
		}
		
	}
	
	private class TableNames implements Iterable<String> {

		@Override
		public Iterator<String> iterator() {			
			return new TableNamesIterator();
		}
		
	}
	
	public Database(String name) {
		this.name = name;
		tables = new ArrayList<Table>();
	}
	
	public String name() {
		return name;
	}
	
	public int tableCount() {
		return tables.size();
	}
	
	public Table table(String tableName) {
		for(Table t : tables) {
			if(t.name().equals(tableName))
				return t;
			
		}		
		return null;
	}
	
	private Boolean addTable(String tableName, TableScheme tableScheme) {
		for(Table t : tables) {
			if(t.name().equals(tableName))
				return false;			
		}	
		
		Table newTable = new Table(tableScheme, tableName);
		tables.add(newTable);
		return true;
	}
	
	public Boolean removeTable(String tableName) {
		for(Table t : tables) {
			if(t.name().equals(tableName)) {
				tables.remove(t);
				return true;
			}			
		}
		return false;
	}
	
	public Iterable<String> tableNames() {
		return new TableNames();
	}
	
	public Boolean addTable(String tableName, Map<String, String> tableScheme) {
		TableScheme ts = new TableScheme();
		for(Map.Entry<String, String> entry : tableScheme.entrySet()) {
			ColumnScheme cs;
			try {
				cs = new ColumnScheme(entry.getKey(), entry.getValue());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			ts.addColumnScheme(cs);
		}
		
		
		return addTable(tableName, ts);
	}
}
