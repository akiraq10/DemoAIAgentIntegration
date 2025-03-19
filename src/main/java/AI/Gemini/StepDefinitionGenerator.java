package AI.Gemini;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class StepDefinitionGenerator {
    private static final String API_KEY = System.getenv("GEMINI_API_KEY");
    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-pro:generateContent?key=" + API_KEY;

    public static String generateStepDefinitions(String featureText, String className, String packageName) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        // Build request for Gemini API
        JSONObject prompt = new JSONObject();
        prompt.put("contents", new JSONArray().put(
                new JSONObject()
                        .put("role", "user")
                        .put("parts", new JSONArray().put(
                                new JSONObject().put("text",
                                        "Generate Java Cucumber step definitions without Explanation of choices and improvements AND note , with class name '" + className + "' for the following feature:\n" + featureText)
                        ))
        ));

        RequestBody body = RequestBody.create(prompt.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(GEMINI_URL).post(body).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected response: " + response);

        JSONObject jsonResponse = new JSONObject(response.body().string());
        String stepDefinitions = jsonResponse.getJSONArray("candidates").getJSONObject(0)
                .getJSONObject("content").getJSONArray("parts")
                .getJSONObject(0).getString("text");

        // Ensure class name matches
        stepDefinitions = stepDefinitions.replace("public class StepDefinitions", "public class " + className);

        // Add package declaration at the top
        String packageDeclaration = "package " + packageName + ";\n\n";
        return packageDeclaration + stepDefinitions;
    }
    public static void main(String[] args) {
        try {
            // Step 1: Read the login.feature file
            String featureFilePath = "src/main/java/CucucumberBDD/Features/login.feature";
            String featureText = new String(Files.readAllBytes(Paths.get(featureFilePath)));
            String className="LoginSteps";
            String packageName="CucucumberBDD.StepDefinition";

            // Step 2: Generate Step Definitions using Gemini
            String stepDefinitions = generateStepDefinitions(featureText,className,packageName);

            // Step 3: Save step definitions to Java file
            String directoryPath = "src/main/java/CucucumberBDD/StepDefinition";
            String filePath = directoryPath + "/"+className+".java";

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

