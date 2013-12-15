package DatabaseMSCore;

public class ColumnScheme<T> {
	private String columnName;
	private Class<T> type;
	private T defaultValue;
	
	public ColumnScheme(String columnName, Class<T> type, T defaultValue) {
		this.columnName = columnName;
		this.type = type;
		this.defaultValue = defaultValue;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public String setColumnName(String newName) {
		return columnName = newName;
	}
	
	public Class<T> getType() {
		return type;		
	}
	
	public T getDefaultValue() {
		return defaultValue;
	}
}