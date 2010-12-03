package info.webinsel.util.servlet.urltree;


import info.webinsel.util.servlet.GetPostServlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public abstract class AbstractContentDispatcher extends GetPostServlet {
	public static final String PARAM_NAME_PATH = "requestPath";
	public static final String PARAM_NAME_LANG = "requestLang";

	public static final String ATTR_CONNECTION = "DBConnection";
	public static final String ATTR_PATH = "pathInfo";
	public static final String ATTR_HOST = "Host";
	
	public static final Pattern QUERY_PATTEEN = Pattern.compile(PARAM_NAME_LANG + "=[^&]*&" + PARAM_NAME_PATH + "=[^&]*&(.+)");
	
	
	public AbstractContentDispatcher() {
		super();
	}


	/**
	 * Returns the query string which has been inculded in the URL before in went through the apache
	 * module mod_rewrite.
	 * @param request - the request object
	 * @return the query string including the <code>?</code>
	 */
	private String getQuery(HttpServletRequest request) {
		String query = request.getQueryString();
		Matcher matcher = QUERY_PATTEEN.matcher(query);
		if (matcher.matches()) {
			return "?" + matcher.group(1);
		}
		else {
			return "";
		}
	}
	
	
	public abstract String getTablePrefix() throws Exception;


	public abstract String getJSPURL() throws Exception;


	protected abstract Connection createConnection() throws Exception;

	
	protected abstract String getRedirectHost(String lang, String path) throws Exception;
	
	
	protected abstract ErrorPageData getErrorPage() throws Exception;
	
	
	protected abstract void addAttributes(HttpServletRequest request) throws Exception;
	
	
	@Override
	protected void doGetPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		  String path = request.getParameter(PARAM_NAME_PATH);
		  String lang = request.getParameter(PARAM_NAME_LANG);
		  
  		Connection connection = createConnection();
	  	try {
	  		boolean redirect = false;
			  if (path.endsWith("/")) {
			  	path = path.substring(0, path.length() - 1);
			  	redirect = true;
			  }
			  
			  URLPath urlPath = URLPath.createInstance(connection.createStatement(), 
			  		getTablePrefix(), lang, path, getErrorPage());
			  String paramPath = path;
			  if (!urlPath.isError404()) {
			  	path = urlPath.createPath(lang);  // case sensitive
			  }
			  
			  if (redirect || !path.equals(paramPath)) {
				  response.sendRedirect("http://" + getRedirectHost(lang, path) + "/" + 
		  		path + getQuery(request));
			  }
			  else {
			  	if (urlPath.isError404()) {
			  		response.setStatus(404);
			  	}
			  	request.setAttribute(ATTR_CONNECTION, connection);
			  	request.setAttribute(ATTR_PATH, urlPath);
			  	request.setAttribute(ATTR_HOST, getRedirectHost(lang, path));
			  	addAttributes(request);
		  		getServletContext().getRequestDispatcher(getJSPURL()).include(
		  				request, response);
			  }
	  	}
	  	finally {
	  		if ((connection != null) && !connection.isClosed()) {
	  			connection.close();
	  		}
	  	}
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
