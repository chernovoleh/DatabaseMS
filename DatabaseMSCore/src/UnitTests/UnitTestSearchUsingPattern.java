package UnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import DatabaseMSCore.ColumnScheme;
import DatabaseMSCore.Table;
import DatabaseMSCore.TableScheme;

public class UnitTestSearchUsingPattern {

	@Test
	public void test() {
		TableScheme ts = new TableScheme();
		ts.pushBackColumn(new ColumnScheme<String>("Name", String.class, ""));
		ts.pushBackColumn(new ColumnScheme<Integer>("Age", Integer.class, 0));
		ts.pushBackColumn(new ColumnScheme<Double>("Weight", Double.class, 0.));
		
		Table table = new Table(ts, "Table");
		
		Map<String, Object> row1 = new HashMap<String, Object>();		
		row1.put("Name", "QQQ");
		row1.put("Age", 22);
		row1.put("Weight", 44.);
		table.addRow(row1);
		
		Map<String, Object> row2 = new HashMap<String, Object>();		
		row2.put("Name", "WWW");
		row2.put("Age", 23);
		row2.put("Weight", 44.);
		table.addRow(row2);
		
		Map<String, Object> row3 = new HashMap<String, Object>();		
		row3.put("Name", "WWW");
		row3.put("Age", 22);
		row3.put("Weight", 44.);
		table.addRow(row3);
		
		Map<String, Object> row4 = new HashMap<String, Object>();		
		row4.put("Name", "WWW");
		row4.put("Age", 22);
		row4.put("Weight", 44.);
		table.addRow(row4);
		
		Map<String, Object> pattern = new HashMap<String, Object>();		
		pattern.put("Name", "WWW");
		pattern.put("Age", 22);
		
		int count = 0;
		for(Object [] row : table.rows(pattern)) {
			assertTrue(row[0].equals("WWW"));
			assertTrue(row[1].equals(22));
			++count;
		}
		
		assertTrue(count == 2);
		
	}

}
