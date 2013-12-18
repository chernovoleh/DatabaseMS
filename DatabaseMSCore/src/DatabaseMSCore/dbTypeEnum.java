package DatabaseMSCore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class dbTypeEnum implements dbType {
	private static final long serialVersionUID = 1L;
	
	private Set<String> enumValues;
	private String enumValue;
	
	public dbTypeEnum(ArrayList<String> enumValues) {
		enumValue = new String();
		this.enumValues = new HashSet<String>();
		this.enumValues.addAll(enumValues);
		this.enumValues.add(new String());
	}
	@Override
	public Boolean canBeInitializedWith(String value) {
		return enumValues.contains(value);
	}

	@Override
	public Boolean setValue(String value) {
		if(!canBeInitializedWith(value))
			return false;
		enumValue = value;
		return null;
	}
	
	@Override
	public String toString() {
		return enumValue;
	}

}
