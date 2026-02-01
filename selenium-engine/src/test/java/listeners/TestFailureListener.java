package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

import driver.DriverFactory;
import utils.FailureUtils;

public class TestFailureListener implements ITestListener {//implements ITestListener for  listening to test events 

    @Override// we are overriding onTestFailure method of ITestListener interface because we want to perform some action when a test fails
    public void onTestFailure(ITestResult result) {//this method is called when a test fails
        FailureUtils.captureFailure(//we are calling captureFailure method of FailureUtils class to capture screenshot and log
                DriverFactory.getDriver(),//getting the driver instance from DriverFactory
                result.getName(),//getting the name of the test method that failed
                result.getThrowable()//getting the throwable that caused the test to fail
        );
    }
}
