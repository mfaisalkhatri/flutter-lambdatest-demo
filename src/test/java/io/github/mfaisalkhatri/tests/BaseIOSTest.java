package io.github.mfaisalkhatri.tests;

import io.github.mfaisalkhatri.drivers.IOSDriverManager;
import org.openqa.selenium.Platform;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseIOSTest {

    protected IOSDriverManager iosDriverManager;

    @Parameters({"buildName", "testName", "app", "platformName", "version", "device"})
    @BeforeClass(alwaysRun = true)
    public void setupTest(final String buildName, final String testName, @Optional("app") final String app, final Platform platform, final String platformVersion,
                          final String deviceName) {
        this.iosDriverManager = IOSDriverManager.builder()
                    .buildName(buildName)
                    .testName(testName)
                    .app(app)
                    .platform(platform)
                    .platformVersion(platformVersion)
                    .deviceName(deviceName)
                    .build()
                    .createIOSDriver();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        this.iosDriverManager.quitDriver();
    }
}
