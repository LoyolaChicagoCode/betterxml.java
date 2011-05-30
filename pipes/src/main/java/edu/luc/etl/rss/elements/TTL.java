package edu.luc.etl.rss.elements;

import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XPCData;

/**It's a number of minutes that indicates how long a channel
 *  can be cached before refreshing from the source. This makes 
 *  it possible for RSS sources to be managed by a file-sharing 
 *  network such as Gnutella.*/
public class TTL extends XElement {

	/* FIXME this might not be the right thing to default to,
	 * but good enough for a first go. And, no TTL means it lives forever,
	 * so MAX_VALUE is as good as we can get on forever (unless I go with a Long) */
	public final static int DEFAULT_VALUE = Integer.MAX_VALUE; 
	
	public int getValue() {
		String str = RSSElementHelper.simplePCData(this);
		try {
			return Integer.valueOf(str);
		} catch (NumberFormatException nfe) {
			return DEFAULT_VALUE;
		}
	}
	
	//TODO should we dummy check for no negative values because those really don't make sense here
	public void setValue(int value) {
		this.removeAllPCData();
		try {
			this.appendChild(new XPCData(Integer.toString(value)));
		} catch (Exception e) {
			//ignoring this because it shouldn't break
		}
	}
}
