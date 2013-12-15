package UnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import DatabaseMSCore.ColumnScheme;
import DatabaseMSCore.TableScheme;

public class UnitTestTableScheme {

	@Test
	public void test() {
		String columnNames [] = {"Name", "Age", "Weight"};
		
		TableScheme ts = new TableScheme();
		Boolean test1 = ts.pushBackColumn(new ColumnScheme<String>(columnNames[0], String.class, ""));
		assertTrue(test1);
		Boolean test2 = ts.pushBackColumn(new ColumnScheme<Integer>(columnNames[1], Integer.class, 0));
		assertTrue(test2);
		Boolean test2_ = ts.pushBackColumn(new ColumnScheme<Integer>(columnNames[1], Integer.class, 0));
		assertFalse(test2_);
		Boolean test3 = ts.pushBackColumn(new ColumnScheme<Double>(columnNames[2], Double.class, 0.));
		assertTrue(test3);
		
		Map<String, Object> values = new HashMap<String, Object>();
				
		values.put("Name", "QQQ");
		values.put("Age", 22.);
		values.put("Weight", 44.);
		
		assertFalse(ts.checkTypes(values));
		
		values.put("Name", "QQQ");
		values.put("Age", 22);
		values.put("Weight", 44.);
		
		assertTrue(ts.checkTypes(values));
		
		
		for(int i = 0; i < ts.columnsCount(); ++i) {
			assertEquals(columnNames[i], ts.columnName(i));
		}
	}

}
