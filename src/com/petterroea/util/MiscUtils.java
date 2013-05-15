package com.petterroea.util;

public class MiscUtils {
	private static OS getPlatform() {
		  String osName = System.getProperty("os.name").toLowerCase();
		  if (osName.contains("win")) return OS.windows;
		  if (osName.contains("mac")) return OS.macos;
		  if (osName.contains("solaris")) return OS.solaris;
		  if (osName.contains("sunos")) return OS.solaris;
		  if (osName.contains("linux")) return OS.linux;
		  if (osName.contains("unix")) return OS.linux;
		  return OS.unknown;
	}
	public static String getOsName()
	{
		OS platform = getPlatform();
		if(platform==OS.windows) return "windows";
		if(platform==OS.linux) return "linux";
		if(platform==OS.macos) return "macosx";
		if(platform==OS.solaris) return "solaris";
		return "unknown";
	}
	private static enum OS
	{
		linux, solaris, windows, macos, unknown;
	}
}
