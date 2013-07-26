package info.webinsel.util.progress;



/**
 * This interface is used by the repeat finders to report their progress.
 * 
 * @author Ben St&ouml;ver
 */
public interface ProgressMonitor {
  public double getProgressValue();
  
  public void setProgressValue(double value);
  
  public void addToProgressValue(double addend);
  
  public boolean isCanceled();
}
