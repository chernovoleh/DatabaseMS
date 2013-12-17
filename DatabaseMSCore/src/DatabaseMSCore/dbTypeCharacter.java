package DatabaseMSCore;

public class dbTypeCharacter implements dbType {
	private static final long serialVersionUID = 1L;
	
	private Character value; 
	@Override
	public Boolean canBeInitializedWith(String val) {
		return val.length() == 1;
	}

	@Override
	public Boolean setValue(String val) {
		if(val.length() != 1)
			return false;
		
		value = val.charAt(0);
		return true;
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
