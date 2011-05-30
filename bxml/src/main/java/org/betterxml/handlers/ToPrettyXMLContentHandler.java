package org.betterxml.handlers;

import java.io.Writer;
import java.util.List;
import java.util.ArrayList;

import org.betterxml.natural3.CDataWrap;

public class ToPrettyXMLContentHandler extends ToXMLContentHandler {

	private final static String INDENT_STRING = "  ";
	private int indentLevel=0;
	private List<CDataWrap> cdataList = new ArrayList<CDataWrap>();
	
	public ToPrettyXMLContentHandler(Writer sysOutWriter) {
		super(sysOutWriter);
	}

	public void whitespace(int length, String cdata) {
		cdataList.add(new CDataWrap(cdata));
	}
	
	@Override
	public void characters(int length, String cdata) {
		cdataList.add(new CDataWrap(cdata));
	}
	
	@Override
	public void endElement(String nsURI, String name, String qname) {
		checkAndPrintCData();
		super.printer.append(getIndentString());
		super.endElement(nsURI, name, qname);
		indentLevel--;
	}
	
	@Override
	public void startElement(String nsURI, String name, String qname, int numberOfAttrs) {
		indentLevel++;
		checkAndPrintCData();
		super.printer.append(getIndentString());
		super.startElement(nsURI, name, qname, numberOfAttrs);
	}
	
	private void checkAndPrintCData() {
		indentLevel++;
		if(!cdataList.isEmpty()) {
			String characters = "\n"+getIndentString()+CDataWrap.reduce(cdataList).trim()+"\n";
			cdataList.clear();
			super.characters(characters.length(), characters);
		} else {
			super.characters(1, "\n");
		}
		indentLevel--;
	}
	
	private String getIndentString() {
		return multiplyString(INDENT_STRING, indentLevel);
	}
	
	private String multiplyString(String base, int factor) {
		StringBuffer buffer = new StringBuffer(base.length()*factor);
		for(int i=0;i<factor;i++) {
			buffer.append(base);
		}
		return buffer.toString();
	}
	
}
