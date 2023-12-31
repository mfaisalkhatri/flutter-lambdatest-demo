package io.github.mfaisalkhatri.tests;

import io.github.mfaisalkhatri.drivers.AndroidDriverManager;
import org.openqa.selenium.Platform;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


public class BaseAndroidTest {

    protected AndroidDriverManager androidDriverManager;

    @Parameters({"buildName", "testName", "app", "platformName", "version", "device"})
    @BeforeClass(alwaysRun = true)
    public void setupTest(final String buildName, final String testName, @Optional("app") final String app, final Platform platform, final String platformVersion,
                          final String deviceName) {
            this.androidDriverManager = AndroidDriverManager.builder()
                    .buildName(buildName)
                    .testName(testName)
                    .app(app)
                    .platform(platform)
                    .platformVersion(platformVersion)
                    .deviceName(deviceName)
                    .build()
                    .createAndroidDriver();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        this.androidDriverManager.quitDriver();
    }
}
