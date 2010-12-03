package info.webinsel.util;



public interface ChangeMonitorable {
	public boolean hasChanged();
	public void registerChange();
	public void reset();	
}
