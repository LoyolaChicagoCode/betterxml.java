package edu.luc.etl.rss.elements;

import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XPCData;


public class Hour extends XElement {

	
	public Hour() {
		super("hour");
	}
	
	public int getValue() {
		String str = RSSElementHelper.simplePCData(this);
		try {
			return Integer.valueOf(str);
		} catch (NumberFormatException nfe) {
			return -1;		//FIXME this might not be the right thing to default to, but good enough for a first go
		}
	}
	
	//TODO should we dummy check for no negative values because those really don't make sense here
	//boolean is if the value is actually set
	public boolean setValue(int value) {
		if (value < 0 || value > 23) return false;
		this.removeAllPCData();
		try {
			this.appendChild(new XPCData(Integer.toString(value)));
		} catch (Exception e) {
			//it shouldn't break, but just in case...
			return false;
		}
		return true;
	}
}
