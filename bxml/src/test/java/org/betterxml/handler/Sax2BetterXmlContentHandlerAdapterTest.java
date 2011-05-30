package org.betterxml.handler;


import org.betterxml.BetterXmlContentHandler;
import org.betterxml.handlers.Sax2BetterXmlContentHandlerAdapter;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xml.sax.helpers.AttributesImpl;

@RunWith(JMock.class)
public class Sax2BetterXmlContentHandlerAdapterTest {

	private Mockery context = new JUnit4Mockery();
	private Sax2BetterXmlContentHandlerAdapter saxHandler;
	private BetterXmlContentHandler handler;
	
	@Before
	public void setUp() throws Exception {
		handler = context.mock(BetterXmlContentHandler.class);
		saxHandler = new Sax2BetterXmlContentHandlerAdapter(handler);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCharacters() throws Exception {
		context.checking(new Expectations() {{
            one(handler).characters(4, "abCd");
        }});
		
        saxHandler.characters(new char[] {'a','b','C','d'}, 0, 4);
	}
	
	@Test
	public void testStartDocument() throws Exception {
		context.checking(new Expectations() {{
            one(handler).startDocument();
        }});
		
        saxHandler.startDocument();
	}
	
	@Test
	public void testEndDocument() throws Exception {
		context.checking(new Expectations() {{
            one(handler).endDocument();
        }});
		
        saxHandler.endDocument();
	}
	
	@Test
	public void testStartElementAndAttributes() throws Exception {
		final String nsURI = "myuri";
		final String name = "element";
		final String qname = "ns:element";
		final int numberOfAttrs = 1;
		AttributesImpl attributes = new AttributesImpl();
		attributes.addAttribute("uri", "name", "qname", "string", "value");
		
		context.checking(new Expectations() {{
			one(handler).startElement(nsURI, name, qname, numberOfAttrs);
			one(handler).attribute("uri", "name", "qname", "value");
        }});
		
        saxHandler.startElement(nsURI, name, qname, attributes);
	}

	/*

	public void endElement(String uri, String localName, String qName) throws SAXException {
		xirHandler.endElement(uri, localName, qName);
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		xirHandler.endPrefixMapping(prefix);
	}

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		xirHandler.whitespace(length, new String(ch, start, length));
	}

	public void processingInstruction(String target, String data) throws SAXException {
		xirHandler.processingInstruction(data, target);
	}

	public void skippedEntity(String name) throws SAXException {
		xirHandler.skippedEntity(name);
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		xirHandler.startPrefixMapping(prefix, uri);
	}*/
}
