package org.betterxml.examples.natural3.phonebook;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.betterxml.natural3.annotations.Children;
import org.betterxml.natural3.annotations.Element;

@Element("Friends")
public class Friends {
	
	@Children(Friend.class)
	private ArrayList<Friend> friends = new ArrayList<Friend>();

	public List<Friend> getFriends(){
		return Collections.unmodifiableList(friends);
	}
	
	public void addFriend(Friend friend) {
		friends.add(friend);
	}
}