package org.betterxml.examples.natural3.particle;

import org.betterxml.natural3.annotations.Attribute;
import org.betterxml.natural3.annotations.Element;

@Element("Parameter")
public class Parameter {
	
	@Attribute("name")
	private String name;
	
	@Attribute("value")
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
