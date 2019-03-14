package lib.core;

import com.programmer.igoodie.utils.io.FileUtils;

public interface IsoConstants {
	public static final String LIBRARY_NAME = "Isom-engine";
	public static final boolean DEVELOPER_MODE = true;
	
	/*
	 * 0=Alpha(a), 1=Beta(b), 2=Release Candidate(rc), 3=Commercial Distribution(r)
	 * 2.1.3.2 == 2.1-r2
	 * 3.2.1 == 3.2a
	 */
	public static final String VERSION_MAJOR = 		"0"; // UI || OS changes
	public static final String VERSION_MINOR = 		"0"; // New features
	public static final String VERSION_REVISION = 	"0"; // No new functionality, but fix or revision
	public static final String VERSION_BUILD = 		"5"; // Incremented for every build
	public static final String VERSION = String.format("v%s.%s.%s.%s", VERSION_MAJOR, VERSION_MINOR, VERSION_REVISION, VERSION_BUILD);
	
	public static final String DATA_FOLDER = "data"; //../data
	public static final String EXTERNAL_DATA_PATH = System.getProperty("user.dir") + FileUtils.SEPERATOR + DATA_FOLDER;

	public static final int STD_CAMERA_COUNT = 2;
}