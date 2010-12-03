package info.webinsel.util.io;



/**
 * Classes that implement this interface can be informed about the progress of reading and writing
 * data.
 * @author Ben St&ouml;ver
 */
public interface IOProgressListener {
  /**
   * This method is called when a reading or writing of data is about to begin.
   */
	public void ioStarts();
	
	
  /**
   * This method is called when a progress in reading or writing data has been made.
   * @param bytes - the number of bytes that have been read or written until yet.
   */
  public void ioProgress(long bytes);
  
  
  /**
   * This method is called only if the progress listeners is passes to a method that processes
   * several files. Every time before a new file is processed, this method is invoked.  
   * @param sourceName - the name of the current source file.
   * @param destName - the name of the current destination file
   * @param size - the size in bytes of the current file
   */
  public void newFile(String sourceName, String destName, long size);
  
  
  /**
   * This method is called when a reading or writing of data has been finished.
   */
	public void ioFinished();
}
