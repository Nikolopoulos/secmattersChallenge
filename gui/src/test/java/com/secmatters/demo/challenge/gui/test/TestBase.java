package com.secmatters.demo.challenge.gui.test;

import java.net.URI;
import junit.framework.TestCase;
import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class TestBase extends TestCase {

    ChromeDriver drv;
    URI siteBase;

    {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Shutdown the driver once this thread dies
                if (drv != null) {
                    drv.close();
                }
            }
        });
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Change the cargo.servlet.port configuration accordingly in pom.xml
        siteBase = new URI("http://localhost:8081/challenge-gui/");
        String drvPath = null;
        if (SystemUtils.IS_OS_WINDOWS) {
            drvPath = LoginPageIT.class.getResource("/chromedriver.exe").getPath();
        } else if (SystemUtils.IS_OS_LINUX) {
            drvPath = LoginPageIT.class.getResource("/chromedriver").getPath();
        } else {
            throw new RuntimeException("No driver available for running platform (" + SystemUtils.OS_NAME + ")");
        }
        System.setProperty("webdriver.chrome.driver", drvPath);
        drv = new ChromeDriver();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
