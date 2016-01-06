package info.bioinfweb.commons;



/**
 * A tool class that creates unique {@code long} IDs. 
 * 
 * @author Ben St&ouml;ver
 * @since 1.2.0
 */
public class LongIDManager {
	private long nextID = 0l;
	
	
	/**
	 * Returns a new sequence identifier that has not been returned before.
	 * 
	 * @return an integer greater or equal to zero
	 */
	protected long createNewID() {
		nextID++;
		return nextID - 1;
	}
	
	
	/**
	 * Peeks the value that would be returned by the next call of {@link #createNewID()}. 
	 * 
	 * @return an integer greater or equal to zero
	 */
	protected long peekNextID() {
		return nextID;
	}
}
