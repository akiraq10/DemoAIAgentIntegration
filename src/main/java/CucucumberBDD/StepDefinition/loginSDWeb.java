package CucucumberBDD.StepDefinition;


import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.junit.Assert.*;

import java.time.Duration;

public class loginSDWeb {

    WebDriver driver;

    @Given("User navigates to {string} using Firefox")
    public void userNavigatesToUsingFirefox(String url) {
        driver = new FirefoxDriver();
        driver.get(url);
    }

    @When("User enters {string} in the username field {string}")
    public void userEntersInTheUsernameField(String username, String usernameField) {
        driver.findElement(By.id(usernameField)).sendKeys(username);
    }

    @And("User enters {string} in the password field {string}")
    public void userEntersInThePasswordField(String password, String passwordField) {
        driver.findElement(By.id(passwordField)).sendKeys(password);
    }

    @And("User clicks the login button {string}")
    public void userClicksTheLoginButton(String loginButton) {
        driver.findElement(By.id(loginButton)).click();
    }

    @Then("User should be redirected to {string} within {int} seconds")
    public void userShouldBeRedirectedToWithinSeconds(String url, int timeout) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        assertEquals(url, driver.getCurrentUrl());
    }

    @When("User enters an invalid username in the field {string}")
    public void userEntersAnInvalidUsernameInTheField(String usernameField) {
        driver.findElement(By.id(usernameField)).sendKeys("invalidUsername");
    }

    @And("User enters an invalid password in the field {string}")
    public void userEntersAnInvalidPasswordInTheField(String passwordField) {
        driver.findElement(By.id(passwordField)).sendKeys("invalidPassword");
    }

    @Then("User should see an error message containing {string}")
    public void userShouldSeeAnErrorMessageContaining(String errorMessage) {
        assertTrue(driver.getPageSource().contains(errorMessage));
    }

    @After
    public void closeTheBrowser() {
       driver.quit();
    }
}
