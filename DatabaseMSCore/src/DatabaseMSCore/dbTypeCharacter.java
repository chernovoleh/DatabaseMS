package DatabaseMSCore;

public class dbTypeCharacter implements dbType {
	private static final long serialVersionUID = 1L;
	
	private Character value; 
	@Override
	public Boolean canBeInitializedWith(String val) {
		return val.length() < 2;
	}

	@Override
	public Boolean setValue(String val) {
		if(val.length() > 1)
			return false;
		
		
		value = val.length() == 1 ? val.charAt(0) : null;
		return true;
	}

	@Override
	public String toString() {
		if(value == null)
			return new String();
		return value.toString();
	}

}
