package info.bioinfweb.commons.testing;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;



/**
 * Offers assert methods to test events from an {@link XMLEventReader}.
 * 
 * @author Sarah Wiechers
 * @author Ben St&ouml;ver
 * @since 2.0.0
 */
public class XMLAssert {
	public static void assertStartDocument(XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		assertEquals(XMLStreamConstants.START_DOCUMENT, reader.nextEvent().getEventType());
	}
	
	
	public static void assertEndDocument(XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		assertEquals(XMLStreamConstants.END_DOCUMENT, reader.nextEvent().getEventType());
	}
	
	
	public static StartElement assertStartElement(QName expectedElement, XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());
		XMLEvent event = reader.nextEvent();
		
		assertEquals(XMLStreamConstants.START_ELEMENT, event.getEventType());
		StartElement element = event.asStartElement();
		
		assertEquals(expectedElement, element.getName());
		
		return element;
	}
	
	
	public static String assertAttribute(QName expectedAttributeName, String expectedAttributeValue, StartElement element) {
		Attribute attribute = element.getAttributeByName(expectedAttributeName);
		
		assertTrue((attribute != null));
		
		if (expectedAttributeValue != null) {
			assertEquals(expectedAttributeValue, attribute.getValue());
		}
		
		return attribute.getValue();
	}
	
	
	public static String assertAttribute(QName expectedAttributeName, StartElement element) {
		return assertAttribute(expectedAttributeName, null, element);
	}
	
	
	public static void assertAttributeCount(int expectedCount, StartElement element) {
		int count = 0;

		@SuppressWarnings("unchecked")
		Iterator<Attribute> attributes = element.getAttributes();
		while (attributes.hasNext()) {
			attributes.next();
			count++;
		}
		
		assertEquals(expectedCount, count);
	}
	
	
	public static void assertDefaultNamespace(QName expectedNameSpace, StartElement element) {
		NamespaceContext nameSpace = element.getNamespaceContext();
		
		assertTrue((nameSpace != null));	
		assertEquals(expectedNameSpace.getNamespaceURI(), nameSpace.getNamespaceURI(""));
	}
	
	
	public static void assertNamespace(QName expectedNameSpace, StartElement element) {
		NamespaceContext nameSpace = element.getNamespaceContext();
		
		assertTrue((nameSpace != null));
		assertEquals(expectedNameSpace.getPrefix(), nameSpace.getPrefix(expectedNameSpace.getNamespaceURI()));
		assertEquals(expectedNameSpace.getNamespaceURI(), nameSpace.getNamespaceURI(expectedNameSpace.getPrefix()));
	}
	
	
	public static void assertNameSpaceCount(int expectedCount, StartElement element) {
		int count = 0;

		@SuppressWarnings("unchecked")
		Iterator<Namespace> nameSpaces = element.getNamespaces();
		while (nameSpaces.hasNext()) {
			nameSpaces.next();
			count++;
		}
		
		assertEquals(expectedCount, count);
	}
	
	
	public static void assertEndElement(QName expectedElement, XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		XMLEvent event = reader.nextEvent();
		
		assertEquals(XMLStreamConstants.END_ELEMENT, event.getEventType());
		assertEquals(expectedElement, event.asEndElement().getName());
	}
	
	
	public static void assertCharactersEvent(String expectedCharacters, XMLEventReader reader) throws XMLStreamException {
		XMLEvent event;
		StringBuffer characters = new StringBuffer();
		
		do {
			assertTrue(reader.hasNext());
			event = reader.nextEvent();
			assertEquals(XMLStreamConstants.CHARACTERS, event.getEventType());
			characters.append(event.asCharacters().getData());
		} while (reader.peek().getEventType() == XMLStreamConstants.CHARACTERS);
		
		assertEquals(expectedCharacters, characters.toString());
	}
	
	
	public static void assertCommentEvent(String expectedElement, XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		XMLEvent event = reader.nextEvent();
		
		assertEquals(XMLStreamConstants.COMMENT, event.getEventType());
		assertEquals(expectedElement, ((Comment)event).getText());
	}
	
	
	public static void assertShortElement(QName expectedElement, String expectedContent, XMLEventReader reader) throws XMLStreamException {
		assertStartElement(expectedElement, reader);
		
		if (expectedContent != null) {
			assertCharactersEvent(expectedContent, reader);
		}
		else {
			XMLEvent event;
			
			do {
				assertTrue(reader.hasNext());
				event = reader.nextEvent();
				assertEquals(XMLStreamConstants.CHARACTERS, event.getEventType());				
			} while (reader.peek().getEventType() == XMLStreamConstants.CHARACTERS);
		}
		
		assertEndElement(expectedElement, reader);
	}
	
	
	/**
	 * This method can e.g. be used to assert the presence of an XML elements that only contain an ID as content.
	 * 
	 * @param expectedElement the expected name of the XML element
	 * @param reader the reader used by the test method
	 * @throws XMLStreamException
	 */
	public static void assertShortElement(QName expectedElement, XMLEventReader reader) throws XMLStreamException {
		assertShortElement(expectedElement, null, reader);
	}
}
