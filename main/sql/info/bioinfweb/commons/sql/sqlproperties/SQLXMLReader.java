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
package info.bioinfweb.commons.sql.sqlproperties;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import info.bioinfweb.commons.io.XMLUtils;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;



/**
 * Reads data necessary to connect to a SQL database. If the XML file contains 
 * nothing but this information you can use one of the <code>read()</code> methods. Otherwise
 * you can implement your own reader class which calls the 
 * <code>readDatabase()</code> method to read a certain part of the document.
 * 
 * @author Ben St&ouml;ver
 */
public class SQLXMLReader implements SQLXMLConstants {
	private XMLEventReader reader;
	private ConcreteSQLProperties properties;
	private QName rootTag = null;

	
	/**
	 * Creates a new instance.
	 * @param rootTag - the root tag of the document to read.
	 */
	public SQLXMLReader(QName rootTag) {
		super();
		this.rootTag = rootTag;
	}
	
	
	/**
	 * Reads information information about a database connection from a XML-document. The namespace is not checked.
	 * <pre>
	 * <Host>host</Host>
	 * <Name>name</Name> 
	 * <User>user</User>
	 * <Password>password</Password>
	 * <Driver>com.mysql.jdbc.Driver</Driver>
	 * <TablePrefix>tablePrefix</TablePrefix>
	 * </pre>
	 * @param reader
	 * @param properties
	 * @throws XMLStreamException
	 */
	public static void readDatabase(XMLEventReader reader, SQLProperties properties) throws XMLStreamException {
    XMLEvent event = reader.nextEvent();
    while (event.getEventType() != XMLStreamConstants.END_ELEMENT) {
      if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
      	StartElement element = event.asStartElement();
        if (element.getName().getLocalPart().equals(TAG_DB_HOST.getLocalPart())) {
        	properties.setDBHost(reader.nextEvent().asCharacters().getData());
        	reader.nextEvent();  // skip END_ELEMENT
        }
        else if (element.getName().getLocalPart().equals(TAG_DB_NAME.getLocalPart())) {
        	properties.setDBName(reader.nextEvent().asCharacters().getData());
        	reader.nextEvent();  // skip END_ELEMENT
        }
        else if (element.getName().getLocalPart().equals(TAG_DB_USER.getLocalPart())) {
        	properties.setDBUser(reader.nextEvent().asCharacters().getData());
        	reader.nextEvent();  // skip END_ELEMENT
        }
        else if (element.getName().getLocalPart().equals(TAG_DB_PASSWORD.getLocalPart())) {
        	properties.setDBPassword(reader.nextEvent().asCharacters().getData());
        	reader.nextEvent();  // skip END_ELEMENT
        }
        else if (element.getName().getLocalPart().equals(TAG_DB_DRIVER.getLocalPart())) {
        	properties.setDBDriver(reader.nextEvent().asCharacters().getData());
        	reader.nextEvent();  // skip END_ELEMENT
        }
        else if (element.getName().getLocalPart().equals(TAG_TABLE_PREFIX.getLocalPart())) {
        	properties.setTablePrefix(reader.nextEvent().asCharacters().getData());
        	reader.nextEvent();  // skip END_ELEMENT
        }
        else {  // evtl. zusätzlich vorhandenes Element, dass nicht gelesen wird
          XMLUtils.reachElementEnd(reader);  // evtl. zusätzlich vorhandenes Element, dass nicht gelesen wird
        }
      }
      event = reader.nextEvent();
    }
  }
	
	
	private void readRoot() throws XMLStreamException {
    XMLEvent event = reader.nextEvent();
    while (event.getEventType() != XMLStreamConstants.END_ELEMENT) {
      if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
      	StartElement element = event.asStartElement();
        if (element.getName().equals(TAG_DB)) {
        	readDatabase(reader, properties);
        	reader.nextEvent();  // END_ELEMENT öberspringen
        }
        else {  // evtl. zusötzlich vorhandenes Element, dass nicht gelesen wird
          XMLUtils.reachElementEnd(reader);  // evtl. zusötzlich vorhandenes Element, dass nicht gelesen wird
        }
      }
      event = reader.nextEvent();
    }
  }
	
	
	private void readDocument() throws XMLStreamException {
		XMLEvent event;
		while (reader.hasNext())
    {
      event = reader.nextEvent();
      switch (event.getEventType()) {
        case XMLStreamConstants.START_DOCUMENT:
        	// nothing to do
          break;
        case XMLStreamConstants.END_DOCUMENT:
          reader.close();
          return;
        case XMLStreamConstants.START_ELEMENT:
        	StartElement element = event.asStartElement();
          if (element.getName().equals(rootTag)) {
        	  readRoot();
          	reader.nextEvent();  // END_ELEMENT öberspringen
          }
          else {
            XMLUtils.reachElementEnd(reader);  // evtl. zusötzlich vorhandenes Element, dass nicht gelesen wird
          }
          break;
      }
    }
  }
	
	
	public ConcreteSQLProperties read(Reader r) throws XMLStreamException {
		reader = XMLInputFactory.newInstance().createXMLEventReader(r);
		properties = new ConcreteSQLProperties();
		try{
			readDocument();
		}
		finally {
	    reader.close();
		}
		return properties;
  }
  
  
  public ConcreteSQLProperties read(InputStream stream) throws XMLStreamException {
		reader = XMLInputFactory.newInstance().createXMLEventReader(stream);
		properties = new ConcreteSQLProperties();
		try{
			readDocument();
		}
		finally {
	    reader.close();
		}
		return properties;
  }
  
  
  public ConcreteSQLProperties read(String data) throws XMLStreamException {
  	return read(new StringReader(data));
  }
  
  
  public ConcreteSQLProperties read(File file) throws XMLStreamException, FileNotFoundException {
  	return read(new FileInputStream(file));
  }	
}
