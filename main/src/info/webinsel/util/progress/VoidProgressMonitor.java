package info.webinsel.util.progress;



/**
 * Implementation of {@link ProgressMonitor} that only stores the progress and does not output it anywhere.
 * 
 * @author Ben St&ouml;ver
 */
public class VoidProgressMonitor extends AbstractProgressMonitor implements ProgressMonitor {
	private double progress = 0;
	
	
	/* (non-Javadoc)
	 * @see info.webinsel.util.progress.ProgressMonitor#getProgressValue()
	 */
	@Override
	public double getProgressValue() {
  	return progress;
	}

	
	/* (non-Javadoc)
	 * @see info.webinsel.util.progress.ProgressMonitor#setProgressValue(double)
	 */
	@Override
	public void setProgressValue(double value) {
		progress = value;
	}
	
	
	/**
	 * This method always returnes <code>false</code>.
	 * 
	 * @see info.webinsel.util.progress.ProgressMonitor#isCanceled()
	 */
	@Override
	public boolean isCanceled() {
		return false;
	}
}
