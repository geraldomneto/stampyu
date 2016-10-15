package com.starksoftware.library.util;

import java.util.List;

/**
 * 
 * @author miqueias.gomes
 *
 */
@SuppressWarnings("rawtypes")
public class ValidatorUtil {

	private ValidatorUtil() {

	}

	
	public static boolean isValid(String obj) {

		return isNotNull(obj) && isNotEmpty(obj);
	}

	public static boolean isValid(List obj) {

		return isNotNull(obj) && isNotEmpty(obj);
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isNotNull(Object obj) {
		return Boolean.FALSE.equals(isNull(obj));
	}

	public static boolean isNotEmpty(String obj) {
		return isNotNull(obj) && Boolean.FALSE.equals(obj.isEmpty());
	}

	public static boolean isNotEmpty(List obj) {
		return isNotNull(obj) && Boolean.FALSE.equals(obj.isEmpty());
	}
	
	public static boolean isNumeric(String obj) {
		return obj.matches("-?\\d+(\\.\\d+)?");
	}
}
