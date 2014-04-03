package info.bioinfweb.commons.appversion;



public enum ApplicationType {
	ALPHA,
	BETA,
	RC,
	FINAL;
	
	
	public static final String ALPHA_STRING = "alpha";
	public static final String BETA_STRING = "beta";
	public static final String RC_STRING = "release candidate";
	public static final String FINAL_STRING = "";


	@Override
	public String toString() {
		switch(this) {
		  case ALPHA:
		  	return ALPHA_STRING;
		  case BETA:
		  	return BETA_STRING;
		  case RC:
		  	return RC_STRING;
		  default:
		  	return FINAL_STRING;
		}
	}
	
	
	/**
	 * Parses a text representation of an application type. This method is not case 
	 * sensitive and the input may contain preceding or trailing whitespaces.
	 * @param text - the string to parse
	 * @return the parsed value or <code>null</code> if the string could not be parsed or
	 *         was also <code>null</code>
	 */
	public static ApplicationType parseType(String text) {
		if (text != null) {
			text = text.trim().toLowerCase();
			if (text.equals(ALPHA_STRING)) {
				return ALPHA;
			}
			else if (text.equals(BETA_STRING)) {
				return BETA;
			}
			else if (text.equals(RC_STRING)) {
				return RC;
			}
			else if (text.equals(FINAL_STRING)) {
				return FINAL;
			}
		}
		return null;
	}
}
