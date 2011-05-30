package edu.luc.etl.atom;

import org.betterxml.natural3.annotations.Attribute;
import org.betterxml.natural3.annotations.Element;

@Element("title")
public class Title extends CDataSingleton {
	
	@Attribute("type")
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
