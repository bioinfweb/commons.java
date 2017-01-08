/*
 * bioinfweb.commons.java - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2008-2011, 2013-2017  Ben Stöver, Sarah Wiechers
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
import javax.xml.stream.XMLStreamReader;
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
   * Reads all events from the {@link XMLEventReader} until one more end element than start elements is found.
   * 
   * @param reader the reader to read the events from
   * @return {@code true} if anything else than ignorable whitespace is found before the next end element
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
  
  
  /**
   * Reads all events from the {@link XMLStreamReader} until one more end element than start elements is found.
   * 
   * @param reader the reader to read the events from
   * @return {@code true} if anything else than ignorable whitespace is found before the next end element
   * @throws XMLStreamException
   */
  public static boolean reachElementEnd(XMLStreamReader reader) throws XMLStreamException {
  	boolean result = false;
		while (reader.next() != XMLStreamConstants.END_ELEMENT) {
			
			if ((reader.getEventType() != XMLStreamConstants.SPACE) && 
					(reader.getEventType() != XMLStreamConstants.CHARACTERS)) {
				
				result = true;
			}
			else if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
				result = result || !reader.getText().trim().equals("");
			}
			
		  if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
	 	    reachElementEnd(reader);
		  }
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
	
	
	public static boolean isCharacterType(int type) {
		return (type == XMLStreamConstants.CHARACTERS) || (type == XMLStreamConstants.CDATA) || (type == XMLStreamConstants.SPACE); 
	}
	
	
	/**
	 * Reads the characters from the next event of the {@link XMLEventReader}. If the next event is not a character event an empty 
	 * string ("") is returned. All encountered comment events will be skipped.
	 * <p>
	 * After calling this method the next element returned by {@code reader} will be the first non-character event after the 
	 * iterator's position before calling this method.
	 * 
	 * @param reader the reader to read the next element from
	 * @return the read characters
	 * @throws XMLStreamException if thrown by {@code reader}
	 */
	public static String readCharactersAsString(XMLEventReader reader) throws XMLStreamException {
		StringBuffer result = new StringBuffer();
		int type = reader.peek().getEventType();
		while (isCharacterType(type) || (type == XMLStreamConstants.COMMENT)) {
			XMLEvent event = reader.nextEvent();
			if (isCharacterType(event.getEventType())) {
				result.append(event.asCharacters().getData());  //TODO Should SPACE be ignored?
			}
			type = reader.peek().getEventType();
		}
		return result.toString();
	}
	
	
	/**
	 * Reads the characters from the next event of the {@link XMLStreamReader}. If the next event is not a character event an empty 
	 * string ("") is returned. All encountered comment events will be skipped.
	 * <p>
	 * After calling this method {@code reader} will be positioned at the first non-character event after the cursor's position 
	 * before calling this method.
	 * 
	 * @param reader the reader to read the next element from
	 * @return the read characters
	 * @throws XMLStreamException if thrown by {@code reader}
	 */
	public static String readCharactersAsString(XMLStreamReader reader) throws XMLStreamException {
		StringBuffer result = new StringBuffer();
		while (isCharacterType(reader.getEventType()) || (reader.getEventType() == XMLStreamConstants.COMMENT)) {
			if (isCharacterType(reader.getEventType())) {
				result.append(reader.getText());  //TODO Should SPACE be ignored?
			}
			reader.next();
		}
		return result.toString();
	}
	
	
	/**
	 * Reads the characters from the next event of the {@link XMLEventReader} a parses them as a {@code boolean} value.
	 * <p>
	 * After calling this method the next element returned by {@code reader} will be the first non-character event after the 
	 * iterator's position before calling this method.
	 * 
	 * @param reader the reader to read the next element from
	 * @return the read value
	 * @throws XMLStreamException if thrown by {@code reader}
	 */
	public static boolean readCharactersAsBoolean(XMLEventReader reader) throws XMLStreamException {
		return Boolean.parseBoolean(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the {@link XMLStreamReader} a parses them as a {@code boolean} value.
	 * <p>
	 * After calling this method {@code reader} will be positioned at the first non-character event after the cursor's position 
	 * before calling this method.
	 * 
	 * @param reader the reader to read the next element from
	 * @return the read value
	 * @throws XMLStreamException if thrown by {@code reader}
	 */
	public static boolean readCharactersAsBoolean(XMLStreamReader reader) throws XMLStreamException {
		return Boolean.parseBoolean(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the {@link XMLEventReader} a parses them as an {@code int} value.
	 * <p>
	 * After calling this method the next element returned by {@code reader} will be the first non-character event after the 
	 * iterator's position before calling this method.
	 * 
	 * @param reader the reader to read the next element from
	 * @return the parsed value
	 * @throws XMLStreamException if thrown by {@code reader}
	 * @throws NumberFormatException if no {@code int} can be parsed from the current characters
	 */
	public static int readCharactersAsInt(XMLEventReader reader) throws XMLStreamException {
		return Integer.parseInt(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the {@link XMLStreamReader} a parses them as a {@code int} value.
	 * <p>
	 * After calling this method {@code reader} will be positioned at the first non-character event after the cursor's position 
	 * before calling this method.
	 * 
	 * @param reader the reader to read the next element from
	 * @return the read value
	 * @throws XMLStreamException if thrown by {@code reader}
	 * @throws NumberFormatException if no {@code int} can be parsed from the current characters
	 */
	public static int readCharactersAsInt(XMLStreamReader reader) throws XMLStreamException {
		return Integer.parseInt(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the {@link XMLEventReader} a parses them as an {@code long} value.
	 * <p>
	 * After calling this method the next element returned by {@code reader} will be the first non-character event after the 
	 * iterator's position before calling this method.
	 * 
	 * @param reader the reader to read the next element from
	 * @return the parsed value
	 * @throws XMLStreamException if thrown by {@code reader}
	 * @throws NumberFormatException if no {@code long} can be parsed from the current characters
	 */
	public static long readCharactersAsLong(XMLEventReader reader) throws XMLStreamException {
		return Long.parseLong(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the {@link XMLStreamReader} a parses them as a {@code long} value.
	 * <p>
	 * After calling this method {@code reader} will be positioned at the first non-character event after the cursor's position 
	 * before calling this method.
	 * 
	 * @param reader the reader to read the next element from
	 * @return the read value
	 * @throws XMLStreamException if thrown by {@code reader}
	 * @throws NumberFormatException if no {@code long} can be parsed from the current characters
	 */
	public static long readCharactersAsLong(XMLStreamReader reader) throws XMLStreamException {
		return Long.parseLong(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the {@link XMLEventReader} a parses them as a {@code float} value.
	 * <p>
	 * After calling this method the next element returned by {@code reader} will be the first non-character event after the 
	 * iterator's position before calling this method.
	 * 
	 * @param reader the reader to read the next element from
	 * @return the parsed value
	 * @throws XMLStreamException if thrown by {@code reader}
	 * @throws NumberFormatException if no {@code float} can be parsed from the current characters
	 */
	public static float readCharactersAsFloat(XMLEventReader reader) throws XMLStreamException {
		return Float.parseFloat(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the {@link XMLStreamReader} a parses them as a {@code float} value.
	 * <p>
	 * After calling this method {@code reader} will be positioned at the first non-character event after the cursor's position 
	 * before calling this method.
	 * 
	 * @param reader the reader to read the next element from
	 * @return the read value
	 * @throws XMLStreamException if thrown by {@code reader}
	 * @throws NumberFormatException if no {@code float} can be parsed from the current characters
	 */
	public static float readCharactersAsFloat(XMLStreamReader reader) throws XMLStreamException {
		return Float.parseFloat(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the {@link XMLEventReader} a parses them as a {@code double} value.
	 * <p>
	 * After calling this method the next element returned by {@code reader} will be the first non-character event after the 
	 * iterator's position before calling this method.
	 * 
	 * @param reader the reader to read the next element from
	 * @return the parsed value
	 * @throws XMLStreamException if thrown by {@code reader}
	 * @throws NumberFormatException if no {@code double} can be parsed from the current characters
	 */
	public static double readCharactersAsDouble(XMLEventReader reader) throws XMLStreamException {
		return Double.parseDouble(readCharactersAsString(reader));
	}
	
	
	/**
	 * Reads the characters from the next event of the {@link XMLStreamReader} a parses them as a {@code double} value.
	 * <p>
	 * After calling this method {@code reader} will be positioned at the first non-character event after the cursor's position 
	 * before calling this method.
	 * 
	 * @param reader the reader to read the next element from
	 * @return the read value
	 * @throws XMLStreamException if thrown by {@code reader}
	 * @throws NumberFormatException if no {@code double} can be parsed from the current characters
	 */
	public static double readCharactersAsDouble(XMLStreamReader reader) throws XMLStreamException {
		return Double.parseDouble(readCharactersAsString(reader));
	}
	
	
	/**
	 * Returns the root element of the XML file that can be read from the specified stream and closes it.
	 * 
	 * @param stream the stream to read from (closed after the execution of this method)
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
	 * @param writer the writer which generates the XML output
	 * @param namespaceURI the URI of the namespace (e.g. <i>http://www.w3.org/1999/xhtml</i>)
	 * @throws XMLStreamException if fired by {@code writer}
	 */
	public static void writeNamespaceAttr(XMLStreamWriter writer, 
			String namespaceURI) throws XMLStreamException {
		
		writer.writeAttribute(XMLConstants.XMLNS_ATTRIBUTE, namespaceURI);
	}
	
	
	/**
	 * Adds the attributes <code>xmlns</code> <code>xmlns:xsi</code> and <code>xsi:schemaLocation</code> 
	 * and the according values to the start element which was last written to the specified writer.
	 * 
	 * @param writer the writer which generates the XML output
	 * @param namespaceURI the URI of the namespace (e.g. <i>http://www.w3.org/1999/xhtml</i>)
	 * @param xsdURI the URI which specifies the XML schema (xsd) which defines the XML code under the
	 *        last start element
	 * @throws XMLStreamException if fired by {@code writer}
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
	 * 
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
			try {
				return FormatVersion.parseFormatVersion(version);
			}
			catch (IllegalArgumentException e) {
				throw new InvalidXSDPathException("The XSD file name \"" + version + 
	    			"\" does not specify a valid format version.");
			}
		}
	}
	
	
	/**
	 * Writes a start element defined by a {@link QName} to an {@link XMLStreamWriter}.
	 * 
	 * @param writer the writer to write the start tag
	 * @param name the name of the tag
	 * @throws XMLStreamException thrown by the specified writer
	 */
	public static void writeStartElement(XMLStreamWriter writer, QName name) throws XMLStreamException {
		writer.writeStartElement(name.getPrefix(), name.getLocalPart(), name.getNamespaceURI());
	}
	
	
	/**
	 * Writes an attribute with a name defined by a {@link QName} to an {@link XMLStreamWriter}.
	 * 
	 * @param writer the writer to write the start tag
	 * @param name the name of the tag
	 * @param value the value of the attribute
	 * @throws IllegalStateException if the current state does not allow attribute writing 
	 * @throws XMLStreamException if the namespace URI has not been bound to a prefix and javax.xml.stream.isRepairingNamespaces 
	 *         has not been set to true
	 */
	public static void writeAttribute(XMLStreamWriter writer, QName name, String value) throws XMLStreamException, IllegalStateException {
		writer.writeAttribute(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart(), value);
	}
}
