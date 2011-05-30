package edu.luc.etl.rss.elements;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.betterxml.xelement.XElement;


public class SkipHours extends XElement {

	public Set<Integer> getHoursToSkip() {
		Set<Integer> set = new HashSet<Integer>();
		List<XElement> hours = getChildrenElements("hour");
		for (XElement hour : hours) {
			set.add(((Hour)hour).getValue());
		}
		return set;
	}
	
	public boolean skipHour(int hour) {
		if (hour < 0 || hour > 23) return false;
		Set<Integer> set = getHoursToSkip();
		return set.contains(hour);
	}
	
	/*returns if it is added*/
	public boolean addHour(int hour) {
		if (hour < 0 || hour > 23) return false;
		if (skipHour(hour)) return false;
		Hour h = new Hour();
		h.setValue(hour);
		appendChild(h);
		return true;
	}
}
