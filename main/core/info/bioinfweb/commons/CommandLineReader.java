package info.bioinfweb.commons;



public class CommandLineReader {
  private String[] args = null;

  
	public CommandLineReader(String[] args) {
		super();
		this.args = args;
	}
  
  
	/**
	 * Checks if the given value is contained in the arguments and returns the index. 
	 * @param value - the value to search for (exact match, case sensitive)
	 * @param start - the start index
	 * @param end - the end index (the last checked index is <code>end - 1</code>)
	 * @return the index of the first occurrence of the value or -1 if it was not found
	 */
	public int contained(String value, int start, int end) {
		for (int i = start; i < end; i++) {
			if (args[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	
	/**
	 * Checks if the given value is contained in the arguments and returns the index.
	 * All elements from <code>start</code> on are searched. 
	 * @param value - the value to search for (exact match, case sensitive)
	 * @param start - the start index
	 * @return the index of the first occurrence of the value or -1 if it was not found
	 */
	public int contained(String value, int start) {
		return contained(value, start, args.length);
	}
	
	
	/**
	 * Checks if the given value is contained in the arguments and returns the index.
	 * All elements are searched. 
	 * @param value - the value to search for (exact match, case sensitive)
	 * @return the index of the first occurrence of the value or -1 if it was not found
	 */
	public int contained(String value) {
		return contained(value, 0, args.length);
	}
	
	
	public String getArg(int index) {
		return args[index];
	}
	
	
	public int argCount() {
		return args.length;
	}
}
