package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

import driver.DriverFactory;
import utils.FailureUtils;

public class TestFailureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        FailureUtils.captureFailure(
                DriverFactory.getDriver(),
                result.getName(),
                result.getThrowable()
        );
    }
}
