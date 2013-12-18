package DatabaseMSCore;

public class dbTypeInteger implements dbType {
	private static final long serialVersionUID = 1L;
	
	private Integer value;
	@Override
	public Boolean canBeInitializedWith(String val) {
		if(val.isEmpty()) return true;
		try {
		      Integer.parseInt(val);
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
		      value = Integer.parseInt(val);
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
