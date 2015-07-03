/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008 - 2015  Ben Stöver
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


import info.bioinfweb.commons.io.XMLUtils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.StartElement;



/**
 * Reads or writes an XML tag describing an {@link ApplicationVersion} object.
 * 
 * @author Ben St&ouml;ver
 */
public class AppVersionXMLReadWrite extends AppVersionXMLConstants {
	/**
	 * Writes an XML tag representing the specified application version.
	 * 
	 * @param writer - the writer that outputs the XML stream to write to
	 * @param version - the version to be written
	 * @throws XMLStreamException
	 */
	public static void write(XMLStreamWriter writer, ApplicationVersion version) throws XMLStreamException {
		writer.writeStartElement(TAG_APP_VERSION.toString());
		writer.writeAttribute(ATTR_MAJOR_APP_VERSION.toString(), "" + version.getMajorRelease());
		writer.writeAttribute(ATTR_MINOR_APP_VERSION.toString(), "" + version.getMinorRelease());
		writer.writeAttribute(ATTR_PATCH_LEVEL.toString(), "" + version.getPatchLevel());
		writer.writeAttribute(ATTR_BUILD_NO.toString(), "" + version.getBuildNumber());
		if (!version.getType().equals(ApplicationType.STABLE)) {
			writer.writeAttribute(ATTR_APP_TYPE.toString(), "" + version.getType());
		}
		writer.writeEndElement();
	}
  
  
  /**
   * Reads an application version object from an XML stream.
   * 
   * @param element - the XML stream element containing the application version
   * @return an application version object containing the information from the XML tag
   * @throws XMLStreamException
   */
  public static ApplicationVersion read(StartElement element) throws XMLStreamException {
  	ApplicationVersion result = new ApplicationVersion();
  	result.setMajorRelease(XMLUtils.readIntAttr(element, ATTR_MAJOR_APP_VERSION, -1));
  	result.setMinorRelease(XMLUtils.readIntAttr(element, ATTR_MINOR_APP_VERSION, -1));
  	result.setPatchLevel(XMLUtils.readIntAttr(element, ATTR_PATCH_LEVEL, -1));
  	result.setBuildNumber(XMLUtils.readIntAttr(element, ATTR_BUILD_NO, -1));
  	ApplicationType type = ApplicationType.parseType(XMLUtils.readStringAttr(
  			element, ATTR_APP_TYPE, null));
  	if (type != null) {
  		result.setType(type);
  	}
  	return result;
  }
}
