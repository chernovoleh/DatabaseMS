package DatabaseMSCore;

public class dbTypeDouble implements dbType {
	private static final long serialVersionUID = 1L;
	
	private Double value;
	@Override
	public Boolean canBeInitializedWith(String val) {
		if(val.isEmpty()) return true;
		try {
		      Double.parseDouble(val);
		} catch (NumberFormatException e) {
		      return false;
		}
		return true;
	}

	@Override
	public Boolean setValue(String val) {
		if(val.isEmpty()) {
			value = null;
			return true;
		}		
		try {
		      value = Double.parseDouble(val);
		} catch (NumberFormatException e) {
		      return false;
		}
		return true;
	}

	@Override
	public String toString() {
		if(value == null)
			return new String();
		return value.toString();
	}

}
