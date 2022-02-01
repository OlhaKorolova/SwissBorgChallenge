package cases;

import base.BaseTest;
import driver.TLDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.CalculatorPage;


public class CalculatorTests extends BaseTest {
	private final int MIN = 10;
	private final int MAX = 15;
	private final int STEP = 1;


	@Test
	public void verifyCalculator() throws Exception {
		WebDriver driver = TLDriverFactory.getDriver();
		CalculatorPage calculator = new CalculatorPage(driver);
		calculator.verifyResult(MIN, MAX, STEP);
	}

	@Test
	public void verifyTermsAndConditionsLink() {
		WebDriver driver = TLDriverFactory.getDriver();
		CalculatorPage calculator = new CalculatorPage(driver);
		calculator.verifyTermsAnDConditionsPage();
	}

	@Test
	public void verifyPrivacyLink() {
		WebDriver driver = TLDriverFactory.getDriver();
		CalculatorPage calculator = new CalculatorPage(driver);
		calculator.verifyPrivacyPage();
	}

	@Test
	public void verifyErrorMessage() {
		WebDriver driver = TLDriverFactory.getDriver();
		CalculatorPage calculator = new CalculatorPage(driver);
		calculator.verifyErrorMessage("string");
	}
}
