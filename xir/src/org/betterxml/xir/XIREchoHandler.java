package org.betterxml.xir;

import java.io.PrintWriter;
import java.io.Writer;

public class XIREchoHandler implements XIRContentHandler {

	private StringBuffer tabs = new StringBuffer();
	private PrintWriter printer;
	
	private void tab() {
		printer.print(tabs);
	}
	
	private void indent() {
		tabs.append(' ');
	}
	
	private void outdent() {
		tabs.deleteCharAt(tabs.length()-1);
	}
	
	public XIREchoHandler(Writer output) {
		printer = new PrintWriter(output);
	}
	
	public void attribute(String nsURI, String name, String value) {
		indent(); 
		tab();
		printer.printf("attribute %s:%s=%s\n", new Object[] { nsURI, name, value });
		outdent();
	}

	public void characters(int length, String cdata) {
		tab();
		printer.printf("characters %s (len=%d)\n", new Object[] { cdata, length} );

	}

	public void endDocument() {
		outdent();
		tab();
		printer.println("document:end");
	}

	public void endElement(String nsURI, String name) {	
		outdent();
		tab();
		printer.printf("element:end %s:%s\n", new Object[] { nsURI, name} );
	}

	public void endPrefixMapping(String nsPrefix) {
		tab();
		printer.printf("prefix:end %s\n", new Object[] { nsPrefix } );
	}

	public void processingInstruction(String name, String target) {
		tab();
		printer.printf("pi %s=%s", new Object[] { name, target });
	}

	public void skippedEntity(String name) {
		tab();
		printer.printf("skipped-entity %s", new Object[] { name });

	}

	public void startDocument() {
		tab();
		printer.println("document:begin");
		indent();
	}

	public void startElement(String nsURI, String name, int numberOfAttrs) {
		tab();
		printer.printf("element:start %s:%s #attrs %d", new Object[] { nsURI, name, numberOfAttrs });
		indent();
	}

	public void startPrefixMapping(String nsPrefix, String nsURI) {
		tab();
		printer.printf("prefix:begin %s", new Object[] { nsPrefix, nsURI } );

	}

	public void whitespace(int length, String cdata) {
		tab();
		printer.printf("characters %s (len=%d)", new Object[] { cdata, length} );

	}

}
