/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starksoftware.library.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Marcos Lisboa
 */
public abstract class EmailValidator {

	public static boolean isEmailValido(String email) {
		if (email == null || email.trim().isEmpty())
			return true;

		Pattern pattern = Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}
}
