package reports;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
  // OB: reports extent instance created here. That instance can be reachable by getReporter()
  // method.

  private static ExtentReports extent;

  public static synchronized ExtentReports getReporter() {

    System.out.println("Extent Report is initialized");
    if (extent == null) {
      // Set HTML reporting file location
      String workingDir = System.getProperty("user.dir");
      extent =
          new ExtentReports(
              workingDir + "/test-output/ExtentReport/ExtentReportResults.html", true);
    }
    return extent;
  }
}
