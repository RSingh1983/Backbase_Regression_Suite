package org.testing.framework.backend.webElements.pageElements;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;


import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.backend.webElements.locator.WebElementLocator;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class Url extends PageObject {

    static Logger logger = LoggerFactory.getLogger(Url.class.getName());

    public Url(WebDriver driver) {
        super(driver);
    }

    public void open_page(String url) throws Exception {
        WebDriver driver = getDriver();
        driver.manage().window().maximize();

        // TODO: Need to figure out a way to throw an error when a page doesn't
        // load. Otherwise
        // the test will just hang until global test timeout.
        // The following do NOT work for some unknown reason:
        // getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        // getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // getDriver().manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

        driver.get(url);
    }

//    public void open_page_chrome(String url) throws Exception {
//        WebDriver driver = getDriver();
//        System.setProperty("webdriver.chrome.driver", "/home/lavanya/Downloads/chromedriver_mac.exe");
//        WebDriver myD = new ChromeDriver();
//        myD.get(url);
//    }

    /**
     * This will get the current web pages' url and return it
     *
     * @return The url of the current page as a string
     */
    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    //SwitchWindow
    public void switchWindow() {
// Store the current window handle
        String winHandleBefore = getDriver().getWindowHandle();

// Switch to new window opened

        for (String winHandle : getDriver().getWindowHandles()) {
            if (!winHandle.equals(winHandleBefore)) {
                getDriver().switchTo().window(winHandle);
                logger.info("switching to new window");
                // break;
            }

        }
        System.out.println("LOGLOG " + getDriver().getCurrentUrl());
    }

    //SwitchWindow
    public void switchWindowWithTitle(String title) {
// Store the current window handle
        String winHandleBefore = getDriver().getWindowHandle();

// Switch to new window opened
        boolean foundWindow = false;
        String reqWindowHandle = null;
        for (String winHandle : getDriver().getWindowHandles()) {
            if (!winHandle.equals(winHandleBefore)) {
                getDriver().switchTo().window(winHandle);
                if (getDriver().getTitle().equals(title)) {
                    foundWindow = true;
                    reqWindowHandle = winHandle;
                    break;
                }
            }
        }
        if (foundWindow)
            getDriver().switchTo().window(reqWindowHandle);
        else
            throw new WebDriverException("No Window found with title "+title);
        System.out.println("LOGLOG " + getDriver().getCurrentUrl());
    }

    public void closeOtherWindows(){
        String currentWindow=getDriver().getWindowHandle();
        for (String winHandle : getDriver().getWindowHandles()) {
            if (!winHandle.equals(currentWindow)) {
                getDriver().switchTo().window(winHandle);
                getDriver().close();
            }
        }
        getDriver().switchTo().window(currentWindow);
    }

    //SwitchWindow
    public void switchWindow(String win_Id) {

// Switch to new window opened
        String winHandle = (String) getDriver().getWindowHandles().toArray()[Integer.parseInt(win_Id)];
        System.out.println("je baat : " + winHandle);
        getDriver().switchTo().window(winHandle);
        System.out.println("LOGLOG " + getDriver().getCurrentUrl());
    }

    //public void getAlertBox
    public Boolean wait_function(String strTime) {
        try {
            int iSleepWait = Integer.parseInt(strTime);
            Thread.sleep(iSleepWait);
            return true;
        } catch (Exception e) {
            logger.info("Exception occurred while waiting for specified time");
            e.printStackTrace();
            return false;
        }
    }

    public void closeBrowser() {
        getDriver().quit();
    }

    /**
     * This will close the current selected window, if its the only one open then it will close the browser
     */
    public void closeCurrentWindow() {
        getDriver().close();
    }

    public void selectAutoFillElementWithText(String autoText, int timeout, String autoselectPath, String pathAppend) {
        WebDriver driver = getDriver();
        String strIdentifier = autoselectPath;
        String strIdentifier1 = pathAppend.replace("_##_", autoText);
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strIdentifier)));

        WebElement autoFillElement = driver.findElement(By.xpath(strIdentifier1));
        autoFillElement.click();
        // autoFillElement.findElement(By.xpath(strIdentifier1)).click();
    }

    /**
     *
     * @param strUrl
     * @return Body text of webpage
     * @throws Exception
     */
    public String open_to_NewTab_GetText(String strUrl) {

        //Switching between tabs using CTRL + tab keys.
        String bodyText;
        String currentWindow = getDriver().getWindowHandle();


        getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
        getDriver().get(strUrl);
        bodyText = getDriver().findElement(By.cssSelector("body")).getText();

        //Switch to current selected tab's content
        getDriver().switchTo().window(currentWindow);

        getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
        return bodyText;
    }

    /**
     * This will assert whether the current page contains the url address being passed
     * with a given url in the xml file from the field location
     *
     * @param field			A keyword location of an element in an xml file as a String
     * @param beanFileName	The name and path of the xml file to look in as a String
     * @param beanPath		The element node type location in the xml file as a String
     * @throws Exception	If the current windows url is not the one thats been passed
     */
    public void isCurrentWindowUrlCorrect(final String field, final String beanFileName, final String beanPath) throws Exception {

        // Find the url from the field in the xml file

        // Create an element locator
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        // Get the value of the field in the xml file and put it into the string url
        String url = elementLocator.getElementPath(field, beanFileName, beanPath);

        // Assert true if the current url contains the url passed else false
        boolean contains = getDriver().getCurrentUrl().contains(url);
        if(!contains) {
            logger.error(getDriver().getCurrentUrl()+" doesn't contain "+url);
        }
        assertTrue(contains);
    }

    public void isCurrentWindowUrlCorrect(final String valueToSearch) throws Exception {
        logger.info("Current Url for Page is -"+getDriver().getCurrentUrl().toString());
        boolean contains = getDriver().getCurrentUrl().contains(valueToSearch);
        if(!contains) {
            logger.error(getDriver().getCurrentUrl()+" doesn't contain "+valueToSearch);
        }
        assertTrue(contains);
    }

    /**
     * This will check to see if there is a new window open
     * It achieves this by looking at how many windows it can find, if more than one then asserts true
     * This will also make the window the come to the fore front
     *
     * @throws Exception	If a new windows window is not open or can be switched to
     */
    public void isANewWindowOpen() throws Exception {

        // Set a boolean value
        boolean newWindow = false;

        // Get the driver
        WebDriver driver = getDriver();

        // Get all the available windows open as a list
        ArrayList<String> availableWindows = new ArrayList<String>(driver.getWindowHandles());
        // If there is more than 1 window open
        if (availableWindows.size() > 1) {
            // Switch to the last one
            driver.switchTo().window(availableWindows.get(availableWindows.size()-1));
            newWindow = true;
        }

        // Assert whether the window was open or not
        assertTrue(newWindow);
    }

    /**
     * This will check to see if there is a new window open with a given url in the xml file from the field location
     * It achieves this by looking at how many windows it can find, if more than one then asserts true
     * This will also make the window the come to the fore front
     *
     * @param field			A keyword location of an element in an xml file as a String
     * @param beanFileName	The name and path of the xml file to look in as a String
     * @param beanPath		The element node type location in the xml file as a String
     * @throws Exception	If a new window is not present with the passed url
     */
    public void isANewWindowOpenWithUrl(final String field, final String beanFileName, final String beanPath) throws Exception {

        // Set a boolean value
        boolean newWindow = false;

        // Find the url from the field in the xml file

        // Create an element locator
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        // Get the value of the field in the xml file and put it into the string url
        String url = elementLocator.getElementPath(field, beanFileName, beanPath);

        // If there is a url to find then search for it
        if(!url.isEmpty()) {
            // Get the driver
            WebDriver driver = getDriver();

            // Get all the available windows open as a list
            ArrayList<String> availableWindows = new ArrayList<String>(driver.getWindowHandles());

            // If there is at least 1 window open
            if (availableWindows.size() >= 1) {
                // Check each window open
                for(String window : availableWindows) {
                    // Switch to the window
                    driver.switchTo().window(window);
                    // Get the current url of the window
                    String windowUrl = driver.getCurrentUrl();

                    // If the window is the one with the passed url
                    if(windowUrl.contains(url)) {
                        // Url is found so set true and exit out of the loop
                        logger.info("Found: " + url);
                        newWindow = true;
                        break;
                    }
                }
            }
        }

        // Assert whether the window was open or not
        assertTrue(newWindow);
    }
}
