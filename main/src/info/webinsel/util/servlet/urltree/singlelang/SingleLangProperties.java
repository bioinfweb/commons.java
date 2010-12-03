package info.webinsel.util.servlet.urltree.singlelang;


import info.webinsel.util.servlet.sqlproperties.ConcreteSQLProperties;
import info.webinsel.util.servlet.urltree.ErrorPageData;



public class SingleLangProperties extends ConcreteSQLProperties {
  private String hostName;
  private String mainJSP;
  private ErrorPageData error404 = new ErrorPageData(404);
  
  
	public String getHostName() {
		return hostName;
	}


	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


	public String getMainJSP() {
		return mainJSP;
	}


	public void setMainJSP(String mainJSP) {
		this.mainJSP = mainJSP;
	}


	public ErrorPageData getError404() {
		return error404;
	}
}
