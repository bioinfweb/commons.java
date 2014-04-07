package info.bioinfweb.commons.changemonitor;



/**
 * Basic implementation of {@link ChangeMonitorable}. 
 * 
 * @author Ben St&ouml;ver
 */
public class ChangeMonitor implements ChangeMonitorable {
	private boolean changed = false;
	
	
	/**
	 * Returns if one or more changes (calls of {@link #registerChange()}) have happened since the
	 * last call of {@link #reset()}.
	 * 
	 * @return {@code true} if a change happened, {@code false} otherwise
	 */
	public boolean hasChanged() {
		return changed;
	}
	
	
	/**
	 * This method should be called of a change happened. (Inherited classes should call this method
	 * e.g. in according setter methods.)
	 */
	public void registerChange() {
		changed = true;
	}
	
	
	/**
	 * {@link #hasChanged()} will return {@code false} after the call of this method until the 
	 * next call of {@link #registerChange()}.
	 */
	public void reset() {
		changed = false;
	}
}
