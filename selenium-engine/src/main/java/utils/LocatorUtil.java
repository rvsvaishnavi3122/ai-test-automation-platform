package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LocatorUtil {

    private static final Path LOCATOR_PATH =
            Path.of("src/main/resources/locators.json");

    private WebDriver driver;

    public LocatorUtil(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement findElement(String key) {
        try {
            JSONObject store = loadJson();
            JSONObject locatorNode = store.getJSONObject(key);

            // 1️⃣ Try primary
            try {
                return driver.findElement(buildBy(
                        locatorNode.getJSONObject("primary")));
            } catch (Exception primaryFailure) {

                // 2️⃣ Try healed if exists
                if (!locatorNode.isNull("healed")) {
                    try {
                        WebElement healedElement = driver.findElement(
                                buildBy(locatorNode.getJSONObject("healed")));

                        updateMetrics(locatorNode);
                        saveJson(store);

                        System.out.println("Used healed locator for: " + key);
                        return healedElement;

                    } catch (Exception ignored) {}
                }

                // 3️⃣ Discover fallback (Phase 3B logic)
                WebElement fallback =
                        driver.findElements(By.tagName("input")).get(0);

                // 4️⃣ Persist healed locator
                locatorNode.put("healed", new JSONObject()
                        .put("type", "tag")
                        .put("value", fallback.getTagName()));

                updateMetrics(locatorNode);
                saveJson(store);

                System.out.println("Persisted healed locator for: " + key);
                return fallback;
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to resolve locator: " + key, e);
        }
    }

    // ---------- helpers ----------

    private By buildBy(JSONObject locator) {
    String type = locator.getString("type");
    String value = locator.getString("value");

    switch (type.toLowerCase()) {
        case "id":
            return By.id(value);
        case "name":
            return By.name(value);
        case "css":
            return By.cssSelector(value);
        default:
            return By.xpath(value);
    }
}


    private void updateMetrics(JSONObject locatorNode) {
        locatorNode.put("healingCount",
                locatorNode.getInt("healingCount") + 1);
        locatorNode.put("lastHealed",
                LocalDateTime.now().toString());
    }

    private JSONObject loadJson() throws Exception {
        String content = Files.readString(LOCATOR_PATH);
        return new JSONObject(content);
    }

    private void saveJson(JSONObject json) throws Exception {
        Files.writeString(LOCATOR_PATH, json.toString(2));
    }
}
