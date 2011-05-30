/**
 * 
 */
package org.betterxml.natural3.elementClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.betterxml.natural3.HighLevelTest;
import org.betterxml.natural3.annotations.Children;
import org.betterxml.natural3.annotations.Element;
import org.betterxml.natural3.annotations.Namespace;

@Element("myroot")
@Namespace(HighLevelTest.defaultNs)
public class Myroot {
	@Children(XElement1.class)
	public List<XElement1> xelement1s = new ArrayList<XElement1>();
	
	public List<XElement1> getXelement1s() { return Collections.unmodifiableList(xelement1s);}
	public void addXelement1(XElement1 xelement) { this.xelement1s.add(xelement); }
}