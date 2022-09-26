package Common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import DriverWrapper.DriverManagement;
import Constant.Constant;
import DriverWrapper.Driver;

public class Utilities {

	public static String getProjectPath() {
		return System.getProperty("user.dir");
	}
	
	public static String getDateNow(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		return formatter.format(date);
	}

	public static String takeScreenShot(String filename, String filepath) throws Exception {
		String path = "";
		try {
			TakesScreenshot scrShot = ((TakesScreenshot) DriverManagement.getDriver());
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile = new File(filepath + File.separator + filename + ".png");
			FileUtils.copyFile(SrcFile, DestFile);
			path = DestFile.getAbsolutePath();
		} catch (Exception e) {
			System.err.println("An error occurred when capturing screen shot: " + e.getMessage());
		}
		return path;
	}
	
	public static void waitForPageLoad(int timeOutInSecond) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return (Driver.executeScript("return document.readyState").equals("complete"));
			}
		};
		WebDriverWait wait = new WebDriverWait(DriverManagement.getDriver(), timeOutInSecond);
		wait.until(pageLoadCondition);
	}

	private static Alert switchToAlert() {
		new WebDriverWait(DriverManagement.getDriver(), Constant.DEFAULT_TIMEOUT).until(ExpectedConditions.alertIsPresent());
		return DriverManagement.getDriver().switchTo().alert();
	}

	public static boolean isAlertPresent() {
		boolean foundAlert = false;
		WebDriverWait wait = new WebDriverWait(DriverManagement.getDriver(), Constant.DEFAULT_TIMEOUT);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
		} catch (TimeoutException eTO) {
			foundAlert = false;
		}
		return foundAlert;
	}

	public static String getAlertMessage() {
		return switchToAlert().getText();
	}

	public static void acceptAlert() {
		switchToAlert().accept();
	}

	public static void dismissAlert() {
		switchToAlert().dismiss();
	}

	public static String convertToNbsp(String input) {
		return String.format(input).replaceAll(" ", "�");
	}

	public static String[] splitString(String string, String delimiter) {
		return string.trim().split(delimiter);
	}

	public static String replaceEscapeCharacters(String string) {
		final String[] characters = { "\\", "^", "$", "{", "}", "[", "]", "(", ")", ".", "*", "+", "?", "|", "<",
				">", "-", "&", "%" };
		for (int i = 0; i < characters.length; i++) {
			if (string.contains(characters[i])) {
				string = string.replace(characters[i], "\\").trim();
			}
		}

		return string;
	}

	public static String getDriverTitle() {
		return DriverManagement.getDriver().getTitle();
	}

	public static Object[] removeStringFromArrayByIndex(Object[] array, int index) {
		if (array == null || index < 0 || index >= array.length) {
			return array;
		}
		Object[] anotherArray = new String[array.length - 1];
		for (int i = 0, k = 0; i < array.length; i++) {

			// if the index is
			// the removal element index
			if (i == index) {
				continue;
			}
			anotherArray[k++] = array[i];
		}
		return anotherArray;
	}

	public static boolean isContains(String[] outer, String[] inner) {
		return Arrays.asList(outer).containsAll(Arrays.asList(inner));
	}
	
	public static boolean isContains(String[] outer, String string) {
		return Arrays.asList(outer).contains(string);
	}

	public static boolean isItemExistedInList(String[] array, String item) {
		boolean existed = false;
		List<String> list = Arrays.asList(array);
		if (list.contains(item)) {
			existed = true;
		}
		return existed;
	}
	
	public static boolean isSorted(String[] array){
        boolean isSorted=true;
        for(int i=1;i < array.length;i++){
            if(array[i-1].toLowerCase().compareTo(array[i].toLowerCase()) > 0){
                isSorted= false;
                break;
            }
        }
        return isSorted;
    }
	
	public static String replaceCharFromStringByIndex(String str, int index, char replace) { 
        StringBuilder string = new StringBuilder(str);
        string.setCharAt(index, replace);
        return string.toString();
	}
	
	public static String removeSubString(String originalString, String subString) {
		String newString = originalString.replace(subString, "").trim();
		return newString;
	}
	
	public static String removeAllCharacterInString(String originalString, String character) {
		String newString = originalString.replaceAll(character, "").trim();
		return newString;
	}
}