package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONObject;

public class FailureAnalyticsStore {

    private static final Path ANALYTICS_PATH =
        Path.of(System.getProperty("user.dir"))
            .resolve("failure-analytics")
            .resolve("failure-analytics.json");


    public static void recordFailure(String testName,
                                     String failureType,
                                     String message) {
        try {
            System.out.println(">>> Writing analytics to: "
                    + ANALYTICS_PATH.toAbsolutePath());

            // 1️ Ensure parent directories exist
            Files.createDirectories(ANALYTICS_PATH.getParent());

            JSONObject root;

            // 2️ Initialize file if missing or empty
            if (!Files.exists(ANALYTICS_PATH)
                    || Files.size(ANALYTICS_PATH) == 0) {

                root = new JSONObject();
                root.put("failures", new JSONArray());

            } else {
                String content = Files.readString(ANALYTICS_PATH);
                root = new JSONObject(content);
            }

            JSONArray failures = root.getJSONArray("failures");

            // 3️ Create analytics entry
            JSONObject entry = new JSONObject();
            entry.put("testName", testName);
            entry.put("failureType", failureType);
            entry.put("message", message);
            entry.put("timestamp", LocalDateTime.now().toString());

            failures.put(entry);

            // 4️⃣ Persist
            Files.writeString(ANALYTICS_PATH, root.toString(2));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to record failure analytics", e);
        }
    }
}
