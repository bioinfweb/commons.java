package info.webinsel.util.servlet;



/**
 * Allows to specify the URL of the output JSP as a contructor parameter.
 * 
 * @author Ben St&ouml;ver
 */
public abstract class ContrParamModel2Servlet extends Model2Servlet {
	private String outputJSP;
	
	
	public ContrParamModel2Servlet(String outputJSP) {
		super();
		this.outputJSP = outputJSP;
	}

	
	@Override
	protected String getOutputJSP() {
		return outputJSP;
	}
}
