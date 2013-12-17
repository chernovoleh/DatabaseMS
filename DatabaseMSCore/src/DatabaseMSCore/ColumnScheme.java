package DatabaseMSCore;

import java.io.Serializable;

public class ColumnScheme implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String columnName;
	private Class<? extends dbType> type;	
	
	public ColumnScheme(String columnName, Class<? extends dbType> type) {
		this.columnName = columnName;
		this.type = type;		
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public String setColumnName(String newName) {
		return columnName = newName;
	}
	
	public dbType getInstance() {
		try {
			return type.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean canBeInitializedWith(String val) {
		try {
			return type.newInstance().canBeInitializedWith(val);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
}
