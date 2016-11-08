/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
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
package info.bioinfweb.commons.servlet.acceptlanguage;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import info.bioinfweb.commons.servlet.GetPostServlet;



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
