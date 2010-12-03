package info.webinsel.wikihelp.server;


import info.webinsel.util.servlet.SQLManipulator;
import java.sql.ResultSet;
import java.sql.SQLException;



public class IndexManagement {
	public static final String TABLE_HELP_CODES = "helpCodes";
	public static final String NAME_CODE = "code";
	public static final String NAME_TOPIC = "topic";
	
	
	private SQLManipulator sqlManipulator = null;
	private String table = null;
	
	
	public IndexManagement(SQLManipulator sqlManipulator, String table) {
		super();
		this.sqlManipulator = sqlManipulator;
		this.table = table;
	}


	public String fetchTopic(int code) throws SQLException {
  	ResultSet rs = sqlManipulator.readFromDB(table, NAME_CODE, "" + code);
  	
  	if (rs.next()) {
  		return rs.getString(NAME_TOPIC);
  	}
  	else {
  		return null;
  	}
  }
}
