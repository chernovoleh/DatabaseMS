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
import DatabaseMSCore.dbType;
import DatabaseMSCore.dbTypeDouble;
import DatabaseMSCore.dbTypeInteger;
import DatabaseMSCore.dbTypeString;

public class UnitTestDatabaseSerialization {

	@Test
	public void test() throws IOException, ClassNotFoundException {
		TableScheme ts1 = new TableScheme();
		TableScheme ts2 = new TableScheme();
		ts1.addColumnScheme(new ColumnScheme("Name", dbTypeString.class));
		ts1.addColumnScheme(new ColumnScheme("Age", dbTypeInteger.class));
		ts1.addColumnScheme(new ColumnScheme("Weight", dbTypeDouble.class));
		ts2.addColumnScheme(new ColumnScheme("Name", dbTypeString.class));
		ts2.addColumnScheme(new ColumnScheme("Age", dbTypeInteger.class));
		ts2.addColumnScheme(new ColumnScheme("Weight", dbTypeDouble.class));
		
		Database db1 = new Database("Db1");
		assertTrue(db1.addTable(ts1, "Table1"));
		assertTrue(db1.addTable(ts2, "Table2"));
		
		Table table1 = db1.table("Table1");
		Table table2 = db1.table("Table2");
		
		Map<String, String> row1 = new HashMap<String, String>();		
		row1.put("Name", "QQQ");
		row1.put("Age", "22");
		row1.put("Weight", "44.0");
		
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
		
		assertEquals(db2.name(), "Db1");
		assertTrue(db2.tableCount() == 2);
		for(String tableName : db2.tableNames()) {
			Table table = db2.table(tableName);
			for(dbType [] row : table.rows()) {
				assertTrue(row[0].toString().equals("QQQ"));
				assertTrue(row[1].toString().equals("22"));
				assertTrue(row[2].toString().equals("44.0"));
			}
		}
	}

}
