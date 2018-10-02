package org.testing.framework.backend.webElements.pageElements;

import net.serenitybdd.core.pages.PageObject;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testing.framework.backend.webElements.locator.WebElementLocator;

import net.thucydides.core.webdriver.javascript.JavascriptExecutorFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.properties.LoadProjectProperties;

import static org.junit.Assert.assertTrue;

public class JavaScript extends PageObject {

    static Logger logger = LoggerFactory.getLogger(JavaScript.class.getName());

    public JavaScript(WebDriver driver) {
        super(driver);
    }

    /**
     * Retrieve the value associated to a css key
     * For example:
     *  given: a {color:#777;}
     *  this function will return the string #777 for the cssKey 'color'
     * @param field the web element for which we want to retrieve a CSS information
     * @param cssKey the css key
     * @return the cssValue associated to the cssKey
     * @throws Exception
     */
    public String retrieveCssValuewithReplacement(final String field,String fieldReplacement, String cssKey, final String beanFileName, final String beanPath) throws Exception {
        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        String[] fieldList = field.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_TEXT_DELIMITER));
        for (String singleField : fieldList) {
            WebElement elementFound = elementLocator.locateElement(field, fieldReplacement, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER),beanFileName, beanPath, getDriver(), true);

            // If the element has been found
            if (elementFound != null) {
                JavascriptExecutor javaExecutor = (JavascriptExecutor) getDriver();
                // retrieve the css value
                String cssValue = (String) javaExecutor.executeScript("return $(arguments[0]).css('" + cssKey + "');", elementFound);
                //String otherCss = (String)javaExecutor.executeScript("return getComputedStyle(arguments[0]).background-color;", elementFound);
                //logger.info("cssValue: "+cssValue+", otherCss: "+otherCss);
                return cssValue;
            }
        }
        return StringUtils.EMPTY;
    }

    public void removeReadOnlyAttribute(final String field, final String beanFileName, final String beanPath) throws Exception {

        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);

        // If the element has been found
        if (elementFound != null) {
            logger.info("document.getElementById('"+ elementFound.getAttribute("id") +"').removeAttribute('readonly',0);");
            JavascriptExecutorFacade javaExecutor = new JavascriptExecutorFacade(getDriver());
            javaExecutor.executeScript("document.getElementById('"+ elementFound.getAttribute("id") +"').removeAttribute('readonly',0);");
        }
    }

    //This will scroll down to bottom of the page
    public void scrollDownToBottomOfPage() throws Exception {
        JavascriptExecutor javaExecuter = (JavascriptExecutor)getDriver();

        javaExecuter.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
    }

    //This will scroll automatically to specified web element
    public void scrollToElement(final String field, final String beanFileName, final String beanPath) throws Exception {
        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);

        // If the element has been found
        if (elementFound != null) {
            JavascriptExecutor javaExecutor = (JavascriptExecutor) getDriver();
            javaExecutor.executeScript("arguments[0].scrollIntoView();", elementFound);
        }
    }

    //This will scroll automatically to specified web element
    public void scrollToElement(final String field, final String replaceString, final String beanFileName, final String beanPath) throws Exception {
        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, replaceString, beanFileName, beanPath, getDriver(), true);

        // If the element has been found
        if (elementFound != null) {
            JavascriptExecutor javaExecutor = (JavascriptExecutor) getDriver();
            javaExecutor.executeScript("arguments[0].scrollIntoView();", elementFound);
        }
    }

    //This will scroll automatically to specified web element
    public void scrollToElementXCoordinate(final String field, final String beanFileName, final String beanPath) throws Exception {
        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);

        // If the element has been found
        if (elementFound != null) {
            JavascriptExecutor javaExecutor = (JavascriptExecutor) getDriver();
            javaExecutor.executeScript("window.scrollTo(0," + elementFound.getLocation().x + ")");
        }
    }

    /**
     * Retrieve the value associated to a css key
     * For example:
     *  given: a {color:#777;}
     *  this function will return the string #777 for the cssKey 'color'
     * @param field the web element for which we want to retrieve a CSS information
     * @param cssKey the css key
     * @return the cssValue associated to the cssKey
     * @throws Exception
     */
    public String retrieveCssValue(final String field, String cssKey, final String beanFileName, final String beanPath) throws Exception {
        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);

        // If the element has been found
        if (elementFound != null) {
            JavascriptExecutor javaExecutor = (JavascriptExecutor) getDriver();
            // retrieve the css value
            String cssValue = (String)javaExecutor.executeScript("return $(arguments[0]).css('"+cssKey+"');", elementFound);
            //String otherCss = (String)javaExecutor.executeScript("return getComputedStyle(arguments[0]).background-color;", elementFound);
            //logger.info("cssValue: "+cssValue+", otherCss: "+otherCss);
            return cssValue;
        }
        return StringUtils.EMPTY;
    }

    public String javaScriptExecutor(String strCommand) throws Exception {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) getDriver();
        String values = javaScriptExecutor.executeScript(strCommand).toString();
        return values;
    }

    public void scrollDownToTopOfPage() throws Exception {
        JavascriptExecutor javaExecuter = (JavascriptExecutor)getDriver();

        javaExecuter.executeScript("window.scrollTo(0,0);");
    }

    public String getTextFromElementById(final String field, final String beanFileName, final String beanPath) throws Exception {
        String id=null;
        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);

        // If the element has been found
        if (elementFound != null) {
            id= ((JavascriptExecutor)getDriver()).executeScript("return document.getElementById('"+ elementFound.getAttribute("id") +"').value").toString();
            logger.info("\n\n id is "+id);
        }
        return id;
    }

    public void scrollDownToCoordinate(String x,String y) throws Exception {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)getDriver();
        javascriptExecutor.executeScript("window.scrollBy(" + x + "," + y + ");");
    }

    /**
     * Checks to see if there is an javascript alert present and dismiss it
     *
     * @return  Alert Message
     */
    public String assert_and_dismiss_JavascriptAlert() {
        Boolean isAlertPresent = isJavascriptAlert("", false);
        assertTrue("Javascript alert is not present",isAlertPresent);
        Alert alert = getDriver().switchTo().alert();// Accept it so that the web driver can move on
        String strAlertText = alert.getText();
        alert.dismiss();
        logger.info("Javascript alert has been Dismissed: "+ strAlertText);
        return strAlertText;
    }

    /**
     * Checks to see if there is an javascript alert present and dismiss it
     *
     * @param errorMsg The message to check for as a String
     * @return Alert Message
     */
    public String assert_and_dismiss_JavascriptAlert(String errorMsg) {
        Boolean isAlertPresent = isJavascriptAlert(errorMsg, false);
        assertTrue("Javascript alert is not present",isAlertPresent);
        Alert alert = getDriver().switchTo().alert();// Accept it so that the web driver can move on
        String strAlertText = alert.getText();
        alert.dismiss();
        logger.info("Javascript alert has been Dismissed: "+ strAlertText);
        return strAlertText;
    }

    /**
     * Checks to see if there is an javascript alert present
     *
     * @throws Exception If no javascript alert is present
     */
    public Boolean isJavascriptAlert() {
        return isJavascriptAlert("", false);
    }

    public Boolean isJavascriptAlert(String errorMsg) {
        return isJavascriptAlert(errorMsg, false);
    }

    /**
     * Checks to see if there is an javascript alert present with a given error message text
     *
     * @param errorMsg The message to check for as a String
     * @throws Exception If no javascript alert is present or the message does not match
     */
    public Boolean isJavascriptAlert(String errorMsg, boolean ignoreError) {
        // Set up a boolean value to follow the alert and set it to not found
        boolean alertFound = false;

        // Try and get the alert
        try {
            // Get the alert
            Alert alert = getDriver().switchTo().alert();

            // If the error message is not empty then test it
            if(!errorMsg.isEmpty()) {
                // If the alert text is as expected
                logger.info(alert.getText());
                if(alert.getText().trim().equalsIgnoreCase(errorMsg.trim())) {
                    // Set to alert found to true
                    alertFound = true;
                }
            }
            // Its not for testing the message, just accept the alert is present
            else {
                // Set to alert found to true
                alertFound = true;
            }


            // Accept it so that the web driver can move on
            alert.accept();
            logger.info("Javascript alert has been closed: "+alert.getText());
        }
        // If its not present then say so
        catch(NoAlertPresentException Ex) {
            logger.info("No javascript alert found");
        }
        if(ignoreError) {
            return alertFound;
        }

        return alertFound;
    }

    /**
     * Checks to see if there is an javascript alert present and accept it
     *
     * @return  Alert Message
     */
    public String assert_and_accept_JavascriptAlert() {
        Boolean isAlertPresent = isJavascriptAlert("", false);
        assertTrue("Javascript alert is not present",isAlertPresent);
        Alert alert = getDriver().switchTo().alert();// Accept it so that the web driver can move on
        String strAlertText = alert.getText();
        alert.accept();
        logger.info("Javascript alert has been Accepted: "+ strAlertText);
        return strAlertText;
    }

    /**
     * Checks to see if there is an javascript alert present and accept it
     *
     * @param errorMsg The message to check for as a String
     * @return Alert Message
     */
    public String assert_and_accept_JavascriptAlert(String errorMsg) {
        Boolean isAlertPresent = isJavascriptAlert(errorMsg, false);
        assertTrue("Javascript alert is not present",isAlertPresent);
        Alert alert = getDriver().switchTo().alert();// Accept it so that the web driver can move on
        String strAlertText = alert.getText();
        alert.accept();
        logger.info("Javascript alert has been Accepted: "+ strAlertText);
        return strAlertText;
    }


    public void accept_alert()
    {
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }


    public void WaitAndAcceptTheAlert()
    {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), 30);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = getDriver().switchTo().alert();
            alert.accept();
        }
        catch (NoAlertPresentException e) {
            e.printStackTrace();
        }

    }

    public void ClickDropDownAndSelectValue(String Dropdown,String Value) throws  Exception
    {
        logger.info("before select initiated");
        org.openqa.selenium.support.ui.Select SelectByVisibleText=new Select(getDriver().findElement(By.id(Dropdown)));
        logger.info(" select initiated");
        SelectByVisibleText.selectByVisibleText(Value);
        logger.info("value initiated");
    }

    public void WaitTillVisible(String Element) throws  Exception
    {

        logger.info("before wait object");
        WebDriverWait wait = new WebDriverWait(getDriver(), 600);
        logger.info("after wait object");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Element)));
        logger.info("found element" + Element);

    }

    public boolean imageAppearsOnPage(final String field, final String beanFileName, final String beanPath) throws Exception{

        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);

        boolean loaded = false;
        // If the element has been found
        if (elementFound != null) {
            Object result = ((JavascriptExecutor) getDriver()).executeScript(
                    "return arguments[0].complete && " +
                            "typeof arguments[0].naturalWidth != \"undefined\" && " +
                            "arguments[0].naturalWidth > 0", elementFound);
            if (result instanceof Boolean) {
                loaded = (Boolean) result;
                System.out.println(loaded);
            }
        }
        return loaded;
    }

    public boolean imageAppearsOnPage(final String field, final String replaceString, final String beanFileName, final String beanPath) throws Exception{

        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement elementFound = elementLocator.locateElement(field, replaceString, beanFileName, beanPath, getDriver(), true);

        boolean loaded = false;
        // If the element has been found
        if (elementFound != null) {
            Object result = ((JavascriptExecutor) getDriver()).executeScript(
                    "return arguments[0].complete && " +
                            "typeof arguments[0].naturalWidth != \"undefined\" && " +
                            "arguments[0].naturalWidth > 0", elementFound);
            if (result instanceof Boolean) {
                loaded = (Boolean) result;
                System.out.println(loaded);
            }
        }
        return loaded;
    }
}

