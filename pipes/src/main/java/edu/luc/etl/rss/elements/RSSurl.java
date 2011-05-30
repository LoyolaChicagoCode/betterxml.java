package edu.luc.etl.rss.elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XPCData;

public class RSSurl extends XElement {
	
	//TODO rename RSSUrlElement
	
	
	//TODO unittest
	public URL getURL() throws MalformedURLException {
		List<XPCData> pcData = this.getPCData();
		if (pcData.size() == 0) {
			return new URL("");
		}
		return new URL(pcData.get(0).getText());
	}
	
	//TODO unittest
	public void setURL(URL url) {
		this.removeAllPCData();
		try {
			this.appendChild(new XPCData(url.toString()));
		} catch (Exception e) {
			//ignoring this because it shouldn't break
		}
	}
	
}
