package edu.luc.etl.rss.elements;

import java.net.MalformedURLException;
import java.net.URL;


public class Source extends BasicElement {

	private final static String URL_ATTR_KEY = "url";
	
	//TODO validate by making sure it has the 1 required attribute -- url
	
	
	protected boolean hasURL() {
		return getAttributes().containsAttribute(URL_ATTR_KEY);
	}
	
	public URL getURL() throws MalformedURLException{
		if (hasURL()) {
			return new URL(getAttributes().getAttributeValue(URL_ATTR_KEY));
		}
		return new URL ("");
	}
	
	public void setURL(URL url) {
		getAttributes().setAttribute(URL_ATTR_KEY, url.toString());
	}
	
	
	
}
