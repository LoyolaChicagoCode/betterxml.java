package org.betterxml.xelement;

import org.betterxml.BetterXmlContentHandler;


public class XPCData extends XNode {
	
	public final static String NAME = "PCDATA";
	
	private String text;
	
	public XPCData() {
		this("");
	}
	
	
	public XPCData(String text) {
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
		return text;
	}
	
	public void trimWhiteSpace() {
		text = text.trim();
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void appendText(String newText) {
		this.text = this.text + newText;
	}
	
	public String toString() {
		return "PCDATA\n\tParent: " + parent.getName() + "\n\t" + text;
	}
	
	protected String convertEntities() {
		return convertEntities(this.text);
	}
	
	protected String convertEntities(String text) {
		String ret = text;
		ret = ret.replaceAll("&","&amp;");
		ret = ret.replaceAll(">","&gt;");
		ret = ret.replaceAll("<","&lt;");
		return ret;
	}

	public void acceptContentHandler(BetterXmlContentHandler contentHandler) {
		contentHandler.characters(text.length(), text);
	}
	
}
