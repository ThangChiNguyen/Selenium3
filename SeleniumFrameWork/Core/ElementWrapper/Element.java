package ElementWrapper;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.google.common.base.Stopwatch;

import DriverWrapper.Driver;
import CoreCommon.Constant;
import Common.Utilities;

public class Element extends BaseElement{

	private static final Logger Logger = Constant.createLogger(Element.class.getName());
	
	public Element(By locator) {
		super(locator);
	}
	
	public Element(String xpath) {
		super(xpath);
	}

	public Element(String originXpath, Object ... dynamicStrings) {
		super(originXpath, dynamicStrings);
	}
	
	public Element getDynamicElement(Object ... dynamicStrings) {
		String formatlocator = Utilities.removeSubString(String.format(this.getLocator().toString(), dynamicStrings), "By.xpath:");
		return new Element(formatlocator);
	}
	
	public void click() {
		try {
			Logger.info(String.format("Click on %s", this.getLocator().toString()));
			this.waitForClickable(Constant.TIMEOUT).click();
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", this.getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	public void clickUsingJs() {
		try {
			Logger.info(String.format("Click on %s by Java Script", this.getLocator().toString()));
			Driver.executeScript("arguments[0].click();", getElement());
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", this.getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	public void doubleClick() {
		Logger.info(String.format("Click on the control %s twice", this.getLocator().toString()));
		int count = 0;
		while(count < 2) {
			click();
			count++;
		}
	}
	
	public void sendKeys(String keysToSend) {
		try {
			Logger.info(String.format("Send keys to element %s", getLocator().toString()));
			this.waitForVisibility(Constant.TIMEOUT).clear();
			this.waitForVisibility(Constant.TIMEOUT).sendKeys(keysToSend);
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	public void submit() {
		try {
			Logger.info(String.format("Submit by element %s", getLocator().toString()));
			this.waitForClickable(Constant.TIMEOUT).submit();
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	public void clear() {
		try {
			Logger.info(String.format("Clear text in element %s", getLocator().toString()));
			this.waitForVisibility(Constant.TIMEOUT).clear();
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	public String getAttribute(String attributeName) {
		try {
			Logger.info(String.format("Get attribute '%s' of element %s", attributeName, getLocator().toString()));
			return getElement().getAttribute(attributeName);
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	public String getText() {
		try {
			Logger.info(String.format("Get text of element %s", getLocator().toString()));
			return this.waitForVisibility(Constant.TIMEOUT).getText();
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	public String getValue() {
		try {
			Logger.info(String.format("Get value of element %s", getLocator().toString()));
			return this.getElement().getAttribute("value");
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	public String getCssValue(String propertyName) {
		String value ="";
		try {
			Logger.info(String.format("Get Css value of element %s", getLocator().toString()));
			value = this.getElement().getCssValue(propertyName);
			return value;
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	public int getSize() {
		int size = 0;
		try {
			Logger.info(String.format("Get size of element %s", getLocator().toString()));
			if(this.getElements() != null){
				size = this.getElements().size();
			}
			return size;
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			return size;
		}
	}
	
	public String[] getAllTexts() {
		List<WebElement> listOfElement = this.getElements();
		String[] arrayName = new String[listOfElement.size()];
		try {
			Logger.info(String.format("Get all name of elements %s", getLocator().toString()));
			for(int i = 0; i < listOfElement.size(); i++) {
				arrayName[i] = listOfElement.get(i).getText();
			}			
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
		}
		return arrayName;
	}
	
	public String selectRandomValueInDropDownList(int timeOutInSeconds) {		
		try
		{
			Logger.info(String.format("Select randowm value in dropdown list of '%s'", getLocator().toString()));
			List<WebElement> webElementList = selection(timeOutInSeconds).getOptions();
			int size = webElementList.size();
			Random num = new Random();
			int i = num.nextInt(size);
			selection(timeOutInSeconds).selectByIndex(i);
			waitForDropDownListPopulated(timeOutInSeconds);
			return selection(timeOutInSeconds).getFirstSelectedOption().getText();
		} catch (StaleElementReferenceException e){
			return selectRandomValueInDropDownList(timeOutInSeconds);
		}
	}
	
	public void hoverTo(int timeOutInSeconds) {
		try {
			Logger.info(String.format("Hover to element %s", getLocator().toString()));
			Actions action = new Actions(getDriver());
			action.moveToElement(waitForVisibility(timeOutInSeconds)).build().perform();
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	public void scrollToElement() {
		try {
			Logger.info(String.format("Scroll to element %s", getLocator().toString()));
			Driver.executeScript("arguments[0].scrollIntoView(true);", this.getElement());
		} catch (Exception e) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}
	
	protected Select selection(int timeOutInSeconds) {
		Select selection = new Select(waitForVisibility(timeOutInSeconds));
		return selection;
	}
	
	public void selectByTextInDropDownList(int timeOutInSeconds, String text) {
		if (timeOutInSeconds <= 0) {
			Logger.severe("The time out is invalid. It must greater than 0");
			return;
		}
		Stopwatch sw = Stopwatch.createStarted();
		try {
			Logger.info(String.format("Select the option of the control %s by text", getLocator().toString()));
			selection(timeOutInSeconds).selectByVisibleText(text);
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) <= (long) timeOutInSeconds) {
				Logger.warning(String.format("Try to select the option of the control %s by text again",
						getLocator().toString()));
				selectByTextInDropDownList(timeOutInSeconds - (int) sw.elapsed(TimeUnit.SECONDS), text);
			}
		} catch (Exception error) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), error.getMessage()));
			throw error;
		}
	}
	
	public void selectByTextInDropDownList(String text) {
		selectByTextInDropDownList(Constant.TIMEOUT, text);
	}
	
	public void selectByIndexInDropDownList(int timeOutInSeconds, int index) {
		if (timeOutInSeconds <= 0) {
			Logger.severe("The time out is invalid. It must greater than 0");
			return;
		}
		Stopwatch sw = Stopwatch.createStarted();
		try {
			Logger.info(String.format("Select the option of the control %s by index", getLocator().toString()));
			selection(timeOutInSeconds).selectByIndex(index);
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) <= (long) timeOutInSeconds) {
				Logger.warning(String.format("Try to select the option of the control %s by index again",
						getLocator().toString()));
				selectByIndexInDropDownList(timeOutInSeconds - (int) sw.elapsed(TimeUnit.SECONDS), index);
			}
		} catch (Exception error) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), error.getMessage()));
			throw error;
		}
	}
	
	public String getSelectedOption(int timeOutInSeconds) {
		String selected = null;
		if (timeOutInSeconds <= 0) {
			Logger.severe("The time out is invalid. It must greater than 0");
			return selected;
		}
		Stopwatch sw = Stopwatch.createStarted();
		try {
			Logger.info(String.format("Get the selected option of the control %s", getLocator().toString()));
			selected = selection(timeOutInSeconds).getFirstSelectedOption().getText();
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) <= (long) timeOutInSeconds) {
				Logger.warning(String.format("Try to get the selected option of the control %s again",
						getLocator().toString()));
				return getSelectedOption(timeOutInSeconds - (int) sw.elapsed(TimeUnit.SECONDS));
			}
		} catch (Exception error) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), error.getMessage()));
			throw error;
		}
		return selected;
	}
	
	public void selectByIndexInDropDownList(int index) {
		selectByIndexInDropDownList(Constant.TIMEOUT, index);
	}
	
	public void selectByValueInDropDownList(int timeOutInSeconds, String value) {
		if (timeOutInSeconds <= 0) {
			Logger.severe("The time out is invalid. It must greater than 0");
			return;
		}
		Stopwatch sw = Stopwatch.createStarted();
		try {
			Logger.info(String.format("Select the option of the control %s by value", getLocator().toString()));
			selection(timeOutInSeconds).selectByValue(value);
		} catch (StaleElementReferenceException ex) {
			if (sw.elapsed(TimeUnit.SECONDS) <= (long) timeOutInSeconds) {
				Logger.warning(String.format("Try to select the option of the control %s by value again",
						getLocator().toString()));
				selectByValueInDropDownList(timeOutInSeconds - (int) sw.elapsed(TimeUnit.SECONDS), value);
			}
		} catch (Exception error) {
			Logger.severe(String.format("Has error with control '%s': %s", getLocator().toString(), error.getMessage()));
			throw error;
		}
	}
	
	public void selectByValue(String value) {
		selectByValueInDropDownList(Constant.TIMEOUT, value);
	}
	
	public void check() {
		while (!isSelected(Constant.TIMEOUT)) {
			click();
		}
	}
	
	public void uncheck() {
		while (isSelected(Constant.TIMEOUT)) {
			click();
		}
	}
	
	public String getSelectedOption() {
		return getSelectedOption(Constant.LONG_TIMEOUT);
	}

}