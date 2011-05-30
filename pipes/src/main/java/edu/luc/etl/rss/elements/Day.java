package edu.luc.etl.rss.elements;

import java.util.EnumSet;

import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XPCData;



public class Day extends XElement {

	//FIXME what to do about case senstivity? not specified in specs
	
	//from specs: "whose value is Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday"
	enum LongDayEnum {Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday }
	
	public Day() {
		super("day");
	}
	
	public String getValue() {
		return RSSElementHelper.simplePCData(this);
	}
	
	/*return if the value was set*/
	public boolean setValue(String value) {
		boolean valid = false;
		for (LongDayEnum d : EnumSet.range(LongDayEnum.Sunday, LongDayEnum.Saturday)) {
			if (d.toString().equals(value)) {
				valid = true;
			}
		}
		if (!valid) return false;
		this.removeAllPCData();
		try {
			this.appendChild(new XPCData(value));
		} catch (Exception e) {
			//it shouldn't break, but just in case...
			return false;
		}
		return true;
	}
}
