package com.saucelabs.cucumberrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/test/resources/com/saucelabs/featurefiles"},
		glue = {"com.saucelabs.stepdefinitions"},
		monochrome = true,
		plugin = {"pretty", "html:target/cucumber/cucumber.html", "json:target/cucumber/cucumber.json",
				  "rerun:target/cucumber/failedScenarios.txt", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class CucumberRunner extends AbstractTestNGCucumberTests {
	
}
