package info.bioinfweb.commons.testing;


import static org.junit.Assert.*;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
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
	
	
	public static void assertStartDocument(XMLStreamReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());
		assertEquals(XMLStreamConstants.START_DOCUMENT, reader.next());
	}
	
	
	public static void assertEndDocument(XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		assertEquals(XMLStreamConstants.END_DOCUMENT, reader.nextEvent().getEventType());
	}
	
	
	public static void assertEndDocument(XMLStreamReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		assertEquals(XMLStreamConstants.END_DOCUMENT, reader.next());
	}
	
	
	public static StartElement assertStartElement(QName expectedElement, XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());
		XMLEvent event = reader.nextEvent();
		
		assertEquals(XMLStreamConstants.START_ELEMENT, event.getEventType());
		StartElement element = event.asStartElement();
		
		assertEquals(expectedElement, element.getName());
		
		return element;
	}
	
	
	public static void assertStartElement(QName expectedElement, XMLStreamReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());
		int eventType = reader.next();
		
		assertEquals(XMLStreamConstants.START_ELEMENT, eventType);		
		assertEquals(expectedElement, reader.getName());
	}
	
	
	public static String assertAttribute(QName expectedAttributeName, String expectedAttributeValue, StartElement element) {
		Attribute attribute = element.getAttributeByName(expectedAttributeName);
		
		assertNotNull(attribute);	
		assertEquals(expectedAttributeName, attribute.getName());
		
		if (expectedAttributeValue != null) {
			assertEquals(expectedAttributeValue, attribute.getValue());
		}
		
		return attribute.getValue();
	}
	
	
	public static void assertAttribute(QName expectedAttributeName, String expectedAttributeValue, int attributeIndex, XMLStreamReader reader) {
		assertTrue(reader.getAttributeCount() > attributeIndex);
		assertEquals(expectedAttributeName, reader.getAttributeName(attributeIndex));
		
		if (expectedAttributeValue != null) {
			assertEquals(expectedAttributeValue, reader.getAttributeValue(attributeIndex));
		}
	}
	
	
	public static String assertAttribute(QName expectedAttributeName, StartElement element) {
		return assertAttribute(expectedAttributeName, null, element);
	}
	
	
	public static void assertAttribute(QName expectedAttributeName, int attributeIndex, XMLStreamReader reader) {
		assertAttribute(expectedAttributeName, null, attributeIndex, reader);
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
	
	
	public static void assertAttributeCount(int expectedCount, XMLStreamReader reader) {
		assertEquals(expectedCount, reader.getAttributeCount());
	}
	
	
	public static void assertDefaultNamespace(QName expectedNameSpace, StartElement element) {
		NamespaceContext nameSpace = element.getNamespaceContext();
		
		assertNotNull(nameSpace);	
		assertEquals(expectedNameSpace.getNamespaceURI(), nameSpace.getNamespaceURI(""));
	}
	
	
	public static void assertDefaultNamespace(QName expectedNameSpace, int index, XMLStreamReader reader) {
		assertEquals(expectedNameSpace.getNamespaceURI(), reader.getNamespaceURI(index));
		assertEquals("", reader.getNamespacePrefix(index));	
	}
	
	
	public static String assertNamespace(QName expectedNameSpace, boolean assertPrefix, StartElement element) {
		NamespaceContext nameSpace = element.getNamespaceContext();
		
		assertNotNull(nameSpace);
		assertNotNull(expectedNameSpace.getNamespaceURI());
		
		if (assertPrefix) {
			assertEquals(expectedNameSpace.getPrefix(), nameSpace.getPrefix(expectedNameSpace.getNamespaceURI()));
			assertEquals(expectedNameSpace.getNamespaceURI(), nameSpace.getNamespaceURI(expectedNameSpace.getPrefix()));
		}
		else {
			String prefix = nameSpace.getPrefix(expectedNameSpace.getNamespaceURI());
			assertNotNull(prefix);
			assertEquals(expectedNameSpace.getNamespaceURI(), nameSpace.getNamespaceURI(prefix));
		}
		
		return nameSpace.getPrefix(expectedNameSpace.getNamespaceURI());
	}
	
	
	public static String assertNamespace(QName expectedNameSpace, boolean assertPrefix, int index, XMLStreamReader reader) {
		String prefix = null;
		
		assertEquals(expectedNameSpace.getNamespaceURI(), reader.getNamespaceURI(index));
		
		if (assertPrefix) {
			prefix = reader.getNamespacePrefix(index);
			assertEquals(expectedNameSpace.getPrefix(), prefix);
		}		
		
		return prefix;
	}
	
	
	public static void assertNamespaceCount(int expectedCount, StartElement element) {
		int count = 0;

		@SuppressWarnings("unchecked")
		Iterator<Namespace> nameSpaces = element.getNamespaces();
		while (nameSpaces.hasNext()) {
			nameSpaces.next();
			count++;
		}
		
		assertEquals(expectedCount, count);
	}
	
	
	public static void assertNamespaceCount(int expectedCount, XMLStreamReader reader) {
		assertEquals(expectedCount, reader.getNamespaceCount());
	}
	
	
	public static void assertEndElement(QName expectedElement, XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		XMLEvent event = reader.nextEvent();
		
		assertEquals(XMLStreamConstants.END_ELEMENT, event.getEventType());
		assertEquals(expectedElement, event.asEndElement().getName());
	}
	
	
	public static void assertEndElement(QName expectedElement, XMLStreamReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());
		int eventType = reader.next();
		
		assertEquals(XMLStreamConstants.END_ELEMENT, eventType);		
		assertEquals(expectedElement, reader.getName());
	}
	
	
	public static void assertCharactersEvent(String expectedCharacters, XMLEventReader reader) throws XMLStreamException {
		XMLEvent event;
		StringBuffer characters = new StringBuffer();
		
		do {
			assertTrue(reader.hasNext());
			event = reader.nextEvent();
			assertEquals(XMLStreamConstants.CHARACTERS, event.getEventType());
			characters.append(event.asCharacters().getData());
		} while (reader.hasNext() && reader.peek().getEventType() == XMLStreamConstants.CHARACTERS);
		
		assertEquals(expectedCharacters, characters.toString());
	}
	
	
	public static void assertCharactersEvent(String expectedCharacters, XMLStreamReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());
		reader.next();
		assertTrue(reader.hasText());			
		assertEquals(expectedCharacters, reader.getText());
	}
	
	
	public static void assertCommentEvent(String expectedElement, XMLEventReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		XMLEvent event = reader.nextEvent();
		
		assertEquals(XMLStreamConstants.COMMENT, event.getEventType());
		assertEquals(expectedElement, ((Comment)event).getText());
	}
	
	
	public static void assertCommentEvent(String expectedElement, XMLStreamReader reader) throws XMLStreamException {
		assertTrue(reader.hasNext());		
		assertEquals(XMLStreamConstants.COMMENT, reader.next());
		assertEquals(expectedElement, reader.getText());
	}
	
	
	public static void assertShortElement(QName expectedElement, String expectedContent, XMLEventReader reader) throws XMLStreamException {
		StartElement element = assertStartElement(expectedElement, reader);
		assertAttributeCount(0, element);
		
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
	
	
	public static void assertShortElement(QName expectedElement, String expectedContent, XMLStreamReader reader) throws XMLStreamException {
		assertStartElement(expectedElement, reader);
		assertAttributeCount(0, reader);
		
		if (expectedContent != null) {
			assertEquals(expectedContent, reader.getElementText());
		}
		else {
			reader.getElementText();  // Character content is skipped
		}
		
		assertEquals(XMLStreamConstants.END_ELEMENT, reader.getEventType());  // Reader is already positioned at end element
		assertEquals(expectedElement, reader.getName());
	}
	
	
	/**
	 * This method can be used to assert the presence of an XML element that only contains character content, 
	 * if the exact content should not be asserted.
	 * 
	 * @param expectedElement the expected name of the XML element
	 * @param reader the reader used by the test method
	 * @throws XMLStreamException
	 */
	public static void assertShortElement(QName expectedElement, XMLEventReader reader) throws XMLStreamException {
		assertShortElement(expectedElement, null, reader);
	}
	
	
	public static void assertShortElement(QName expectedElement, XMLStreamReader reader) throws XMLStreamException {
		assertShortElement(expectedElement, null, reader);
	}
	
	
	public static void assertEmptyElement(QName expectedElement, XMLEventReader reader) throws XMLStreamException {
		StartElement element = assertStartElement(expectedElement, reader);
		assertAttributeCount(0, element);
		assertEndElement(expectedElement, reader);
	}
	
	
	public static void assertEmptyElement(QName expectedElement, XMLStreamReader reader) throws XMLStreamException {
		assertStartElement(expectedElement, reader);
		assertAttributeCount(0, reader);
		assertEndElement(expectedElement, reader);
	}
}
