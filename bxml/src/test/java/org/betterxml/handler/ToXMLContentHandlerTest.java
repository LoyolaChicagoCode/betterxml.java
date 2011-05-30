package org.betterxml.handler;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.betterxml.handlers.ToXMLContentHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ToXMLContentHandlerTest {
	private final String nsURI = "http://betterxml.org/test";
	private final String name = "name";
	private final String qname = "q:name";
	private final String value = "value";
	
	private ToXMLContentHandler handler;
	private StringWriter writer;
	
	@Before
	public void setUp() throws Exception {
		writer = new StringWriter();
		handler = new ToXMLContentHandler(writer);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStartDocument() {
		String preamble = "this is an invalid preamble";
		handler.setPreamble(preamble);
		handler.startDocument(); writer.flush();
		
		assertEquals(preamble, writer.toString());
	}

	@Test
	public void testEndDocument() {
		handler.endDocument(); writer.flush();
		assertEquals("", writer.toString());
	}

	@Test
	public void testStartElementNoAttributes() {
		handler.startElement(nsURI, name, "", 0); writer.flush();
		
		String expected = String.format("<%s>", name);
		assertEquals(expected, writer.toString());
	}
	
	@Test
	public void testStartElementAttributes() {
		handler.startElement(nsURI, name, "", 1); 
		handler.attribute(nsURI, name, "", value); writer.flush();
		//TODO add in attributes
		String expected = String.format("<%s %s=\"%s\">", name, name, value);
		assertEquals(expected, writer.toString());
	}
	
	@Test
	public void testStartElementNoAttributesNS() {
		handler.startElement(nsURI, name, qname, 0); writer.flush();
		
		String expected = String.format("<%s>", qname);
		assertEquals(expected, writer.toString());
	}
	
	@Test
	public void testStartElementAttributesNS() {
		handler.startElement(nsURI, name, qname, 1); 
		handler.attribute(nsURI, name, qname, value); writer.flush();
		//TODO add in attributes
		String expected = String.format("<%s %s=\"%s\">", qname, qname, value);
		assertEquals(expected, writer.toString());
	}

	@Test
	public void testEndElementNS() {
		handler.endElement(nsURI, name, qname); writer.flush();
		
		String expected = String.format("</%s>", qname);
		assertEquals(expected, writer.toString());
	}
	
	@Test
	public void testEndElement() {
		handler.endElement(nsURI, name, ""); writer.flush();
		
		String expected = String.format("</%s>", name);
		assertEquals(expected, writer.toString());
	}

	@Test
	public void testCharacters() {
		String characters = "this is a test of the character broadcast system.";
		handler.characters(characters.length(), characters); writer.flush();
		
		assertEquals(characters, writer.toString());
	}

	@Test
	public void testProcessingInstruction() {
		String name = "name";
		String target = "target";
		handler.processingInstruction(name, target); writer.flush();
		
		String expected = String.format("<?%s %s?>", name, target);
		assertEquals(expected, writer.toString());
	}

	@Test
	public void testWhitespace() {
		String whitespace = "  \t\t\n\n  ";
		handler.whitespace(whitespace.length(), whitespace);
		
		assertEquals(whitespace, writer.toString());	
	}
	
	/* TODO implement tests
	 * @Test
	@Test
	public void testSkippedEntity() {
		fail("Not yet implemented");
	}
	
	public void testStartPrefixMapping() {
		fail("Not yet implemented");
	}

	@Test
	public void testEndPrefixMapping() {
		fail("Not yet implemented");
	}*/
}
