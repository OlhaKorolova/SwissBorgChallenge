package cases;

import base.BaseTest;
import driver.TLDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.CalculatorPage;

public class CalculatorTests extends BaseTest {
	//The following constants are used to modify the range and step of the testing data (to control runtime).
	private final int MIN = 10;
	private final int MAX = 100;
	private final int STEP = 1;


	@Test(groups = "regression")
	public void verifyCalculatorTest() throws Exception {
		WebDriver driver = TLDriverFactory.getDriver();
		CalculatorPage calculator = new CalculatorPage(driver);
		calculator.verifyResult(MIN, MAX, STEP);
	}

	//This test is failing
	@Test(groups = "regression")
	public void verifyTermsAndConditionsLinkTest() {
		WebDriver driver = TLDriverFactory.getDriver();
		CalculatorPage calculator = new CalculatorPage(driver);
		calculator.verifyTermsAnDConditionsPage();
	}

	//This test is failing
	@Test(groups = "regression")
	public void verifyPrivacyLinkTest() {
		WebDriver driver = TLDriverFactory.getDriver();
		CalculatorPage calculator = new CalculatorPage(driver);
		calculator.verifyPrivacyPage();
	}

	@Test(groups = "regression")
	public void verifyErrorMessageTest() {
		WebDriver driver = TLDriverFactory.getDriver();
		CalculatorPage calculator = new CalculatorPage(driver);
		calculator.verifyErrorMessage("string");
	}

	@Test(groups = "regression")
	public void verifyFactorialForNegativeInteger() {
		WebDriver driver = TLDriverFactory.getDriver();
		CalculatorPage calculator = new CalculatorPage(driver);
		calculator.verifyErrorMessage("-5");
	}
}
