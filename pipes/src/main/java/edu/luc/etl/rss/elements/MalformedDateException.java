package edu.luc.etl.rss.elements;

public class MalformedDateException extends Exception {

	private final String dateStr;
	
	public MalformedDateException(String message, String dateStr) {
		this.dateStr = dateStr;  
	}
	
	public String getDateStr() {
		return dateStr;
	}
	
}
