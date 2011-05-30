package edu.luc.etl.rss.elements;

public class Guid extends BasicElement {

	private final static String PERMALINK_ATTR_KEY = "isPermalink";
	
	/* From http://www.rssboard.org/rss-specification#ltguidgtSubelementOfLtitemgt
	 * If the guid element has an attribute named isPermaLink with a value of true, 
	 * the reader may assume that it is a permalink to the item, that is, a url that 
	 * can be opened in a Web browser, that points to the full item described by the 
	 * <item> element.
	 * 
	 * isPermaLink is optional, its default value is true. If its value is false, 
	 * the guid may not be assumed to be a url, or a url to anything in particular.
	 */
	private final static boolean DEFAULT_PERMALINK = true;
	
	public boolean getIsPermalink() {
		if (getAttributes().containsAttribute(PERMALINK_ATTR_KEY)) {
			String value = getAttributes().getAttributeValue(PERMALINK_ATTR_KEY);
			//since the default value is true, should only return false if that is the value
			if (value.equalsIgnoreCase("false")) {
				return false;
			} else {
				return DEFAULT_PERMALINK;	 
			}
		}
		else {	//if it isn't there, return the default
			return DEFAULT_PERMALINK;
		}
	}
	
	public void setIsPermalink(boolean permalink) {
		if (permalink = true) {
			getAttributes().setAttribute(PERMALINK_ATTR_KEY, "true");
		} else {
			getAttributes().setAttribute(PERMALINK_ATTR_KEY, "false");	
		}
	}
	
	public void removeIsPermalink() {
		getAttributes().removeAttribute(PERMALINK_ATTR_KEY);
	}
	
		
}
