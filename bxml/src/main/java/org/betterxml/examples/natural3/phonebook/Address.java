package org.betterxml.examples.natural3.phonebook;


import java.util.ArrayList;
import java.util.List;

import org.betterxml.natural3.annotations.Children;
import org.betterxml.natural3.annotations.Element;

@Element("Address")
public class Address {

	@Children(Line.class)
	private List<Line> lines = new ArrayList<Line>();
	
	@Children(Phone.class)
	private List<Phone> phones = new ArrayList<Phone>();

	public void addPhone(Phone phone) {
		phones.add(phone);
	}
	
	public List<Phone> getPhones() {
		return this.phones;
	}

	public List<Line> getLines() {
		return lines;
	}

	public void addLine(Line line) {
		this.lines.add(line);
	}

}