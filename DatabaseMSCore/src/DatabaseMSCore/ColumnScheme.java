package DatabaseMSCore;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ColumnScheme implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final String [] typeNames = {
		dbTypeString.class.getSimpleName(), dbTypeInteger.class.getSimpleName(), 
        dbTypeDouble.class.getSimpleName(), dbTypeCharacter.class.getSimpleName(),
        dbTypeDate.class.getSimpleName(), dbTypeDateInterval.class.getSimpleName(),
        dbTypeEnum.class.getSimpleName()};
	
	private String columnName;
	
	
	private class InstanceCreator implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private String simpleTypeName;
		public InstanceCreator(String simpleTypeName) {
			this.simpleTypeName = simpleTypeName;
		}
		public dbType createInstance() {
			if(simpleTypeName.indexOf("Enum") == -1){
				
				Class<? extends dbType> type = null;
				try {
					type = (Class<? extends dbType>) Class.forName("DatabaseMSCore." + simpleTypeName);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					return type.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;				
			}	
			else {			
				
				ArrayList<String> enumValues = new ArrayList<String>();
				Pattern pattern = Pattern.compile(Pattern.quote("|"));
		        String[] data = pattern.split(simpleTypeName);
		        enumValues.addAll(Arrays.asList(data));
		        enumValues.remove(0);
				
				Class<dbTypeEnum> type = dbTypeEnum.class;
				Constructor<dbTypeEnum> ctor = null;
				try {
					ctor = type.getConstructor(ArrayList.class);
				} catch (NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					return ctor.newInstance(enumValues);
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}			
		}
	}
	
	private InstanceCreator instanceCreator;	
	
	
	public ColumnScheme(String columnName, final String simpleName) throws ClassNotFoundException {
		this.columnName = columnName;
		instanceCreator = new InstanceCreator(simpleName);		
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public String setColumnName(String newName) {
		return columnName = newName;
	}
	
	public dbType getInstance() {
		return instanceCreator.createInstance();
	}
	
	public Boolean canBeInitializedWith(String val) {
		return instanceCreator.createInstance().canBeInitializedWith(val);
	}
	
	public static String[] getTypeNames() {
		return typeNames;
	}
	
	
}
