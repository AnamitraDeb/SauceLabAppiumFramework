package com.saucelabs.gestures;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class AndroidGestures 
{
	
	public static void scroll(AppiumDriver driver)
	{
	driver.executeScript("mobile: scrollGesture", ImmutableMap.of(
				    "left", 100, "top", 200, "width", 2000, "height", 2000,
				    "direction", "down",
				    "percent", 0.75
				       ));
	}
	
	public static void scrollToText(AppiumDriver driver, String text)
	{
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));"));
	}
	public static void scrollForward(AppiumDriver driver)
	{
		driver.findElement(AppiumBy.androidUIAutomator(
	            "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
	}
}
