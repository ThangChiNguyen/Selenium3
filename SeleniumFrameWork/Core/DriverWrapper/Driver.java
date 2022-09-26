package DriverWrapper;

import java.net.URL;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import CoreCommon.Constant;

public class Driver extends BaseDriver {
	
	private static final Logger Logger = Constant.createLogger(Driver.class.getName());
	private WebDriver driver;
	private static DesiredCapabilities capabilities;

	public Driver(DriverType type, String runningMode, String hub) {
		try {
			Logger.info(String.format("Creating new %s driver", type.getValue()));
			switch (type.getValue().toLowerCase()) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver", Constant.CHROME_PATH);
				if(runningMode.equalsIgnoreCase("remote")) {
					capabilities = DesiredCapabilities.chrome();
					driver = new RemoteWebDriver(new URL(hub), capabilities);
				}else if (runningMode.equalsIgnoreCase("local")){
					driver = new ChromeDriver();
				}
				break;
			case "firefox":
				System.setProperty("webdriver.gecko.driver", Constant.FIREFOX_PATH);
				if(runningMode.equalsIgnoreCase("remote")) {
					capabilities = DesiredCapabilities.firefox();
					driver = new RemoteWebDriver(new URL(hub), capabilities);
				}else if (runningMode.equalsIgnoreCase("local")) {
					driver = new FirefoxDriver();
				}
				break;
			default:
				System.out.println(String.format("Invalid driver value => '%s'", type.getValue()));
				break;
			}
			DriverManagement.addDriver(driver);
		} catch (Exception e) {
			Logger.severe(e.getMessage());
		}
	}

	private static JavascriptExecutor jsExecutor() {
		return ((JavascriptExecutor) getDriver());
	}

	public static Object executeScript(String script, Object... args) {
		return jsExecutor().executeScript(script);
	}

}