package info.bioinfweb.commons;



/**
 * An extension of the apache commons class {@link org.apache.commons.lang3.SystemUtils}.
 * 
 * @author Ben St&ouml;ver
 */
public class SystemUtils extends org.apache.commons.lang3.SystemUtils {
  public static final boolean IS_64_BIT_ARCHITECTURE = is64BitArchitecture();
  		
  		
  /**
   * It is unnecessary to create instances of this class.
   */
  protected SystemUtils() {
		super();
	}
  
  
	private static boolean is64BitArchitecture() {
		String arch = System.getenv("PROCESSOR_ARCHITECTURE");
		String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");

		if (IS_OS_WINDOWS) {
			return arch.endsWith("64") || wow64Arch != null && wow64Arch.endsWith("64");
		}
		else {
			return System.getProperty("os.arch").contains("64");
		}
	}
}
