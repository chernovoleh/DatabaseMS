package UnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import DatabaseMSCore.ColumnScheme;
import DatabaseMSCore.TableScheme;
import DatabaseMSCore.dbTypeDouble;
import DatabaseMSCore.dbTypeInteger;
import DatabaseMSCore.dbTypeString;

public class UnitTestTableScheme {

	@Test
	public void test() {
		String columnNames [] = {"Name", "Age", "Weight"};
		
		TableScheme ts = new TableScheme();
		Boolean test1 = ts.pushBackColumn(new ColumnScheme(columnNames[0], dbTypeString.class));
		assertTrue(test1);
		Boolean test2 = ts.pushBackColumn(new ColumnScheme(columnNames[1], dbTypeInteger.class));
		assertTrue(test2);
		Boolean test2_ = ts.pushBackColumn(new ColumnScheme(columnNames[1], dbTypeInteger.class));
		assertFalse(test2_);
		Boolean test3 = ts.pushBackColumn(new ColumnScheme(columnNames[2], dbTypeDouble.class));
		assertTrue(test3);
		
		Map<String, String> values = new HashMap<String, String>();
				
		values.put("Name", "QQQ");
		values.put("Age", "22.");
		values.put("Weight", "44.");
		
		assertFalse(ts.checkTypes(values));
		
		values.put("Name", "QQQ");
		values.put("Age", "22");
		values.put("Weight", "44.");
		
		assertTrue(ts.checkTypes(values));
		
		
		for(int i = 0; i < ts.columnsCount(); ++i) {
			assertEquals(columnNames[i], ts.columnName(i));
		}
	}

}
