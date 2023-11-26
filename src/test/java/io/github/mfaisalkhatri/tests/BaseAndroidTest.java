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
    public void setupTest(String buildName, String testName, @Optional("app") String app, Platform platform, String platformVersion,
                          String deviceName) {
        if(platform.is(Platform.ANDROID)) {
            androidDriverManager = AndroidDriverManager.builder()
                    .buildName(buildName)
                    .testName(testName)
                    .app(app)
                    .platform(platform)
                    .platformVersion(platformVersion)
                    .deviceName(deviceName)
                    .build()
                    .createAndroidDriver();
        } else if(platform.is(Platform.IOS)){
            System.out.println("Platform is not supported!");
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        this.androidDriverManager.quitDriver();
    }
}
