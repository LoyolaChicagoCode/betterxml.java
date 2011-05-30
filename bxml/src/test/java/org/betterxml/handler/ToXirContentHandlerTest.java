package org.betterxml.handler;

import static org.junit.Assert.*;

import java.io.StringWriter;

import org.betterxml.handlers.ToXMLContentHandler;
import org.betterxml.handlers.ToXirContentHandler;
import org.betterxml.xir.XDOBuilder;
import org.betterxml.xir.XIRDataObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the XirContentHandler to ensure that is producing the output for events.
 * This output should be consistent with that from the XIR Data objects generated
 * by the XDOBuilder class.
 * 
 * @author mbone
 */
public class ToXirContentHandlerTest {

	private final String nsURI = "http://betterxml.org/test";
	private final String name = "name";
	private final String qname = "q:name";
	private final String value = "value";
	
	private ToXirContentHandler handler;
	private StringWriter writer;
	private XIRDataObject xdo;
	
	@Before
	public void setUp() throws Exception {
		xdo = null;
		writer = new StringWriter();
		handler = new ToXirContentHandler(writer);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAttribute() {
		handler.attribute(nsURI, name, qname, value); 
		writer.flush();
		
		xdo = XDOBuilder.buildAttribute(nsURI, name, qname, value);
		assertEquals(xdo.toString().trim(), writer.toString().trim());
	}

	@Test
	public void testCharacters() {
		String characters = "these are some characters";
		handler.characters(characters.length(), characters); 
		writer.flush();
		
		xdo = XDOBuilder.buildCharacters(characters);
		assertEquals(xdo.toString().trim(), writer.toString().trim());
	}

	@Test
	public void testEndDocument() {
		handler.endDocument(); 
		writer.flush();
		
		xdo = XDOBuilder.buildEndDocument();
		assertEquals(xdo.toString().trim(), writer.toString().trim());
		
	}

	@Test
	public void testEndElement() {
		handler.endElement(nsURI, name, qname);
		writer.flush();
		
		xdo = XDOBuilder.buildEndElement(nsURI, name, qname);
		assertEquals(xdo.toString().trim(), writer.toString().trim());
	}

	@Test
	public void testPrefixMapping() {
		String prefix = "pre";
		handler.startPrefixMapping(prefix, nsURI);
		handler.endPrefixMapping(prefix);
		writer.flush();
		
		String expected  = "";
		xdo = XDOBuilder.buildStartPrefixMapping(prefix, nsURI);
		expected += xdo.toString().trim() + "\n\n";
		xdo = XDOBuilder.buildEndPrefixMapping(prefix, nsURI);
		expected += xdo.toString();
		assertEquals(expected.trim(), writer.toString().trim());
	}

	@Test
	public void testProcessingInstruction() {
		String name = "instruct_name";
		String target = "instruct_target";
		handler.processingInstruction(name, target);
		writer.flush();

		xdo = XDOBuilder.buildProcessingInstruction(target, name);
		assertEquals(xdo.toString().trim(), writer.toString().trim());
	}

	@Test
	public void testSkippedEntity() {
		handler.skippedEntity(name);
		writer.flush();
		
		xdo = XDOBuilder.buildSkippedEntity(name); 
		assertEquals(xdo.toString().trim(), writer.toString().trim());
	}

	@Test
	public void testStartDocument() {
		handler.startDocument();
		writer.flush();
		
		xdo = XDOBuilder.buildStartDocument();
		assertEquals(xdo.toString().trim(), writer.toString().trim());
	}

	@Test
	public void testStartElement() {
		handler.startElement(nsURI, name, qname, 0);
		writer.flush();
		
		xdo = XDOBuilder.buildStartElement(nsURI, name, qname, 0);
		assertEquals(xdo.toString().trim(), writer.toString().trim());
	}

	@Test
	public void testWhitespace() {
		String whitespace = "  \t \n\n  ";
		handler.whitespace(whitespace.length(), whitespace);
		
		xdo = XDOBuilder.buildWhitespace(whitespace);
		assertEquals(xdo.toString().trim(), writer.toString().trim());
	}

}
