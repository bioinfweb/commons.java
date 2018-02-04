/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2018 Ben Stöver, Sarah Wiechers
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
