package com.starksoftware.library.cdi;

import javax.naming.InitialContext;

public class ContainerUtil {
	private static ContainerUtil containerUtil;
	private final String EJB_STRING = "java:global/stampyu-web/stampyu-1.0";

	private ContainerUtil() {

	}

	public static ContainerUtil getInstance() {
		if (containerUtil == null)
			containerUtil = new ContainerUtil();

		return containerUtil;
	}

	public Object lookup(String ejbName) {
		return doLookup(ejbName);
	}

	public Object lookup(Class<?> classOfLookup) {
		return doLookup(classOfLookup.getSimpleName());
	}

	private Object doLookup(String ejbName) {
		Object ejbLookup = null;

		try {
			InitialContext ctx = new InitialContext();
			ejbLookup = ctx.lookup(String.format(EJB_STRING, ejbName));
		} catch (Exception e) {
			ejbLookup = null;
		}

		return ejbLookup;
	}
}
