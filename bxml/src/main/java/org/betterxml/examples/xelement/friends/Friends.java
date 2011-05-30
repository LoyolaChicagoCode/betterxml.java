package org.betterxml.examples.xelement.friends;

import java.util.List;

import org.betterxml.xelement.XElement;

public class Friends extends XElement implements FriendsProcessor {

	public Friends() {
		super();
	}
	
	
	public String getPhoneNumber(String id) {
		List<XElement> friends = getChildrenElements("Friend");
		String phoneNumber = null;
		for (XElement friend : friends ) {
			phoneNumber = ((FriendsProcessor)friend).getPhoneNumber(id);
			if (phoneNumber != null) {
				return phoneNumber;
			}
		}
		return "ID does not exist in friends directory.";
	}
	
	
	public String getEmail(String id) {
		List<XElement> friends = getChildrenElements("Friend");
		String email = null;
		for (XElement friend : friends ) {
			email = ((FriendsProcessor)friend).getEmail(id);
			if (email != null) {
				return email;
			}
		}
		return "ID does not exist in friends directory";
	}
	
}
