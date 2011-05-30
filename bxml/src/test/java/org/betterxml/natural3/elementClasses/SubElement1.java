/**
 * 
 */
package org.betterxml.natural3.elementClasses;

import org.betterxml.natural3.annotations.Attribute;
import org.betterxml.natural3.annotations.Element;

@Element("subelement1")
public class SubElement1 {
	@Attribute("a")
	private String a;
	
	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}
}