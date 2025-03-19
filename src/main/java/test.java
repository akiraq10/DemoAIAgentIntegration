public class test {
    //private static final String API_KEY = System.getProperty("GEMINI_API_KEY", "default_key");
    //private static final String API_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-1.0-pro:generateContent?key=" + API_KEY;

    public static void main(String[] args) {
        String API_KEY = System.getenv("GEMINI_API_KEY");
        System.out.println(API_KEY);
    }
}

