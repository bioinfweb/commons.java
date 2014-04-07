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
