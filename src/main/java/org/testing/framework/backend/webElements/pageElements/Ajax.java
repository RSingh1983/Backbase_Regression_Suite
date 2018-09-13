package org.testing.framework.backend.webElements.pageElements;

import net.serenitybdd.core.pages.PageObject;

import org.testing.framework.backend.webElements.locator.WebElementLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;

public class Ajax extends PageObject {

    static Logger logger = LoggerFactory.getLogger(Ajax.class.getName());

    public Ajax(WebDriver driver) {
        super(driver);
    }

    /**
     * This waits for Ajax element to load found on the current page
     *
     * @throws Exception
     */
    public void waitForAjaxElement(String ajaxIdentifier) throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();

        if (elementLocator == null) {
            logger.info("Ajax Element not found");
        }
        else {
            elementLocator.waitForAjaxToComplete(getDriver(), ajaxIdentifier);
        }
    }


}
