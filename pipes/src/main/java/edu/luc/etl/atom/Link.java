package edu.luc.etl.atom;

import org.betterxml.natural3.annotations.Attribute;
import org.betterxml.natural3.annotations.Element;

@Element("link")
public class Link {

	@Attribute("rel")
	private String rel;
	
	@Attribute("type")
	private String type;
	
	@Attribute("href")
	private String href;
	
	@Attribute("title")
	private String title;

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
