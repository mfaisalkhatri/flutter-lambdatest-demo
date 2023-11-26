package io.github.mfaisalkhatri.drivers;

import io.appium.java_client.ios.IOSDriver;
import lombok.Builder;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static java.text.MessageFormat.format;

@Builder
public class IOSDriverManager {
    private static final ThreadLocal<IOSDriver> DRIVER = new ThreadLocal<>();

    private String buildName;
    private String testName;
    private Platform platform;
    private String platformVersion;
    private String deviceName;
    private String app;
    private static final String LT_USERNAME = System.getProperty("LT_USERNAME");
    private static final String LT_ACCESS_KEY = System.getProperty("LT_ACCESS_KEY");
    private static final String GRID_URL = "@mobile-hub.lambdatest.com/wd/hub";


    public IOSDriverManager createIOSDriver() {
        try {
            setDriver(new IOSDriver(new URL(format("https://{0}:{1}{2}", LT_USERNAME, LT_ACCESS_KEY, GRID_URL)), setCapabilities()));
            setupBrowserTimeouts();

        } catch (MalformedURLException e) {
            throw new Error("Error while creating IOS Driver Session");
        }
        return this;
    }

    public IOSDriver getIOSDriver() {
        return IOSDriverManager.DRIVER.get();
    }

    public void quitDriver() {
        if (null != DRIVER.get()) {
            getIOSDriver().quit();
            DRIVER.remove();
        }
    }

    private HashMap<String, Object> ltOptions() {
        final var ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", LT_USERNAME);
        ltOptions.put("accessKey", LT_ACCESS_KEY);
        ltOptions.put("platformName", platform);
        ltOptions.put("deviceName", deviceName);
        ltOptions.put("platformVersion", platformVersion);
        ltOptions.put("isRealMobile", false);
        ltOptions.put("app", app);
        ltOptions.put("automationName", "Flutter");
        ltOptions.put("build", buildName);
        ltOptions.put("name", testName);
        ltOptions.put("w3c", true);
        ltOptions.put("autoAcceptAlerts", true);
        ltOptions.put("video", true);
        ltOptions.put("console", true);
        ltOptions.put("network", false);
        return ltOptions;
    }

    private DesiredCapabilities setCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("lt:options", ltOptions());
        return capabilities;
    }

    private void setDriver(final IOSDriver driver) {
        IOSDriverManager.DRIVER.set(driver);
    }

    private void setupBrowserTimeouts() {
        getIOSDriver().manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(20));
    }
}
