package info.webinsel.util.servlet.sqlproperties;

public interface SQLProperties {

	public abstract String getDBDriver();

	public abstract void setDBDriver(String dbDriver);

	public abstract String getDBHost();

	public abstract void setDBHost(String dbHost);

	public abstract String getDBName();

	public abstract void setDBName(String dbName);

	public abstract String getDBPassword();

	public abstract void setDBPassword(String dbPassword);

	public abstract String getDBUser();

	public abstract void setDBUser(String dbUser);

	public abstract String getTablePrefix();

	public abstract void setTablePrefix(String tablePrefix);

}