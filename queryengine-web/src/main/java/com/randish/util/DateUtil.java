package com.randish.util;

import java.util.Date;

public class DateUtil {
	public static String getDifference(Date dateStart, Date dateStop) {

		String dateDiff = "";
		try {

			// in milliseconds
			long diff = dateStop.getTime() - dateStart.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			if (diffDays > 0)
				dateDiff += diffDays + " days, ";

			if (diffHours > 0)
				dateDiff += diffHours + " hours, ";

			if (diffMinutes > 0)
				dateDiff += diffMinutes + " minutes, ";

			if (diffSeconds > 0)
				dateDiff += diffSeconds + " seconds";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateDiff;

	}
	
	public static void main(String[] args) throws InterruptedException {
		Date start = new Date();
		Thread.sleep(5000);
		System.out.println(getDifference(start, new Date()));
	}

}
