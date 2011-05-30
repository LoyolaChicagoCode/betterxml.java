package edu.luc.etl.rss.elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XPCData;

public class Image extends XElement {

	private final static int DEFAULT_WIDTH = 88;
	private final static int MAX_WIDTH = 144;
	
	private final static int DEFAULT_HEIGHT = 31;
	private final static int MAX_HEIGHT = 400;
	
	private final static String TITLE_TAG 		= "title";
	private final static String DESCRIPTION_TAG = "description";
	private final static String LINK_TAG = "link";
	
	/*Three required elements:
	 *  1) url
	 *  2) title
	 *  3) link
	 */
	
	/*Three optional elements:
	 *  1) width
	 *  2) height
	 *  3) description
	 */
	
	//TODO validate by making sure the mandatory elements are present
	
	public URL getURL() throws MalformedURLException{
		List<XElement> url = getChildrenElements("url");
		if (url.size() == 0) {
			throw new MalformedURLException("No URL specified");
		} else {
			return ((RSSurl)url.get(0)).getURL();
		}
	}
	
	public void setURL(URL url) {
		RSSurl u = new RSSurl();
		u.setName("url");
		u.setURL(url);
		removeAllElements("url");
		appendChild(u);
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
	
	
	/*from specs: "Maximum value for width is 144, default value is 88."*/
	public int getWidth() {
		List<XElement> width = getChildrenElements("width");
		if (width.size() == 0) return DEFAULT_WIDTH;
		String widthStr = ((BasicElement)width.get(0)).getValue();
		try {
			int w = Integer.valueOf(widthStr);
			if (w < 0 || w > MAX_WIDTH) return DEFAULT_WIDTH;
			return w;
		} catch (NumberFormatException nfe) {
			return DEFAULT_WIDTH;
		}
	}
	
	public void setWidth(int width) {
		int w = width;
		if (width < 0 || width > MAX_WIDTH) {
			w = DEFAULT_WIDTH;
		} 
		removeAllElements("width");
		XElement element = new XElement("width");
		element.appendChild(new XPCData(Integer.toString(w)));
		appendChild(element);
	}
	
	/*from specs: "Maximum value for height is 400, default value is 31."*/
	public int getHeight() {
		List<XElement> height = getChildrenElements("height");
		if (height.size() == 0) return DEFAULT_HEIGHT;
		String heightStr = ((BasicElement)height.get(0)).getValue();
		try {
			int h = Integer.valueOf(heightStr);
			if (h < 0 || h > MAX_HEIGHT) return DEFAULT_HEIGHT;
			return h;
		} catch (NumberFormatException nfe) {
			return DEFAULT_HEIGHT;
		}
	}
	
	public void setHeight(int height) {
		int h = height;
		if (height < 0 || height > MAX_HEIGHT) {
			h = DEFAULT_HEIGHT;
		} 
		removeAllElements("height");
		XElement element = new XElement("height");
		element.appendChild(new XPCData(Integer.toString(h)));
		appendChild(element);
	}
}
