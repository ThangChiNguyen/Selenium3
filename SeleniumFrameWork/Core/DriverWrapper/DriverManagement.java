package DriverWrapper;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;

import CoreCommon.Constant;

public class DriverManagement {

	private static final Logger Logger = Constant.createLogger(DriverManagement.class.getName());
	private static ConcurrentHashMap<String, WebDriver> driverThreadMap = new ConcurrentHashMap<String, WebDriver>();

	public static void createDriver(DriverType type, String runningMode, String... hub) {
		if (runningMode.equalsIgnoreCase("remote")) {
			new Driver(type, runningMode, hub[0]);
		} else if (runningMode.equalsIgnoreCase("local")) {
			new Driver(type, runningMode, null);
		}
	}

	public static String getThreadName() {
		return Thread.currentThread().getName() + "-" + Thread.currentThread().getId();
	}

	public static WebDriver getDriver() {
		return driverThreadMap.get(getThreadName());
	}

	public static void addDriver(WebDriver driver) throws Exception {
		try {
			Logger.info("DriverManagement is adding driver on thread " + getThreadName());
			driverThreadMap.put(getThreadName(), driver);
		} catch (Exception e) {
			// TODO: handle exception
			Logger.severe(e.getMessage());
		}
	}
	
}