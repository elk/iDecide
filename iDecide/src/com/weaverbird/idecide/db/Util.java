package com.weaverbird.idecide.db;

public class Util {
	
	public static boolean isEmptyString(String str) {
		if (str == null) {
			return true;
		}
		if (str.trim().length() == 0) {
			return true;
		}
		return false;
	}

}
