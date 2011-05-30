package edu.luc.etl.rss.elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.betterxml.xelement.XElement;


public class TextInput extends XElement {
	
	
	private final static String TITLE_TAG 		= "title";
	private final static String DESCRIPTION_TAG = "description";
	private final static String LINK_TAG 		= "link";
	private final static String NAME_TAG 		= "name";
	
	/*Contains 4 mandatory child elements
	 *   1) title
	 *   2) description
	 *   3) name
	 *   4) link
	 */
	
	//TODO validate the element by checking on the presence of the 4 mandatory child elements
	
	/*WOW -- from the specs:
	 * "The purpose of the <textInput> element is something of a mystery. You can 
	 *  use it to specify a search engine box. Or to allow a reader to 
	 *  provide feedback. Most aggregators ignore it."
	 */
	
	private XElement getFirstElement(String elementName) {
		List<XElement> list = getChildrenElements(elementName);
		if (list.size() == 0) return null;
		return list.get(0);
	}
	
	private void setBasicValue(String value, String tag) {
		BasicElement be = (BasicElement)getFirstElement(tag);
		if (be == null) {
			be = new BasicElement();
			be.setName(tag);
			this.appendChild(be);
		}
		be.setValue(value);
	}
	
	private String getBasicValue(String tag) {
		BasicElement be = (BasicElement)getFirstElement(tag);
		if (be == null) return "";
		return be.getValue();
	}
	
	public String getTitle() {
		return getBasicValue(TITLE_TAG);
	}
	

	public void setTitle(String title) {
		if (title == null) title = "";	//since it is required we can't remove it if null
		else setBasicValue(title, TITLE_TAG);
	}
	
	public String getDescription() {
		return getBasicValue(DESCRIPTION_TAG);
	}
	
	public void setDescription(String description) {
		if (description == null) description = "";	//since it is required we can't remove it if null
		else setBasicValue(description, DESCRIPTION_TAG);
	}
	
	public String getName() {
		return getBasicValue(NAME_TAG);
	}
	

	public void setName(String name) {
		if (name == null) name = "";	//since it is required we can't remove it if null
		else setBasicValue(name, NAME_TAG);
	}
	
	
	private URL getURL(String tag) throws MalformedURLException {
		RSSurl rssurl = (RSSurl)getFirstElement(tag);
		if (rssurl == null) return null;
		return rssurl.getURL();
	}
	
	public URL getLink() throws MalformedURLException{
		return getURL(LINK_TAG);
	}
	
	private void setURL(URL url, String tag) {
		RSSurl rssurl = (RSSurl)getFirstElement(tag);
		if (rssurl == null) {
			rssurl = new RSSurl();
			rssurl.setName(tag);
			this.appendChild(rssurl);
		}
		rssurl.setURL(url);
	}
	
	public void setLink(URL url) {
		setURL(url, LINK_TAG);
	}
	
	
	
}
