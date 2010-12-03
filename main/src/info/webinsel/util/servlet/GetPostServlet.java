package info.webinsel.util.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public abstract class GetPostServlet extends HttpServlet {
	private String contentType = "text/html";
	
	
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
	
	
	public static int getIntParameter(HttpServletRequest request, String name, 
			int defaultValue) {
		
		if (request.getParameter(name) != null) {
			try {
				return Integer.parseInt(request.getParameter(name));
			}
			catch (NumberFormatException e) {}
		}
		return defaultValue;
	}
	
	
	public static long getLongParameter(HttpServletRequest request, String name, 
			long defaultValue) {
		
		if (request.getParameter(name) != null) {
			try {
				return Long.parseLong(request.getParameter(name));
			}
			catch (NumberFormatException e) {}
		}
		return defaultValue;
	}
}
