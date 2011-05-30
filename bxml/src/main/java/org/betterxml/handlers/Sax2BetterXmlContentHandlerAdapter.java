package org.betterxml.handlers;

import org.betterxml.BetterXmlContentHandler;
import org.betterxml.xir.XIRDataObjectException;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.LexicalHandler;


/**
 * 
 * @author gkt, mbone
 * 
 * This handler class is responsible for turning SAX(2) events into BetterXML
 * content handler events.
 *
 */
public class Sax2BetterXmlContentHandlerAdapter implements ContentHandler, ErrorHandler, LexicalHandler {
	
	private BetterXmlContentHandler xirHandler;
	
	public Sax2BetterXmlContentHandlerAdapter(BetterXmlContentHandler xirHandler) {
		this.xirHandler = xirHandler;
	}

	/*
	 * Everything below here until the next comment are implementations of
	 * SAX2 interface methods--all translate and call the appropriate XIRContentHandler methods
	 */
	public void characters(char[] ch, int start, int length) throws SAXException {
		xirHandler.characters(length, new String(ch, start, length));
	}

	public void endDocument() throws SAXException {
		xirHandler.endDocument();
	}

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

	public void startDocument() throws SAXException {
		xirHandler.startDocument();
	}

	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException, XIRDataObjectException {
		xirHandler.startElement(uri, localName, qName, atts.getLength());
		
		for(int i=0; i < atts.getLength(); i++) {
			//TODO what is this ternary operator doing, is it necessary?
			String attrUri = atts.getURI(i) == "" ? uri : atts.getURI(i);
			xirHandler.attribute(attrUri, atts.getLocalName(i), atts.getQName(i), atts.getValue(i));
		}
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		xirHandler.startPrefixMapping(prefix, uri);
	}

	/* TODO: Implement ErrorHandler methods...eventually
	 */
	public void error(SAXParseException exception) throws SAXException {
		System.out.println(exception);
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		System.out.println(exception);		
	}

	public void warning(SAXParseException exception) throws SAXException {
		System.out.println(exception);		
	}
	
	public void setDocumentLocator(Locator locator) {
		// TODO We could eventually keep XIR records for source document "location" information. Not high priority but possibly useful!
	}
	
	public void comment(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
	}
	
	public void endCDATA() throws SAXException {
		// TODO Auto-generated method stub
	}
	
	public void endDTD() throws SAXException {
		// TODO Auto-generated method stub
	}
	
	public void endEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
	}
	
	public void startCDATA() throws SAXException {
		// TODO Auto-generated method stub
	}
	
	public void startDTD(String name, String publicId, String systemId) throws SAXException {
		// TODO Auto-generated method stub
	}
	
	public void startEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
	}
	
}
