package utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.openqa.selenium.By;

public class LocatorUtil {

    public static By getLocator(String key) {
        try {
            InputStream is = LocatorUtil.class
                    .getClassLoader()
                    .getResourceAsStream("locators/locators.json");

            if (is == null) {
                throw new RuntimeException("locators.json not found in classpath");
            }

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(content);
            JSONObject locator = json.getJSONObject(key);

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

        } catch (Exception e) {
            throw new RuntimeException("Failed to get locator: " + key, e);
        }
    }
}
