package lib.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import lib.core.IsoConstants;

public class FileUtils implements IsoConstants {
	private static final String SEPERATOR = File.pathSeparator;
	
	private static String externalDataPath = System.getProperty("user.dir") + SEPERATOR + DATA_FOLDER;
	
	public static void setExternalDataPath(String path) {
		externalDataPath = path;
	}
	
	public static String getOS() {
		String osname = System.getProperty("os.name").toLowerCase();
		if(osname.indexOf("win")>=0) return "windows";
		if(osname.indexOf("mac")>=0) return "mac";
		if(osname.indexOf("sunos")>=0) return "windows";
		if(osname.indexOf("nix") + osname.indexOf("nux") + osname.indexOf("aix") + 3 != 0) return "unix";
		return "unknown";
	}
	
	/* Write string */
	public static void writeExternalString(String str, String externalPath) {
		writeString(str, externalDataPath + SEPERATOR + externalPath);
	}
	
	public static void writeString(String str, String path) {
		writeString(str, new File(path));
	}
	
	public static void writeString(String str, File file) {
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(str);
			bw.close();
		} catch (IOException e) {
			ConsoleLogger.error("An IO error occurred writing string: %s", str);
		}
	}
	
	/* Read String */
	public static String readExternalString(String externalPath) {
		return readString(externalDataPath + SEPERATOR + externalPath);
	}

	public static String readString(String path) {
		return readString(new File(path));
	}
	
	public static String readString(File file) {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String str="", line;
			while((line=br.readLine()) != null) {
				str += line + "\n"; 
			}
			br.close();
			return str;
		}
		catch(FileNotFoundException e) {
			ConsoleLogger.error("File cannot be found: %s", file.getAbsolutePath());
			return null;
		}
		catch(IOException e) {
			ConsoleLogger.error("An IO exception occurred reading file: %s", file.getAbsolutePath());
			return null;
		}
	}
}
