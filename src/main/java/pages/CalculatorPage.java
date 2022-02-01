package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.math.BigInteger;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static org.testng.Reporter.log;
import static utils.SiteUrls.PRIVACY;
import static utils.SiteUrls.TERMS;

public class CalculatorPage extends BasePage {

	private final By calculateButton = By.id("getFactorial");
	private final By enterIntegerField = By.id("number");
	private final By result = By.id("resultDiv");
	private final By termsAndConditionsLink = By.linkText("Terms and Conditions");
	private final By privacyLink = By.linkText("Privacy");
	private final By termsAndPrivacyText = By.tagName("body");

	public CalculatorPage(WebDriver driver) {
		super(driver);
		navigate();
	}

	private void clearField() {
		clear(enterIntegerField);
	}

	private void enterValue(String input) {
		clearField();
		writeText(enterIntegerField, input);
	}

	public void getFactorial(String input) {
		enterValue(input);
		clickElement(calculateButton);
	}

	public void getFactorial(Integer input) {
		enterValue(input.toString());
		clickElement(calculateButton);
		waitTextVisibility(result, input.toString());
	}

	public Double extractFactorialFromResponse() {
		String[] cutString = getText(result).split("\\:");
		String receivedFact = cutString[1].trim();
		return 	Double.parseDouble(receivedFact);
	}

	public void verifyResult(int min, int max, int step) throws Exception {
		if (min < 10 || max > 100) {
			log("Please enter a value in the range from 10 to 100");
			throw new Exception("Invalid testing range.");
		}

		for (int i = min; i <= max; i += step ) {
			getFactorial(i);

			Double actualResult = extractFactorialFromResponse();
			log("Actual result is: "+actualResult);

			Double expectedResult = calculateFactorial(i);
			log("Expected result : "+expectedResult);

			Assert.assertEquals(actualResult, expectedResult);
		}
	}

	public Double calculateFactorial(Integer inputNumber) {
		BigInteger fact = BigInteger.ONE;

		for(int i = 1; i <= inputNumber; i++) {
			fact = fact.multiply(BigInteger.valueOf(i));
		}
		return fact.doubleValue();
	}

	public void verifyTermsAnDConditionsPage() {
		clickElement(termsAndConditionsLink);
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(getDriver().getCurrentUrl(), "https://"+USERNAME+":"+PASSWORD+"@"+BASE_URL+TERMS);
		soft.assertEquals(getText(termsAndPrivacyText), "This is the terms and conditions document. We are not yet ready with it. Stay tuned!");
		soft.assertAll();
	}

	public void verifyPrivacyPage() {
		clickElement(privacyLink);
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(getDriver().getCurrentUrl(), "https://"+USERNAME+":"+PASSWORD+"@"+BASE_URL+PRIVACY);
		soft.assertEquals(getText(termsAndPrivacyText), "This is the privacy document. We are not yet ready with it. Stay tuned!");
		soft.assertAll();
	}

	public void verifyErrorMessage(String invalidInput) {
		getFactorial(invalidInput);
		Assert.assertEquals(getText(result), "Please enter an integer");
	}
}
