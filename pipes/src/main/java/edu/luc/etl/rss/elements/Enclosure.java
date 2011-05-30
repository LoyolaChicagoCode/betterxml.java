package edu.luc.etl.rss.elements;

import java.net.MalformedURLException;
import java.net.URL;

import org.betterxml.xelement.XElement;

/**
 * <enclosure> is an optional sub-element of <item>.
 * It has three required attributes. url says where the enclosure is located, 
 * length says how big it is in bytes, and type says what its type is, a standard 
 * MIME type.
 * 
 * The url must be an http url.
 * 
 * <enclosure url="http://www.scripting.com/mp3s/weatherReportSuite.mp3" 
 * 		length="12216320" type="audio/mpeg" />
 * 
 * A use-case narrative for this element is 
 * @author nabicht
 *
 */
public class Enclosure extends XElement {

	private final static String URL_ATTR_KEY = "url";
	private final static String LENGTH_ATTR_KEY = "length";
	private final static String TYPE_ATTR_KEY = "type";
	
	//TODO validate by making sure it has the 3 required attributes
	
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
	
	protected boolean hasLength() {
		return getAttributes().containsAttribute(LENGTH_ATTR_KEY);
	}
	
	//FIXME  should I default to -1?
	public long getLength() {
		if (hasLength()) {
			try {
				return Long.valueOf(getAttributes().getAttributeValue(LENGTH_ATTR_KEY));
			} catch(NumberFormatException nfe) {
				return -1;	
			}
		}
		return -1;
	}
	
	public void setLength(long length) {
		getAttributes().setAttribute(LENGTH_ATTR_KEY, Long.toString(length));
	}
	
	
	protected boolean hasType() {
		return getAttributes().containsAttribute(TYPE_ATTR_KEY);
	}
	
	public String getType() {
		if (hasType()) {
			return getAttributes().getAttributeValue(TYPE_ATTR_KEY);
		}
		return "";
	}
	
	public void setType(String type) {
		getAttributes().setAttribute(TYPE_ATTR_KEY, type);
	}
	
}
