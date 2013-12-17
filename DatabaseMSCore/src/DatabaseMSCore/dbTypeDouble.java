package DatabaseMSCore;

public class dbTypeDouble implements dbType {
	private static final long serialVersionUID = 1L;
	
	private Double value;
	@Override
	public Boolean canBeInitializedWith(String val) {
		try {
		      Double.parseDouble(val);
		} catch (NumberFormatException e) {
		      return false;
		}
		return true;
	}

	@Override
	public Boolean setValue(String val) {
		try {
		      value = Double.parseDouble(val);
		} catch (NumberFormatException e) {
		      return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
