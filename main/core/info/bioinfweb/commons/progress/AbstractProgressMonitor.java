package info.bioinfweb.commons.progress;



/**
 * Basic implementation of the {@link ProgressMonitor} interface.
 * 
 * @author Ben St&ouml;ver
 */
public abstract class AbstractProgressMonitor implements ProgressMonitor {
	public void addToProgressValue(double addend) {
		setProgressValue(getProgressValue() + addend);
	}
}
