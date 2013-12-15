package DatabaseMSCore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Table implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private TableScheme tableScheme;
	private ArrayList<Object [] > rows;
	private String tableName;
	
	private class RowsIterator implements Iterator<Object[]> {

		private int index = 0;
		
		@Override
		public boolean hasNext() {
			return index < rows.size();
		}

		@Override
		public Object[] next() {
			return rows.get(index++);
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub			
		}
		
	}
	private class Rows implements Iterable<Object []> {

		@Override
		public Iterator<Object[]> iterator() {			
			return new RowsIterator();
		}
		
	}
	
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
	
	public String name() {
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
			row[i] = value;
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
	
	public void removeValue(int rowIndex, String columnName) {
		if(rowIndex >= rows.size())
			throw new IndexOutOfBoundsException();
		
		int colIndex = tableScheme.columnIndex(columnName);
		rows.get(rowIndex)[colIndex] = null;
	}
	
	public void removeRow(int rowIndex) {
		if(rowIndex >= rows.size())
			throw new IndexOutOfBoundsException();
		rows.remove(rowIndex);
	}	
	
	public Boolean changeColumnName(String oldName, String newName) {
		return tableScheme.changeColumnName(oldName, newName);
	}
	
	public Iterable<Object[]> rows() {
		return new Rows();
	}
	

}
