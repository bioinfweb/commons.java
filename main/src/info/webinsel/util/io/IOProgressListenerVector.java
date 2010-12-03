package info.webinsel.util.io;


import java.util.Vector;



/**
 * This class can be used if several profress listeners should be informed about the same process.
 * You have to add all listerns to an instance of this class and pass the instance as the only
 * progress lister. All included listeners will be informed by this class than.
 * @author Ben St&ouml;ver
 */
public class IOProgressListenerVector implements IOProgressListener {
  private Vector<IOProgressListener> vector = new Vector<IOProgressListener>();

  
	public void ioStarts() {
		for (int i = 0; i < size(); i++) {
			get(i).ioStarts();
		}
	}

	
	public void ioProgress(long bytes) {
		for (int i = 0; i < size(); i++) {
			get(i).ioProgress(bytes);
		}
	}

	
	public void newFile(String sourceName, String destName, long size) {
		for (int i = 0; i < size(); i++) {
			get(i).newFile(sourceName, destName, size);
		}
	}


	public void ioFinished() {
		for (int i = 0; i < size(); i++) {
			get(i).ioFinished();
		}
	}


	public boolean add(IOProgressListener listener) {
		return vector.add(listener);
	}

	
	public IOProgressListener get(int pos) {
		return vector.get(pos);
	}


	public void clear() {
		vector.clear();
	}

	
	public boolean remove(IOProgressListener listener) {
		return vector.remove(listener);
	}

	
	public int size() {
		return vector.size();
	}
}
