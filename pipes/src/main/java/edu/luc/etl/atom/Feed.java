package edu.luc.etl.atom;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.natural3.annotations.Children;
import org.betterxml.natural3.annotations.Element;
import org.betterxml.natural3.annotations.Singleton;

@Element("feed")
public class Feed {
	
	@Children(Entry.class)
	private List<Entry> entries = new ArrayList<Entry>();
	
	@Children(Id.class)
	@Singleton
	private Id id;
	
	@Children(Updated.class)
	@Singleton
	private Updated updated;
	
	@Children(Title.class)
	@Singleton
	private Title title;
	
	@Children(Author.class)
	@Singleton
	private Author author;
	
	@Children(Link.class)
	private List<Link> links = new ArrayList<Link>();
	
	public void addEntrie(Entry entry) {
		this.entries.add(entry);
	}
	
	public List<Entry> getEntries() {
		return entries;
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public Updated getUpdated() {
		return updated;
	}

	public void setUpdated(Updated updated) {
		this.updated = updated;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public void addLink(Link link) {
		this.links.add(link);
	}

	public List<Link> getLinks() {
		return links;
	}

}
