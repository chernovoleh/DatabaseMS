package DatabaseMSCore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class TableScheme implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ColumnScheme> columnSchemes;	
	
	public TableScheme() {
		columnSchemes = new ArrayList<ColumnScheme>();
	}
	
	public <T> Boolean pushBackColumn(ColumnScheme column) {
		for(ColumnScheme cs : columnSchemes)
			if(cs.getColumnName().equals(column.getColumnName()))
				return false;
		
		columnSchemes.add(column);		
		return true;
	}
	
	public Boolean checkType(String columnName, String value) {
		int index = columnIndex(columnName);			
		if(!columnSchemes.get(index).canBeInitializedWith(value))
			return false;
		return true;
	}
	
	public Boolean checkTypes(Map<String, String> values) {		
		for(Map.Entry<String, String> pair : values.entrySet()) {
			if(!checkType(pair.getKey(), pair.getValue()))
				return false;
		}		
		return true;
	}
	
	public Boolean changeColumnName(String oldName, String newName) {
		if(oldName.equals(newName))
			return true;
		
		int index = columnIndex(oldName);
		
		for(ColumnScheme column : columnSchemes) {
			if(column.getColumnName().equals(newName)) {
				return false;
			}
		}
		
		columnSchemes.get(index).setColumnName(newName);		
		return true;			
	}
	
	public int columnsCount() {
		return columnSchemes.size();
	}
	
	public int columnIndex(String columnName) {
		for(int i = 0; i < columnSchemes.size(); ++i) {
			if(columnSchemes.get(i).getColumnName().equals(columnName))
				return i;
		}
		
		throw new IndexOutOfBoundsException();
	}
	
	public String columnName(int index) {
		if(index >= columnSchemes.size())
			throw new IndexOutOfBoundsException();
		
		return columnSchemes.get(index).getColumnName();
	}
	
	public dbType getNewInstance(int index) {
		if(index >= columnSchemes.size())
			throw new IndexOutOfBoundsException();
		
		return columnSchemes.get(index).getInstance();
	}
	
	public dbType getNewInstance(String columnName) {
		int index = columnIndex(columnName);		
		return columnSchemes.get(index).getInstance();
	}

}
