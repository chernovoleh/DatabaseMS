package DatabaseMSCore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Table implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private TableScheme tableScheme;
	private ArrayList<dbType [] > rows;
	private String tableName;	
	
	public Table(TableScheme tableScheme, String tableName) {
		this.tableName = tableName;
		this.tableScheme = tableScheme;
		rows = new ArrayList<dbType []>();
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
	
	public Boolean addRow(Map<String, String> values) {
		if(!tableScheme.checkTypes(values))
			return false;
		
		dbType [] row = new dbType[tableScheme.columnsCount()];
		for(int i = 0; i < row.length; ++i) {
			String columnName = tableScheme.columnName(i);
			String value = values.get(columnName);
			if(value == null) value = new String();
			
			dbType cell = tableScheme.getNewInstance(i);
			cell.setValue(value);
			row[i] = cell;
		}
		
		rows.add(row);
		return true;
	}
	
	public Boolean setValue(int rowIndex, int columnIndex, String newValue) {
		if(rowIndex >= rows.size())
			throw new IndexOutOfBoundsException();
		
		String columnName = tableScheme.columnName(columnIndex);
		
		if(!tableScheme.checkType(columnName, newValue))
			return false;
		
		rows.get(rowIndex)[columnIndex].setValue(newValue);	
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
	
	public Iterable<dbType[]> rows() {
		return rows;
	}
	
	public dbType [] getRow(int index) {
		return rows.get(index);
	}
	
	public Iterable<Integer> rows(Map<String, String> pattern) {
		ArrayList<Integer> chosenRows = new ArrayList<Integer>();
		for(int i = 0;i < rows.size();++i) {
			Boolean flag = true;
			for(Map.Entry<String, String> entry : pattern.entrySet()) {
				int j = tableScheme.columnIndex(entry.getKey());
				if(!rows.get(i)[j].toString().equals(entry.getValue()))
					flag = false;
			}
			
			if(flag)
				chosenRows.add(i);
		}
		return chosenRows;
	}	
	
	public Iterable<String> columnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		for(int i = 0;i < tableScheme.columnsCount();++i)
			columnNames.add(tableScheme.columnName(i));
		
		return columnNames;
	}
	
	public Boolean checkType(String columnName, String value) {
		return tableScheme.checkType(columnName, value);
	}

}
