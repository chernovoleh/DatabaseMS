package UnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import DatabaseMSCore.ColumnScheme;
import DatabaseMSCore.Table;
import DatabaseMSCore.TableScheme;
import DatabaseMSCore.dbTypeDouble;
import DatabaseMSCore.dbTypeInteger;
import DatabaseMSCore.dbTypeString;

public class UnitTestSearchUsingPattern {

	@Test
	public void test() {
		TableScheme ts = new TableScheme();
		ts.pushBackColumn(new ColumnScheme("Name", dbTypeString.class));
		ts.pushBackColumn(new ColumnScheme("Age", dbTypeInteger.class));
		ts.pushBackColumn(new ColumnScheme("Weight", dbTypeDouble.class));
		
		Table table = new Table(ts, "Table");
		
		Map<String, String> row1 = new HashMap<String, String>();		
		row1.put("Name", "QQQ");
		row1.put("Age", "22");
		row1.put("Weight", "44.");
		table.addRow(row1);
		
		Map<String, String> row2 = new HashMap<String, String>();		
		row2.put("Name", "WWW");
		row2.put("Age", "23");
		row2.put("Weight", "44.");
		table.addRow(row2);
		
		Map<String, String> row3 = new HashMap<String, String>();		
		row3.put("Name", "WWW");
		row3.put("Age", "22");
		row3.put("Weight", "44.");
		table.addRow(row3);
		
		Map<String, String> row4 = new HashMap<String, String>();		
		row4.put("Name", "WWW");
		row4.put("Age", "22");
		row4.put("Weight", "44.");
		table.addRow(row4);
		
		Map<String, String> pattern = new HashMap<String, String>();		
		pattern.put("Name", "WWW");
		pattern.put("Age", "22");
		
		int count = 0;
		for(Integer i : table.rows(pattern)) {
			assertTrue(table.getRow(i)[0].toString().equals("WWW"));
			assertTrue(table.getRow(i)[1].toString().equals("22"));
			++count;
		}
		
		assertTrue(count == 2);		
	}

}
