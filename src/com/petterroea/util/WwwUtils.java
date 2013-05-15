package com.petterroea.util;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.Certificate;
import javax.net.ssl.HttpsURLConnection;

public class WwwUtils {
	public static String executeHttpsPost(String url, String data, InputStream key)
	{
	    HttpsURLConnection localHttpsURLConnection = null;
	    try
	    {
	    	URL localURL = new URL(url);
	    	localHttpsURLConnection = (HttpsURLConnection)localURL.openConnection();
	    	localHttpsURLConnection.setRequestMethod("POST");
	    	localHttpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

	    	localHttpsURLConnection.setRequestProperty("Content-Length", "" + Integer.toString(data.getBytes().length));
	    	localHttpsURLConnection.setRequestProperty("Content-Language", "en-US");

	    	localHttpsURLConnection.setUseCaches(false);
	    	localHttpsURLConnection.setDoInput(true);
	    	localHttpsURLConnection.setDoOutput(true);

	    	localHttpsURLConnection.connect();
	    	Certificate[] arrayOfCertificate = localHttpsURLConnection.getServerCertificates();

	    	byte[] arrayOfByte1 = new byte[294];
	    	DataInputStream localDataInputStream = new DataInputStream(key);
	    	localDataInputStream.readFully(arrayOfByte1);
	    	localDataInputStream.close();

	    	Certificate localCertificate = arrayOfCertificate[0];
	    	PublicKey localPublicKey = localCertificate.getPublicKey();
	    	byte[] arrayOfByte2 = localPublicKey.getEncoded();

	    	for (int i = 0; i < arrayOfByte2.length; i++) {
	    		if (arrayOfByte2[i] != arrayOfByte1[i]) throw new RuntimeException("Public key mismatch");
	    	}

	    	DataOutputStream localDataOutputStream = new DataOutputStream(localHttpsURLConnection.getOutputStream());
	    	localDataOutputStream.writeBytes(data);
	    	localDataOutputStream.flush();
	    	localDataOutputStream.close();

	    	InputStream localInputStream = localHttpsURLConnection.getInputStream();
	    	BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));

	    	StringBuffer localStringBuffer = new StringBuffer();
	    	String str1;
	    	while ((str1 = localBufferedReader.readLine()) != null) {
	    		localStringBuffer.append(str1);
	    		localStringBuffer.append('\r');
	    	}
	    	localBufferedReader.close();

	    	return localStringBuffer.toString();
	    }
	    catch (Exception localException)
	    {
	    	byte[] arrayOfByte1;
	    	localException.printStackTrace();
	    	return null;
	    }
	    finally
	    {
	    	if (localHttpsURLConnection != null)
	    		localHttpsURLConnection.disconnect();
	    }
	}
	public static String executePost(String targetURL, String urlParameters)
	{
		try{
			HttpURLConnection connection = (HttpURLConnection) new URL(targetURL).openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			//connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			OutputStream output = null;
			try {
				output = connection.getOutputStream();
			    output.write(urlParameters.getBytes());
			} finally {
				if (output != null) try { output.flush(); output.close(); } catch (IOException logOrIgnore) {}
			}
			InputStream response = connection.getInputStream();
			String contentType = connection.getHeaderField("Content-Type");
			String responseStr = "";
			if (true) {
				BufferedReader reader = null;
				    try {
				        reader = new BufferedReader(new InputStreamReader(response));
				        for (String line; (line = reader.readLine()) != null;) {
				            //System.out.println(line);
				        	responseStr = responseStr + line;
				        	Thread.sleep(2);
				        }
				    } finally {
				        if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
				    }
				} else {
				    // It's likely binary content, use InputStream/OutputStream.
					System.out.println("Binary content");
				}
				return responseStr;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static void downloadFile(URL url, File target, String filename, DownloadSpeedInterface i)
	{
		try {
			int len = url.openConnection().getContentLength();
			InputStream in = url.openStream();
			target.getParentFile().mkdirs();
			target.delete();
			target.createNewFile();
			FileOutputStream out = new FileOutputStream(target);
			byte[] buffer = new byte[153600];
			int totalBytesRead = 0;
			int bytesRead = 0;
			long lastSpeedUpdate = System.currentTimeMillis();
			while((bytesRead = in.read(buffer)) > 0)
			{
				out.write(buffer,  0, bytesRead);
				buffer = new byte[153600];
				totalBytesRead += bytesRead;
				float kb=((float)bytesRead/1024.0f)*8.0f;
				float kbSec = kb*((float)(System.currentTimeMillis()-lastSpeedUpdate)/1000.0f);
				i.setNewSpeed(kbSec);
				lastSpeedUpdate = System.currentTimeMillis();
			}
			in.close();
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
		    try {
		        desktop.browse(uri);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	}
	public static void openWebpage(URL url) {
		try {
		    openWebpage(url.toURI());
		} catch (URISyntaxException e) {
		    e.printStackTrace();
		}
	}
	
}
