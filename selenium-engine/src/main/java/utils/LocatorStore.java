package utils;

import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;

public class LocatorStore {

    private static final Path LOCATOR_PATH =
            Path.of("src/main/resources/locators.json");

    public static JSONObject load() {
        try {
            String content = Files.readString(LOCATOR_PATH);
            return new JSONObject(content);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load locators.json", e);
        }
    }

    public static void save(JSONObject json) {
        try {
            Files.writeString(LOCATOR_PATH, json.toString(2));
        } catch (Exception e) {
            throw new RuntimeException("Failed to save locators.json", e);
        }
    }
}
