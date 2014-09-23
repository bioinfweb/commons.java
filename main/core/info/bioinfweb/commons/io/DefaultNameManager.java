package info.bioinfweb.commons.io;



public class DefaultNameManager {
  private int count = 0;
  private String prefix = "NewFile";
  
  
  public DefaultNameManager(String prefix) {
		super();
		this.prefix = prefix;
	}


	public DefaultNameManager() {
		super();
	}


	public String getPrefix() {
		return prefix;
	}


	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	public int getCount() {
		return count;
	}


	public String newDefaultName() {
  	count++;
  	return prefix + count;  // Erster Rückgabewert ist "1".
  }
}
