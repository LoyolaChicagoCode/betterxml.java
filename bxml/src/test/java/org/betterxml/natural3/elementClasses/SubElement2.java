/**
 * 
 */
package org.betterxml.natural3.elementClasses;

import org.betterxml.natural3.HighLevelTest;
import org.betterxml.natural3.annotations.Attribute;
import org.betterxml.natural3.annotations.Element;
import org.betterxml.natural3.annotations.Namespace;

@Element("subelement2")
@Namespace(HighLevelTest.nsTwo)
public class SubElement2 {
	@Attribute("a")
	private String a;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}
}