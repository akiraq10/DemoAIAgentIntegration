package AI.chatGPT;
import java.io.IOException;

public class GenerateFeatureTest {
    public static void main(String[] args) {
        try {
            String featureDescription = "Login page with username and password validation";
            String filePath = "src/test/resources/features/generated_login.feature";
            AIHelper.generateFeatureFile(featureDescription, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

