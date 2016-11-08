/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2016  Ben Stöver, Sarah Wiechers
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.commons.appversion;


import javax.xml.namespace.QName;



/**
 * Constants used for the XML representation of an instance of {@link ApplicationVersion} as they are
 * used by {@link AppVersionXMLReadWrite}.
 * 
 * @author Ben St&ouml;ver
 */
public class AppVersionXMLConstants {
	//TODO Should a special namespace be used here? 
	
  public static final QName TAG_APP_VERSION = new QName("AppVersion");
  public static final QName ATTR_MAJOR_APP_VERSION = new QName("Major");
  public static final QName ATTR_MINOR_APP_VERSION = new QName("Minor");
  public static final QName ATTR_PATCH_LEVEL = new QName("Patch");
  public static final QName ATTR_BUILD_NO = new QName("Build");
  public static final QName ATTR_APP_TYPE = new QName("type");  
}
