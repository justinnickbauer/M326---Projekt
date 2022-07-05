package ch.justinbauer;

import ch.justinbauer.exceptions.DateFormatException;

import java.util.Calendar;

public class Date implements Comparable<Date> {
	public static final byte[] MONTH_MAX_DAYS = new byte[] {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private int fullDate = 0;

	public Date(String date) throws DateFormatException {
		if (date.contains(".")) {
			String[] parts = date.split("\\.");
			
			if (parts.length != 3) {
				throw new DateFormatException("Format Wrong");
			}
			
			try {
				this.fullDate = Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]) * 100 + Integer.parseInt(parts[2]) * 10000;
			}
			catch (NumberFormatException e) {
				throw new DateFormatException("Format Wrong");
			}
			
			this.check();
		}
		else {
			throw new DateFormatException("Format Wrong");
		}
	}

	public Date(int day, int month, int year) throws DateFormatException {
		this.fullDate = day + month * 100 + year * 10000;
		this.check();
	}

	public Date(int day, int month) throws DateFormatException {
		this(day, month, Date.getCurrentDate().getYear());
	}

	public Date(int day) throws DateFormatException {
		this(day, Date.getCurrentDate().getMonth(), Date.getCurrentDate().getYear());
	}

	public Date() {
		this(Date.getCurrentDate().getDay(), Date.getCurrentDate().getMonth(), Date.getCurrentDate().getYear());
	}

	public void check() throws DateFormatException {
		int day = this.getDay();
		int month = this.getMonth();
		int year = this.getYear();
		
		if ((year) < 1) {
			throw new DateFormatException("Wrong Year");
		}
		
		if ((month < 1) || (month > 12)) {
			throw new DateFormatException("Wrong Month");
		}
		
		if (day > Date.getDaysForMonth(month, year)) {
			throw new DateFormatException("Wrong Tag");
		}
	}

	public String format() {
		StringBuilder s = new StringBuilder();
		
		s.append(this.getDay());
		s.append(".");
		s.append(this.getMonth());
		s.append(".");
		s.append(this.getYear());
		
		return s.toString();
	}

	public Date getFollowingDate() throws DateFormatException {
		return this.getFollowingDate(1);
	}

	public Date getFollowingDate(int daysJump) throws DateFormatException {
		int year = this.getYear();
		int month = this.getMonth();
		int day = this.getDay();
		int maxDays = Date.getDaysForMonth(month, year);
		int signumDaysJump = daysJump / Math.abs(daysJump);
		
		if (signumDaysJump == 1) {
			for (int i = 0; i < daysJump; i++) {
				day++;
				
				if (day > maxDays) {
					day = 1;
					month++;
					
					if (month > 12) {
						month = 1;
						year++;
					}
					
					maxDays = Date.getDaysForMonth(month, year);
				}
			}
		}
		else if (signumDaysJump == -1) {
			for (int i = 0; i > daysJump; i--) {
				day--;
				
				if (day < 1) {
					month--;
					
					if (month < 1) {
						month = 12;
						year--;
					}
					
					day = Date.getDaysForMonth(month, year);
				}
			}
			
			if (year < 1) {
				throw new DateFormatException("Bad Year");
			}
		}
		
		return new Date(day, month, year);
	}

	public int delta(Date secondDate) throws DateFormatException {
		int daysDiff = 0;
		
		if (this.compareTo(secondDate) > 0) {
			throw new DateFormatException("Illegal Date");
		}
		
		while (!this.equals(secondDate)) {
			daysDiff++;
			
			secondDate = secondDate.getFollowingDate(-1);
		}
		
		return daysDiff;
	}

	public int getFullDate() {
		return this.fullDate;
	}

	public int getDay() {
		return this.getFullDate() % 100;
	}

	public int getMonth() {
		return (this.getFullDate() / 100) % 100;
	}

	public int getYear() {
		return this.getFullDate() / 10000;
	}

	public int compareTo(Date date) {
		return (int) Math.signum(this.getFullDate() - date.getFullDate());
	}

	@Override
	public String toString() {
		return this.format();
	}

	@Override
	public boolean equals(Object obj) {
		if ((null == obj) || (obj.getClass() != Date.class)) {
			return false;
		}
		
		return ((Date) obj).getFullDate() == this.getFullDate();
	}

	public static Date getCurrentDate() throws DateFormatException {
		Calendar calender = Calendar.getInstance();
		
		return new Date(calender.get(Calendar.DAY_OF_MONTH), calender.get(Calendar.MONTH) + 1, calender.get(Calendar.YEAR)); 
	}

	public static int getDaysForMonth(int month, int year) throws DateFormatException {
		if ((month < 1) || (month > 12)) {
			throw new DateFormatException("Bad Month");
		}
		
		if (year < 1) {
			throw new DateFormatException("Bad Year");
		}
		
		return Date.MONTH_MAX_DAYS[month - 1] - (((month == 2) && !Date.isLeapYear(year)) ? 1 : 0);
	}

	public static boolean isLeapYear(int year) throws DateFormatException {
		if (year < 1) {
			throw new DateFormatException("Leap Year");
		}
		
		return (((year % 400) == 0) || (((year % 4) == 0) && ((year % 100) != 0)));
	}
}