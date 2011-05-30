package edu.luc.etl.atom;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.natural3.annotations.Children;
import org.betterxml.natural3.annotations.Element;

@Element("author")
public class Author {

	@Children(Name.class)
	private List<Name> names = new ArrayList<Name>();
	
	public void addName(Name name) {
		names.add(name);
	}
	
	public List<Name> getNames() {
		return this.names; //unmodifiable?
	}
	
}
