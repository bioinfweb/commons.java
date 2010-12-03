package info.webinsel.util.servlet.sqlproperties;



/**
 * Classes implementing this interface provide a bean to store information to connect to a SQL database.
 * 
 * @author Ben St&ouml;ver
 */
public interface SQLProperties {
	public String getDBDriver();

	
	public void setDBDriver(String dbDriver);

	
	public String getDBHost();

	
	public void setDBHost(String dbHost);

	
	public String getDBName();

	
	public void setDBName(String dbName);

	
	public String getDBPassword();

	
	public void setDBPassword(String dbPassword);

	
	public String getDBUser();

	
	public void setDBUser(String dbUser);

	
	public String getTablePrefix();

	
	public void setTablePrefix(String tablePrefix);
}