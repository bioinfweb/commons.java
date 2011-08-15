package info.webinsel.util.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Reads the URL of the output JSP from a HTTP parameter called {@link #PARAM_OUTPUT_JSP}.
 * 
 * @author Ben St&ouml;ver
 */
public abstract class ReqParamModel2Servlet extends Model2Servlet {
	public static final String PARAM_OUTPUT_JSP = "outputJSP";
  
	
  private String outputJSP = ""; 
	
	
	@Override
	protected void doGetPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		outputJSP = request.getParameter(PARAM_OUTPUT_JSP);
		super.doGetPost(request, response);
	}


	@Override
	protected String getOutputJSP() {
		return outputJSP;
	}

}
