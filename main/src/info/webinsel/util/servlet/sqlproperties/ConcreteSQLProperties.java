package info.webinsel.util.servlet.sqlproperties;



/**
 * Default implementation of the {@link SQLProperties} interface.
 * 
 * @author Ben St&ouml;ver
 */
public class ConcreteSQLProperties implements SQLProperties {
  private String dbHost;
  private String dbName;
  private String dbUser;
  private String dbPassword;
  private String dbDriver;
  private String tablePrefix;


	/* (non-Javadoc)
	 * @see info.webinsel.util.servlet.sqlproperties.SQLProperties#getDBDriver()
	 */
	public String getDBDriver() {
		return dbDriver;
	}
	
	
	/* (non-Javadoc)
	 * @see info.webinsel.util.servlet.sqlproperties.SQLProperties#setDBDriver(java.lang.String)
	 */
	public void setDBDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}


	/* (non-Javadoc)
	 * @see info.webinsel.util.servlet.sqlproperties.SQLProperties#getDBHost()
	 */
	public String getDBHost() {
		return dbHost;
	}


	/* (non-Javadoc)
	 * @see info.webinsel.util.servlet.sqlproperties.SQLProperties#setDBHost(java.lang.String)
	 */
	public void setDBHost(String dbHost) {
		this.dbHost = dbHost;
	}


	/* (non-Javadoc)
	 * @see info.webinsel.util.servlet.sqlproperties.SQLProperties#getDBName()
	 */
	public String getDBName() {
		return dbName;
	}


	/* (non-Javadoc)
	 * @see info.webinsel.util.servlet.sqlproperties.SQLProperties#setDBName(java.lang.String)
	 */
	public void setDBName(String dbName) {
		this.dbName = dbName;
	}


	/* (non-Javadoc)
	 * @see info.webinsel.util.servlet.sqlproperties.SQLProperties#getDBPassword()
	 */
	public String getDBPassword() {
		return dbPassword;
	}


	/* (non-Javadoc)
	 * @see info.webinsel.util.servlet.sqlproperties.SQLProperties#setDBPassword(java.lang.String)
	 */
	public void setDBPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}


	/* (non-Javadoc)
	 * @see info.webinsel.util.servlet.sqlproperties.SQLProperties#getDBUser()
	 */
	public String getDBUser() {
		return dbUser;
	}


	/* (non-Javadoc)
	 * @see info.webinsel.util.servlet.sqlproperties.SQLProperties#setDBUser(java.lang.String)
	 */
	public void setDBUser(String dbUser) {
		this.dbUser = dbUser;
	}

	
	/* (non-Javadoc)
	 * @see info.webinsel.util.servlet.sqlproperties.SQLProperties#getTablePrefix()
	 */
	public String getTablePrefix() {
		return tablePrefix;
	}


	/* (non-Javadoc)
	 * @see info.webinsel.util.servlet.sqlproperties.SQLProperties#setTablePrefix(java.lang.String)
	 */
	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
}
