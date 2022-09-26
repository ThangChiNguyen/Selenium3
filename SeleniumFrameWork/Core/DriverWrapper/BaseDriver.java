package DriverWrapper;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import CoreCommon.Constant;

abstract class BaseDriver implements WebDriver {

	protected static final Logger Logger = Constant.createLogger(BaseDriver.class.getName());
	
	protected static WebDriver getDriver() {
		return DriverManagement.getDriver();
	}
	
	@Override
	public void get(String url) {
		Logger.info(String.format("Load a new web page for the driver %s", DriverManagement.getThreadName()));
		getDriver().get(url);
	}

	@Override
	public String getCurrentUrl() {
		Logger.info(String.format("Get current url of the driver %s", DriverManagement.getThreadName()));
		return getDriver().getCurrentUrl();
	}

	@Override
	public String getTitle() {
		Logger.info(String.format("Get current title of the driver %s", DriverManagement.getThreadName()));
		return getDriver().getTitle();
	}

	@Deprecated
	public List<WebElement> findElements(By by) {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Deprecated
	public WebElement findElement(By by) {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public String getPageSource() {
		Logger.info(String.format("Get source of the last load page of the driver %s", DriverManagement.getThreadName()));
		return getDriver().getPageSource();
	}

	@Override
	public void close() {
		Logger.info(String.format("Close the current window of the driver %s", DriverManagement.getThreadName()));
		getDriver().close();
	}

	@Override
	public void quit() {
		Logger.info(String.format("Quit the driver %s", DriverManagement.getThreadName()));
		getDriver().quit();
	}

	@Deprecated
	public Set<String> getWindowHandles() {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Deprecated
	public String getWindowHandle() {
		throw new UnsupportedOperationException("This method is not supported");
	}

	@Override
	public TargetLocator switchTo() {
		Logger.info(String.format("Send commands to the different frame or window of the driver %s",
				DriverManagement.getThreadName()));
		return getDriver().switchTo();
	}

	@Override
	public Navigation navigate() {
		Logger.info(String.format("Navigate to a given url of the driver %s", DriverManagement.getThreadName()));
		return getDriver().navigate();
	}

	@Override
	public Options manage() {
		Logger.info(String.format("Get the option interface of the driver %s", DriverManagement.getThreadName()));
		return getDriver().manage();
	}

}