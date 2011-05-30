package org.betterxml.xir;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * 
 * @author gkt
 * 
 * This handler class is responsible for turning SAX(2) events into XIR
 * records. In the startElement() method, a single SAX event generates at
 * least 1 + #attributes XIR records.
 *
 */
public class Sax2XIR implements ContentHandler, ErrorHandler {
	
	private Writer outputStream;
	private Map<String, String> uriMap;
	
	public Sax2XIR(Writer outputStream) {
		this.outputStream = outputStream;
		this.uriMap = new HashMap<String, String>();
	}
	
	public Sax2XIR(PrintStream out) {
		this(new OutputStreamWriter(out));
	}


	/**
	 * This internal method is 
	 * @param xdo
	 */
	private void writeXIR(XIRDataObject xdo)  {
		try {
			outputStream.write(xdo + "\n");
			// TODO: This is another ugly Java-ism? Why hasn't it been fixed after all these years?
			// TODO: Uncomment flush() to see the evil in action.
			outputStream.flush();
		} catch (IOException io) {
			System.out.println("write failure");
			throw new RuntimeException("I/O Error in underlying stream passed to Sax2XIR");
		}
	}

	/*
	 * Everything below here until the next comment are implementations of
	 * SAX2 interface methods--all responsible for generating XIR.
	 */
	public void characters(char[] ch, int start, int length) throws SAXException {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.characters, XIRDataObjectSubtypes.none);
		xdo.setBase64("cdata", new String(ch, start, length));
		xdo.setVerbatim("length", String.valueOf(length));
		writeXIR(xdo);
	}

	public void endDocument() throws SAXException {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.document, XIRDataObjectSubtypes.end);
		writeXIR(xdo);		
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.element, XIRDataObjectSubtypes.end);
		xdo.setVerbatim("ns", uri);
		xdo.setVerbatim("name", localName);
		writeXIR(xdo);
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.prefix_mapping, XIRDataObjectSubtypes.end);
		xdo.setVerbatim("prefix", prefix);
		xdo.setVerbatim("uri", uriMap.get(prefix));
		writeXIR(xdo);
		uriMap.remove(prefix);
	}

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.whitespace, XIRDataObjectSubtypes.none);
		xdo.setBase64("cdata", new String(ch, start, length));
		xdo.setVerbatim("length", String.valueOf(length));
		writeXIR(xdo);
	}

	public void processingInstruction(String target, String data) throws SAXException {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.processing_instruction, XIRDataObjectSubtypes.none);
		xdo.setVerbatim("target", target);
		xdo.setVerbatim("data", data);
		writeXIR(xdo);
	}

	public void skippedEntity(String name) throws SAXException {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.skipped_entity, XIRDataObjectSubtypes.none);
		xdo.setVerbatim("name", name);
		writeXIR(xdo);
	}

	public void startDocument() throws SAXException {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.document, XIRDataObjectSubtypes.begin);
		writeXIR(xdo);
	}

	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException, XIRDataObjectException {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.element, XIRDataObjectSubtypes.begin);
		xdo.setVerbatim("ns", uri);
		xdo.setVerbatim("name", localName);
		xdo.setVerbatim("attributes", String.valueOf(atts.getLength()));
		writeXIR(xdo);
		
		for (int i=0; i < atts.getLength(); i++) {
			xdo = new XIRDataObject(XIRDataObjectTypes.attribute, XIRDataObjectSubtypes.none);
			// TODO: Ugly ass kludge? WTH is going on here with namespaces?
			xdo.setVerbatim("ns", atts.getURI(i) == "" ? uri : atts.getURI(i));
			xdo.setVerbatim("name", atts.getLocalName(i));
			xdo.setVerbatim("value", atts.getValue(i));
			writeXIR(xdo);			
		}
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.prefix_mapping, XIRDataObjectSubtypes.begin);
		xdo.setVerbatim("prefix", prefix);
		xdo.setVerbatim("uri", uri);
		writeXIR(xdo);
		uriMap.put(prefix, uri);
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
	
}
