package org.betterxml.examples.natural3.phonebook;


import java.util.ArrayList;
import java.util.List;

import org.betterxml.natural3.CDataWrap;
import org.betterxml.natural3.annotations.CData;
import org.betterxml.natural3.annotations.Element;

@Element("Phone")
public class Phone {
	
	@CData
	private List<CDataWrap> text = new ArrayList<CDataWrap>();

	public List<CDataWrap> getText() {
		return text;
	}

	public void addText(String text) {
		this.text.add(new CDataWrap(text));
	}
}