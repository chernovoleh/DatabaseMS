package DatabaseMSCore;

import java.io.Serializable;

public interface dbType extends Serializable{
	Boolean canBeInitializedWith(String value);
	Boolean setValue(String value);	
}
