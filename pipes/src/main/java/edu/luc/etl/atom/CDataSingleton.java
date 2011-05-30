package edu.luc.etl.atom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.betterxml.natural3.CDataWrap;
import org.betterxml.natural3.annotations.CData;

public abstract class CDataSingleton {

	@CData
	private List<CDataWrap> cdataWrap = new ArrayList<CDataWrap>();

	public List<CDataWrap> getCdataWrap() {
		return Collections.unmodifiableList(cdataWrap);
	}

	public void addCdataWrap(String cdata) {
		cdataWrap.add(new CDataWrap(cdata));
	}

	@Override
	public String toString() {
		return CDataWrap.reduce(cdataWrap);
	}
}
