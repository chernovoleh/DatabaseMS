package DatabaseMSCore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dbTypeDateInterval implements dbType {
	private static final long serialVersionUID = 1L;
	
	private Date dateBegin;
	private Date dateEnd;
	private DateFormat df;
	
	public dbTypeDateInterval() {
		df = new SimpleDateFormat("dd/MM/yyyy");
	}

	@Override
	public Boolean canBeInitializedWith(String val) {
		if(val.isEmpty()) return true;
		try {
			int index = val.indexOf('-');
			if(index == -1)
				return false;
			
			String val1 = val.substring(0, index);
			String val2 = val.substring(index+1);
			df.parse(val1);
			df.parse(val2);
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
			int index = val.indexOf('-');
			if(index == -1)
				return false;
			
			String val1 = val.substring(0, index);
			String val2 = val.substring(index+1);
			dateBegin = df.parse(val1);
			dateEnd = df.parse(val2);
		} catch (ParseException e) {
			dateBegin = null;
			dateEnd = null;			
			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		if(dateBegin == null)
			return new String();
		
				
		return df.format(dateBegin) + " - " + df.format(dateEnd);
	}

}
