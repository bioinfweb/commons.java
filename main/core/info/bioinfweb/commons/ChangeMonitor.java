package info.bioinfweb.commons;



public class ChangeMonitor implements ChangeMonitorable {
	private boolean changed = false;
	
	
	public boolean hasChanged() {
		return changed;
	}
	
	
	public void registerChange() {
		changed = true;
	}
	
	
	public void reset() {
		changed = false;
	}
}
