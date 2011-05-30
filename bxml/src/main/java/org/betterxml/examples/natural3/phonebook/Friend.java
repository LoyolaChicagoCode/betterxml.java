package org.betterxml.examples.natural3.phonebook;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.betterxml.natural3.CDataWrap;
import org.betterxml.natural3.annotations.Attribute;
import org.betterxml.natural3.annotations.CData;
import org.betterxml.natural3.annotations.Children;
import org.betterxml.natural3.annotations.Element;

@Element("Friend")
public class Friend {

	@Children(Address.class)
	List<Address> addys = new ArrayList<Address>(3);

	@CData
	private ArrayList<CDataWrap> cdataWrap = new ArrayList<CDataWrap>();

	@Attribute("nick_name")
	private String nickName;

	@Attribute("number")
	private String phoneNumber;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<CDataWrap> getCdataWrap() {
		return Collections.unmodifiableList(cdataWrap);
	}

	public void addCdataWrap(String cdata) {
		cdataWrap.add(new CDataWrap(cdata));
	}

	public void addAddy(Address address) {
		addys.add(address);
	}

	public List<Address> getAddys() {
		return addys;
	}

}