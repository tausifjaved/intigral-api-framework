package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import org.intigral.assignment.utils.ConfigManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

/**
 * Base class for all test classes, providing setup and teardown functionalities
 * for ExtentReports and RestAssured configurations.
 */
public class BaseTest {

    /**
     * Shared instance of ExtentReports for generating test reports.
     */
    protected static ExtentReports extent;

    /**
     * Shared instance of ExtentTest for managing individual test logs.
     */
    protected static ExtentTest test;

    /**
     * Sets up the ExtentReports configuration and initializes the reporting system.
     * Executed once before the entire test suite starts.
     */
    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReport.html");
        spark.config().setReportName("API Test Report");
        spark.config().setDocumentTitle("Extent Report");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Environment", "Test");
        extent.setSystemInfo("Tester", "Your Name");
    }

    /**
     * Configures the RestAssured base URI from the properties file.
     * Executed before each test class.
     */
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigManager.get("baseUrl");
    }

    /**
     * Finalizes the ExtentReports by flushing the report to a file.
     * Executed once after the entire test suite completes.
     */
    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }
}
