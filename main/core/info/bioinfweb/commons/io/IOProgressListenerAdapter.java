package info.bioinfweb.commons.io;



/**
 * An abstract adapter class for receiving IO progress events. The methods in this class are empty. 
 * This class exists as convenience for creating listener objects. 
 * @author Ben St&ouml;ver
 */
public abstract class IOProgressListenerAdapter implements IOProgressListener {
	public void ioFinished() {}

	
	public void ioProgress(long bytes) {}

	
	public void ioStarts() {}


	public void newFile(String name) {}
}
