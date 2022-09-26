package Common;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class TestListener implements ITestListener {

	public static ConcurrentHashMap<String, ExtentTest> testSuite = new ConcurrentHashMap<String, ExtentTest>();

	public void onStart(ITestContext context) {
		System.out.println("*** Test Suite " + context.getName() + " started ***");
		// Handle Report
		if (!ExtentTestManager.isTestExisted(context.getName())) {
			ExtentTest tmpSuite = ExtentTestManager.startTest(context.getName(), null);
			testSuite.put(context.getName(), tmpSuite);
		}
	}

	public void onTestStart(ITestResult result) {
		System.out.println(("*** Running test: " + result.getMethod().getMethodName() + " ..."));
		ExtentTestManager.getTest().assignCategory(result.getTestContext().getName());
	}

	public void onFinish(ITestContext context) {
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
		ExtentTestManager.endTest();
	}

	public void onTestSuccess(ITestResult result) {
		ExtentTestManager.getTest().log(Status.PASS,
				"*** TEST EXECUTION COMPLETE - PASSED: " + result.getMethod().getMethodName());
	}

	public void onTestFailure(ITestResult result) {

		if (result.getThrowable() instanceof AssertionError) {
			ExtentTestManager.getTest().fail("*** TEST EXECUTION COMPLETE - FAILED: "
					+ result.getMethod().getMethodName() + " - " + result.getThrowable().getMessage());
		} else {
			// capture screenshot
			String screenshotFileName = UUID.randomUUID().toString();
			String screenshotFilePath = "";
			try {
				screenshotFilePath = Utilities.takeScreenShot(screenshotFileName,
						ExtentReportManager.getScreenshotFolder());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// attach screenshots to report
			try {
				ExtentTestManager.getTest()
						.error("*** TEST EXECUTION COMPLETE - ERROR: " + result.getMethod().getMethodName() + " - "
								+ result.getThrowable().getMessage(),
								MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build());
			} catch (IOException e) {
				ExtentTestManager.getTest().error("*** TEST EXECUTION COMPLETE - ERROR: "
						+ result.getMethod().getMethodName() + " - " + result.getThrowable().getMessage());
				Logger.info("An exception occured while taking screenshot " + e.getCause());
			}
		}
	}

	public void onTestSkipped(ITestResult result) {
		Logger.info("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		Logger.info("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}
	
}