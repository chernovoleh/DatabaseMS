package UnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import DatabaseMSCore.ColumnScheme;
import DatabaseMSCore.TableScheme;
import DatabaseMSCore.Table;

public class UnitTestTable {

	@Test
	public void test() {
		TableScheme ts = new TableScheme();
		ts.pushBackColumn(new ColumnScheme<String>("Name", String.class, ""));
		ts.pushBackColumn(new ColumnScheme<Integer>("Age", Integer.class, 0));
		ts.pushBackColumn(new ColumnScheme<Double>("Weight", Double.class, 0.));
		
		Table table = new Table(ts, "Table1");
		assertTrue(table.columnsCount() == 3);
		assertTrue(table.rowsCount() == 0);
		assertTrue(table.name() == "Table1");		
		
		Map<String, Object> row1 = new HashMap<String, Object>();		
		row1.put("Name", "QQQ");
		row1.put("Age", 22);
		row1.put("Weight", 44.);
		
		assertTrue(table.addRow(row1));
		assertTrue(table.addRow(row1));
		assertTrue(table.rowsCount() == 2);
		
		for(Object [] row : table.rows()) {
			assertTrue(row[0].equals("QQQ"));
			assertTrue(row[1].equals(22));
			assertTrue(row[2].equals(44.));
		}
		
		table.removeRow(0);
		assertTrue(table.rowsCount() == 1);
		
		for(Object [] row : table.rows()) {
			assertTrue(row[0].equals("QQQ"));
			assertTrue(row[1].equals(22));
			assertTrue(row[2].equals(44.));
		}
		assertTrue(table.changeColumnName("Name", "NAME"));
		
		assertTrue(table.setValue(0, "NAME", "QQQQ"));
		assertTrue(table.setValue(0, "Age", 20));
		assertTrue(table.setValue(0, "Weight", 12.));
		assertFalse(table.setValue(0, "Age", 20.));
		
		for(Object [] row : table.rows()) {
			assertTrue(row[0].equals("QQQQ"));
			assertTrue(row[1].equals(20));
			assertTrue(row[2].equals(12.));
		}
		
		
	}

}