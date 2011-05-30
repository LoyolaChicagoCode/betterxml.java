package edu.luc.etl.rss.elements;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.xelement.XElement;

public class RSS extends XElement {

	private final static String CHANNEL_TAG_STR = "channel";
	
	
	public List<Channel> getChannels() {
		List<Channel> channels = new ArrayList<Channel>();
		for (XElement element : getChildrenElements(CHANNEL_TAG_STR)) {
			channels.add((Channel)element);
		}
		return new ArrayList<Channel>();
	}
	
	public void setChannels(List<Channel> channels) {
		this.removeAllElements(CHANNEL_TAG_STR);
		appendChildren(channels);
	}
	
	
	public void addChannel(Channel channel) {
		appendChild(channel);
	}

	
	public void removeChannel(Channel channel) {
		this.removeChild(channel);
	}
	
	
	public void removeAllChannels() {
		this.removeAllElements(CHANNEL_TAG_STR);
	}
}
