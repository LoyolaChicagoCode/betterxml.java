package edu.luc.etl.atom;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.natural3.annotations.Children;
import org.betterxml.natural3.annotations.Element;
import org.betterxml.natural3.annotations.Singleton;

@Element("entry")
public class Entry {
	
	@Children(Id.class)
	@Singleton
	private Id id = null;
	
	@Children(Published.class)
	@Singleton
	private Published published = null;
	
	@Children(Updated.class)
	@Singleton
	private Updated updated = null;
	
	@Children(Category.class)
	@Singleton
	private Category category = null;
	
	@Children(Title.class)
	@Singleton
	private Title titles = null;
	
	@Children(Content.class)
	@Singleton
	private Content content = null;
	
	@Children(Link.class)
	private List<Link> links = new ArrayList<Link>();
	
	@Children(Author.class)
	private List<Author> authors = new ArrayList<Author>();
	
	public void addLink(Link link) {
		links.add(link);
	}
	
	public List<Link> getLinks() {
		return this.links;
	}
	
	public void addAuthor(Author author) {
		authors.add(author);
	}
	
	public List<Author> getAuthors() {
		return this.authors;
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public Published getPublished() {
		return published;
	}

	public void setPublished(Published published) {
		this.published = published;
	}

	public Updated getUpdated() {
		return updated;
	}

	public void setUpdated(Updated updated) {
		this.updated = updated;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Title getTitles() {
		return titles;
	}

	public void setTitles(Title titles) {
		this.titles = titles;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
}
