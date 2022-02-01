package base;

import com.relevantcodes.extentreports.ExtentReports;
import driver.TLDriverFactory;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import reports.ExtentManager;
import utils.PropReader;


public abstract class BaseTest {

  public static String USERNAME;
  public static String PASSWORD;
  public static String BROWSER_PARAM;
  public static String HEIGHT_PARAM;
  public static String WIDTH_PARAM;
  protected static ExtentReports extentReports;
  protected WebDriver driver;

  public BaseTest() {
  }

  private void setDefaultValueToCommandLineParams() {
    if (System.getProperty("height") == null || System.getProperty("width") == null) {
      HEIGHT_PARAM = "1240";
      WIDTH_PARAM = "1980";
    } else {
      HEIGHT_PARAM = System.getProperty("height");
      WIDTH_PARAM = System.getProperty("width");
    }

    if (System.getProperty("browser") == null) {
      BROWSER_PARAM = "chrome";
    } else {
      BROWSER_PARAM = System.getProperty("browser");
    }
  }

  private Dimension getDimension() {
    return new Dimension(Integer.parseInt(WIDTH_PARAM), Integer.parseInt(HEIGHT_PARAM));
  }

    @AfterSuite
    public static void AfterSuite() {
        extentReports.flush();
    }

  @BeforeSuite(alwaysRun = true)
  public void BeforeSuite(ITestContext ctx) {
    setDefaultValueToCommandLineParams();
    extentReports = ExtentManager.getReporter();
    setTestData();
  }

  private void setTestData() {
    USERNAME = PropReader.read("test.username");
    PASSWORD = PropReader.read("test.password");

  }

  @BeforeMethod(alwaysRun = true)
  public void SetupBeforeMethod() {
    TLDriverFactory.setDriver(BROWSER_PARAM, getDimension());
  }

  @AfterMethod(alwaysRun = true)
  public void AfterMethod(ITestResult result, ITestContext ctx) {
    WebDriver driver = TLDriverFactory.getDriver();
    if (driver != null) {
      driver.quit();
    }
  }

}
