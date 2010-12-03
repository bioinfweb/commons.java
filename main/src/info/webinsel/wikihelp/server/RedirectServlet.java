package info.webinsel.wikihelp.server;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import info.webinsel.util.servlet.GetPostServlet;
import info.webinsel.wikihelp.server.config.PropertiesManager;
import info.webinsel.wikihelp.server.config.WikiHelpProperties;



public class RedirectServlet extends GetPostServlet {
	public static final String TABLE_HELP_CODES = "codes";
	public static final String CODE_NAME = "code";
	public static final String TOPIC_NAME = "topic";
	
	public static final String PARAM_FORMAT = "format";
	public static final String FORMAT_VALUE_STRING = "str";
	public static final String FORMAT_VALUE_NUMBER = "no";
	public static final String PARAM_CODE = CODE_NAME;
	
	
  @Override
	protected void doGetPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Connection connection = PropertiesManager.createConnection(getServletContext());
			WikiHelpProperties properties = PropertiesManager.getProperties(getServletContext());
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM " + properties.getTablePrefix() + TABLE_HELP_CODES + " " + 
					"WHERE " + CODE_NAME + " = ? " + 
					"LIMIT 1");
			try {
				if (FORMAT_VALUE_NUMBER.equals(request.getParameter(PARAM_FORMAT))) {
					statement.setLong(1, Long.parseLong(request.getParameter(CODE_NAME)));
				}
				else {
					statement.setString(1, request.getParameter(PARAM_CODE));
				}
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					response.sendRedirect(properties.getWikiBaseURL() + rs.getString(TOPIC_NAME));
				}
				else {
					response.sendRedirect(properties.getInvalidHelpCodeURL() + "?" + 
							PARAM_CODE + '=' + request.getParameter(PARAM_CODE));
				}
			}
			finally {
				statement.close();
			}
		}
		catch (Exception e) {
			//TODO Ggf. zu Fehlerseite aus Properties umleiten.
			throw new ServletException(e);
		}
	}
}
