/**
 * 
 */
package org.betterxml.natural3.elementClasses;

import org.betterxml.natural3.annotations.Children;
import org.betterxml.natural3.annotations.Element;
import org.betterxml.natural3.annotations.Singleton;

@Element("xelement1")
public class XElement1 {
	@Children(SubElement1.class)
	@Singleton
	private SubElement1 subelement1;
	
	@Children(SubElement2.class)
	@Singleton
	private SubElement2 subelement2;

	public SubElement1 getSubelement1() { return subelement1; }
	public void setSubelement1(SubElement1 subelement1) { this.subelement1 = subelement1; }
	public SubElement2 getSubelement2() { return subelement2; }
	public void setSubelement2(SubElement2 subelement2) { this.subelement2 = subelement2; }
}