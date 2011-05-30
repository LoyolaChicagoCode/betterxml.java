package edu.luc.etl.rss.elements;

import java.util.List;

import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XPCData;

public class RSSElementHelper {

	public static String simplePCData(XElement element) {
		List<XPCData> pcData = element.getPCData();
		if (pcData.size() == 0) return "";
		return pcData.get(0).getText();
	}
	
}
