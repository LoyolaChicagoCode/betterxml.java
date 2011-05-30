package org.betterxml.xelement;

import org.betterxml.BetterXmlContentHandler;

public class XDocument extends XNode {

	XElement rootElement = null;
	
	/**
	 * Set the root element of the XDocument instance to the supplied XElement.
	 * @param rootElement  XElement. What to set the root element of the XDocument to.
	 */
	public void setRootElement(XElement rootElement) {
		this.rootElement = rootElement;
	}
	
	/**
	 * Compare the supplied name to the name of the root element.
	 * @param name  String. The name to check against the name of the root element.
	 * @return  boolean.  True if the supplied name is equal to that of the root element. False if it is not.
	 */
	public boolean rootElementHasName(String name) {
		if (rootElement == null) return false;
		if (rootElement.nameEquals(name)) return true;
		return false;
	}
	
	
	/**
	 * Get the root element of the XDocument instance.
	 * @return  XElement. The root element of the instance of XDocument.
	 */
	public XElement getRootElement() {
		return rootElement;
	}
	
	/**
	 * Checks to see if the instance of XDocument has a defined root element.
	 * @return  boolean. True if XDocument has a defined root element. False if it does not.
	 */
	public boolean hasRootElement() {
		if (rootElement == null) {
			return false;
		}
		return true;
	}

	public void acceptContentHandler(BetterXmlContentHandler contentHandler) {
		contentHandler.startDocument();
		if(this.hasRootElement()) rootElement.acceptContentHandler(contentHandler);
	}
	
	
	
}
