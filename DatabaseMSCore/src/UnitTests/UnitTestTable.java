package UnitTests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import DatabaseMSCore.ColumnScheme;
import DatabaseMSCore.TableScheme;
import DatabaseMSCore.Table;
import DatabaseMSCore.dbTypeDouble;
import DatabaseMSCore.dbTypeInteger;
import DatabaseMSCore.dbTypeString;

public class UnitTestTable {

	@Test
	public void test() throws ClassNotFoundException {
		TableScheme ts = new TableScheme();
		ts.addColumnScheme(new ColumnScheme("Name", dbTypeString.class.getSimpleName()));
		ts.addColumnScheme(new ColumnScheme("Age", dbTypeInteger.class.getSimpleName()));
		ts.addColumnScheme(new ColumnScheme("Weight", dbTypeDouble.class.getSimpleName()));
		
		Table table = new Table(ts, "Table1");
		assertTrue(table.columnsCount() == 3);
		assertTrue(table.rowsCount() == 0);
		assertTrue(table.name() == "Table1");		
		
		Map<String, String> row1 = new HashMap<String, String>();		
		row1.put("Name", "QQQ");
		row1.put("Age", "22");
		row1.put("Weight", "44.0");
		
		assertTrue(table.addRow(row1));
		assertTrue(table.addRow(row1));
		assertTrue(table.rowsCount() == 2);
		
		for(Object [] row : table.rows()) {
			assertTrue(row[0].toString().equals("QQQ"));
			assertTrue(row[1].toString().equals("22"));
			assertTrue(row[2].toString().equals("44.0"));
		}
		
		table.removeRow(0);
		assertTrue(table.rowsCount() == 1);
		
		for(Object [] row : table.rows()) {
			assertTrue(row[0].toString().equals("QQQ"));
			assertTrue(row[1].toString().equals("22"));
			assertTrue(row[2].toString().equals("44.0"));
		}
		assertTrue(table.changeColumnName("Name", "NAME"));
		
		assertTrue(table.setValue(0, 0, "QQQQ"));
		assertTrue(table.setValue(0, 1, "20"));
		assertTrue(table.setValue(0, 2, "12.0"));
		assertFalse(table.setValue(0, 1, "20.0"));
		
		for(Object [] row : table.rows()) {
			assertTrue(row[0].toString().equals("QQQQ"));
			assertTrue(row[1].toString().equals("20"));
			assertTrue(row[2].toString().equals("12.0"));
		}
		
		
	}

}