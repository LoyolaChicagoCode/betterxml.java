package edu.luc.etl.rss.elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import org.betterxml.xelement.XElement;

/**
 * from http://www.rssboard.org/rss-specification#hrelementsOfLtitemgt:
 * A channel may contain any number of <item>s. An item may represent a 
 * "story" -- much like a story in a newspaper or magazine; if so its 
 * description is a synopsis of the story, and the link points to the 
 * full story. An item may also be complete in itself, if so, the 
 * description contains the text (entity-encoded HTML is allowed; 
 * see examples), and the link and title may be omitted. All elements 
 * of an item are optional, however at least one of title or description 
 * must be present.
 * @author nabicht
 *
 */
public class Item extends XElement {

	private static final String CATEGORY_TAG 	= "category";
	private final static String TITLE_TAG 		= "title";
	private final static String DESCRIPTION_TAG = "description";
	private final static String LINK_TAG 		= "link";
	
	private final static String AUTHOR_TAG		= "author";
	private final static String COMMENTS_TAG	= "comments";
	private final static String ENCLOSURE_TAG	= "enclosure";
	private final static String GUID_TAG		= "guid";
	private final static String PUBDATE_TAG		= "pubDate";
	private final static String SOURCE_TAG		= "source";
	
	
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
	
	/* Author **********************************************/
	public String getAuthor() {
		return getBasicValue(AUTHOR_TAG);
	}
	
	public void setAuthor(String author) {
		if (author == null) removeAuthor();
		else setBasicValue(author, AUTHOR_TAG);
	}
	
	public void removeAuthor() {
		removeAllElements(AUTHOR_TAG);
	}
	
	
	/* category ***********************************************/
	public void addCategory(Category category) {
		appendChild(category);
	}
	
	public void addCategory(String category) {
		addCategory(category, null);
	}
	
	public void addCategory(String category, String domain) {
		Category cat = new Category();
		cat.setName(CATEGORY_TAG);
		cat.setValue(category);
		if (domain != null) {
			cat.setDomain(domain);
		}
		appendChild(cat);
	}
	
	public void setCategories(List<Category> categories) {
		this.setChildren(categories);
	}
	
	public void removeCategory(Category category) {
		removeChild(category);
	}
	
	public void clearCategories() {
		removeAllElements(CATEGORY_TAG);
	}
	
	/* comments **********************************************/
	public URL getComments() throws MalformedURLException {
		return getURL(COMMENTS_TAG);
	}
	
	public void setComments(URL comments) {
		if (comments == null) removeComments();
		else setURL(comments, COMMENTS_TAG);
	}
	
	public void removeComments() {
		removeAllElements(COMMENTS_TAG);
	}
	
	/* enclosure **********************************************/
	public Enclosure getEnclosure() {
		return (Enclosure)getFirstElement(ENCLOSURE_TAG);
	}
	
	public void setEnclosure(Enclosure enclosure) {
		if (enclosure == null) {
			removeEnclosure();
		}
		else {
			appendChild(enclosure);
		}
	}
	
	public void removeEnclosure() {
		removeAllElements(ENCLOSURE_TAG);
	}
	
	/* Guid **********************************************/
	public Guid getGuid() {
		return (Guid)getFirstElement(GUID_TAG);
	}
	
	public void setGuid(Guid guid) {
		if (guid == null) {
			removeGuid();
		}
		else {
			appendChild(guid);
		}
	}
	
	public void removeGuid() {
		removeAllElements(GUID_TAG);
	}
	
	/* Source **********************************************/
	public Source getSource() {
		return (Source)getFirstElement(SOURCE_TAG);
	}
	
	public void setSource(Guid source) {
		if (source == null) {
			removeSource();
		}
		else {
			appendChild(source);
		}
	}
	
	public void removeSource() {
		removeAllElements(SOURCE_TAG);
	}
	
	
	/* pubDate **********************************************/
	public Calendar getPubDate() throws MalformedDateException {
		return getDate(PUBDATE_TAG);
	}
	
	public void setPubDate(Calendar pubDate) {
		if (pubDate == null) removePubDate();
		else setDate(pubDate, PUBDATE_TAG);
	}
	
	public void removePubDate() {
		removeAllElements(PUBDATE_TAG);
	}
	
	
	//TODO put setDate and getDate in a shared place (they could easily be static functions)
	private void setDate(Calendar date, String tag) {
		RSSDate rssDate = (RSSDate)getFirstElement(tag);
		if (rssDate == null) {
			rssDate = new RSSDate();
			rssDate.setName(tag);
			this.appendChild(rssDate);
		}
		rssDate.setDate(date);
	}

	private Calendar getDate(String tag) throws MalformedDateException {
		RSSDate date = (RSSDate)getFirstElement(tag);
		if (date == null) return null;
		return date.getDate();	
	}
	
}
