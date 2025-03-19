package AI.chatGPT;
import okhttp3.*;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AIHelper {

        private static final String API_KEY = "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";  // Thay bằng OpenAI API key của bạn
        private static final String API_URL = "https://api.openai.com/v1/completions";

        public static void generateFeatureFile(String featureDescription, String filePath) throws IOException {
            OkHttpClient client = new OkHttpClient();

            String prompt = "Write Cucumber test case in Gherkin syntax for: " + featureDescription;
            JSONObject json = new JSONObject();
            json.put("model", "gpt-3.5-turbo");
            json.put("prompt", prompt);
            json.put("max_tokens", 200);
            json.put("temperature", 0.7);

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

// Debug: In phản hồi từ OpenAI API
            System.out.println("🔥 OpenAI Response: " + responseBody);

// Kiểm tra xem response có chứa "choices" không
            JSONObject responseJson = new JSONObject(responseBody);
            if (!responseJson.has("choices")) {
                throw new JSONException("⚠️ OpenAI Response không chứa 'choices': " + responseBody);
            }

            String generatedTestCase = responseJson.getJSONArray("choices").getJSONObject(0).getString("text").trim();

// Lưu vào file feature
            Files.write(Paths.get(filePath), generatedTestCase.getBytes());
            System.out.println("✅ Test case Gherkin đã được tạo tại: " + filePath);

        }


}
