package com.petterroea.util;

import java.util.ArrayList;

public class StringUtils {
	/**
	 * Splits a string, and understands string marks(")
	 * @param string The string to split
	 * @return An array of strings.
	 */
	public static String[] split(String string) {
		ArrayList<String> args = new ArrayList<String>();
		boolean inString = false;
		int lastStart = 0;
		for(int i = 0; i < string.length(); i++)
		{
			char c = string.charAt(i);
			if(c==' '&&!inString) { args.add(string.substring(lastStart, i)); lastStart=i+1;}
			else if(c=='"') { if(inString) { inString = false; } else { inString = true; } }
		}
		if(lastStart<string.length())
		{
			args.add(string.substring(lastStart, string.length()));
		}
		String[] out = new String[args.size()];
		for(int i = 0; i < out.length; i++)
		{
			out[i]=args.get(i);
		}
		return out;
	}
}
