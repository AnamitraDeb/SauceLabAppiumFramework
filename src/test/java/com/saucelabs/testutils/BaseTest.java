package com.saucelabs.testutils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.saucelabs.pages.LoginPage;
import com.saucelabs.utilities.PropertyLoader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class BaseTest {
	
	public AppiumDriver driver;
	public LoginPage loginPage;
	public Properties properties = PropertyLoader.propertyFileLoader("src//test//resources//com//saucelabs//properties//config.properties");
	public Logger logger;
	public AppiumDriverLocalService service;
	
	@BeforeSuite(alwaysRun = true)
	public void startAppiumServer()
	{
		service= AppiumDriverLocalService.buildService(new AppiumServiceBuilder().
				withAppiumJS(new File(properties.getProperty("appiumMainJsPath"))).
	    		withIPAddress(properties.getProperty("appiumIPAddress")).
	    		usingPort(Integer.parseInt(properties.getProperty("appiumPort"))).
	    		withTimeout(Duration.ofSeconds(3000)).
	    		withArgument(GeneralServerFlag.SESSION_OVERRIDE).
	    		withLogFile(new File("logs" + File.separator + "appiumServer.log")));
	    service.start();
	    service.clearOutPutStreams();
	}
	
	
	@BeforeMethod(alwaysRun = true)
	public void initializeDriver() throws MalformedURLException
	{
	    String appPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + 
		                 "com" + File.separator + "saucelabs" + File.separator + "apps" + File.separator + 
		                 properties.getProperty("androidAppName");
		try
		 {
			  UiAutomator2Options options = new UiAutomator2Options();
			  options.setAvd(properties.getProperty("androidAvdDevice"));
			  options.setAvdLaunchTimeout(Duration.ofMinutes(20));
			  options.setAvdReadyTimeout(Duration.ofMinutes(20));
			  options.setPlatformName("android");
			  options.setDeviceName(properties.getProperty("androidAvdDevice"));
			  options.setAutomationName(properties.getProperty("androidAutomationName"));
			  options.setAdbExecTimeout(Duration.ofSeconds(24000));
			  options.setUiautomator2ServerLaunchTimeout(Duration.ofMinutes(5));
			  options.setApp(appPath);
			  options.setAndroidInstallTimeout(Duration.ofMinutes(10));
			  options.setAppWaitDuration(Duration.ofMinutes(5));
			  options.setAppPackage(properties.getProperty("androidAppPackage"));
			  options.setAppActivity(properties.getProperty("androidAppActivity"));
			  options.setNewCommandTimeout(Duration.ofMinutes(1));

			  URL url =new URL("http://" + properties.getProperty("appiumIPAddress")+ ":" + properties.getProperty("appiumPort"));
			  
			  driver = new AndroidDriver(url, options);
			  System.out.println("Session ID ==>" + driver.getSessionId());
			  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			  loginPage = new LoginPage(driver); 
			  
			  logger = LogManager.getLogger(this.getClass().getName());
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 throw new RuntimeException("Unable to create driver instance");
		 }
		  
	}
	
	 public static String captureScreenshot(String testCaseName, AppiumDriver driver) throws IOException
	 {
		 SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss"); 
	     String strDate = ft.format(new Date()); 

		 String destPath = "Screenshots" + File.separator + testCaseName + "_" + strDate + ".png";
		 File source = driver.getScreenshotAs(OutputType.FILE);
		 File destination = new File(destPath);
		 
		 FileUtils.copyFile(source, destination);
		 return destPath;
		 
	 }
	
	@AfterMethod(alwaysRun = true)
	public void tearDownDriver()
	{
		driver.quit();
	}
	
	@AfterSuite(alwaysRun = true)
	public void stopAppiumServer()
	{
		service.stop();
	}
	
}
