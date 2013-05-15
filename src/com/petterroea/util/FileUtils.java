package com.petterroea.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class FileUtils {
	public static String getMd5(File file)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			InputStream is = new FileInputStream(file);				
			byte[] buffer = new byte[8192];
			int read = 0;
			while( (read = is.read(buffer)) > 0) {
				md.update(buffer, 0, read);
			}	
			byte[] digest = md.digest();
			String res = "";
			String tmp = "";
			for (int i = 0; i < digest.length; i++) {
				tmp = (Integer.toHexString(0xFF & digest[i]));
				if (tmp.length() == 1) {
					res += "0" + tmp;
				} else {
					res += tmp;
				}
			}
			res = URLEncoder.encode(res, "UTF-8");
			return res;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String readFile(File f)
	{
		String[] contents = new String[0];
		BufferedReader br = null;
	    try {
	    	br = new BufferedReader(new FileReader(f));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        br.close();
	        return sb.toString();
	    } catch(Exception e) {
	        
	    }
	    return "";
	}
	public static boolean deleteDirectory(File directory) {
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i<files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i]);
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    return(directory.delete());
	}
}
