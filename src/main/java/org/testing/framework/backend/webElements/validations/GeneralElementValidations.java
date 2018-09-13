package org.testing.framework.backend.webElements.validations;

import net.serenitybdd.core.pages.PageObject;
import org.testing.framework.backend.webElements.locator.WebElementLocator;
import org.testing.framework.properties.LoadProjectProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class GeneralElementValidations extends PageObject {

    static Logger logger = LoggerFactory.getLogger(GeneralElementValidations.class.getName());

    public GeneralElementValidations(WebDriver driver) {
        super(driver);
    }


    public boolean checkIfElementPresent(final String field, String fieldReplacement, final String beanFileName, final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        String[] fieldList = field.split(
                LoadProjectProperties.getStringProperty(
                        LoadProjectProperties.REPLACE_TEXT_DELIMITER));
        boolean allFieldsPresent = false;
        for (String singleField : fieldList) {
            WebElement element = elementLocator.locateElement(singleField, fieldReplacement, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), true);
            if (element == null) {
                allFieldsPresent = false;
                break;
            } else {
                allFieldsPresent = true;
            }
        }
        return allFieldsPresent;
    }

    /**
     * This Method can assert the presence of multiple elements seperated by a
     * delimiter
     *
     * @param field	A keyword location of an element in an xml file as a String
     * @param beanFileName The name and path of the xml file to look in as a String
     * @param beanPath The element node type location in the xml file as a String
     * @throws Exception If the element is not present
     */
    public void isElementPresent(final String field, String fieldReplacement, final String beanFileName, final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        String[] fieldList = field.split(
                LoadProjectProperties.getStringProperty(
                        LoadProjectProperties.REPLACE_TEXT_DELIMITER));
        boolean allFieldsPresent = false;
        for (String singleField : fieldList) {
            WebElement element = elementLocator.locateElement(singleField, fieldReplacement, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), true);
            if (element == null) {
                allFieldsPresent = false;
                break;
            } else {
                allFieldsPresent = true;
            }
        }
        assertTrue(allFieldsPresent);
    }


    public void isElementPresentReplace(final String field, String fieldReplacement, final String beanFileName, final String beanPath,  final String replaceCharacter) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        String[] fieldList = field.split(
                LoadProjectProperties.getStringProperty(
                        LoadProjectProperties.REPLACE_TEXT_DELIMITER));
        boolean allFieldsPresent = false;
        for (String singleField : fieldList) {
            WebElement element = elementLocator.locateElement(singleField, fieldReplacement, replaceCharacter, beanFileName, beanPath, getDriver(), true);
            if (element == null) {
                allFieldsPresent = false;
                break;
            } else {
                allFieldsPresent = true;
            }
        }
        assertTrue(allFieldsPresent);
    }

    public void isElementPresent(final String field, final String beanFileName, final String beanPath) throws Exception {
        isElementPresent(field, "", beanFileName, beanPath);
    }

    /**
     * Checks to see if there is the correct amount of elements in the given element
     *
     * @param field	A keyword location of an element in an xml file as a String
     * @param numberOfElements The number of elements that should be present as an Int
     * @param elementTag The tag name of the html element to look for
     * @param beanFileName The name and path of the xml file to look in as a String
     * @param beanPath The element node type location in the xml file as a String
     * @throws Exception If the count does not match
     */
    public void isNumberOfElementsByTagInFieldCorrect(final String field, final int numberOfElements, final String elementTag, final String beanFileName, final String beanPath) throws Exception {

        // Get the element to search in
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);

        // Get a list of all elements in this element by the passed tag name
        List<WebElement> tagElements = element.findElements(By.tagName(elementTag));

        // If its the same amount as asked for, return true else fail
        if(numberOfElements == tagElements.size()) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }

    /**
     * Checks to see if there is the correct amount of elements on the page
     *
     * @param field	A keyword location of an element in an xml file as a String
     * @param numberOfElements The number of elements that should be present as an Int
     * @param beanFileName The name and path of the xml file to look in as a String
     * @param beanPath The element node type location in the xml file as a String
     * @throws Exception If the count does not match
     */
    public void isNumberOfElementsOnPageCorrect(final String field, final int numberOfElements, final String beanFileName, final String beanPath) throws Exception {
        // Get the count of the elements
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        int elementCount = elementLocator.getElementCount(field, beanFileName, beanPath, getDriver(), true);
        logger.info("Field: "+field+", Element count: "+elementCount);
        // If its the same amount as asked for, return true else fail
        if(numberOfElements == elementCount) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }

    /**
     * Checks to see if there is expected number of characters for a value
     *
     * @param field	A keyword location of an element in an xml file as a String
     * @param numberOfCharacters The number of characters that should be present as an Int
     * @param beanFileName The name and path of the xml file to look in as a String
     * @param beanPath The element node type location in the xml file as a String
     * @throws Exception If the count does not match
     */

    public void isNumberOfCharactersCorrect(final String field, final int numberOfCharacters, final String beanFileName, final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);
        if(elementFound!=null) {
            //Get the count of characters
            int elementCount = elementFound.getText().length();

            if (elementCount == numberOfCharacters) {
                assertTrue(true);
            } else {
                logger.error(elementCount + " != " + numberOfCharacters);
                assertTrue(false);
            }
        }
    }

    /**
     * This Method can assert the presence and visibility of multiple elements seperated by a
     * delimiter
     *
     * @param field	A keyword location of an element in an xml file as a String
     * @param beanFileName The name and path of the xml file to look in as a String
     * @param beanPath The element node type location in the xml file as a String
     * @throws Exception If the element is not present
     */
    public boolean isElementVisible(final String field, String fieldReplacement, final String beanFileName, final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        String[] fieldList = field.split(
                LoadProjectProperties.getStringProperty(
                        LoadProjectProperties.ASSERT_TEXT_DELIMITER));
        boolean allFieldsVisible = false;
        for (String singleField : fieldList) {
            WebElement element = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);
            logger.info("Element Text is: " + element.getText());
            if (element == null || !element.isDisplayed()) {
                allFieldsVisible = false;
                break;
            } else {
                allFieldsVisible = true;
            }
        }
        return allFieldsVisible;
    }


    public void isErrorMessageNotVisible(final String field, String fieldReplacement, final String beanFileName, final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        String[] fieldList = field.split(
                LoadProjectProperties.getStringProperty(
                        LoadProjectProperties.ASSERT_TEXT_DELIMITER));
        boolean allFieldsVisible = false;
        for (String singleField : fieldList) {
            // webElements element = elementLocator.locateElement(singleField, fieldReplacement, beanFileName, beanPath, getDriver(), true);
            WebElement element = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);
            logger.info("Text message is: "+element.getText());
            if (element == null || !element.isDisplayed()) {
                allFieldsVisible = true;
                break;
            } else {
                allFieldsVisible = false;
            }
        }
        assertTrue(allFieldsVisible);
    }
    public int numberOfElementsOnPage(final String field,final String beanFileName,final String beanPath) throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();
        int elementCount = elementLocator.getElementCount(field, beanFileName, beanPath, getDriver(), true);
        logger.info("Field: " + field + ", Element count: " + elementCount);
        return elementCount;
    }

    public int numberOfElementsOnPage(final String field,String replacement,String delimiter,final String beanFileName,final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        int elementCount = elementLocator.getElementCount(field,replacement,delimiter,beanFileName, beanPath, getDriver(), false);
        logger.info("Field: " + field + ", Element count: " + elementCount);
        return elementCount;
    }

}
