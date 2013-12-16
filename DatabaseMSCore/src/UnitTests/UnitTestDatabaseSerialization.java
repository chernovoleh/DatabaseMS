package UnitTests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import DatabaseMSCore.ColumnScheme;
import DatabaseMSCore.Database;
import DatabaseMSCore.Table;
import DatabaseMSCore.TableScheme;

public class UnitTestDatabaseSerialization {

	@Test
	public void test() throws IOException, ClassNotFoundException {
		TableScheme ts = new TableScheme();
		ts.pushBackColumn(new ColumnScheme<String>("Name", String.class, ""));
		ts.pushBackColumn(new ColumnScheme<Integer>("Age", Integer.class, 0));
		ts.pushBackColumn(new ColumnScheme<Double>("Weight", Double.class, 0.));
		
		Database db1 = new Database("Db2");
		assertTrue(db1.addTable(ts, "Table1"));
		assertTrue(db1.addTable(ts, "Table2"));
		
		Table table1 = db1.table("Table1");
		Table table2 = db1.table("Table2");
		
		Map<String, Object> row1 = new HashMap<String, Object>();		
		row1.put("Name", "QQQ");
		row1.put("Age", 22);
		row1.put("Weight", 44.);
		
		table1.addRow(row1);
		table2.addRow(row1);
		table1.addRow(row1);
		table2.addRow(row1);
		
		FileOutputStream fos = new FileOutputStream("temp");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(db1);
		oos.close();
		
		FileInputStream fis = new FileInputStream("temp");
		ObjectInputStream oin = new ObjectInputStream(fis);
		
		Database db2 = (Database)oin.readObject();
		
		oin.close();	
		
		assertEquals(db2.name(), "Db2");
		assertTrue(db2.tableCount() == 2);
		for(String tableName : db2.tableNames()) {
			Table table = db2.table(tableName);
			for(Object [] row : table.rows()) {
				assertTrue(row[0].equals("QQQ"));
				assertTrue(row[1].equals(22));
				assertTrue(row[2].equals(44.));
			}
		}
	}

}
