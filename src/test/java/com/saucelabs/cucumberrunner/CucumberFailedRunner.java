package com.saucelabs.cucumberrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"@target/cucumber/failedScenarios.txt"},
		glue = {"com.saucelabs.stepdefinitions"},
		monochrome = true,
		plugin = {"pretty", "html:target/cucumber/failedcucumber.html", "json:target/cucumber/failedcucumber.json", 
				  "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class CucumberFailedRunner extends AbstractTestNGCucumberTests {
	
}
