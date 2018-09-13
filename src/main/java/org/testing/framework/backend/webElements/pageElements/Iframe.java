package org.testing.framework.backend.webElements.pageElements;

import org.testing.framework.backend.webElements.locator.WebElementLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.pages.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Iframe extends PageObject {

    static Logger logger = LoggerFactory.getLogger(Iframe.class.getName());

    public Iframe(WebDriver driver) {
        super(driver);
    }

    /**
     * This will select the passed iframe field
     *
     * @param field			A keyword location of an element in an xml file as a String
     * @param beanFileName	The name and path of the xml file to look in as a String
     * @param beanPath		The element node type location in the xml file as a String
     * @throws Exception	If the iframe is not there or can not be found
     */
    public void select_the_frame(final String field, final String beanFileName, final String beanPath) throws Exception {

        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);

        // If the element has been found
        if (elementFound != null) {
            // Switch to it
            getDriver().switchTo().frame(elementFound);
        }
    }

    /**
     * This will switch to the default page. i.e. exit all frames if within
     *
     * @throws Exception	If the default page is not there or can not be found
     */
    public void select_default_page() throws Exception {
        getDriver().switchTo().defaultContent();
    }
}

