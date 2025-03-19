package AI.Gemini;
import okhttp3.*;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class FeatureGeneratorWithGemini {
    private static final String API_KEY = "AIzaSyDcZ9B5Tyh-bPPOwZtQckoRzRhaftpeDdA";
    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-pro:generateContent?key=" + API_KEY;

    public static String generateFeatureFile(String requirement) throws IOException {
        //OkHttpClient client = new OkHttpClient();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        JSONObject prompt = new JSONObject();
        prompt.put("contents", new JSONObject().put("parts", new JSONObject().put("text",
                "Generate a Cucumber BDD feature file with @tag without Explanation of choices and improvements AND note in BDD feature for the following requirement:\n" + requirement)));

        RequestBody body = RequestBody.create(prompt.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(GEMINI_URL).post(body).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        JSONObject jsonResponse = new JSONObject(response.body().string());
        return jsonResponse.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text");
    }

    public static void main(String[] args) {
        try {
            String featureText = generateFeatureFile("User should be able to log in to https://qavn.asia:444 with valid credentials.");
            FileWriter fileWriter = new FileWriter("src/main/java/CucucumberBDD/Features/login.feature");
            fileWriter.write(featureText);
            fileWriter.close();
            System.out.println("Feature file generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
