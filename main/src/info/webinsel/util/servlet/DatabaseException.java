package info.webinsel.util.servlet;



public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;


	public DatabaseException() {
		super();
	}

	
	public DatabaseException(String msg) {
		super(msg);
	}
}
