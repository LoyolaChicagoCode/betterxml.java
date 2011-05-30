package org.betterxml.xir;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Stack;

public class XIR2XMLHandler implements XIRContentHandler {
	private static final String DEFAULT_NS_PREFIX = "";
	private static final String NO_URI = "";
	private PrintWriter printer;
	private HashMap<String, String> prefixes;
	private HashMap<String, String> newPrefixes;
	private HashMap<String, String> reversePrefixMap;
	private String currentNamespace;
	private Stack<String> namespaceScope;
	private int attrCount = 0;
	

	public XIR2XMLHandler() {
		prefixes = new HashMap<String, String>();
		prefixes.put(DEFAULT_NS_PREFIX, NO_URI);
		newPrefixes = new HashMap<String, String>();
		reversePrefixMap = new HashMap<String, String>();
		namespaceScope = new Stack<String>();
		currentNamespace = NO_URI;
	}	
		
	public XIR2XMLHandler(Writer output) {
		this();
		printer = new PrintWriter(output);
	}
	
	public void startDocument() {
		// TODO: XIR needs a record for the document encoding.
		printer.printf("<?xml?>");
	}

	public void endDocument() {
		// NOP
	}

	private void emitNewPrefixes() {
		for ( String nsPrefix : newPrefixes.keySet() )
			if (nsPrefix.equals(DEFAULT_NS_PREFIX))
				printer.printf(" xmlns=\"%s\"", new Object[] { newPrefixes.get(nsPrefix) });
			else 
				printer.printf(" xmlns:\"%s\"=\"%s\"", new Object[] { nsPrefix, newPrefixes.get(nsPrefix) });
		newPrefixes.clear();
	}
	
	public void startElement(String nsURI, String name, int numberOfAttrs) {
		attrCount = numberOfAttrs;
		if (isCurrentNamespace(nsURI) || isDefaultNamespace(nsURI))
			printer.printf("<%s", new Object[] { name });
		else
			printer.printf("<%s:%s", new Object[] { getPrefix(nsURI), name });
		emitNewPrefixes();
		if (numberOfAttrs == 0)
			printer.print(">");
	}

	public void endElement(String nsURI, String name) {	
		if (isCurrentNamespace(nsURI) || isDefaultNamespace(nsURI))
			printer.printf("</%s>", new Object[] { name });
		else
			printer.printf("</%s:%s>", new Object[] { getPrefix(nsURI), name });
	}

	public void attribute(String nsURI, String name, String value) {
		attrCount--;
		if (!name.equals("")) {
			if (isCurrentNamespace(nsURI) || isDefaultNamespace(nsURI))
				printer.printf(" %s=\"%s\"", new Object[] { name, value });
			else
				printer.printf(" %s:%s=\"%s\"", new Object[] { getPrefix(nsURI), name, value });
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
		printer.printf("<?%s=%s?>", new Object[] { name, target });
	}

	public void skippedEntity(String name) {
		// TODO: Write emitter code for skipped entity.
	}

	public void whitespace(int length, String cdata) {
		printer.print(cdata);
	}
	
	private boolean isDefaultNamespace(String nsURI) {
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
	}
	

}
