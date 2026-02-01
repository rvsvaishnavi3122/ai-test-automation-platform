package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONObject;

public class HealingMetricsStore {

    private static final Path METRICS_PATH =
            Path.of(System.getProperty("user.dir"))
                .resolve("healing-metrics")
                .resolve("healing-metrics.json");

    public static void recordHealing(String locatorKey,
                                     boolean success,
                                     String strategy) {
        try {
            Files.createDirectories(METRICS_PATH.getParent());

            JSONObject root;
            if (!Files.exists(METRICS_PATH) || Files.size(METRICS_PATH) == 0) {
                root = new JSONObject();
                root.put("metrics", new JSONArray());
            } else {
                root = new JSONObject(Files.readString(METRICS_PATH));
            }

            JSONArray metrics = root.getJSONArray("metrics");

            JSONObject entry = new JSONObject();
            entry.put("locatorKey", locatorKey);
            entry.put("success", success);
            entry.put("strategy", strategy);
            entry.put("timestamp", LocalDateTime.now().toString());

            metrics.put(entry);
            Files.writeString(METRICS_PATH, root.toString(2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
