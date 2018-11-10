package org.testing.framework.backend.webElements.pageElements;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.backend.webElements.locator.WebElementLocator;
import org.testing.framework.properties.LoadProjectProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Text extends PageObject {

    static Logger logger = LoggerFactory.getLogger(Text.class.getName());

    public Text(WebDriver driver) {
        super(driver);
    }


    public String getColorFromElement(final String field, String fieldReplacementValue, final String beanFileName, final String beanPath) throws Exception {

        String text = "";

        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, fieldReplacementValue, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), true);

        logger.info("");
        // If the element has been found
        if (elementFound != null) {

            text = elementFound.getCssValue("color");

        }
        return text;
    }

    /**
     * This will get the text inside the element that has been passed in
     *
     * @param field        A keyword location of an element in an xml file as a String
     * @param beanFileName The name and path of the xml file to look in as a String
     * @param beanPath     The element node type location in the xml file as a String
     * @return The text in the element or empty
     * @throws Exception If the element is not present
     */
    public String getTextFromElement(final String field, final String beanFileName, final String beanPath) throws Exception {
//        String text = "";
//        // Locate the passed element
//        WebElementLocator elementLocator = WebElementLocator.getInstance();
//        WebElement elementFound = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);
//
//        // If the element has been found
//        if (elementFound != null) {
//            text = elementFound.getText();
//            if (text.isEmpty() && elementFound.getAttribute("value") != null) {
//                text = elementFound.getAttribute("value");
//            }
//            if (text.isEmpty()) {
//                logger.warn("No text found in element" + field);
//            }
//        }
//        return text;
        return getTextFromElement(field,"",beanFileName,beanPath);
    }

    public String getTextFromElement(final String field, String fieldReplacementValue, final String beanFileName, final String beanPath) throws Exception {
        String text = "";
        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, fieldReplacementValue, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), true);
        // If the element has been found
        if (elementFound != null) {
            text = elementFound.getText();
            if (text.isEmpty() && elementFound.getAttribute("value") != null) {
                text = elementFound.getAttribute("value");
            }
            if (text.isEmpty()) {
                logger.warn("No text found in element" + field);
            }
        }
        return text;
    }

    public String getTextFromElement(final String field, String fieldReplacementValue, final String beanFileName, final String beanPath, final String repDelimiterChar) throws Exception {

        String text = "";

        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, fieldReplacementValue, repDelimiterChar, beanFileName, beanPath, getDriver(), true);

        logger.info("");
        // If the element has been found
        if (elementFound != null) {

            text = elementFound.getText();

            // areas of text (eg, inside <td> will return
            // element.getAttribute("value") as null
            if (text.isEmpty() && elementFound.getAttribute("value") != null) {
                // get the value from text input elements
                text = elementFound.getAttribute("value");
            }

            if (text.isEmpty()) {
                logger.warn("No text found in element" + field);
            }
        }

        return text;
    }

    public List<WebElement> getWebElements(final String field, String fieldReplacementValue, final String beanFileName, final String beanPath, final boolean raiseErrorifUnavailable) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(field, fieldReplacementValue, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), raiseErrorifUnavailable);
        List<WebElement> elementsFound = element.findElements(net.serenitybdd.core.annotations.findby.By.xpath(elementLocator.getElementPath(field, beanFileName, beanPath)));
        return elementsFound;
    }

    public List<String> listOfElements(final String field, String fieldReplacementValue, final String beanFileName, final String beanPath, final boolean raiseErrorifUnavailable) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        List<WebElement> element = elementLocator.locateMultipleElements(field, fieldReplacementValue, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), raiseErrorifUnavailable);
        List<String> elementsFound = new ArrayList<>();
        if (!element.isEmpty())
            for (WebElement webElement : element) {
                elementsFound.add(webElement.getText());
            }
        return elementsFound;
    }

    /**
     * This asserts as to whether there is the supplied text found on the current page
     *
     * @param keyword The text to search for
     * @throws Exception
     */
    public void isTextPresentOnPage(String keyword) throws Exception {
        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));

        // TODO: The following line bodyText.getText() causes firefox to crash
        // with an "unresponsive script" error. Comment out for now.
        // String bodyTextString = bodyText.getText();
        String bodyTextString = getDriver().getPageSource();
        boolean allKeywordsPresent = false;

        for (String singleKeyword : keywordList) {
            allKeywordsPresent = bodyTextString.contains(singleKeyword.trim());
            if (!allKeywordsPresent) {
                break;
            }
        }
        assertTrue(allKeywordsPresent);
    }


    /**
     * This checks to see if there is any text present at the given field location
     * <p>
     * This Method can assert the presence of multiple elements separated by a delimiter in the field parameter
     *
     * @param keyword      The text to search for
     * @param field        A keyword location of an element in an xml file as a String
     * @param beanFileName The name and path of the xml file to look in as a String
     * @param beanPath     The element node type location in the xml file as a String
     * @throws Exception If the text is not present then it throws an error
     */
    public void isTextPresentAtLocation(final String keyword, final String field, final String fieldReplacementValue, final String beanFileName, final String beanPath) throws Exception {

        // Split the keywords and fields up using the delimiter
        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
        String[] fieldList = field.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));

        boolean allKeywordsPresent = false;

        // If the lists are the same length then do the checking, else throw an error
        if (keywordList.length != fieldList.length) {
            throw new Exception("Keyword list and field list count does not match:" + keywordList.length + " != " + fieldList.length + ", keyword:" + Arrays.toString(keywordList) + ", fieldList:" + Arrays.toString(fieldList));
        }

        // Set up the variables for the loop
        int count = keywordList.length;
        WebElementLocator elementLocator = WebElementLocator.getInstance();

        // For each item in the lists
        for (int i = 0; i < count; i++) {
            WebElement element = elementLocator.locateElement(fieldList[i], fieldReplacementValue, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), true);
            allKeywordsPresent = element.getText().contains(keywordList[i].trim());

            // areas of text (eg, inside <td> will return element.getAttribute("value") as null
            String value = element.getAttribute("value");
            logger.info("Looking for: " + keywordList[i] + " in [text: " + element.getText() + ", attrValue: " + value + "]");
            if (element.getText().isEmpty() && value != null) {
                // get the value from text input elements
                allKeywordsPresent = value.contains(keywordList[i].trim());
            }
            if (!allKeywordsPresent) {
                break;
            }
        }
        assertTrue(allKeywordsPresent);
    }

    public void isTextPresentAtLocation(final String keyword, final String field, final String beanFileName, final String beanPath) throws Exception {
        isTextPresentAtLocation(keyword, field, "", beanFileName, beanPath);
    }

    /**
     * This checks to see if there is any text present at the given field location
     * <p>
     * This Method can assert the presence of multiple elements separated by a delimiter in the field parameter
     *
     * @param field        A keyword location of an element in an xml file as a String
     * @param beanFileName The name and path of the xml file to look in as a String
     * @param beanPath     The element node type location in the xml file as a String
     * @throws Exception If the text is not present then it throws an error
     */
    public void isAnyTextPresentAtLocation(final String field, final String beanFileName, final String beanPath) throws Exception {
        isAnyTextPresentAtLocation(field, "", beanFileName, beanPath);
    }

    public void isAnyTextPresentAtLocation(final String field, String fieldReplacement, final String beanFileName, final String beanPath) throws Exception {

        // Get a locator
        WebElementLocator elementLocator = WebElementLocator.getInstance();

        // See if there are multiple elements to look in
        String[] fieldList = field.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));

        // Set up a boolean to follow the results
        boolean allTextPresent = false;

        // For each element to look in
        for (String singleField : fieldList) {

            // Get the web element from the given field
            WebElement element = elementLocator.locateElement(singleField, fieldReplacement, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), true);

            // If the element is not empty
            if (!element.getText().trim().isEmpty()) {
                allTextPresent = true;
            } else {
                // Text is not found so false and then break from the loop as it's already failed
                allTextPresent = false;
                break;
            }
        }

        // Assert the result
        assertTrue(allTextPresent);
    }

    /**
     * This checks to see if expected Title text is present at the given field location
     * <p>
     * This Method can assert the presence of multiple elements separated by a delimiter in the field parameter
     *
     * @param keyword      The text to search for
     * @param field        A keyword location of an element in an xml file as a String
     * @param beanFileName The name and path of the xml file to look in as a String
     * @param beanPath     The element node type location in the xml file as a String
     * @throws Exception If the text is not present then it throws an error
     */


    public void isAttributeTextPresentAtLocation(final String keyword, final String field, final String attribute, final String beanFileName, final String beanPath) throws Exception {

        // Split the keywords and fields up using the delimiter
//        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
//        String[] fieldList = field.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
        boolean allKeywordsPresent = false;

        // If the lists are the same length then do the checking, else throw an error
//        if (keywordList.length == fieldList.length) {

            // Set up the variables for the loop
//            int count = keywordList.length;
            WebElementLocator elementLocator = WebElementLocator.getInstance();

            // For each item in the lists
//            for (int i = 0; i < count; i++) {

                WebElement element = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);
                logger.info("Assert keyword is: " + keyword);
                allKeywordsPresent = element.getText().contains(keyword.trim());

                if (element.getText().isEmpty() && element.getAttribute(attribute) != null) {
                    // get the title text from elements
                    allKeywordsPresent = element.getAttribute(attribute).contains(keyword.trim());
                }

//                if (!allKeywordsPresent) {
//                    break;
//                }
//            }
//        } else {
//            throw new Exception("Keyword list and field list count does not match");
//        }

        assertTrue(allKeywordsPresent);

    }

    public void isAttributeTextPresentAtLocation(final String keyword, final String field, final String fieldReplacement, final String attribute, final String beanFileName, final String beanPath) throws Exception {

        // Split the keywords and fields up using the delimiter
        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
        String[] fieldList = field.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
        boolean allKeywordsPresent = false;

        // If the lists are the same length then do the checking, else throw an error
        if (keywordList.length == fieldList.length) {

            // Set up the variables for the loop
            int count = keywordList.length;
            WebElementLocator elementLocator = WebElementLocator.getInstance();

            // For each item in the lists
            for (int i = 0; i < count; i++) {
                WebElement element = elementLocator.locateElement(fieldList[i], fieldReplacement, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), true);

                logger.info("Assert keyword is: " + keywordList[i]);
                allKeywordsPresent = element.getText().contains(keywordList[i].trim());

                if (element.getText().isEmpty() && element.getAttribute(attribute) != null) {
                    // get the title text from elements
                    allKeywordsPresent = element.getAttribute(attribute).contains(keywordList[i].trim());
                }

                if (!allKeywordsPresent) {
                    break;
                }
            }
        } else {
            throw new Exception("Keyword list and field list count does not match");
        }

        assertTrue(allKeywordsPresent);

    }


    public String getAttributeValueFromElement(final String field, final String fieldReplacement, final String attribute, final String beanFileName, final String beanPath) throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(field, fieldReplacement, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), true);

        return element.getAttribute(attribute);
    }

//    public ArrayList<String> getAttributeValueFromAllElement(final String field, final String fieldReplacement, final String attribute, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable) throws Exception {
//
//        ArrayList<String> attributeValues = new ArrayList<String>();
//        WebElementLocator elementLocator = WebElementLocator.getInstance();
//        List<WebElement> elements = elementLocator.locateMultipleElements(field, fieldReplacement, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
//
//        for(WebElement element : elements){
//            logger.info("Element : " + element.getText());
//            logger.info("Element Attribute: " + element.getAttribute(attribute));
//
//            String src = ((JavascriptExecutor)getDriver()).executeScript("return arguments[0].attributes['" + attribute + "'].value;", element).toString();
//            logger.info("Actual Source is: " + src);
//
//            attributeValues.add(element.getAttribute(attribute));
//        }
//        return attributeValues;
//    }

    public HashMap<String,String> getAttributeValueFromAllElement(final String field, final String fieldReplacement, final String attribute, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable) throws Exception {

        HashMap<String,String> attributeValues = new HashMap<>();
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        List<WebElement> elements = elementLocator.locateMultipleElements(field, fieldReplacement, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);

        for(WebElement element : elements){
//            logger.info("Actual Attribute in Source: " + ((JavascriptExecutor)getDriver()).executeScript("return arguments[0].attributes['" + attribute + "'].value;", element).toString());
//            logger.info("Element Attribute by Selenium: " + element.getAttribute(attribute));

            if(element.getAttribute(attribute) != null)
                attributeValues.put(((JavascriptExecutor)getDriver()).executeScript("return arguments[0].attributes['" + attribute + "'].value;", element).toString(),element.getAttribute(attribute));
        }
        return attributeValues;
    }



}