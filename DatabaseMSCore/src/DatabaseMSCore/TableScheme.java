package DatabaseMSCore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class TableScheme implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ColumnScheme<?> > columnSchemes;	
	
	public TableScheme() {
		columnSchemes = new ArrayList<ColumnScheme<?>>();
	}
	
	public <T> Boolean pushBackColumn(ColumnScheme<T> column) {
		for(ColumnScheme<?> cs : columnSchemes)
			if(cs.getColumnName() == column.getColumnName())
				return false;
		
		columnSchemes.add(column);		
		return true;
	}
	
	public Boolean checkType(String columnName, Object value) {
		int index = columnIndex(columnName);			
		if(!columnSchemes.get(index).getType().isInstance(value))
			return false;
		return true;
	}
	
	public Boolean checkTypes(Map<String, Object> values) {		
		for(Map.Entry<String, Object> pair : values.entrySet()) {
			if(!checkType(pair.getKey(), pair.getValue()))
				return false;
		}
		
		return true;
	}
	
	public Boolean changeColumnName(String oldName, String newName) {
		if(oldName == newName)
			return true;
		
		int index = columnIndex(oldName);
		
		for(ColumnScheme<?> column : columnSchemes) {
			if(column.getColumnName() == newName) {
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
			if(columnSchemes.get(i).getColumnName() == columnName)
				return i;
		}
		
		throw new IndexOutOfBoundsException();
	}
	
	public String columnName(int index) {
		if(index >= columnSchemes.size())
			throw new IndexOutOfBoundsException();
		
		return columnSchemes.get(index).getColumnName();
	}
	
	public Object defaultValue(String columnName) {
		int index = columnIndex(columnName);		
		return columnSchemes.get(index).getDefaultValue();
	}
	
	public Class<?> columnType(String columnName) {
		int index = columnIndex(columnName);
		return columnSchemes.get(index).getType();
	}

}
