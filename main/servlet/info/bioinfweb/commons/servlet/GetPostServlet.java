package info.bioinfweb.commons.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public abstract class GetPostServlet extends HttpServlet {
	public static final String DEFAULT_CONTENT_TYPE = "text/html";
	
	
	private String contentType = DEFAULT_CONTENT_TYPE;
	
	
	public GetPostServlet() {
		super();
	}


	public GetPostServlet(String contentType) {
		super();
		this.contentType = contentType;
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(contentType);
		doGetPost(request, response);
	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(contentType);
		doGetPost(request, response);
	}
	
	
	protected abstract void doGetPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
