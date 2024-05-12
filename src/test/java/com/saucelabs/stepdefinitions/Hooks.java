package com.saucelabs.stepdefinitions;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.saucelabs.pages.LoginPage;
import com.saucelabs.utilities.PageObjectManager;
import com.saucelabs.utilities.PropertyLoader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

public class Hooks {
	
	public static AppiumDriver driver;
	public static LoginPage loginPage;
	public static Properties properties = PropertyLoader.propertyFileLoader("src//test//resources//com//saucelabs//properties//config.properties");
	public Logger logger;
	public static AppiumDriverLocalService service;
	public static PageObjectManager pageObjectManager;
	
	@BeforeAll
	public static void startAppiumServer()
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
	
	@Before
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
			  pageObjectManager = new PageObjectManager(driver);
			  
			  logger = LogManager.getLogger(this.getClass().getName());
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 throw new RuntimeException("Unable to create driver instance");
		 }
		  
	}
	
	@AfterStep
	public void addScreenshot(Scenario scenario) throws IOException
	{
		if(scenario.isFailed())
		{
			File srcPath= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			byte[] fileContent=FileUtils.readFileToByteArray(srcPath);
			scenario.attach(fileContent, "image/png", "FailedImage");
		}
	}
	
	@After
	public void tearDownDriver()
	{
		driver.quit();
	}
	
	@AfterAll
	public static void stopAppiumServer()
	{
		service.stop();
	}

}
