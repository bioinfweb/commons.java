package info.bioinfweb.commons.servlet;


import javax.servlet.http.Cookie;



public class CookieManager {
  public static Cookie getFirst(Cookie[] cookies, String name) {
  	if (cookies != null) {
	  	for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					return cookies[i];
				}
			}
  	}
  	return null;
  }
}
