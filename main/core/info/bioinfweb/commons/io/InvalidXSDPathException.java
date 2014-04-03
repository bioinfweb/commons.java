package info.bioinfweb.commons.io;



/**
 * This exception is thrown by {@link XMLUtils#extractXSDFileName(javax.xml.stream.events.StartElement)} if the
 * value of the <code>xsi:schemaLocation</code> attribute is not a valid path.
 * 
 * @author Ben St&ouml;ver
 */
public class InvalidXSDPathException extends Exception {
	public InvalidXSDPathException() {
		super();
	}

	
	public InvalidXSDPathException(String message, Throwable throwable) {
		super(message, throwable);
	}

	
	public InvalidXSDPathException(String message) {
		super(message);
	}


	public InvalidXSDPathException(Throwable throwable) {
		super(throwable);
	}
}
