package com.saucelabs.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryListener implements IRetryAnalyzer {
	
	int maxRetry = 2;
	int count = 0;

	@Override
	public boolean retry(ITestResult result) {
		if(count<maxRetry)
		{
			count++;
			return true;
		}
		return false;
	}
}
