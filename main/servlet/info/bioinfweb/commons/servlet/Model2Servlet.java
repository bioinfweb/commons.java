/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Provides general functionality for servlets that are part of a model 2 architecture. The path to the output JSP
 * can be provided as a constructor parameter or as a request parameter. (A request parameter would be ignored if a 
 * constructor parameter was provided.)
 * 
 * @author Ben St&ouml;ver 
 */
public abstract class Model2Servlet extends GetPostServlet {
	public static final String PARAM_OUTPUT_JSP = "outputJSP";
  
	
  private String outputJSP = null; 
	
	
  public Model2Servlet() {
		super();
	}


	public Model2Servlet(String contentType) {
		super(contentType);
	}


	public Model2Servlet(String contentType, String outputJSP) {
		super();
		this.outputJSP = outputJSP;
	}


	/**
   * Implementations of this method are called before this servlet delegates to the output JSP.
   * @param request
   * @param response
   * @throws Exception - all thrown exceptions are packed in a {@link ServletException}.
   */
  protected abstract void generateBeans(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	
  /**
   * @return the URL relative to the servlet context
   */
  protected  String getOutputJSP() {
  	return outputJSP;
  }
  
  
	@Override
	protected void doGetPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			generateBeans(request, response);
			String outputJSP = getOutputJSP();
			if (outputJSP == null) {
				outputJSP = request.getParameter(PARAM_OUTPUT_JSP);
			}
			getServletContext().getRequestDispatcher(outputJSP).include(
					request, response);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
