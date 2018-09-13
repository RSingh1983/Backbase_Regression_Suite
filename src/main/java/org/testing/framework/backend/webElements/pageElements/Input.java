package org.testing.framework.backend.webElements.pageElements;

import org.testing.framework.backend.webElements.locator.WebElementLocator;
import org.testing.framework.properties.LoadProjectProperties;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.pages.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Input extends PageObject {

    static Logger logger = LoggerFactory.getLogger(Input.class.getName());

    public Input(WebDriver driver) {
        super(driver);
    }

    /**
     * This will input a value into the passed element after clearing the field
     *
     * @param value			A String value that will be inserted to the element
     * @param field			A keyword location of an element in an xml file as a String
     * @param beanFileName	The name and path of the xml file to look in as a String
     * @param beanPath		The element node type location in the xml file as a String
     * @throws Exception	If the value can not be input
     */
    public void input_the_element(final String value, final String field, String fieldReplacementValue, final String beanFileName, final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, fieldReplacementValue, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), true);
        elementFound.clear();

        // Because clear can make a javascript run to fill the field again
        // Here it will select all the text before writing the value
        elementFound.sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
    }

    public void input_the_element_and_click_tab(final String value, final String field, final String beanFileName, final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);
        elementFound.clear();

        // Because clear can make a javascript run to fill the field again
        // Here it will select all the text before writing the value
        elementFound.sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
        elementFound.sendKeys(Keys.TAB);
    }

    public void input_the_element_and_enter(final String value, final String field, final String beanFileName, final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);
        elementFound.clear();
        // Because clear can make a javascript run to fill the field again
        // Here it will select all the text before writing the value
        elementFound.sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
        elementFound.sendKeys(Keys.ENTER);
    }

    public void input_the_element(final String value, final String field, final String beanFileName, final String beanPath) throws Exception {
        input_the_element(value, field, "", beanFileName, beanPath);
    }

    /**
     * This will input a value into the passed element without clearing the field first
     *
     * @param value			A String value that will be inserted to the element
     * @param field			A keyword location of an element in an xml file as a String
     * @param beanFileName	The name and path of the xml file to look in as a String
     * @param beanPath		The element node type location in the xml file as a String
     * @throws Exception	If the value can not be input
     */
    public void input_the_element_without_clearing(final String value, final String field, final String beanFileName, final String beanPath) throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);

        elementFound.sendKeys(value);
    }

    public void input_the_element_without_clearing(final String value, final String field,final String fieldReplacementValue, final String beanFileName, final String beanPath) throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field,fieldReplacementValue, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), true);

        elementFound.sendKeys(value);
    }
}

