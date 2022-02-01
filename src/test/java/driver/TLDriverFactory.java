package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class TLDriverFactory {

  private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
  private static WebDriver driver = null;

  public static synchronized void setDriver(String browser, Dimension dimension) {
    if (browser == null || browser.equals("")) {
      browser = "chrome";
    }

    if (browser.equals("firefox")) {
      WebDriverManager.firefoxdriver().setup();
      driver = new FirefoxDriver();
      driver.manage().window().setSize(dimension);
      tlDriver.set(driver);

    } else if (browser.equals("chrome")) {
      WebDriverManager.chromedriver().setup();
      driver = new ChromeDriver();
      driver.manage().window().setSize(dimension);
      tlDriver.set(driver);

    } else if (browser.equals("safari")) {
      driver = new SafariDriver();
      driver.manage().window().setSize(dimension);
      tlDriver.set(driver);
    }

  }

  public static synchronized WebDriver getDriver() {
    return tlDriver.get();
  }
}
