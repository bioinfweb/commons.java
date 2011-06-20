package info.webinsel.util.servlet.acceptlanguage;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import info.webinsel.util.servlet.GetPostServlet;



/**
 * This servlet redirects the client to the URL associated with its accepted language. If available the accepted 
 * language the highest quality score is selected. If no accepted language is available the URL associated with the
 * default language is used.
 *       
 * @author Ben St&ouml;ver
 */
public abstract class AcceptLanguageRedirectServlet extends GetPostServlet {
	public AcceptLanguageRedirectServlet() {
		super();
	}


	public AcceptLanguageRedirectServlet(String contentType) {
		super(contentType);
	}


	protected abstract String getDefaultLanguage() throws ServletException;


	protected abstract String getURL(String language)throws ServletException;


	/**
	 * Returns the best matching language URL.
	 * @param languages - the list of languages the client accepts (Must be sorted descendant by the quality score.)
	 * @return
	 */
	private String findURL(List<AcceptLanguageEntry> languages) throws ServletException {
    String url = null;
		for (int i = 0; i < languages.size(); i++) {
			url = getURL(languages.get(i).getLanguage());
			if (url != null) {
				return url;
			}
		}
		return getURL(getDefaultLanguage());
	}
	
	
	@Override
	protected void doGetPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.sendRedirect(findURL(AcceptLanguageParser.parseHeader(request)));
	}
}
