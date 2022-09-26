package Tiki;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import Common.ExtentTestManager;
import Common.TestListener;
import Constant.Constant;
import DriverWrapper.DriverManagement;
import DriverWrapper.DriverType;

public class BaseTest {
	
	@Parameters({"broswer", "runningMode"})
	@BeforeMethod
	public void beforeMethod(@Optional("Chrome") String browser, @Optional("Local") String runningMode,
			 ITestContext context, Method method) {
		ExtentTestManager.startTest(method.getName(), TestListener.testSuite.get(context.getName()));
		System.out.println("Pre-condition: Instantiate Webdriver");
		DriverManagement.createDriver(DriverType.valueOf(browser), runningMode, Constant.HUB_URL);
		DriverManagement.getDriver().manage().window().maximize();
	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("Post-condition: Quit");
		DriverManagement.getDriver().quit();
	}
	
}