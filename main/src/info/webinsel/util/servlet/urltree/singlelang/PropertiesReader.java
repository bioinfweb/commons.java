package info.webinsel.util.servlet.urltree.singlelang;


import info.webinsel.util.io.XMLUtils;
import info.webinsel.util.servlet.sqlproperties.SQLXMLReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;



public class PropertiesReader implements SingleLangXMLConstants {
	private XMLEventReader reader;
	private SingleLangProperties properties;
	
	
	private void read404() throws XMLStreamException {
    XMLEvent event = reader.nextEvent();
    while (event.getEventType() != XMLStreamConstants.END_ELEMENT) {
      if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
      	StartElement element = event.asStartElement();
        if (element.getName().equals(TAG_HEAD)) {
        	properties.getError404().setHead(reader.nextEvent().asCharacters().getData());
        	reader.nextEvent();  // END_ELEMENT überspringen
        }
        else if (element.getName().equals(TAG_ON_LOAD)) {
        	properties.getError404().setOnLoad(reader.nextEvent().asCharacters().getData());
        	reader.nextEvent();  // END_ELEMENT überspringen
        }
        else if (element.getName().equals(TAG_BODY)) {
        	properties.getError404().setBody(reader.nextEvent().asCharacters().getData());
        	reader.nextEvent();  // END_ELEMENT überspringen
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
        if (element.getName().equals(TAG_HOST_NAME)) {
        	properties.setHostName(reader.nextEvent().asCharacters().getData());
        	reader.nextEvent();  // END_ELEMENT überspringen
        }
        else if (element.getName().equals(TAG_MAIN_JSP)) {
        	properties.setMainJSP(reader.nextEvent().asCharacters().getData());
        	reader.nextEvent();  // END_ELEMENT überspringen
        }
        else if (element.getName().equals(TAG_DB)) {
        	SQLXMLReader.readDatabase(reader, properties);
        	reader.nextEvent();  // END_ELEMENT überspringen
        }
        else if (element.getName().equals(TAG_404)) {
        	read404();
        	reader.nextEvent();  // END_ELEMENT überspringen
        }
        else {  // evtl. zusätzlich vorhandenes Element, dass nicht gelesen wird
          XMLUtils.reachElementEnd(reader);  // evtl. zusätzlich vorhandenes Element, dass nicht gelesen wird
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
          if (element.getName().equals(TAG_ROOT)) {
        	  readRoot();
          	reader.nextEvent();  // END_ELEMENT überspringen
          }
          else {
            XMLUtils.reachElementEnd(reader);  // evtl. zusätzlich vorhandenes Element, dass nicht gelesen wird
          }
          break;
      }
    }
  }
	
	
	public SingleLangProperties read(Reader r) throws XMLStreamException {
		reader = XMLInputFactory.newInstance().createXMLEventReader(r);
		properties = new SingleLangProperties();
		try{
			readDocument();
		}
		finally {
	    reader.close();
		}
		return properties;
  }
  
  
  public SingleLangProperties read(InputStream stream) throws XMLStreamException {
		reader = XMLInputFactory.newInstance().createXMLEventReader(stream);
		properties = new SingleLangProperties();
		try{
			readDocument();
		}
		finally {
	    reader.close();
		}
		return properties;
  }
  
  
  public SingleLangProperties read(String data) throws XMLStreamException {
  	return read(new StringReader(data));
  }
  
  
  public SingleLangProperties read(File file) throws XMLStreamException, FileNotFoundException {
  	return read(new FileInputStream(file));
  }
}
