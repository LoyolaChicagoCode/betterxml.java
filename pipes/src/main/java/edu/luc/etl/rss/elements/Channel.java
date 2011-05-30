package edu.luc.etl.rss.elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import org.betterxml.xelement.XElement;

public class Channel extends XElement {
	//required tags
	private static final String TITLE_TAG 			= "title";
	private static final String DESCRIPTION_TAG 	= "description";
	private static final String LINK_TAG 			= "link";

	//optional tags
	private static final String LANGUAGE_TAG 		= "language";
	private static final String COPYRIGHT_TAG 		= "copyright";
	private static final String MANAGINGEDITOR_TAG 	= "managingEditor";
	private static final String WEBMASTER_TAG 		= "webMaster";
	private static final String PUBDATE_TAG 		= "pubDate";
	private static final String LASTBUILD_TAG 		= "lastBuildDate";
	private static final String CATEGORY_TAG 		= "category";
	private static final String GENERATOR_TAG 		= "generator";
	private static final String DOCS_TAG 			= "docs";
	private static final String CLOUD_TAG 			= "cloud";
	private static final String TTL_TAG				= "ttl";
	private static final String IMAGE_TAG			= "image";
	private static final String RATING_TAG			= "rating";
	private static final String TEXTINPUT_TAG		= "textInput";
	private static final String SKIPHOURS_TAG		= "skipHours";
	private static final String SKIPDAYS_TAG		= "skipDays";
	
	
	/*TODO check for the required 3 elements in order to validate element
	 * 1) title
	 * 2) link
	 * 3) description
	 */
	
//	TODO make this a static function somewhere that all the classes that have it can use
	private XElement getFirstElement(String elementName) {
		List<XElement> list = getChildrenElements(elementName);
		if (list.size() == 0) return null;
		return list.get(0);
	}
	
	//TODO make this a static function somewhere that all the classes that have it can use
	private String getBasicValue(String tag) {
		BasicElement be = (BasicElement)getFirstElement(tag);
		if (be == null) return "";
		return be.getValue();
	}
	
	public String getTitle() {
		return getBasicValue(TITLE_TAG);
	}
	
	public String getDescription() {
		return getBasicValue(DESCRIPTION_TAG);
	}
	
	private URL getURL(String tag) throws MalformedURLException {
		RSSurl rssurl = (RSSurl)getFirstElement(tag);
		if (rssurl == null) return null;
		return rssurl.getURL();
	}
	
	public URL getLink() throws MalformedURLException{
		return getURL(LINK_TAG);
	}
	
	private void setBasicValue(String value, String tag) {
		BasicElement be = (BasicElement)getFirstElement(tag);
		if (be == null) {
			be = new BasicElement();
			be.setName(tag);
			appendChild(be);
		}
		be.setValue(value);
	}
	
	private void setURL(URL url, String tag) {
		RSSurl rssurl = (RSSurl)getFirstElement(tag);
		if (rssurl == null) {
			rssurl = new RSSurl();
			rssurl.setName(tag);
			appendChild(rssurl);
		}
		rssurl.setURL(url);
	}
	
	
	public void setTitle(String title) {
		if (title == null) title = "";	//since it is required we can't remove it if null
		else setBasicValue(title, TITLE_TAG);
	}
	
	public void setDescription(String description) {
		if (description == null) description = "";	//since it is required we can't remove it if null
		else setBasicValue(description, DESCRIPTION_TAG);
	}
	
	public void setLink(URL url) {
		setURL(url, LINK_TAG);
	}
	
	
	/* all the getters and setters and removers of optional child elements
	 * language
	 * copyright
	 * managingEditor
	 * webMaster
	 * pubDate
	 * lastBuildDate
	 * category
	 * generator
	 * docs
	 * cloud
	 * ttl
	 * image
	 * rating
	 * textInput
	 * skipHours
	 * skipDays
	 */
	
	//TODO put setDate and getDate in a shared place (they could easily be static functions)
	private void setDate(Calendar date, String tag) {
		RSSDate rssDate = (RSSDate)getFirstElement(tag);
		if (rssDate == null) {
			rssDate = new RSSDate();
			rssDate.setName(tag);
			appendChild(rssDate);
		}
		rssDate.setDate(date);
	}

	private Calendar getDate(String tag) throws MalformedDateException {
		RSSDate date = (RSSDate)getFirstElement(tag);
		if (date == null) return null;
		return date.getDate();	
	}
	
	
	/* Language **********************************************/
	public String getLanguage() {
		return getBasicValue(LANGUAGE_TAG);
	}
	
	public void setLanguage(String language) {
		if (language == null) removeLanguage();
		else setBasicValue(language, LANGUAGE_TAG);
	}
	
	public void removeLanguage() {
		removeAllElements(LANGUAGE_TAG);
	}
	
	
	/* copyright **********************************************/
	public String getCopyright() {
		return getBasicValue(COPYRIGHT_TAG);
	}
	
	public void setCopyright(String copyright) {
		if (copyright == null) removeCopyright();
		else setBasicValue(copyright, COPYRIGHT_TAG);
	}
	
	public void removeCopyright() {
		removeAllElements(COPYRIGHT_TAG);
	}
	
	
	/* managingEditor **********************************************/
	public String getManagingEditor() {
		return getBasicValue(MANAGINGEDITOR_TAG);
	}
	
	public void setManagingEditor(String managingEditor) {
		if (managingEditor == null) removeManagingEditor();
		else setBasicValue(managingEditor, MANAGINGEDITOR_TAG);
	}
	
	public void removeManagingEditor() {
		removeAllElements(MANAGINGEDITOR_TAG);
	}
	
	/* webMaster **********************************************/
	public String getWebMaster() {
		return getBasicValue(WEBMASTER_TAG);
	}
	
	public void setWebMaster(String webMaster) {
		if (webMaster == null) removeWebMaster();
		else setBasicValue(webMaster, WEBMASTER_TAG);
	}
	
	public void removeWebMaster() {
		removeAllElements(WEBMASTER_TAG);
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
	
	/* lastBuildDate **********************************************/
	public Calendar getLastBuildDate() throws MalformedDateException {
		return getDate(LASTBUILD_TAG);
	}
	
	public void setLastBuildDate(Calendar lastBuildDate) {
		if (lastBuildDate == null) removeLastBuildDate();
		else setDate(lastBuildDate, LASTBUILD_TAG);
	}
	
	public void removeLastBuildDate() {
		removeAllElements(LASTBUILD_TAG);
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
		setChildren(categories);		
	}
	
	public void removeCategory(Category category) {
		removeChild(category);
	}
	
	public void clearCategories() {
		removeAllElements(CATEGORY_TAG);
	}
	
	/* generator **********************************************/
	public String getGenerator() {
		return getBasicValue(GENERATOR_TAG);
	}
	
	public void setGenerator(String generator) {
		if (generator == null) removeGenerator();
		else setBasicValue(generator, GENERATOR_TAG);
	}
	
	public void removeGenerator() {
		removeAllElements(GENERATOR_TAG);
	}
	
	/* docs **********************************************/
	public URL getDocs() throws MalformedURLException {
		return getURL(DOCS_TAG);
	}
	
	public void setDocs(URL docs) {
		if (docs == null) removeDocs();
		else setURL(docs, DOCS_TAG);
	}
	
	public void removeDocs() {
		removeAllElements(DOCS_TAG);
	}
	
	/* cloud **********************************************/
	public Cloud getCloud() {
		return (Cloud)getFirstElement(CLOUD_TAG);
	}
	
	public void setCloud(Cloud cloud) {
		if (cloud == null) removeCloud();
		else {
			appendChild(cloud);
		}
	}
	
	public void removeCloud() {
		removeAllElements(CLOUD_TAG);
	}
	
	
	/* ttl ******************************************/
	public int getTTL() {
		TTL ttl = (TTL)getFirstElement(TTL_TAG);
		if (ttl == null) return TTL.DEFAULT_VALUE;
		return ttl.getValue();
	}
	
	public void setTTL(int ttl) {
		TTL ttlElement = (TTL)getFirstElement(TTL_TAG);
		if (ttlElement == null) {
			ttlElement = new TTL();
			ttlElement.setName(TTL_TAG);
			this.appendChild(ttlElement);
		}
		ttlElement.setValue(ttl);
	}
	
	public void removeTTL() {
		removeAllElements(TTL_TAG);
	}
	
	/* image **********************************************/
	public Image getImage() {
		return (Image)getFirstElement(IMAGE_TAG);
	}
	
	public void setImage(Image image) {
		if (image == null) removeImage();
		else {
			appendChild(image);
		}
	}
	
	public void removeImage() {
		removeAllElements(IMAGE_TAG);
	}
	
	/* rating **********************************************/
	public String getRating() {
		return getBasicValue(RATING_TAG);
	}
	
	public void setRating(String rating) {
		if (rating == null) removeRating();
		else setBasicValue(rating, RATING_TAG);
	}
	
	public void removeRating() {
		removeAllElements(RATING_TAG);
	}
	
	/* textInput **********************************************/
	public TextInput getTextInput() {
		return (TextInput)getFirstElement(TEXTINPUT_TAG);
	}
	
	public void setTextInput(TextInput textInput) {
		if (textInput == null) removeTextInput();
		else {
			appendChild(textInput);
		}
	}
	
	public void removeTextInput() {
		removeAllElements(TEXTINPUT_TAG);
	}
	
	/* skipHours **********************************************/
	public SkipHours getSkipHours() {
		return (SkipHours)getFirstElement(SKIPHOURS_TAG);
	}
	
	public void setSkipHours(SkipHours skipHours) {
		if (skipHours == null) {
			removeSkipHours();
		}
		else {
			appendChild(skipHours);
		}
	}
	
	public void removeSkipHours() {
		removeAllElements(SKIPHOURS_TAG);
	}
	
	/* skipHours **********************************************/
	public SkipDays getSkipDays() {
		return (SkipDays)getFirstElement(SKIPDAYS_TAG);
	}
	
	public void setSkipDays(SkipDays skipDays) {
		if (skipDays == null) {
			removeSkipDays();
		}
		else {
			appendChild(skipDays);
		}
	}
	
	public void removeSkipDays() {
		removeAllElements(SKIPDAYS_TAG);
	}
	
}
