package org.betterxml.handlers;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

import org.betterxml.BetterXmlContentHandler;
import org.betterxml.BetterXmlException;
import org.betterxml.xir.XDOBuilder;
import org.betterxml.xir.XIRDataObject;
/**
 * Outputs xir.  This content handler is mostly stateless (besides ns mappings) and will
 * therefore output invalid XIR if asked.  For example, this handler will happily print out
 * and start element and end element records with no attributes in between even if the start
 * element record specified 5 attributes.
 * 
 * @author mbone
 */
public class ToXirContentHandler implements BetterXmlContentHandler {

	//TODO this is too simplistic...we need a hashmap<string, Stack<String>> for nested elemeents that redefine the nsmapping
	private HashMap<String, String> namespaceMapping = new HashMap<String, String>();
	private Writer output;
	
	public ToXirContentHandler(Writer output) {
		this.output = output;
	}
	
	public void attribute(String nsURI, String name, String qname, String value) {
		writeXIR(XDOBuilder.buildAttribute(nsURI, name, qname, value));
	}

	public void characters(int length, String cdata) {
		writeXIR(XDOBuilder.buildCharacters(cdata));
	}
	
	public void endDocument() {
		writeXIR(XDOBuilder.buildEndDocument());
	}
	
	public void endElement(String nsURI, String name, String qname) {
		writeXIR(XDOBuilder.buildEndElement(nsURI, name, qname));
	}
	
	public void endPrefixMapping(String nsPrefix) {
		String nsURI = namespaceMapping.remove(nsPrefix);
		writeXIR(XDOBuilder.buildEndPrefixMapping(nsPrefix, nsURI));
	}
	
	public void processingInstruction(String name, String target) {
		writeXIR(XDOBuilder.buildProcessingInstruction(target, name));
	}

	public void skippedEntity(String name) {
		writeXIR(XDOBuilder.buildSkippedEntity(name));
	}
	
	public void startDocument() {
		writeXIR(XDOBuilder.buildStartDocument());
	}
	
	public void startElement(String nsURI, String name, String qname,
			int numberOfAttrs) {
		writeXIR(XDOBuilder.buildStartElement(nsURI, name, qname, numberOfAttrs));
		
	}
	
	public void startPrefixMapping(String nsPrefix, String nsURI) {
		namespaceMapping.put(nsPrefix, nsURI);
		writeXIR(XDOBuilder.buildStartPrefixMapping(nsPrefix, nsURI));
	}
	
	public void whitespace(int length, String cdata) {
		writeXIR(XDOBuilder.buildWhitespace(cdata));
	}
	
	/**
	 * This internal method is 
	 * @param xdo
	 */
	private void writeXIR(XIRDataObject xdo)  {
		try {
			output.write(xdo + "\n");
			output.flush();
		} catch (IOException io) {
			throw new BetterXmlException("I/O Error in underlying stream passed to ToXirContentHandler");
		}
	}

}
