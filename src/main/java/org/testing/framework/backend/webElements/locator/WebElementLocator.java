package org.testing.framework.backend.webElements.locator;

import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testing.framework.model.uiModels.WebElementsModel;
import org.testing.framework.properties.LoadProjectProperties;
import org.testing.framework.utils.file.FileHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class WebElementLocator {

    static Logger logger = LoggerFactory.getLogger(WebElementLocator.class.getName());

    @Autowired
    public WebElementsModel webElementsModel;

    private enum ElementLocatorType {
        id, name, css, className, xpath, linkText, xpathDataID, cssDataID
    }

    private enum ElementLocatorNode {
        keyword, element_locator_type, element_locator_value, selector_parent, selector_debug
    }

    private static WebElementLocator instance = null;

    public static WebElementLocator getInstance() {
        if (instance == null) {
            instance = new WebElementLocator();
        }
        return instance;
    }
    private static String XML_EXTENSION = "xml";

    /**
     * This will return an element from a current web page in the driver
     *
     * @param keyword		A keyword location of an element in an xml file as a String - The element to look inside
     * @param beanFileName	The name and path of the xml file to look in as a String
     * @param beanPath		The element node type location in the xml file as a String
     * @param driver		The driver for the web page
     * @param raiseErrorIfUnavailable	A boolean that says whether to throw the error
     * @return 				A webElements
     * @throws Exception	If the elements can't be found
     */
    public WebElement locateElement(String keyword, final String beanFileName, final String beanPath, final WebDriver driver,
                                    final boolean raiseErrorIfUnavailable) throws Exception {
        return this.locateElement(keyword, "", "", beanFileName, beanPath, driver, raiseErrorIfUnavailable);
    }

    public WebElement locateElement(String keyword,String repString ,final String beanFileName, final String beanPath, final WebDriver driver,
                                    final boolean raiseErrorIfUnavailable) throws Exception {
        return this.locateElement(keyword, repString, LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, driver, raiseErrorIfUnavailable);
    }

    /**
     * This will return an element from a current web page in the driver. Its able to replace a string in the value found from the keyword.
     *
     * @param keyword		A keyword location of an element in an xml file as a String - The element to look inside
     * @param replaceString	A string to insert into the value found from the keyword
     * @param replaceDelimiter	A String that will be replaced by the replaceString in keyword
     * @param beanFileName	The name and path of the xml file to look in as a String
     * @param beanPath		The element node type location in the xml file as a String
     * @param driver		The driver for the web page
     * @param raiseErrorIfUnavailable	A boolean that says whether to throw the error
     * @return 				A WebElement
     * @throws Exception	If the elements can't be found
     */
    public WebElement locateElement(String keyword, String replaceString, String replaceDelimiter,
                                    final String beanFileName, final String beanPath, final WebDriver driver,
                                    final boolean raiseErrorIfUnavailable) throws Exception {
        Locator locator = new Locator(keyword, replaceString, replaceDelimiter, beanFileName,
                beanPath, driver, raiseErrorIfUnavailable);
        return locateElementByLocator(locator);
    }

    /**
     * This will return a list of all the elements matching the one that was passed
     *
     * @param keyword		A keyword location of an element in an xml file as a String
     * @param beanFileName	The name and path of the xml file to look in as a String
     * @param beanPath		The element node type location in the xml file as a String
     * @param driver		The driver for the web page
     * @param raiseErrorIfUnavailable	A boolean that says whether to throw the error
     * @return 				A list of webElements
     * @throws Exception	If the elements can't be found
     */
    public List<WebElement> locateMultipleElements(final String keyword, final String beanFileName, final String beanPath, final WebDriver driver,
                                                   final boolean raiseErrorIfUnavailable) throws Exception {
        Locator locator = new Locator(keyword, "", "", beanFileName,
                beanPath, driver, raiseErrorIfUnavailable);
        List<WebElement> elements = locateElementsByLocator(locator);
        logger.info(elements.size()+" elements found");
        return elements;
    }

    public List<WebElement> locateMultipleElements(final String keyword,final String replace,String replaceDelimiter, final String beanFileName, final String beanPath, final WebDriver driver,
                                                   final boolean raiseErrorIfUnavailable) throws Exception {
        Locator locator = new Locator(keyword, replace, replaceDelimiter, beanFileName,
                beanPath, driver, raiseErrorIfUnavailable);
        List<WebElement> elements = locateElementsByLocator(locator);
        logger.info(elements.size()+" elements found");
        return elements;
    }

    /**
     * This will get the amount of elements
     *
     * @param keyword		A keyword location of an element in an xml file as a String
     * @param beanFileName	The name and path of the xml file to look in as a String
     * @param beanPath		The element node type location in the xml file as a String
     * @param driver		The driver for the web page
     * @param raiseErrorIfUnavailable	A boolean that says whether to throw the error
     * @return				The count of the elements as an int
     * @throws Exception	If the elements can't be found
     */
    public int getElementCount(final String keyword, final String beanFileName, final String beanPath, final WebDriver driver,
                               final boolean raiseErrorIfUnavailable) throws Exception {
        return locateMultipleElements(keyword, beanFileName, beanPath, driver, raiseErrorIfUnavailable).size();
    }

    public String getElementPath(String keyword, final String beanFileName, final String beanPath) throws Exception {
        Locator locator = new Locator(keyword, beanFileName, beanPath);

        DocumentBuilder documentBuilder = createDocumentBuilder();

        // Get the files to search in
        File[] files = FileHandler.getFileList(locator.getbeanFileName(), XML_EXTENSION);

        // For each xml file to check
        for(File xmlFile : files) {
            logger.info("file looking into for selector  "+xmlFile.getAbsolutePath());
            // find the selector tag by xpath using keyword
            Selector selector = findSelector(locator, documentBuilder, xmlFile);
            if (selector != null) {
                return selector.getLocatorValue();
            }
        }
        return "";
    }

    /**
     * This method allows to wait ajax to complete
     * @param driver
     * @param ajaxBusyIdentifier
     * @throws IOException
     * @throws InterruptedException
     */
    public void waitForAjaxToComplete(final WebDriver driver, String ajaxBusyIdentifier) throws IOException, InterruptedException {
        Boolean ajaxBusy = driver.findElement(By.id(ajaxBusyIdentifier)).isDisplayed();
        long maxPageLoadTimeout = LoadProjectProperties.getNumberProperty(LoadProjectProperties.WEBDRIVER_PAGELOAD_MAXTIMEOUT_SEC);
        long secondsElapsedWaiting = 0;
        while (ajaxBusy && secondsElapsedWaiting < maxPageLoadTimeout) {
            logger.info("Ajax component is still busy. Wait 1 second.");
            Thread.currentThread().sleep(1000);
            secondsElapsedWaiting++;
            ajaxBusy = driver.findElement(By.id(ajaxBusyIdentifier)).isDisplayed();
        }
        if (ajaxBusy) {
            throw new org.openqa.selenium.TimeoutException("Ajax component failed to load within " + maxPageLoadTimeout + " seconds.");
        }
    }

    /**
     * Algorithm to find all elements in a HTML page from a locator
     * @param locator contains all information to locate elements
     * @return list of web elements found in the html page
     * @throws Exception if element is not found and isRaiseError = True
     */
    private List<WebElement> locateElementsByLocator(Locator locator) throws Exception {
        DocumentBuilder documentBuilder = createDocumentBuilder();

        // Get the files to search in
        File[] files = FileHandler.getFileList(locator.getbeanFileName(), XML_EXTENSION);

        // For each xml file to check
        for(File xmlFile : files) {
            // find the selector tag by xpath using keyword
            Selector selector = findSelector(locator, documentBuilder, xmlFile);
            if(selector == null) {
                continue;
            }
            try{
                SearchContext searchContext = locator.getDriver();
                // look inside an element if exists
                if(selector.getParent() != null) {
                    searchContext = findParentByLocator(new Locator(selector.getParent(), locator));
                }

                List<WebElement> elementsFound = findElementsBySelector(selector, locator, searchContext);
                // Break out of the loop if it's been found
                if (!CollectionUtils.isEmpty(elementsFound)) {
                    return elementsFound;
                }
            } catch (Exception e) {

                LogEntries logEntries = getDriver().manage().logs().get(LogType.BROWSER);
                logger.info("***********************************************Logs from Browser ***************************************************************************************");
                for (LogEntry entry : logEntries) {
                    logger.info(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
                }
                logger.info("*********************************************************************************************************************************************************");

                // for debugging purpose, list element that matches the debug selector
                if(selector.getDebugSelector() != null) {
                    logger.error(e.getMessage());
                    logger.error("######## DEBUG SELECTOR MODE - START ###########");
                    Locator debugLocator = new Locator(selector.getDebugSelector(), locator);
                    Selector debugSelector = findSelector(debugLocator, documentBuilder, xmlFile);
                    List<WebElement> elementsFound = findElementsBySelector(debugSelector, debugLocator, locator.getDriver());
                    for (WebElement element : elementsFound) {
                        logger.error("# element found: " + element.getAttribute("id"));
                    }
                    logger.error("######## DEBUG SELECTOR MODE - END ###########");
                    throw e;
                } else {
                    if(locator.isRaiseErrorIfUnavailable()) {
                        logger.warn(e.getMessage(), e);
                    }
                }
            }
        }

        Exception e = new NoSuchElementException(locator.toString());
        if(locator.isRaiseErrorIfUnavailable()) {
            throw e;
        } else {
            logger.warn("*************************************************************************************");
            logger.warn(e.getMessage());
            logger.warn("*************************************************************************************");
        }
        return Collections.emptyList();
    }

    private WebElement findParentByLocator(Locator locator) throws Exception {
        // looking for parent element if exists
        WebElement parent = locateElementByLocator(locator);
        // Create an action and move the mouse to the centre of the element - simulates a hover over action
        Actions action = new Actions(locator.getDriver());
        action.moveToElement(parent).build().perform();
        return parent;
    }

    private Selector findSelector(Locator locator, DocumentBuilder documentBuilder, File xmlFile) throws SAXException, IOException, XPathExpressionException {
        Document document = documentBuilder.parse(xmlFile);
        XPath xp = XPathFactory.newInstance().newXPath();
        String xpath = locator.getbeanPath() + "/keyword[.= '" + locator.getKeyword() + "']/..";
        logger.debug("looking for selector [xpath: "+xpath+", xml: "+xmlFile.getName()+"]");
        XPathExpression xPathExpression = xp.compile(xpath);
        NodeList nodes = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        if(nodes.getLength() == 0) {
            logger.warn("no selector found [xpath: "+xpath+", xml: "+xmlFile.getPath()+"]");
            return null;
        }
        if(nodes.getLength() > 1) {
            throw new IllegalStateException("more than 1 node has been found [nodes: "+nodes.getLength()+", xpath: "+xpath+"]");
        }
        Node item = nodes.item(0);
        return new Selector((Element) item);
    }

    /**
     * Find all visible web elements functions of some criteria
     * @param selector the selector defined in xmlLocation for a specific keyword
     * @param locator specified different parameters...
     * @param searchContext the page (= the driver) or the parent selector
     * @return list of all visible elements with expected conditions
     * @throws Exception
     */
    private List<WebElement> findElementsBySelector(Selector selector, Locator locator, SearchContext searchContext) throws Exception {
        WebDriverWait wait = new WebDriverWait(locator.getDriver(),
                LoadProjectProperties.getNumberProperty(
                        LoadProjectProperties.WEBDRIVER_ELEMENTLOAD_MAXTIMEOUT_SEC));

        String selectorType = selector.getLocatorType();

        // Update all the replaceDelimiter
        String searchValue = "";
        searchValue = selector.getLocatorValue().replace(locator.replaceDelimiter, locator.replaceString);

        String message = "Looking for element by " + selectorType + ": " + searchValue + ", keyword: " + locator.getKeyword();
        if(selector.isLookingInsideAnElement()) {
            logger.info(message + ", parentKeyword: " + selector.getParent());
        } else {
            logger.info(message);
        }
        switch(ElementLocatorType.valueOf(selectorType)) {
            case id:
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(searchValue)));
                return searchContext.findElements(By.id(searchValue));
            case name:
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(searchValue)));
                return searchContext.findElements(By.name(searchValue));
            case css:
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(searchValue)));
                return searchContext.findElements(By.cssSelector(searchValue));
            case className:
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(searchValue)));
                return searchContext.findElements(By.className(searchValue));
            case xpath:
                // if we are looking inside an element, we add a "point" at the beginning, to avoid to look in the whole page with //
                if(selector.isLookingInsideAnElement() && !searchValue.startsWith(".")) {
                    searchValue = "." + searchValue;
                }
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchValue)));
                return searchContext.findElements(By.xpath(searchValue));
            case linkText:
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(searchValue)));
                return searchContext.findElements(By.linkText(searchValue));
            case xpathDataID:
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@date-id='"+searchValue+"']")));
                return searchContext.findElements(By.xpath("//*[@date-id='" + searchValue + "']"));
            case cssDataID:
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[date-id='"+searchValue+"']")));
                return searchContext.findElements(By.cssSelector("[date-id='" + searchValue + "']"));
            default:throw new AssertionError("selector "+selectorType+" hasn't been implemented yet.");
        }
    }

    private DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        return documentBuilderFactory.newDocumentBuilder();
    }

    private WebElement locateElementByLocator(Locator locator) throws Exception {
        List<WebElement> elements = locateElementsByLocator(locator);
        if(!(elements.isEmpty())) {
            WebElement element = elements.get(0);
            if (elements.size() > 1) {
                logger.warn("more than one element has been found : (" + elements.size() + " elements) using keyword: " + locator.getKeyword());
            }
            return element;
        } else {
            return null;
        }
    }

    public int getElementCount(final String keyword, String repacement,String delimiter,final String beanFileName, final String beanPath, final WebDriver driver,
                               final boolean raiseErrorIfUnavailable) throws Exception {
        return locateMultipleElements(keyword,repacement,delimiter,beanFileName, beanPath, driver, raiseErrorIfUnavailable).size();
    }

    /**
     * All criteria in one object...
     */
    class Locator {
        private String keyword;
        private final String beanPath;
        private final String replaceString;
        private final String replaceDelimiter;
        private final String beanFileName;
        private final WebDriver driver;
        private final boolean raiseErrorIfUnavailable;
        private boolean parent = false;

        Locator(String keyword, final String beanFileName, final String beanPath) {
            this(keyword, null, null, beanFileName, beanPath, null, true);
        }

        Locator(String keyword, String replaceString, String replaceDelimiter, String beanFileName,
                String beanPath, WebDriver driver, boolean raiseErrorIfUnavailable) {
            this.keyword = keyword;
            this.beanFileName = beanFileName;
            this.beanPath = beanPath;
            this.replaceString = replaceString;
            this.replaceDelimiter = replaceDelimiter;

            this.driver = driver;
            this.raiseErrorIfUnavailable = raiseErrorIfUnavailable;
            this.parent = false;
        }

        /**
         * Clone a locator with another keyword
         * @param parentKeyword the new locator's keyword
         * @param locator the locator to clone
         */
        public Locator(String parentKeyword, Locator locator) {
            this(parentKeyword, locator.replaceString, locator.replaceDelimiter, locator.beanFileName,
                    locator.beanPath,locator.driver,locator.raiseErrorIfUnavailable);
            this.parent = true;
        }

        @Override
        public String toString() {
            return "[keyword:" + keyword+", beanPath:"+ beanPath +", beanFileName:"+ beanFileName +"]";
        }

        public String getKeyword() {
            return keyword;
        }

        public String getbeanFileName() {
            return beanFileName;
        }

        public String getbeanPath() {
            return beanPath;
        }

        public WebDriver getDriver() {
            return driver;
        }

        public boolean isRaiseErrorIfUnavailable() {
            return raiseErrorIfUnavailable;
        }

        public boolean isParent() {
            return parent;
        }
    }

    /**
     * Represents a selector
     * Example:
     <selector>
     <keyword>productPriceAnySix</keyword>
     <element_locator_type>xpath</element_locator_type>
     <element_locator_value>.//li[1]/p</element_locator_value>
     <selector_parent>productAreaByName</selector_parent>
     </selector>
     */
    private class Selector {
        private String keyword;
        private String locatorType;
        private String locatorValue;
        private String parent;
        private String debugSelector;

        public Selector(Element element) {
            // keyword
            NodeList nodeList = element.getElementsByTagName(ElementLocatorNode.keyword.toString());
            this.keyword = nodeList.item(0).getChildNodes().item(0).getNodeValue();

            // locator type
            nodeList = element.getElementsByTagName(ElementLocatorNode.element_locator_type.toString());
            this.locatorType = nodeList.item(0).getChildNodes().item(0).getNodeValue();

            // locator value
            nodeList = element.getElementsByTagName(ElementLocatorNode.element_locator_value.toString());
            this.locatorValue = nodeList.item(0).getChildNodes().item(0).getNodeValue();

            // parent locator (optional)
            nodeList = element.getElementsByTagName(ElementLocatorNode.selector_parent.toString());
            if(nodeList.item(0) != null) {
                this.parent = nodeList.item(0).getChildNodes().item(0).getNodeValue();
            }

            // debug locator (optional)
            nodeList = element.getElementsByTagName(ElementLocatorNode.selector_debug.toString());
            if(nodeList.item(0) != null) {
                this.debugSelector = nodeList.item(0).getChildNodes().item(0).getNodeValue();
            }
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getParent() {
            return parent;
        }

        public boolean isLookingInsideAnElement() {
            return parent != null;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getLocatorType() {
            return locatorType;
        }

        public String getLocatorValue() {
            return locatorValue;
        }

        public String getDebugSelector() {
            return debugSelector;
        }
    }
}
