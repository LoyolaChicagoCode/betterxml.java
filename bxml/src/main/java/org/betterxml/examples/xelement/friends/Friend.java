package org.betterxml.examples.xelement.friends;

import org.betterxml.xelement.XElement;

public class Friend extends XElement implements FriendsProcessor {

	public Friend() {
		super();
	}
	

	public String getPhoneNumber(String id) {
		if (attributes.getAttributeValue("id").equals(id)) {
			if (attributes.containsAttribute("phone")) {
				return attributes.getAttributeValue("phone");
			} else {
				return "No phone number saved.";
			}
		}
		return null;
	}
	
	
	public String getEmail(String id) {
		if (attributes.getAttributeValue("id").equals(id)) {
			if (attributes.containsAttribute("email")) {
				return attributes.getAttributeValue("email");
			} else {
				return "No email saved.";
			}
		}
		return null;
	}
	
}
