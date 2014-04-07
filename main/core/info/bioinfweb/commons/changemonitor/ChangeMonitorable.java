package info.bioinfweb.commons.changemonitor;



/**
 * A common interface that can be implemented by classes that track whether they have undergone 
 * changes.
 * 
 * @author Ben St&ouml;ver
 * @see ChangeMonitor
 */
public interface ChangeMonitorable {
	/**
	 * Implementing classes should return if one or more changes (calls of {@link #registerChange()}) 
	 * have happened since the last call of {@link #reset()} here.
	 * 
	 * @return {@code true} if a change happened, {@code false} otherwise
	 */
	public boolean hasChanged();
	
	/**
	 * This method should be called of a change happened. (Implementing classes should call this method
	 * e.g. in according setter methods.)
	 */
	public void registerChange();
	
	/**
	 * Implementing classes should make sure that {@link #hasChanged()} will return {@code false} after
	 * the call of this method until the next call of {@link #registerChange()}.
	 */
	public void reset();	
}
