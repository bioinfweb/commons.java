package info.bioinfweb.commons.core.io.streammessage;


import java.io.DataInputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;



/**
 * Abstract factory class that provides an {@link EnumMap} to generate and read the according 
 * {@link Message} object when a message is send over the stream.
 * 
 * @author Ben St&ouml;ver
 * 
 * @param <T> - the message type
 */
public abstract class MessageReader<T extends Enum<T>> {
	private Class<T> typeClass = null;
	
	
	private Map<T, Class<? extends Message<T>>> messages = 
		  new HashMap<T, Class<? extends Message<T>>>();
	
	
	public MessageReader(Class<T> typeClass) {
		super();
		this.typeClass = typeClass;
		fillList();
	}
	
	
	protected Map<T, Class<? extends Message<T>>> getMessages() {
		return messages;
	}


	protected abstract void fillList();
	
	
	public Message read(DataInputStream in) throws IOException {
		try {
			Message message = messages.get(typeClass.getEnumConstants()[in.readInt()]).newInstance();
	  	message.read(in);
			return message;
		}
		catch (IllegalAccessException e) {
			throw new Error(e);  // Passiert wohl nicht
		}
		catch (InstantiationException e) {
			throw new Error(e);  // Passiert wohl nicht
		}
	}
}
