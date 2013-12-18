package DatabaseMSCore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class dbTypeDate implements dbType {
	private static final long serialVersionUID = 1L;
	
	private Date value;
	private DateFormat df;
	
	public dbTypeDate() {
		df = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	@Override
	public Boolean canBeInitializedWith(String val) {
		if(val.isEmpty()) return true;
		try {
			df.parse(val);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Boolean setValue(String val) {
		if(val.isEmpty()) return true;
		try {
			value = df.parse(val);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		if(value == null)
			return new String();
		
				
		return df.format(value);
	}
}
