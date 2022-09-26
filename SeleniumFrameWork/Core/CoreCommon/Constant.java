package CoreCommon;

import java.util.logging.Logger;

import Common.Utilities;

public class Constant {
	
	public static final int TIMEOUT = 30;
	public static final int SHORT_TIMEOUT = 5;
	public static final int VERY_SHORT_TIMEOUT = 3;
	public static final int LONG_TIMEOUT = 60;
	public static final String CHROME_PATH = Utilities.getProjectPath() + "\\Executables\\chromedriver.exe";
	public static final String FIREFOX_PATH = Utilities.getProjectPath() + "\\Executables\\geckodriver.exe";
	
	static {
		String path = Utilities.getProjectPath() + "\\Core\\CoreCommon\\logger.properties";
		System.setProperty("java.util.logging.config.file", path);
	}
	
	// Creates the logger.
	public static final Logger createLogger(String className) {
		return Logger.getLogger(className);
	}
	
}