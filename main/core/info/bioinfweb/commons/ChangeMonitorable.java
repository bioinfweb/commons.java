package info.bioinfweb.commons;



public interface ChangeMonitorable {
	public boolean hasChanged();
	public void registerChange();
	public void reset();	
}
