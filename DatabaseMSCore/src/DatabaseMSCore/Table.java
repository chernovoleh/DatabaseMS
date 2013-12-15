package DatabaseMSCore;

import java.util.ArrayList;
import java.util.Map;

public class Table {
	private TableScheme tableScheme;
	private ArrayList<Object [] > rows;
	private String tableName;
	
	public Table(TableScheme tableScheme, String tableName) {
		this.tableName = tableName;
		this.tableScheme = tableScheme;
		rows = new ArrayList<Object []>();
	}
	
	public int columnsCount() {
		return tableScheme.columnsCount();
	}
	
	public int rowsCount() {
		return rows.size();
	}
	
	public String getName() {
		return tableName;
	}
	
	public Boolean addRow(Map<String, Object> values) {
		if(!tableScheme.checkTypes(values))
			return false;
		
		Object [] row = new Object[tableScheme.columnsCount()];
		for(int i = 0; i < row.length; ++i) {
			String columnName = tableScheme.columnName(i);
			Object value = values.get(columnName);
			if(value == null) 
				value = tableScheme.defaultValue(columnName);			
		}
		
		rows.add(row);
		return true;
	}
	
	public Boolean setValue(int rowIndex, String columnName, Object newValue) {
		if(rowIndex >= rows.size())
			throw new IndexOutOfBoundsException();
		
		int colIndex = tableScheme.columnIndex(columnName);
		
		if(!tableScheme.checkType(columnName, newValue))
			return false;
		
		rows.get(rowIndex)[colIndex] = newValue;	
		return true;
	}
	

}
