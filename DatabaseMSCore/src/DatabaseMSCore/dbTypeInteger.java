package DatabaseMSCore;

public class dbTypeInteger implements dbType {
	private static final long serialVersionUID = 1L;
	
	private Integer value;
	@Override
	public Boolean canBeInitializedWith(String val) {
		try {
		      Integer.parseInt(val);
		} catch (NumberFormatException e) {
		      return false;
		}
		return true;

	}

	@Override
	public Boolean setValue(String val) {
		try {
		      value = Integer.parseInt(val);
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
