package edu.luc.etl.rss.elements;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.SimpleTimeZone;


public class RSSDate extends BasicElement {

	private final static int MS_IN_HOUR = 1000 * 60 * 60;
	private final static int MS_IN_MINUTE = 1000 * 60;
	
	private final static SimpleDateFormat SET_FORMAT = new SimpleDateFormat("EEE, dd MMM yy HH:mm:ss Z");
	
	/*TODO we can check if the Day of Week, if provided, matches the date, 
	 * but I say we just ignore the day of week and parse the date, time
	 */
	
	/*TODO do we want to be nice and not care about case on these valid months?
	 *   the specs at http://asg.web.cmu.edu/rfc/rfc822.html#sec-5 don't mention 
	 *   anything about case.
	 */
	
	enum MonthEnum { Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec} 
	
	enum ShortDayEnum { Sun, Mon, Tue, Wed, Thu, Fri, Sat }
	
	public void setDate(Calendar date) {
		setValue(SET_FORMAT.format(date.getTime()));
	}
	
	
	/*TODO could clean this up a lot by having a REGEX do the validating of the string*/
	public Calendar getDate() throws MalformedDateException {
		final String ds = getValue().trim();
		String dateStr = ds;
		
		String day = "";
		
		
		if (dateStr.charAt(3) == ',') {
			day = dateStr.substring(0,3);
			dateStr = dateStr.substring(4).trim();
		}
		
		int count = 1;
		int dayNum = 0;
		boolean validDay = false;
		for (ShortDayEnum d : EnumSet.range(ShortDayEnum.Sun, ShortDayEnum.Sat)) {
			if (d.toString().equals(day)) {
				dayNum = count;
				validDay = true;
				break;
			}
			count++;
		}
		if (!validDay) {
			throw new MalformedDateException("Specified day is invalid", ds);
		}
		
		String[] dateParts = dateStr.split("\\s+");
		
		int dayOfMonth;
		try {
			dayOfMonth = Integer.valueOf(dateParts[0]);
		} catch (NumberFormatException nfe) {
			throw new MalformedDateException("Where the Day of Month should be is not a valid integer", ds);
		}
		
		String monthPart = dateParts[1];
		boolean validMonth = false;
		int month = -1;
		count = 0;
		for (MonthEnum m : EnumSet.range(MonthEnum.Jan, MonthEnum.Dec)) {
			if (m.toString().equals(monthPart)) {
				month = count;
				validMonth = true;
				break;
			}
			count++;
		}
		
		if (!validMonth) {
			throw new MalformedDateException("Specified month is invalid", ds);
		}
		
		String yearPart = dateParts[2];
		if (yearPart.length() != 2 || yearPart.length() != 4) {
			throw new MalformedDateException("Specified year is invalid", ds);
		}
		if (yearPart.length() == 2) {	//since RFC822 stupidly allows for 2 digit years
			yearPart = "20" + yearPart;
		}
		int year = -1;
		try {
			year = Integer.valueOf(yearPart);
		} catch(NumberFormatException nfe) {
			throw new MalformedDateException("Specified year is invalid", ds); 
		}
		
		String timePart = dateParts[3];
		String[] timeParts = timePart.split(":");
		if (timeParts.length != 3 || timeParts.length !=2) {
			throw new MalformedDateException("Specified time is invalid", ds);
		}
		
		String hourpart = timeParts[0];
		int hour = -1;
		try {
			hour = Integer.valueOf(hourpart);
		} catch(NumberFormatException nfe) {
			throw new MalformedDateException("Specified hour is invalid", ds); 
		}
		
		if (hour < 0 || hour > 23) {
			throw new MalformedDateException("Specified hour is invalid", ds);
		}
		
		String minutePart = timeParts[1];
		int minute = -1;
		try {
			minute = Integer.valueOf(minutePart);
		} catch(NumberFormatException nfe) {
			throw new MalformedDateException("Specified minute is invalid", ds); 
		}
		
		if (minute < 0 || minute > 59) {
			throw new MalformedDateException("Specified minute is invalid", ds);
		}
		
		int second = 0;
		if (timeParts.length == 3) {
			String secondpart = timeParts[2];
			try {
				second = Integer.valueOf(secondpart);
			} catch(NumberFormatException nfe) {
				throw new MalformedDateException("Specified second is invalid", ds); 
			}
			
			if (second < 0 || second > 59) {
				throw new MalformedDateException("Specified second is invalid", ds);
			}
		}
		
		String tzPart = dateParts[4];
		int tzOffset = 0;
		if (tzPart.equals("UT") || tzPart.equals("GMT") || tzPart.equals("Z")) {
			tzOffset = 0;
		} else if (tzPart.equals("EST") || tzPart.equals("CDT")) {
			tzOffset = -5 *MS_IN_HOUR;
		} else if (tzPart.equals("EDT")) {
			tzOffset = -4 *MS_IN_HOUR;
		} else if (tzPart.equals("CST") || tzPart.equals("MDT")) {
			tzOffset = -6 *MS_IN_HOUR;
		} else if (tzPart.equals("MST") || tzPart.equals("PDT")) {
			tzOffset = -7 *MS_IN_HOUR;
		} else if (tzPart.equals("PST")) {
			tzOffset = -8 * MS_IN_HOUR;
		} else if (tzPart.equals("A")) {
			tzOffset = -1 * MS_IN_HOUR;
		} else if (tzPart.equals("B")) {
			tzOffset = -2 * MS_IN_HOUR;
		} else if (tzPart.equals("C")) {
			tzOffset = -3 * MS_IN_HOUR;
		} else if (tzPart.equals("D")) {
			tzOffset = -4 * MS_IN_HOUR;
		} else if (tzPart.equals("E")) {
			tzOffset = -5 * MS_IN_HOUR;
		} else if (tzPart.equals("F")) {
			tzOffset = -6 * MS_IN_HOUR;
		} else if (tzPart.equals("G")) {
			tzOffset = -7 * MS_IN_HOUR;
		} else if (tzPart.equals("H")) {
			tzOffset = -8 * MS_IN_HOUR;
		} else if (tzPart.equals("I")) {
			tzOffset = -9 * MS_IN_HOUR;
		} else if (tzPart.equals("K")) {
			tzOffset = -10 * MS_IN_HOUR;
		} else if (tzPart.equals("L")) {
			tzOffset = -11 * MS_IN_HOUR;
		} else if (tzPart.equals("M")) {
			tzOffset = -12 * MS_IN_HOUR;
		} else if (tzPart.equals("N")) {
			tzOffset = 1 * MS_IN_HOUR;
		} else if (tzPart.equals("O")) {
			tzOffset = 2 * MS_IN_HOUR;
		} else if (tzPart.equals("P")) {
			tzOffset = 3 * MS_IN_HOUR;
		} else if (tzPart.equals("Q")) {
			tzOffset = 4 * MS_IN_HOUR;
		} else if (tzPart.equals("R")) {
			tzOffset = 5 * MS_IN_HOUR;
		} else if (tzPart.equals("S")) {
			tzOffset = 6 * MS_IN_HOUR;
		} else if (tzPart.equals("T")) {
			tzOffset = 7 * MS_IN_HOUR;
		} else if (tzPart.equals("U")) {
			tzOffset = 8 * MS_IN_HOUR;
		} else if (tzPart.equals("V")) {
			tzOffset = 9 * MS_IN_HOUR;
		} else if (tzPart.equals("W")) {
			tzOffset = 10 * MS_IN_HOUR;
		} else if (tzPart.equals("X")) {
			tzOffset = 11 * MS_IN_HOUR;
		} else if (tzPart.equals("Y")) {
			tzOffset = 12 * MS_IN_HOUR;
		} else if (tzPart.length() == 5) {
			int multiplier = 0;
			if (tzPart.charAt(0) == '+') {
				multiplier = 1;
			} else if (tzPart.charAt(0) == '-') {
				multiplier = -1;
			} else {
				throw new MalformedDateException("Specified timezone is invalid", ds);
			}
			
			String hhStr = tzPart.substring(1,3);
			int hh = -1;
			try {
				hh = Integer.valueOf(hhStr);
			} catch (NumberFormatException nfe) {
				throw new MalformedDateException("Specified timezone is invalid", ds);
			}
			if (hh < 0 || hh > 12) {
				throw new MalformedDateException("Specified timezone is invalid", ds);
			}
			
			String mmStr = tzPart.substring(3);
			int mm = -1;
			try {
				mm = Integer.valueOf(mmStr);
			} catch (NumberFormatException nfe) {
				throw new MalformedDateException("Specified timezone is invalid", ds);
			}
			if (mm < 0 || mm > 59) {
				throw new MalformedDateException("Specified timezone is invalid", ds);
			}
			tzOffset = multiplier * ((hh * MS_IN_HOUR) + (mm * MS_IN_MINUTE));
		} else {
			throw new MalformedDateException("Specified timezone is invalid", ds);
		}
		
		SimpleTimeZone stz = new SimpleTimeZone(tzOffset, tzPart);
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, dayOfMonth);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		c.set(Calendar.MILLISECOND, 0);
		c.setTimeZone(stz);
		
		//TODO test that the day of month is right when everythign else in Calendar gets set
		
		if (!day.equals("")) {
			if (dayNum != c.get(Calendar.DAY_OF_WEEK)) {
				throw new MalformedDateException("Incorrect Day of Week is specified", ds);
			}
		}
		return c;
	}
	
	
	//TODO unittest
	protected String getAllowedDatesStr() {
		StringBuffer sb = new StringBuffer();
		for (MonthEnum m : EnumSet.range(MonthEnum.Jan, MonthEnum.Dec)) {
			sb.append(m.toString());
			sb.append(", ");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
}
