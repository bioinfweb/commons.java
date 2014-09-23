package info.bioinfweb.commons.servlet.webapp;


import info.bioinfweb.commons.RandomValues;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;



//TODO Klären, inwieweit sich diese Klasse mit ResultSet überschneidet und was sie wirklich leisten soll.
public class DBObject {
	public static final String ID_COLUMN_NAME = "id";
	public static final int UNDEFINED_ID = Integer.MIN_VALUE;
	public static final int MIN_ID = 0;
	public static final int MAX_ID = Integer.MAX_VALUE;
	
	
  private MySQLManipulator manipulator = null;
  private String table = null;
  private int id = UNDEFINED_ID;
  private HashMap<String, Object> properties = new HashMap<String, Object>();
  
  
	/**
	 * Creates a new instance without an ID.
	 * @param manipulator - the manipulator to read and write data
	 * @param table - the table which stores this kind of object
	 */
	public DBObject(MySQLManipulator manipulator, String table) {
		super();
		this.manipulator = manipulator;
		this.table = table;
	}
	
	
	public static DBObject readInstance(MySQLManipulator manipulator, String table, int id) throws SQLException {
		ResultSet rs = manipulator.readFromDB(table, ID_COLUMN_NAME, "" + id);;
		if (rs.next()) {
			
		}
	}


	public String getTable() {
		return table;
	}
	
	
	/**
	 * Returns the number of objects (= number of names) that have been stored. 
	 */
	public int size() {
		return properties.size();
	}
	
	
	public boolean hasID() {
		return id == UNDEFINED_ID;
	}
	
	
	/**
	 * The database ID of this object. IDs are between <code>0</code> and 
	 * <code>Integer.MAX_VALUE</code>. Negative database entries remain for special use
	 * to the concrete implementation. 
	 * @return the ID of this object or <code>UNDEFINED_ID</code> if none was assigned.
	 */
	public int getId() {
		return id;
	}
	
	
	/**
	 * Tests of the given column has the given value is assigned table
	 * @param column - the column to be searched
	 * @param value - the value (internaly convertes with its <code>toString()</code>)-method)
	 * @return true, if the given value is present obe or more times.
	 * @throws SQLException
	 */
	public boolean valueExists(String column, Object value) throws SQLException {
  	ResultSet rs = manipulator.readFromDB(table, column, value.toString());
		return rs.next();
	}
	
	
	/**
	 * Tests if there is an object with the given database ID present in the assigned 
	 * table.
	 * @param id - the ID to search for
	 * @return true, if the ID is present
	 * @throws SQLException
	 */
	public boolean idExists(int id) throws SQLException {
		return valueExists(ID_COLUMN_NAME, id);
	}


	private int createID() throws SQLException {
		int result = 0;
		do {
			result = RandomValues.randInt(MIN_ID, MAX_ID);
		} while (idExists(result));
		return result;
	}
	
	
	/**
	 * Writes the current values to the database. If an ID is defined, the according 
	 * database entry is updated, otherwise a new entry is created.
	 * @return the ID (just created if it was undefined before)
	 */
	public int writeToDB() throws SQLException {
    // Prepare keys and values:
		String[] keys = new String[size() + 1];
		Object[] values = new Object[size() + 1];
		keys[0] = ID_COLUMN_NAME;
		values[0] = id;
		Iterator<Entry<String, Object>> it = properties.entrySet().iterator();
		for (int i = 1; i <= size(); i++) {
			Entry<String, Object> entry = it.next();  // Muss genauso oft einen Wert zurückgeben.
			keys[i] = entry.getKey();
			values[i] = entry.getValue();
		}
		
		// Write to database and create new ID:
		if (hasID()) {
			manipulator.updateDBEntry(table, ID_COLUMN_NAME, "" + id, keys, values);
		}
		else {
			id = createID();
			values[0] = id;
			manipulator.insertIntoDB(table, keys, values);
		}
		
		return id;
	}
}
