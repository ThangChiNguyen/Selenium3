package ElementWrapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Stopwatch;

import DriverWrapper.DriverManagement;
import CoreCommon.Constant;

public class BaseElement {

	private static final Logger Logger = Constant.createLogger(BaseElement.class.getName());
	protected WebElement _element = null;
	protected List<WebElement> _elements = null;
	private By _byLocator;
	
	public BaseElement(By locator) {
		this._byLocator = locator;
	}

	public BaseElement(String xpath) {
		this._byLocator = By.xpath(xpath);
	}	

	public BaseElement(String originXpath, Object ... dynamicStrings) {
		this._byLocator = By.xpath(String.format(originXpath, dynamicStrings));
	}
	
	public BaseElement(WebElement webElement, By locator) {
		this._element = webElement;
		this._byLocator = locator;
	}
	
	protected By getLocator() {
		return this._byLocator;
	} 
	
	protected WebDriver getDriver() {
		return DriverManagement.getDriver();
	}
	
	protected WebElement getElement() {
		try {
			return getDriver().findElement(this.getLocator());
		} catch (Exception e) {
			return null;
		}
	}
	
	protected List<WebElement> getElements(){
		try {
			return getDriver().findElements(this.getLocator());
		} catch (Exception e) {
			return null;
		}
	}

	protected WebElement getElement(int timeOutInSeconds) {
		return waitForPresent(timeOutInSeconds);
	}
	
	protected List<WebElement> getElements(int timeOutInSeconds) {
		return waitForAllElementsPresent(timeOutInSeconds);
	}
	
	/*=========================Element-Waiter===========================================================================*/
	
	public WebElement waitForPresent(int timeOutInSeconds) {
		try {
			Logger.info(String.format("Wait for control %s to be present in DOM with timeOut %s", getLocator().toString(),
					timeOutInSeconds));
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
			_element = wait.until(ExpectedConditions.presenceOfElementLocated(getLocator()));
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
		return _element;
	}
	
	public List<WebElement> waitForAllElementsPresent(int timeOutInSeconds) {
		try {
			Logger.info(String.format("Wait for all controls %s to be present in DOM with timeOut %s", getLocator().toString(),
					timeOutInSeconds));
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
			_elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getLocator()));
		} catch (Exception error) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), error.getMessage()));
			throw error;
		}
		return _elements;
	}
	
	public WebElement waitForClickable(int timeOutInSeconds) {
		try {
			Logger.info(String.format("Wait for control %s to be clickabled with timeout: %s", getLocator().toString(), timeOutInSeconds));
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
			_element = wait.until(ExpectedConditions.elementToBeClickable(getLocator()));
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
		return _element;
	}
	
	public void waitForInvisibility(int timeOutInSeconds) {
		try {
			Logger.info(String.format("Wait for control %s invisibility with timeOut: %s", getLocator().toString(),
					timeOutInSeconds));
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(getLocator()));
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	public WebElement waitForVisibility(int timeOutInSeconds) {
		try {
			Logger.info(String.format("Wait for control %s visibility with timeOut: %s", getLocator().toString(),
					timeOutInSeconds));
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
			_element = wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator()));
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
		return _element;
	}
	
	public List<WebElement> waitForVisibilityOfAllElements(int timeOutInSeconds) {
		try {
			Logger.info(String.format("Wait for control %s of elements visibility with timeOut: %s", getLocator().toString(),
					timeOutInSeconds));
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
			_elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getLocator()));
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
		return _elements;
	}
	
	public void waitForElementAttributeContains(String attribute, String value, int timeOutInSeconds) {
		try {
			Logger.info(String.format("Wait until control %s of element contains %s with timeout: %s", getLocator().toString(), attribute, timeOutInSeconds));
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
			wait.until(ExpectedConditions.attributeContains(getLocator(), attribute, value));
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	public void waitForDropDownListPopulated(int timeOutInSeconds) {
		try {
			Logger.info(String.format("Wait until all options in dropdown list %s are populated", getLocator().toString()));
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(getLocator())));
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
		
	} 
	
	/*=========================Check-Element===========================================================================*/
	
	public boolean isEnabled(int timeOutInSeconds) {
		boolean isEnabled = false;
		if (timeOutInSeconds <= 0) {
			Logger.severe("The time out is invalid. It must greater than 0");
			return isEnabled;
		}
		Stopwatch sw = Stopwatch.createStarted();
		try {
			Logger.info(String.format("Check if the control %s is enabled", getLocator().toString()));
			isEnabled = waitForVisibility(timeOutInSeconds).isEnabled();
		} catch (StaleElementReferenceException e) {
			if (sw.elapsed(TimeUnit.SECONDS) < (long) timeOutInSeconds) {
				Logger.warning(String.format("Try to check if the control the control %s is enabled again",
						getLocator().toString()));
				return isEnabled(timeOutInSeconds - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), error.getMessage()));
			throw error;
		}
		return isEnabled;
	}
	
	public boolean isExited(int timeOutInSeconds) {
		boolean isExited = false;
		try {
			Logger.info(String.format("Check if the control %s is existed in DOM", getLocator().toString()));
			waitForPresent(timeOutInSeconds);
			isExited = true;
		} catch (NoSuchElementException | TimeoutException ex) {
			Logger.info(String.format("The control %s is not existed in DOM", getLocator().toString()));
			isExited = false;
		} catch (Exception error) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), error.getMessage()));
			throw error;
		}
		return isExited;
	}
	
	public boolean isDisplayed(int timeOutInSeconds) {
		try {
			Logger.info(String.format("Check if control %s is displayed", getLocator().toString()));
			WebElement e = getElement(timeOutInSeconds);
			if(e != null) {
				return e.isDisplayed();
			}
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
		return false;
	}
	
	public boolean isSelected(int timeOutInSeconds) {
		try {
			Logger.info(String.format("Check if control %s is selected", getLocator().toString()));
			return getElement().isSelected();
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			return false;
		}
	}
	
	public boolean isSelectedDropDownListValueDisplayed(String value) {
		try {
			Logger.info(String.format("Check if default value list %s displays "+value, getLocator().toString()));
			Select dropDownList = new Select(getElement());
			String selectedValue = dropDownList.getFirstSelectedOption().getText();
			if(selectedValue.equals(value)) {
				return true;
			}else {
				return false;
			}					
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			return false;
		}
	}
	
	public boolean isDisplayed() {
		return isDisplayed(Constant.TIMEOUT);
	}
	
	public boolean isExited() {
		return isExited(Constant.TIMEOUT);
	}
	
	public boolean isEnabled() {
		return isEnabled(Constant.TIMEOUT);
	}
	
	public boolean isSelected() {
		return isSelected(Constant.TIMEOUT);
	}
}