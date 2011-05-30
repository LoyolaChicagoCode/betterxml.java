package edu.luc.etl.rss.elements;

import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XPCData;

/*TODO refactor this to BasicTextElement so that I can make a BasicIntElement for things
 * like hour and day
 */
public class BasicElement extends XElement {

	public String getValue() {
		return RSSElementHelper.simplePCData(this);
	}
	
	
	public void setValue(String value) {
		this.removeAllPCData();
		try {
			this.appendChild(new XPCData(value));
		} catch (Exception e) {
			//ignoring this because it shouldn't break
		}
	}
}
