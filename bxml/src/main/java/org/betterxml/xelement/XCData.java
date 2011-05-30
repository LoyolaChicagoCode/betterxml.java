package org.betterxml.xelement;

import org.betterxml.BetterXmlContentHandler;


/**
 * This class describes a CDATA section.
 * 
 * A CDATA section is an escaped text section of an XML document. Data within a
 * CDATA section is not to be parsed by the XML parser. This is not
 * to be confused with PCData (Parsed Character Data), which, as the name implies,
 * is parsed.
 * @author mbutcher
 * @see XPCData
 *
 */
public class XCData extends XNode {

	public final static String NAME = "CDATA";
	
	private String text;
	
	public XCData() {
		this("");
	}
	
	
	public XCData(String text) {
		this.text = text;
		this.name = NAME;
		initialize();
	}
	
	/**
	 * This function is so that you don't have to overwrite the constructor. Anything you would like 
	 * implemented at construction you should put in <code>initialize()</code>. It is always called at 
	 * construction.
	 */
	public void initialize() {  }
	
	
	public String getText() {
		return this.text;
	}
	
	public void trimWhiteSpace() {
		this.text = this.text.trim();
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void appendText(String newText) {
		this.text = this.text + newText;
	}
	
	public String toString() {
		return "CDATA\n\tParent: " + parent.getName() + "\n\t" + text;
	}
	
	public void acceptContentHandler(BetterXmlContentHandler contentHandler) {
		contentHandler.characters(text.length(), text);
	}
}
