package info.bioinfweb.commons.sql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class SQLConnector {
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	
	
	public static Connection getConnection(String host, String name, String user, 
			String password, String driver) throws ClassNotFoundException, SQLException {
		
    Class.forName(driver);
    return DriverManager.getConnection("jdbc:mysql://" + host + "/" + name + "?user=" +
    		user + "&password=" + password);
	}
}
