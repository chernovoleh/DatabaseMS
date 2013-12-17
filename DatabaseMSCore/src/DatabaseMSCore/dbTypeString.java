package DatabaseMSCore;

public class dbTypeString implements dbType {
	private static final long serialVersionUID = 1L;
	
	private String value;
	@Override
	public Boolean canBeInitializedWith(String val) {
		return true;
	}

	@Override
	public Boolean setValue(String val) {
		value = val;
		return true;
	}

	@Override
	public String toString() {
		return value;
	}

}
