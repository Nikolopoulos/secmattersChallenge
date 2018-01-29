package com.secmatters.demo.challenge.gui.test;

import org.junit.Test;

public class LoginPageIT extends TestBase {

    @Test
    public void testLoginPage() {
        drv.get(siteBase.toString());
        assertTrue(drv.getPageSource().contains("Enter username and password"));
    }
}
