package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PropReader;

import java.time.Duration;

import static org.testng.Reporter.log;

public abstract class BasePage  {

	private WebDriver driver;
	protected WebDriverWait wait;
	protected static String BASE_URL;
	protected static String USERNAME;
	protected static String PASSWORD;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		readProperties();
	}

	public WebDriver getDriver() {
		return driver;
	}

	private void readProperties() {
		USERNAME = PropReader.read("test.username");
		BASE_URL = PropReader.read("test.env.url");
		PASSWORD = PropReader.read("test.password");
	}

	//Navigation to main page with login:pass
	public void navigate() {
		log("Navigating to " + BASE_URL, true);
		driver.get("https://"+USERNAME+":"+PASSWORD+"@"+BASE_URL);
	}

	protected void writeText(By by, String text) {
		findElement(by).sendKeys(text);
		log("Text entered " + text + " into " + by.toString() + "\n", true);
	}

	public void clear(By by) {
		findElement(by).clear();
		log("Text found in " + by.toString() + " is cleared\n", true);
	}

	protected WebElement findElement(By by) {
		WebElement el;
		try {
			el = waitVisibility(by);
			log("Element with selector " + by.toString() + " is found\n", true);
		} catch (TimeoutException t) {
			log("Element with selector " + by.toString() + " is found\n", true);
			el = null;
		}
		return el;
	}

	protected void clickElement(By by) {
		WebElement el = findElement(by);
		el.click();
		log("Element " + by.toString() + " is clicked\n", true);
	}

	//Waiter for specific text to be visible
	public void waitTextVisibility(By by, String text) throws TimeoutException {
		try {
			wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(by, text));
		} catch (Exception e) {
			log("Timeout while searching for " + by.toString(), true);
		}
	}

	//Waiter for specific element to be visible
	public WebElement waitVisibility(By by) throws TimeoutException {
		WebElement el = null;
		try {
			wait = new WebDriverWait(driver, 20);
			el = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			el = null;
			log("Timeout while searching for " + by.toString(), true);
		}
		return el;
	}

	protected String getText(By by) {
		String result = "";
		WebElement el = findElement(by);
		if (el == null) result = "";
		else result = el.getText();
		log("Displayed Text = " + result + "\n", true);
		return result;
	}
}
