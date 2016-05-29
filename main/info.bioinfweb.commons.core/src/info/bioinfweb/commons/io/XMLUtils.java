/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2016  Ben Stöver
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
package info.bioinfweb.commons.io;


import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;



/**
 * Tools to work with XML data.
 * 
 * @author Ben St&ouml;ver
 */
public class XMLUtils {
	public static final String QNAME_SEPARATOR = ":";
	
	public static final String ATTR_XSI_NAMESPACE = XMLConstants.XMLNS_ATTRIBUTE + ":xsi";
	public static final String ATTR_SCHEMA_LOCATION_LOCAL_PART = "schemaLocation";
	public static final String ATTR_SCHEMA_LOCATION = "xsi:" + ATTR_SCHEMA_LOCATION_LOCAL_PART;
	
	public static final Pattern XSD_FILE_NAME_PATTERN = Pattern.compile(".+/(.+)\\.xsd\\s*", Pattern.CASE_INSENSITIVE);
	
	
  /**
   * Reads all events from the reader until one more end element than start elements is found.
   * 
   * @param reader
   * @return <code>true</code> if anything else than ignorable whitespace is found before the next end element
   * @throws XMLStreamException
   */
  public static boolean reachElementEnd(XMLEventReader reader) throws XMLStreamException {
  	boolean result = false;
    XMLEvent event = reader.nextEvent();
		while (event.getEventType() != XMLStreamConstants.END_ELEMENT) {
			if ((event.getEventType() != XMLStreamConstants.SPACE) && 
					(event.getEventType() != XMLStreamConstants.CHARACTERS)) {
				
				result = true;
			}
			else if (event.getEventType() == XMLStreamConstants.CHARACTERS) {
				result = result || !event.asCharacters().getData().trim().equals("");
			}
			
		  if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
	 	    reachElementEnd(reader);
		  }
	    event = reader.nextEvent();
		}
		return result;
  }
  
  
  public static String readStringAttr(StartElement element, QName name, String oldValue) {
		Attribute attr = element.getAttributeByName(name);
		if (attr == null) {
			return oldValue;
		}
		else {
			return attr.getValue();
		}
	}
	
	
	public static boolean readBooleanAttr(StartElement element, QName name, boolean oldValue) {
		Attribute attr = element.getAttributeByName(name);
		if (attr == null) {
			return oldValue;
		}
		else {
			return Boolean.parseBoolean(attr.getValue());
		}
	}
	
	
	public static int readIntAttr(StartElement element, QName name, int oldValue) {
		Attribute attr = element.getAttributeByName(name);
		if (attr == null) {
			return oldValue;
		}
		else {
			return Integer.parseInt(attr.getValue());
		}
	}
	
	
	public static float readFloatAttr(StartElement element, QName name, float oldValue) {
		Attribute attr = element.getAttributeByName(name);
		if (attr == null) {
			return oldValue;
		}
		else {
			return Float.parseFloat(attr.getValue());
		}
	}
	
	
	public static double readDoubleAttr(StartElement element, QName name, double oldValue) {
		Attribute attr = element.getAttributeByName(name);
		if (attr == null) {
			return oldValue;
		}
		else {
			return Double.parseDouble(attr.getValue());
		}
	}
	
	
	public static Color readColorAttr(StartElement element, QName name, Color oldValue) {
		Attribute attr = element.getAttributeByName(name);
		if (attr == null) {
			return oldValue;
		}
		else {
			String value = attr.getValue();
			int red = Integer.parseInt(value.subSequence(1, 3).toString(), 16);
			int green = Integer.parseInt(value.subSequence(3, 5).toString(), 16);
			int blue = Integer.parseInt(value.subSequence(5, 7).toString(), 16);
			return new Color(red, green, blue);
		}
	}
	
	
	/**
	 * Reads the characters from the next event of the stream. If the next event in the stream is not a
	 * character event an empty string ("") is returned.
	 * @param reader - the stream to read the next element from
	 * @return
	 * @throws XMLStreamException
	 */
	public static String readCharactersAsString(XMLEventReader reader) throws XMLStreamException {
		StringBuffer result = new StringBuffer();
		while (reader.peek().getEventType() == XMLStreamConstants.CHARACTERS) {
			result.append(reader.nextEvent().asCharacters().getData());
		}
		return result.toString();
	}
	
	
	/**
	 * Reads the characters from the next event of the stream a parses them as a {@code boolean} value.
	 * @param reader - the stream to read the next element from
	 * @return
	 * @throws XMLStreamException
	 */
	public static boolean readCharactersAsBoolean(XMLEventReader reader) throws XMLStreamException {
		return Boolean.parseBoolean(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the stream a parses them as an {@code int} value.
	 * @param reader - the stream to read the next element from
	 * @return
	 * @throws XMLStreamException
	 */
	public static int readCharactersAsInt(XMLEventReader reader) throws XMLStreamException {
		return Integer.parseInt(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the stream a parses them as an {@code long} value.
	 * @param reader - the stream to read the next element from
	 * @return
	 * @throws XMLStreamException
	 */
	public static long readCharactersAsLong(XMLEventReader reader) throws XMLStreamException {
		return Long.parseLong(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the stream a parses them as a {@code float} value.
	 * @param reader - the stream to read the next element from
	 * @return
	 * @throws XMLStreamException
	 */
	public static float readCharactersAsFloat(XMLEventReader reader) throws XMLStreamException {
		return Float.parseFloat(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the stream a parses them as a {@code double} value.
	 * @param reader - the stream to read the next element from
	 * @return
	 * @throws XMLStreamException
	 */
	public static double readCharactersAsDouble(XMLEventReader reader) throws XMLStreamException {
		return Double.parseDouble(readCharactersAsString(reader));
	}
	
	
	/**
	 * Returns the root element of the XML file that can be read from the specified stream and closes it.
	 * @param stream - the stream to read from (closed after the execution of this method)
	 * @return the root element of the XML data
	 * @throws IOException if the stream cannot be closed
	 * @throws XMLStreamException if the XML format is invalid
	 */
	public static StartElement readRootElement(InputStream stream) throws IOException, XMLStreamException {
		XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(stream);
		
		StartElement result = null;
		try {
			XMLEvent event;
			while (reader.hasNext() && (result == null)) {
	      event = reader.nextEvent();
	      if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
	      	result = event.asStartElement();
	      }
	    }
		}
		finally {
	    reader.close();
	    stream.close();
		}
		return result;
	}
	
	
	public static StartElement readRootElement(File file) throws IOException, XMLStreamException {
		return readRootElement(new FileInputStream(file));
	}
	
	
	/**
	 * Adds the <code>xmlns</code> attribute to the start element which was last written to the specified 
	 * writer.
	 * 
	 * @param writer - the writer which generates the XML output
	 * @param namespaceURI - the URI of the namespace (e.g. <i>http://www.w3.org/1999/xhtml</a>)
	 * @throws XMLStreamException
	 */
	public static void writeNamespaceAttr(XMLStreamWriter writer, 
			String namespaceURI) throws XMLStreamException {
		
		writer.writeAttribute(XMLConstants.XMLNS_ATTRIBUTE, namespaceURI);
	}
	
	
	/**
	 * Adds the attributes <code>xmlns</code> <code>xmlns:xsi</code> and <code>xsi:schemaLocation</code> 
	 * and the according values to the start element which was last written to the specified writer.
	 * 
	 * @param writer - the writer which generates the XML output
	 * @param namespaceURI - the URI of the namespace (e.g. <i>http://www.w3.org/1999/xhtml</a>)
	 * @param xsdURI - the URI which specifies the XML schema (xsd) which defines the XML code under the
	 *        last start element
	 * @throws XMLStreamException
	 */
	public static void writeNamespaceXSDAttr(XMLStreamWriter writer, 
			String namespaceURI, String xsdURI) throws XMLStreamException {
		
		writeNamespaceAttr(writer, namespaceURI);
		writer.writeAttribute(ATTR_XSI_NAMESPACE, XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
		writer.writeAttribute(ATTR_SCHEMA_LOCATION, namespaceURI + " " + xsdURI);
	}
	
	
	/**
	 * Extracts the XSD file name from the <code>xsi:schemaLocation</code> attribute (e.g. <code>1.1</code> from 
	 * <code>http://bioinfweb.info/xmlns/xtg http://bioinfweb.info/xmlns/xtg/1.1.xsd</code>). 
	 * @param startElement - the element containing the <code>xsi:schemaLocation</code> attribute 
	 * @return the file name of the referenced XSD file or <code>null</code> if <code>startElement</code> has no 
	 *         <code>xsi:schemaLocation</code> attribute  
   * @throws InvalidXSDPathException if the path to the XSD file in malformed
	 */
	public static String extractXSDFileName(StartElement startElement) throws InvalidXSDPathException {
  	Attribute path = startElement.getAttributeByName(new QName(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, 
  			ATTR_SCHEMA_LOCATION_LOCAL_PART));
		if (path == null) {
			return null;
		}
		else {
			Matcher matcher = XSD_FILE_NAME_PATTERN.matcher(path.getValue());
			if (matcher.matches()) {
				return matcher.group(1);
			}
			else {
				throw new InvalidXSDPathException("The attrubute value \"" + path.getValue() + 
						"\" does not contain a valid path.");
			}
		}
	}
	
	
	/**
	 * Extracts the format version from the <code>xsi:schemaLocation</code> attribute (e.g. <code>1.1</code> from 
	 * <code>http://bioinfweb.info/xmlns/xtg http://bioinfweb.info/xmlns/xtg/1.1.xsd</code>). The name of the XSD
	 * file has to consist of the format version where major and minor version are separated by a dot. 
	 * @param startElement - the element containing the <code>xsi:schemaLocation</code> attribute 
	 * @return the format version or <code>null</code> if <code>startElement</code> has no 
	 *         <code>xsi:schemaLocation</code> attribute  
   * @throws InvalidXSDPathException if the path to the XSD file in malformed
	 */
	public static FormatVersion extractFormatVersion(StartElement startElement) throws InvalidXSDPathException {
		String version = extractXSDFileName(startElement);
		if (version == null) {
			return null;
		}
		else {
			String[] parts = version.split("\\.");
			if (parts.length == 2) {
				try {
					return new FormatVersion(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
				}
				catch (NumberFormatException e) {}
			}
			
			throw new InvalidXSDPathException("The XSD file name \"" + version + 
    			"\" does not specify a valid format version.");
		}
	}
}
