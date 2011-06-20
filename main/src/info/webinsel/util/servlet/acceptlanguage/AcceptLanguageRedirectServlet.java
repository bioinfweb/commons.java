package info.webinsel.util.servlet.acceptlanguage;


import java.io.IOException;
import java.util.List;
import java.util.Map;

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
public class AcceptLanguageRedirectServlet extends GetPostServlet {
  private String defaultLanguage = "en";
  private Map<String, String> urls;
  
  
	public AcceptLanguageRedirectServlet(String defaultLanguage, Map<String, String> urls) {
		super();
		this.defaultLanguage = defaultLanguage;
		this.urls = urls;
	}


	public String getDefaultLanguage() {
		return defaultLanguage;
	}


	public Map<String, String> getUrls() {
		return urls;
	}


	/**
	 * Returns the best matching language URL.
	 * @param languages - the list of languages the client accepts (Must be sorted descendant by the quality score.)
	 * @return
	 */
	private String findURL(List<AcceptLanguageEntry> languages) {
    String url = null;
		for (int i = 0; i < languages.size(); i++) {
			url = getUrls().get(languages.get(i).getLanguage());
			if (url != null) {
				return url;
			}
		}
		return getUrls().get(getDefaultLanguage());
	}
	
	
	@Override
	protected void doGetPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.sendRedirect(findURL(AcceptLanguageParser.parseHeader(request)));
	}
}
