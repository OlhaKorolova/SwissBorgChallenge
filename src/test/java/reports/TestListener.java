package reports;

import base.BaseTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static driver.TLDriverFactory.getDriver;
import static org.testng.Reporter.log;

public class TestListener extends BaseTest implements ITestListener {

  private static String getTestMethodName(ITestResult iTestResult) {
    return iTestResult.getMethod().getConstructorOrMethod().getName();
  }

  @Override
  public void onStart(ITestContext iTestContext) {
    System.out.println("I am in onStart method " + iTestContext.getName());

    iTestContext.setAttribute("WebDriver", getDriver());
  }

  @Override
  public void onFinish(ITestContext iTestContext) {
    System.out.println("I am in onFinish method " + iTestContext.getName());
    // Do tier down operations for extentreports reporting!
    ExtentTestManager.endTest();
    ExtentManager.getReporter().flush();
  }

  @Override
  public void onTestStart(ITestResult iTestResult) {
    System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
    // Start operation for extentreports.
    ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(), "");
    ExtentTestManager.getTest().assignCategory(iTestResult.getTestClass().getName());
  }

  @Override
  public void onTestSuccess(ITestResult iTestResult) {
    System.out.println(
        "I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
    // Extentreports log operation for passed tests.
    ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");

    // Get driver from BaseTest and assign to local webdriver variable.
    WebDriver webDriver = getDriver();

    // Take base64Screenshot screenshot.
    String base64Screenshot =
        "data:image/png;base64," + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);

    // Extentreports log and screenshot operations for failed tests.
    ExtentTestManager.getTest()
        .log(
            LogStatus.PASS,
            "Test passed",
            ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
  }

  @Override
  public void onTestFailure(ITestResult iTestResult) {
    log(
        "I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");

    // Get driver from BaseTest and assign to local webdriver variable.
    WebDriver webDriver = getDriver();

    // Take base64Screenshot screenshot.
    String base64Screenshot =
        "data:image/png;base64," + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);

    // Extentreports log and screenshot operations for failed tests.
    ExtentTestManager.getTest()
        .log(
            LogStatus.FAIL,
            "Test Failed with message " + iTestResult.getThrowable().getMessage(),
            ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
  }

  @Override
  public void onTestSkipped(ITestResult iTestResult) {
    log(
        "I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
    // Extentreports log operation for skipped tests.
    ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");

    // Get driver from BaseTest and assign to local webdriver variable.
    WebDriver webDriver = getDriver();

    // Take base64Screenshot screenshot.
    String base64Screenshot =
        "data:image/png;base64," + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);

    // Extentreports log and screenshot operations for failed tests.
    ExtentTestManager.getTest()
        .log(
            LogStatus.SKIP,
            "Test Skipped",
            ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    System.out.println(
        "Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
  }
}
