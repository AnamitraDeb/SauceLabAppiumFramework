package com.saucelabs.listeners;


import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.saucelabs.testutils.BaseTest;
import com.saucelabs.utilities.ExtentReporter;

import io.appium.java_client.AppiumDriver;

public class TestNGListener implements ITestListener 
{
	
	public AppiumDriver driver;
	ExtentReports extent = ExtentReporter.getExtentReporter();
	ExtentTest test;

	@Override
	public void onTestStart(ITestResult result)
	{
		test = extent.createTest(result.getMethod().getMethodName());
		test.assignCategory("Android Test");
		test.assignAuthor("Anamitra Deb");
	}

	@Override
	public void onTestSuccess(ITestResult result)
	{
	   test.log(Status.PASS, "TEST PASSED");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		try 
		{
			driver=(AppiumDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		String base64FilePath = "";
		try
		{
			base64FilePath = BaseTest.captureScreenshot(result.getMethod().getMethodName(), driver) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		test.log(Status.FAIL, "TEST FAILED");
		test.addScreenCaptureFromBase64String(base64FilePath, result.getMethod().getMethodName());
		test.fail(result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result)
	{
		test.log(Status.SKIP, "TEST SKIPPED");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context)
	{
      extent.flush();
	}

}
