package edu.luc.etl.rss.elements;


public class Category extends BasicElement {

	private final static String DOMAIN_ATTR_KEY = "domain";
	
	protected boolean hasDomain() {
		return getAttributes().containsAttribute(DOMAIN_ATTR_KEY);
	}
	
	public String getDomain() {
		if (hasDomain()) {
			return getAttributes().getAttributeValue(DOMAIN_ATTR_KEY);
		}
		return "";
	}
	
	public void setDomain(String domain) {
		getAttributes().setAttribute(DOMAIN_ATTR_KEY, domain);
	}
	
}
