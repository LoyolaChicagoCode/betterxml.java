package org.betterxml.xelement;

import java.util.HashMap;

import org.betterxml.BetterXmlException;


public class XNameSpace {

	//TODO javadoc the whole damn class
	//TODO unit test this thing (tedious but a necessity)
	
	private HashMap<String, Class<? extends XElement>> elementToClassMappings = new HashMap<String, Class<? extends XElement>>();
	private final String uri;
	
	/* defaultClass is private because I have made the decision that you should not be able
	 * to change the default class while it is running, it is default for a reason -- we can 
	 * change this later if need be.
	 */
	private final Class<? extends XElement> defaultClass;
	
	public XNameSpace(String uri) {
		this(XElement.class, uri);
	}
	
	public XNameSpace(Class<? extends XElement> defaultClass, String uri) {
		this.defaultClass = defaultClass;
		this.uri = uri;
	}
	
	public String getURI() {
		return uri;
	}
		
	public void setElementMapping(String elementName, Class<? extends XElement> klass) throws BetterXmlException {
		elementToClassMappings.put(elementName, klass);
	}
	
	public HashMap<String, Class<? extends XElement>> getMappings() {
		return elementToClassMappings;
	}
	
	public Class<? extends XElement> getClass(String elementName) {
		Class<? extends XElement> klass = elementToClassMappings.get(elementName);
		if (klass == null) {
			return defaultClass;
		}
		return klass;
	}
	
	public Class<? extends XElement> getDefaultClass() {
		return defaultClass;
	}
	
}
