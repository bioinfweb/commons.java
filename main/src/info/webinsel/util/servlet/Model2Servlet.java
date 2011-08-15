package info.webinsel.util.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Provides general functionality for servlets that are part of a model 2 architecture.
 * 
 * @author Ben St&ouml;ver
 */
public abstract class Model2Servlet extends GetPostServlet {
  /**
   * Implementations of this method are called before this servlet delegates to the output JSP.
   * @param request
   * @param response
   * @throws Exception - all thrown exceptions are packed in a {@link ServletException}.
   */
  protected abstract void generateBeans(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	
  /**
   * Implementing classes must specify the URL of the output JSP here.
   * @return the URL relative to the servlet context
   */
  protected abstract String getOutputJSP();
  
  
	@Override
	protected void doGetPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			generateBeans(request, response);
			getServletContext().getRequestDispatcher(getOutputJSP()).include(
					request, response);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
