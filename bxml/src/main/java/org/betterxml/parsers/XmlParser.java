package org.betterxml.parsers;

import java.io.Reader;

import org.betterxml.BetterXmlContentHandler;
import org.betterxml.BetterXmlException;
import org.betterxml.BetterXmlParser;
import org.betterxml.handlers.Sax2BetterXmlContentHandlerAdapter;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * This uses the an adapter to translate from sax events to BetterXmlContentHandler events.
 * 
 * @see BetterXmlContentHandler 
 * @see Sax2BetterXmlContentHandlerAdapter
 */
public class XmlParser implements BetterXmlParser {
	private XMLReader xmlReader;
	private BetterXmlContentHandler handler;
	
	/**
	 * Create a new xml parser.  
	 * @throws BetterXmlException
	 */
	public XmlParser() {
		try {
			xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setFeature("http://xml.org/sax/features/namespaces", true);
			xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
		} catch (SAXException e) {
			throw new BetterXmlException(e);
		} 
	}	

	public void parse(Reader reader) {
		ContentHandler saxHandler = new Sax2BetterXmlContentHandlerAdapter(handler);
		xmlReader.setContentHandler(saxHandler);
		
		try {
			xmlReader.setProperty("http://xml.org/sax/properties/lexical-handler", saxHandler);
			xmlReader.parse(new InputSource(reader));
		} catch (Exception e) {
			throw new BetterXmlException(e);
		}
		
	}

	public BetterXmlContentHandler getContentHandler() {
		return handler;
	}

	public BetterXmlParser setContentHandler(BetterXmlContentHandler handler) {
		this.handler = handler;
		return this;
	}
}
