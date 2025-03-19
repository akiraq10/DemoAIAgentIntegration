package CucucumberBDD.StepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks {


    public void setUp() {
        WebDriverManager.chromedriver().setup();
    }


    public void tearDown() {

    }
}
