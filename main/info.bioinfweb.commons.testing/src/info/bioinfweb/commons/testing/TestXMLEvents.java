package info.bioinfweb.commons.testing;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.XMLEvent;



public class TestXMLEvents {
	
	
	public static void assertStartDocument(XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		assertEquals(XMLStreamConstants.START_DOCUMENT, reader.nextEvent().getEventType());
	}
	
	
	public static void assertEndDocument(XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		assertEquals(XMLStreamConstants.END_DOCUMENT, reader.nextEvent().getEventType());
	}
	
	
	public static void assertStartElement(QName expectedElement, XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());
		XMLEvent event = reader.nextEvent();
		
		assertEquals(XMLStreamConstants.START_ELEMENT, event.getEventType());
		assertEquals(expectedElement, event.asStartElement().getName());
		//TODO assert Attributes
	}
	
	
	public static void assertEndElement(QName expectedElement, XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		XMLEvent event = reader.nextEvent();
		
		assertEquals(XMLStreamConstants.END_ELEMENT, event.getEventType());
		assertEquals(expectedElement, event.asEndElement().getName());
	}
	
	
	public static void assertCharactersEvent(String expectedElement, XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		XMLEvent event = reader.nextEvent();
		
		assertEquals(XMLStreamConstants.CHARACTERS, event.getEventType());
		assertEquals(expectedElement, event.asCharacters().getData());
	}
	
	
	public static void assertCommentEvent(String expectedElement, XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		XMLEvent event = reader.nextEvent();
		
		assertEquals(XMLStreamConstants.COMMENT, event.getEventType());
		assertEquals(expectedElement, ((Comment)event).getText());
	}
	
	
	public static void assertShortElement(QName expectedElement, String expectedContent, XMLEventReader reader) throws XMLStreamException {
		assertStartElement(expectedElement, reader);
		assertCharactersEvent(expectedContent, reader);
		assertEndElement(expectedElement, reader);
	}
}
