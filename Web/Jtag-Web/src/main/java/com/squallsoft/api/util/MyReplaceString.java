package com.squallsoft.api.util;

import java.text.Normalizer;

public class MyReplaceString {

	public static String formatarPermalink(String value) {
		// Persistência com JPA! -> persistencia _com_jpa
		
		String link = value.trim();
		
		link = link.toLowerCase();
		
		link = Normalizer.normalize(link, Normalizer.Form.NFD);
		
		link = link.replaceAll("\\s", "_"); // remove espaço
		
		link = link.replaceAll("\\W", ""); // remove não alfanumericos
		
		link = link.replaceAll("\\_+", "_"); // remove double underline
		
		return link;		
	}
}
