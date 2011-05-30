package edu.luc.etl.rss;

import org.betterxml.handlers.ToXElementContentHandler;

import edu.luc.etl.rss.elements.BasicElement;
import edu.luc.etl.rss.elements.Category;
import edu.luc.etl.rss.elements.Channel;
import edu.luc.etl.rss.elements.Cloud;
import edu.luc.etl.rss.elements.Day;
import edu.luc.etl.rss.elements.Enclosure;
import edu.luc.etl.rss.elements.Guid;
import edu.luc.etl.rss.elements.Hour;
import edu.luc.etl.rss.elements.Image;
import edu.luc.etl.rss.elements.Language;
import edu.luc.etl.rss.elements.RSS;
import edu.luc.etl.rss.elements.RSSDate;
import edu.luc.etl.rss.elements.RSSurl;
import edu.luc.etl.rss.elements.SkipDays;
import edu.luc.etl.rss.elements.SkipHours;
import edu.luc.etl.rss.elements.Source;
import edu.luc.etl.rss.elements.TTL;
import edu.luc.etl.rss.elements.TextInput;

public class ToRSSXElementContentHandler extends ToXElementContentHandler {

	public ToRSSXElementContentHandler() {
		super();
		registerRSSElements();
	}
	
	protected void registerRSSElements() {
		registerElementClass(RSS.class, "rss");
		registerElementClass(Channel.class, "channel");
		registerElementClass(BasicElement.class, "title");
		registerElementClass(RSSurl.class, "link");
		registerElementClass(BasicElement.class, "description");
		registerElementClass(Language.class, "language");
		registerElementClass(BasicElement.class, "copyright");
		registerElementClass(BasicElement.class, "managingEditor");
		registerElementClass(BasicElement.class, "webMaster");
		registerElementClass(RSSDate.class, "pubDate");
		registerElementClass(RSSDate.class, "lastBuildDate");
		registerElementClass(BasicElement.class, "generator");
		registerElementClass(RSSurl.class, "docs");
		registerElementClass(Cloud.class, "cloud");
		registerElementClass(TTL.class, "ttl");
		registerElementClass(Image.class, "image");
		registerElementClass(BasicElement.class, "rating");
		registerElementClass(TextInput.class, "textInput");
		registerElementClass(SkipHours.class, "skipHours");
		registerElementClass(SkipDays.class, "skipDays");
		registerElementClass(Hour.class, "hour");
		registerElementClass(Day.class, "day");
		registerElementClass(BasicElement.class, "author");
		registerElementClass(Category.class, "category");
		registerElementClass(Enclosure.class, "enclosure");
		registerElementClass(Guid.class, "guid");
		registerElementClass(Source.class, "source"); 
		registerElementClass(RSSurl.class, "url"); 
		registerElementClass(BasicElement.class, "width");
		registerElementClass(BasicElement.class, "height");
	}
	
}
