package CucucumberBDD.Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/java/CucucumberBDD/Features", // Path to feature files
        glue = "CucucumberBDD.StepDefinition",   // Path to step definitions
        plugin = {
                "pretty",                            // Prints steps in console
                "html:target/cucumber-reports.html", // Generates HTML report
                "json:target/cucumber.json"          // Generates JSON report
        },
        monochrome = true,                        // Improves console output readability
        dryRun = false,                           // Set to true to check step mappings without execution
        tags = "@login"                        // Run scenarios with this tag
)

public class TestRunner {
}
