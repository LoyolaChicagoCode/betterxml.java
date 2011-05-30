package edu.luc.etl.rss.elements;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.betterxml.xelement.XElement;


public class SkipDays extends XElement {

	public Set<String> getDaysToSkip() {
		Set<String> set = new HashSet<String>();
		List<XElement> days = getChildrenElements("day");
		for (XElement day : days) {
			set.add(((Day)day).getValue());
		}
		return set;
	}
	
	/*I'm case sensitive for now because specs don't really say what to do*/
	public boolean skipDay(String day) {
		Set<String> set = getDaysToSkip();
		return set.contains(day);
	}
	
	/*returns if it is added*/
	public boolean addDay(String day) {
		if (skipDay(day)) return false;
		Day d = new Day();
		if (d.setValue(day) == false) {
			return false;
		}
		appendChild(d);
		return true;
	}
}
