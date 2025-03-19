package AI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static AI.Gemini.FeatureGeneratorWithGemini.generateFeatureFile;
import static AI.Gemini.StepDefinitionGenerator.generateStepDefinitions;

public class AIHelper {
    public static void main(String[] args) {
        // identify feature and test scripts file name for cucumber
        String className="loginSDWeb";
        String packageName="CucucumberBDD.StepDefinition";
        String featurePath="src/main/java/CucucumberBDD/Features/"+className+".feature";
        String directoryPath = "src/main/java/CucucumberBDD/StepDefinition";
        String filePath = directoryPath + "/"+className+".java";

        //prompt request
        String testScenario="i using webdrivermanager with Firefox for Webdriver, and i need the browser will be closed after test done" +
                "i have website https://qavn.asia:444, it has login page with elements : " +
                "the username field has attribute: id=UserName, " +
                "password field has attribute:  id=Password, " +
                "login btn field has attribute  id=ses-submit-btn. " +
                "i wanna to create a test case login with valid username=admin, password=1," +
                "Expected: the 'https://qavn.asia:444/Folder/ViewFolder' displayed after 5s logging to website success "+
                "create a test case login with invalid credentials " +
                "Expected: The error message contain 'Login was unsuccessful' text is displayed. " ;
//                "I need to close Webdriver browser after test completed.";

        try {
            String featureText = generateFeatureFile(testScenario,className);
            FileWriter fileWriter = new FileWriter(featurePath);
            fileWriter.write(featureText);
            fileWriter.close();
            System.out.println("Feature file generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Step 1: Read the login.feature file
            String featureText = new String(Files.readAllBytes(Paths.get(featurePath)));

            // Step 2: Generate Step Definitions using Gemini
            String stepDefinitions = generateStepDefinitions(featureText,className,packageName);

            // Ensure the directory exists
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Write to file
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(stepDefinitions);
            fileWriter.close();

            System.out.println("Step Definitions generated successfully at: " + new File(filePath).getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
