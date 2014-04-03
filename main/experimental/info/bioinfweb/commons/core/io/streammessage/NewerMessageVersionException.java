package info.bioinfweb.commons.core.io.streammessage;



public class NewerMessageVersionException extends RuntimeException {
	public NewerMessageVersionException() {
		super();
	}

	
	public NewerMessageVersionException(String msg, Throwable th) {
		super(msg, th);
	}

	
	public NewerMessageVersionException(String msg) {
		super(msg);
	}

	
	public NewerMessageVersionException(Throwable th) {
		super(th);
	}
}
