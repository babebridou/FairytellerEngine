package com.fairyteller.utilities.reflection;

import java.lang.reflect.Method;


/**
 * Simple Helper class for performing some basic reflection
 * Convention for getters/setters is as follow:
 * - is/set for boolean and Boolean classes
 * - get/set for other properties
 * 
 * @author tphilipakis
 *
 */
public class ReflectHelper {

	private static final String GET_PREFIX = "get";
	private static final String IS_PREFIX = "is";
	private static final String SET_PREFIX = "set";

	public static Method parseGetter(Object bean, String propertyName,
			Class<?> propertyClass) throws SecurityException,
			NoSuchMethodException {
		return bean.getClass().getMethod(
				getGetMethodName(propertyName, propertyClass));

	}

	public static Method parseSetter(Object bean, String propertyName,
			Class<?> propertyClass) throws SecurityException,
			NoSuchMethodException {
		return bean.getClass().getMethod(
				getSetMethodName(propertyName, propertyClass));
	}

	private static String capitalizeMethodName(String propertyName) {
		StringBuilder sb = new StringBuilder();
		sb.append(Character.toUpperCase(propertyName.charAt(0)));
		sb.append(propertyName.substring(1));
		return sb.toString();
	}

	public static String getSetMethodName(String propertyName,
			Class<?> propertyClass) {
		return SET_PREFIX + capitalizeMethodName(propertyName);
	}

	public static String getGetMethodName(String propertyName,
			Class<?> propertyClass) {
		if (propertyClass == boolean.class) {
			return IS_PREFIX + capitalizeMethodName(propertyName);
		} else if (propertyClass == Boolean.class) {
			return IS_PREFIX + capitalizeMethodName(propertyName);
		} else {
			return GET_PREFIX + capitalizeMethodName(propertyName);
		}
	}
}
