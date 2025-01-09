package org.intigral.assignment.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Listener class for integrating TestNG with ExtentReports.
 * This class implements the {@link ITestListener} interface to generate
 * ExtentReports for the test execution lifecycle.
 */
public class ExtentReportListener implements ITestListener {

    // Shared ExtentReports instance for generating the report
    private static ExtentReports extent;

    // Thread-safe container for ExtentTest instances
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    /**
     * Called before the start of the test suite. Initializes ExtentReports and configures the reporter.
     *
     * @param context TestNG context providing suite-level information
     */
    @Override
    public void onStart(ITestContext context) {
        System.out.println("Initializing ExtentReports...");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/ExtentReport.html");
        sparkReporter.config().setReportName("API Test Report");
        sparkReporter.config().setDocumentTitle("Test Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "Test");
        extent.setSystemInfo("Tester", "Your Name");

        System.out.println("ExtentReports initialized.");
    }

    /**
     * Called when a test method starts. Creates a new ExtentTest instance for the test.
     *
     * @param result Provides information about the test method
     */
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Starting test: " + result.getMethod().getMethodName());
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    /**
     * Called when a test method passes. Marks the test as passed in the ExtentReport.
     *
     * @param result Provides information about the test method
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getMethod().getMethodName());
        test.get().pass("Test passed");
    }

    /**
     * Called when a test method fails. Logs the failure and exception details in the ExtentReport.
     *
     * @param result Provides information about the test method
     */
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getMethod().getMethodName());
        test.get().fail(result.getThrowable());
    }

    /**
     * Called when a test method is skipped. Logs the skip reason in the ExtentReport.
     *
     * @param result Provides information about the test method
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getMethod().getMethodName());
        test.get().skip("Test skipped: " + result.getThrowable());
    }

    /**
     * Called after the completion of the test suite. Finalizes and flushes the ExtentReports instance.
     *
     * @param context TestNG context providing suite-level information
     */
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Finalizing ExtentReports...");
        System.out.println("Tests logged: " + extent.getStats());
        extent.flush();
        System.out.println("ExtentReports finalized.");
    }
}
