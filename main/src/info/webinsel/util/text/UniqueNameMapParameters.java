package info.webinsel.util.text;


import java.util.Map;
import java.util.TreeMap;



/**
 * Stores the parameters used by {@link UniqueNameMap}.
 * 
 * @author Ben St&ouml;ver
 */
public class UniqueNameMapParameters {
  public static int UNLIMITED_LENGTH = -1;
	public static final char DEFAULT_FILL_UP_CHAR = ' ';

	
  private int maxNameLength = UNLIMITED_LENGTH;
  private boolean fillUp = false;
  private char fillUpChar = DEFAULT_FILL_UP_CHAR;
  private Map<String, String> replacements = new TreeMap<String, String>();
  
  
	public int getMaxNameLength() {
		return maxNameLength;
	}
	
	
	public void setMaxNameLength(int maxNameLength) {
		this.maxNameLength = maxNameLength;
	}
	
	
	public boolean isUnlimitedLength() {
		return maxNameLength == UNLIMITED_LENGTH;
	}
	
	
	public boolean isFillUp() {
		return fillUp;
	}


	public void setFillUp(boolean fillUp) {
		this.fillUp = fillUp;
	}


	public char getFillUpChar() {
		return fillUpChar;
	}


	public void setFillUpChar(char fillUpChat) {
		this.fillUpChar = fillUpChat;
	}


	public Map<String, String> getReplacements() {
		return replacements;
	}
	
	
	public boolean hasReplacements() {
		return !replacements.isEmpty();
	}
}
