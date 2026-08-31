package io.github.mfaisalkhatri.listener;

import io.github.mfaisalkhatri.logging.FrameworkLogger;
import org.slf4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger LOGGER = FrameworkLogger.getLogger (TestListener.class);

    @Override
    public void onTestStart (final ITestResult result) {
        LOGGER.info ("TEST STARTED : {}", result.getMethod ()
            .getMethodName ());
    }

    @Override
    public void onTestSuccess (final ITestResult result) {
        LOGGER.info ("TEST PASSED : {}", result.getMethod ()
            .getMethodName ());
    }

    @Override
    public void onTestFailure (final ITestResult result) {
        LOGGER.info ("TEST FAILED : {}", result.getMethod ()
            .getMethodName ());
        if (result.getThrowable () != null) {
            LOGGER.error ("Failure Reason: {}", result.getThrowable ()
                .getMessage ());
        }
    }

    @Override
    public void onTestSkipped (final ITestResult result) {
        LOGGER.info ("TEST SKIPPED : {}", result.getMethod ()
            .getMethodName ());
    }
}
