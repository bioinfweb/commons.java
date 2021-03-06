/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.servlet;


import info.bioinfweb.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * This servlet is able to forward any request to any URL (supported by
 * <code>URLConnection</code>) including its GET and POST data.<br>
 * Forwarding POST data is only possible if <code>getParameter()</code>,
 * <code>getInputStream()</code> or any other method that consumes the POST
 * data has been called previously on the specified request-object.<br>
 * The URL to forward the request to must be specified with a GET parameter
 * <code>targetURL</code>. (You have to include this parameter in the URL, 
 * you cannot use the <code><jsp:param></code> directive for this.)
 *   
 * @author Ben St&ouml;ver
 */
public class HTTPIncludeServlet extends GetPostServlet {
	public static final String PARAM_TARGET_URL = "targetURL";
	public static final Pattern TARGET_URL_PATTEEN = 
		  Pattern.compile(".*?[[^&]*&]*" + PARAM_TARGET_URL + "=([^&]+).*");
	
	
	private static String extractTargetURL(String query) {
		Matcher matcher = TARGET_URL_PATTEEN.matcher(query);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		else {
			return null;
		}
	}
	
	
	@Override
	protected void doGetPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Vor dem Lesen des Streams darf nicht getParameter() o.ä. aufgerufen worden sein.
		String targetURL = extractTargetURL(request.getQueryString());
		if (targetURL != null) {
			URLConnection connection = new URL(targetURL + "?" +
					request.getQueryString()).openConnection();  //TODO PARAM_TARGET_URL kann ggf. noch aus QueryString entfernt werden
			connection.setDoInput(true);
			connection.setDoOutput(true);
			
			IOUtils.copy(request.getInputStream(), connection.getOutputStream());
			response.setContentType(connection.getContentType());
			IOUtils.copy(connection.getInputStream(), response.getOutputStream());
		}
		else {
			throw new ServletException("There was no target URL specified. (You can only use a GET parameter for this.)"); 
		}
	}
}
