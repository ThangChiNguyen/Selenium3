package Common;

import java.util.concurrent.ConcurrentHashMap;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {

	static ConcurrentHashMap<String, ExtentTest> extentTestMap = new ConcurrentHashMap<String, ExtentTest>();
	static ExtentReports extent = ExtentReportManager.getInstance();
	
	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get(String.valueOf(Thread.currentThread().getId()));
	}
	
	public static synchronized void endTest() {
		ExtentReportManager.flushReport();
	}
	
	public static synchronized ExtentTest startTest(String testName, ExtentTest parent) {
		try {
			ExtentTest test;
			if (parent != null) {
				test = parent.createNode(testName);
				extentTestMap.put(String.valueOf(Thread.currentThread().getId()), test);
			} else {
				test = extent.createTest(testName);
				extentTestMap.put(String.valueOf(Thread.currentThread().getId()), test);
			}
			return test;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static synchronized boolean isTestExisted(String key) {
		return extentTestMap.containsKey(key);
	}
	
}