package org.betterxml.handlers;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Stack;

import org.betterxml.BetterXmlContentHandler;

public class ToXMLContentHandler implements BetterXmlContentHandler {
	private static final String DEFAULT_NS_PREFIX = "";
	private static final String NO_URI = "";
	private String preamble = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	protected PrintWriter printer;
	private HashMap<String, String> prefixes;
	private HashMap<String, String> newPrefixes;
	private HashMap<String, String> reversePrefixMap;
	private String currentNamespace;
	private Stack<String> namespaceScope;
	private int attrCount = 0;
	

	public ToXMLContentHandler() {
		prefixes = new HashMap<String, String>();
		prefixes.put(DEFAULT_NS_PREFIX, NO_URI);
		newPrefixes = new HashMap<String, String>();
		reversePrefixMap = new HashMap<String, String>();
		namespaceScope = new Stack<String>();
		currentNamespace = NO_URI;
	}	
		
	public ToXMLContentHandler(Writer output) {
		this();
		printer = new PrintWriter(output);
	}
	
	//TODO should this really be here?
	public void setPreamble(String preamble) {
		this.preamble = preamble;
	}
	
	public void startDocument() {
		printer.printf(preamble);
	}

	public void endDocument() {
		// NOP
	}

	private void emitNewPrefixes() {
		for ( String nsPrefix : newPrefixes.keySet() )
			if (nsPrefix.equals(DEFAULT_NS_PREFIX))
				printer.printf(" xmlns=\"%s\"", newPrefixes.get(nsPrefix));
			else 
				printer.printf(" xmlns:\"%s\"=\"%s\"", nsPrefix, newPrefixes.get(nsPrefix));
		newPrefixes.clear();
	}
	
	public void startElement(String nsURI, String name, String qname, int numberOfAttrs) {
		attrCount = numberOfAttrs;
		printer.printf("<%s", !qname.equals("") ? qname: name);
		/*if (isCurrentNamespace(nsURI) || isDefaultNamespace(nsURI))
			printer.printf("<%s", new Object[] { name });
		else
			printer.printf("<%s:%s", new Object[] { getPrefix(nsURI), name });*/
		emitNewPrefixes();
		if (numberOfAttrs == 0)
			printer.print(">");
	}

	public void endElement(String nsURI, String name, String qname) {	
		printer.printf("</%s>", !qname.equals("") ? qname: name);
		/*if (isCurrentNamespace(nsURI) || isDefaultNamespace(nsURI))
			printer.printf("</%s>", new Object[] { name });
		else
			printer.printf("</%s:%s>", new Object[] { getPrefix(nsURI), name });*/
	}

	public void attribute(String nsURI, String name, String qname, String value) {
		attrCount--;
		if (!name.equals("")) {
			printer.printf(" %s=\"%s\"", !qname.equals("") ? qname: name, value);
			/*if (isCurrentNamespace(nsURI) || isDefaultNamespace(nsURI))
				printer.printf(" %s=\"%s\"", new Object[] { name, value });
			else
				printer.printf(" %s:%s=\"%s\"", new Object[] { getPrefix(nsURI), name, value });*/
		}
		if (attrCount == 0)
			printer.print(">");
	}

	public void characters(int length, String cdata) {
		printer.print(cdata);
	}

	public void startPrefixMapping(String nsPrefix, String nsURI) {
		currentNamespace = nsURI;
		namespaceScope.push(currentNamespace);
		prefixes.put(nsPrefix, nsURI);
		reversePrefixMap.put(nsURI, nsPrefix);
		newPrefixes.put(nsPrefix, nsURI);
	}

	public void endPrefixMapping(String nsPrefix) {
		try {
			prefixes.remove(nsPrefix);
		} catch (Exception noSuchPrefix) {
			// Safe to ignore.
		}
		namespaceScope.pop();
		try {
			currentNamespace = namespaceScope.peek();
		} catch(Exception e) {
			currentNamespace = "";
		}
	}

	public void processingInstruction(String name, String target) {
		printer.format("<?%s %s?>", name, target);
	}

	public void skippedEntity(String name) {
		// TODO: Write emitter code for skipped entity.
	}

	public void whitespace(int length, String cdata) {
		printer.print(cdata);
	}
	
	/*private boolean isDefaultNamespace(String nsURI) {
		return getDefaultNamespace().equals(nsURI);
	}

	private boolean isCurrentNamespace(String nsURI) {
		return getCurrentNamespace().equals(nsURI);
	}

	private String getPrefix(String nsURI) {
		return reversePrefixMap.get(nsURI);
	}
	
	private String getCurrentNamespace() {
		try {
			return namespaceScope.peek();
		} catch(Exception e) {
			return DEFAULT_NS_PREFIX;
		}
	}
	
	private String getDefaultNamespace() {
		return prefixes.get(DEFAULT_NS_PREFIX);
	}*/
	
}
