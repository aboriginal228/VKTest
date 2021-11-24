package common;

import aquality.selenium.browser.AqualityServices;
import common.utils.ConfigUtil;
import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected final String DEFAULT_URL = ConfigUtil.getDefaultUrl();

    protected final Dimension defaultSize = ConfigUtil.getDefaultSize();

    @BeforeMethod
    protected void beforeMethod() {
        AqualityServices.getBrowser().goTo(DEFAULT_URL);
        AqualityServices.getBrowser().setWindowSize(defaultSize.width, defaultSize.height);
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            AqualityServices.getBrowser().quit();
        }
    }
}