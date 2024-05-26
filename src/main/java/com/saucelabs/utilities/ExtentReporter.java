package com.saucelabs.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter
{
	 public static ExtentReports getExtentReporter()
     {  
		 SimpleDateFormat ft = new SimpleDateFormat("d-MMM-YY HH-mm-ss"); 
	     String strDate = ft.format(new Date());
	 
		 String reportPath = "Reports" + File.separator + "SparkReport " + strDate + File.separator + "TestNG" + File.separator + "ExtentReportsTestNG.html";
		 ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
		 reporter.config().setDocumentTitle("SauceLabs Ecommerce App");
		 reporter.config().setReportName("Appium Test Reports");
		 
		 ExtentReports extent = new ExtentReports();
		 extent.attachReporter(reporter);
		 extent.setSystemInfo("Tester", "Anamitra Deb");

		 return extent;
	}
}
