package com.randish.util;

public class StringUtil {
	
	/**
	 * checks whether the string has blank or not
	 * 
	 * @param str
	 * @return true if the given string is null or blank
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}
	
	public static boolean isBlank(String... strs) {
		for (String str: strs) {
			if (isBlank(str))
				return true;
		}
		return false;
	}
	
	
	
	/**
	 * checks whether the string has value or not
	 * @param str
	 * @return true if the given string has some value
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	
	
	/**
	 * checks whether all string has values or not
	 * @param strs
	 * @return true if the given strings has values
	 */
	public static boolean isNotBlank(String... strs) {
		for (String str: strs) {
			if (isBlank(str))
				return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println("Is blank " + isBlank(""));
		System.out.println("Is blank with array" + isBlank("", "rajesh"));
		System.out.println("Is not blank " + isNotBlank("anth"));
		System.out.println("Is not blank with array" + isNotBlank("anthari", "rajesh"));
	}
	
}