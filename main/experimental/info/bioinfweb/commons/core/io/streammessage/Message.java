package info.bioinfweb.commons.core.io.streammessage;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;



/**
 * Abstract base class for all messages send between the main and the VLCFrameGrab application.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 */
public abstract class Message<T extends Enum<T>> {
  private T type = null;
  private int version = 0;

  
	public Message(T type, int version) {
		super();
		this.type = type;
		this.version = version;
	}
  
	
	public T getType() {
		return type;
	}


	public int getVersion() {
		return version;
	}
	
	
	/**
	 * Writes the message type and version to the stream. This method can be extended by
	 * subclasses.
	 * @param stream
	 * @throws IOException
	 * @throws NewerMessageVersionException
	 */
	public void write(DataOutputStream stream) throws IOException, 
	    NewerMessageVersionException {
		
		stream.writeInt(getType().ordinal());
		stream.writeInt(getVersion());
	}
  
	
	/**
	 * Reads the message version and than calls <code>doRead</code>. The message type id 
	 * not read because it must be read before to decide which message object to create.
	 * @param stream - the stream to read the data from
	 * @throws IOException
	 * @throws NewerMessageVersionException - if a version newer than the version
	 *         of this class is read
	 */
	public void read(DataInputStream stream) throws IOException,
	    NewerMessageVersionException {
		
		int version = stream.readInt();
		if (version > getVersion()) {
			throw new NewerMessageVersionException("The stream data has a newer version then this class (" + version + ").");
		}
		else {
			doRead(stream, version);
		}
	}
	
	
	/**
	 * Subclasses should implement this method to read the data this message 
	 * object contains. The message type and version have already been read 
	 * before this method is called.
	 * @param stream - the stream to read the data from
	 * @param version - the version which was read (smaller or equal to the 
	 *        version of this class)
	 * @throws IOException
	 */
	protected abstract void doRead(DataInputStream stream, int version) throws IOException;
}
