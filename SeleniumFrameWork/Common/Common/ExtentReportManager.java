package Common;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	
	private static ExtentReports extent;
	private static String reportFileName = "Test-Automation-Report " + Utilities.getDateNow("MM.dd.yyyy - HH.mm.ss")
	+ ".html";
	private static String fileSeperator = System.getProperty("file.separator");
	private static String reportFilepath = System.getProperty("user.dir") + fileSeperator + "TestReport";
	private static String reportFileLocation = reportFilepath + fileSeperator + reportFileName;
	
	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance();
		return extent;
	}
	
	public static ExtentReports createInstance() {
		String fileName = getReportPath(reportFilepath);
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(reportFileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(reportFileName);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		// Set environment details
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("AUT", "QA");
		return extent;
	}
	
	public static void flushReport() {
		System.out.println("Report: " + reportFileLocation + " is created!");
		getInstance().flush();  
	}
	
	// Create the report path
	private static String getReportPath(String path) {
		File testDirectory = new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				System.out.println("Report directory: " + path + " is created!");
				return reportFileLocation;
			} else {
				System.out.println("Failed to create report directory: " + path);
				return System.getProperty("user.dir");
			}
		} else {
			System.out.println("Report directory already exists: " + path);
		}
		return reportFileLocation;
	}

	public static synchronized String getScreenshotFolder() {
		String path = reportFilepath + fileSeperator + "screenshots";
		File output = new File(path);
		if (!output.exists())
			output.mkdir();
		return path;
	}
		
}