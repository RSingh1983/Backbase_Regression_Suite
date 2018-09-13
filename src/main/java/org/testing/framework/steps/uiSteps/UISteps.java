package org.testing.framework.steps.uiSteps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testing.framework.backend.webElements.locator.WebElementLocator;
import org.testing.framework.backend.webElements.pageElements.*;
import org.testing.framework.backend.webElements.validations.*;
import org.testing.framework.model.uiModels.WebElementsModel;
import org.testing.framework.properties.DisplayOrderType;
import org.testing.framework.steps.AuatSteps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public abstract class UISteps extends AuatSteps {

    @Autowired
    public WebElementsModel webElementsModel;

    static Logger logger = LoggerFactory.getLogger(UISteps.class.getName());
    private String email;

    Ajax ajaxWebElement;
    CheckBox checkBoxWebElement;
    Click clickWebElement;
    Iframe iframeWebElement;
    Input inputWebElement;
    JavaScript javaScriptWebElement;
    Select selectWebElement;
    Text textWebElement;
    Url urlWebElement;
    Table tableWebElement;

    GeneralElementValidations generalElementValidations;
    ImageValidations imageValidations;

//    Properties prop = new Properties();

    public UISteps(Pages pages) {
        super(pages);
        ajaxWebElement = getPages().get(Ajax.class);
        checkBoxWebElement = getPages().get(CheckBox.class);
        clickWebElement = getPages().get(Click.class);
        iframeWebElement = getPages().get(Iframe.class);
        inputWebElement = getPages().get(Input.class);
        javaScriptWebElement = getPages().get(JavaScript.class);
        selectWebElement = getPages().get(Select.class);
        tableWebElement = getPages().get(Table.class);
        textWebElement = getPages().get(Text.class);
        urlWebElement = getPages().get(Url.class);

        generalElementValidations = getPages().get(GeneralElementValidations.class);
        imageValidations = getPages().get(ImageValidations.class);
    }

    @Step
    public void verifyAlertMessage(String alertMsg) throws Exception {
        // clickWebElement.click_the_element(link, getBeanFilePath(), webElementsModel.getClickSelector(), true);
        javaScriptWebElement.isJavascriptAlert(alertMsg);
    }


    @Step
    public void move_Slider(String element, int xoffset, int yoffset) throws Exception {
        clickWebElement.move_Slider(element, xoffset, yoffset, getBeanFilePath(), webElementsModel.getClickSelector(), true);
    }


    @Step
    public void doubleClick(String element) throws Exception {
        clickWebElement.doubleClick(element, getBeanFilePath(), webElementsModel.getClickSelector(), true);
    }


    @Step
    public Integer getTableSize(String tableName) throws Exception {
        Integer tableSize = tableWebElement.getTableSize(tableName, getBeanFilePath(), webElementsModel.getTextSelector());
        return tableSize;
    }


    @Step
    public boolean checkIfElementPresent(String field) throws Exception {
        boolean val = checkIfElementPresent(field, "");
        return val;
    }

    @Step
    public boolean checkIfElementPresent(String field, String fieldReplacement) throws Exception {
        boolean val = generalElementValidations.checkIfElementPresent(field, fieldReplacement, getBeanFilePath(), webElementsModel.getClickSelector());
        return val;
    }

    @Step
    public void assertTextColor(String field, String fieldReplacement, String expectedColor) throws Exception {
        String cssColor = javaScriptWebElement.retrieveCssValue(field, "color", getBeanFilePath(), webElementsModel.getClickSelector());
        logger.info(cssColor);
        boolean in = cssColor.contains(expectedColor);
        if (!in) {
            logger.error(expectedColor + " not in " + cssColor);
        }
        assertTrue(in);
    }

    @Step
    public void click_the_button_check_alert(String buttonName, String replaceString) throws Exception {

        clickWebElement.click_the_element(buttonName, replaceString, getBeanFilePath(), webElementsModel.getClickSelector(), true);
        javaScriptWebElement.isJavascriptAlert("", true);

    }

    /**
     * This will return a url value from the urlMap
     *
     * @param id The id of the url in the map
     * @return
     */
    public String getUrl(String id) {
        return webElementsModel.getUrlMap().get(id);
    }


    // Bean File

    /**
     * This must return the location of the bean file/folder that holds the information for the test
     *
     * @return A string location for an bean file/folder
     */
    public abstract String getBeanFilePath();

    /**
     * This will get the value from a certain field in the xml file which has been defined in the text tag in xmlLocations
     *
     * @param field Name of the field in the xml file
     * @throws Exception
     * @return The value located by the field as a string
     */
    public String getBeanFileValue(String field) throws Exception {

        // Create an element locator
        WebElementLocator elementLocator = WebElementLocator.getInstance();

        // Create a value string and get the value of the field in the xml file
        String value = elementLocator.getElementPath(field, getBeanFilePath(), webElementsModel.getTextSelector());

        // If the value is not found throw an exception
        if (value.isEmpty()) {
            logger.warn("Value not found for field: " + field);
            throw new Exception("Value not found for field: " + field);
        }

        logger.info("Value of field " + field + " : " + value);

        // Return the value
        return value;
    }


    // Javascript

    /**
     * This will remove the read only attribute on a field so that
     *
     * @param field The field name of the element in the text area of the xml file
     * @throws Exception
     */
    public void removeReadOnlyAttribute(String field) throws Exception {
        javaScriptWebElement.removeReadOnlyAttribute(field, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    // Web page

    /**
     * This will get the text inside the element that has been passed in
     *
     * @param field The field to check inside
     * @return The String found in the element or empty
     * @throws Exception
     */
    public String getTextFromElement(String field) throws Exception {
        return textWebElement.getTextFromElement(field, getBeanFilePath(), webElementsModel.getTextSelector());
    }


    public String getTextFromElement(String field, String fieldReplacementValue) throws Exception {
        return textWebElement.getTextFromElement(field, fieldReplacementValue, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    public List<String> getTextFromElements(String field, String fieldReplacementValue) throws Exception {

        String text = "";
        List<String> list = new ArrayList<String>();
        List<WebElement> elementsFound = textWebElement.getWebElement(field, fieldReplacementValue, getBeanFilePath(), webElementsModel.getTextSelector());
        logger.info("");
        // If elements are not found
        if (elementsFound != null) {

            for (int i = 0; i < elementsFound.size(); i++) {
                text = elementsFound.get(i).getText();
                list.add(text);
            }
//            // areas of text (eg, inside <td> will return
//            // element.getAttribute("value") as null
//            if (text.isEmpty() && elementFound.getAttribute("value") != null) {
//                // get the value from text input elements
//                text = elementFound.getAttribute("value");
//            }

            if (list.isEmpty()) {
                logger.warn("No text found in element" + field);
            }

        }
        return list;

    }

    public String getTextFromElement(String field, String fieldReplacementValue, String replaceDelimiter) throws Exception {
        return textWebElement.getTextFromElement(field, fieldReplacementValue, replaceDelimiter, getBeanFilePath(), webElementsModel.getTextSelector());
    }


    //
    // Here are the steps for the BDD stories including web page manipulation
    //

    /**
     * Opens the site defined by the host name property in the webElementsModel of the testingContext bean
     *
     * @throws Exception
     */
    @Step
    public void open_the_home_page() throws Exception {
        String url = webElementsModel.getHostname();
        logger.info("Opening page: " + url);
        urlWebElement.open_page(url);
    }

    /**
     * Opens the site as defined in the urlMap of the testingContext bean
     *
     * @param webSiteName The id of the site to open in the urlMap
     * @throws Exception
     */
    @Step
    public void open_site(String webSiteName) throws Exception {
        String url = webElementsModel.getUrlMap().get(webSiteName);
        logger.info("Opening Page: " + url);
        urlWebElement.open_page(url);
    }

    /**
     * Opens the page as defined in the urlMap of the testingContext bean using the host name in the webElementsModel
     * as the base path
     *
     * @param webPageName The id of the page to open in the urlMap as defined in the urlMap of the testingContext bean
     * @throws Exception
     */
    @Step
    public void open_page(String webPageName) throws Exception {
        String url = webElementsModel.getHostname() + webElementsModel.getUrlMap().get(webPageName);
        logger.info("Opening page: " + url);
        urlWebElement.open_page(url);
    }

    /**
     * Opens the page as defined in the urlMap of the testingContext bean using the given urlMap id
     * as the base path and page id
     *
     * @param siteName The site name as defined in the urlMap of the testingContext bean
     * @param pageName The page name as defined in the urlMap of the testingContext bean to be appended to the site name
     * @throws Exception
     */
    @Step
    public void open_page(String siteName, String pageName) throws Exception {
        String url = webElementsModel.getUrlMap().get(siteName) + webElementsModel.getUrlMap().get(pageName);
        logger.info("Opening Page: " + url);
        urlWebElement.open_page(url);
    }

    /**
     * Opens the page as defined in the urlMap of the testingContext bean using the given urlMap id
     * as the base path and the url to be appended to the site name
     *
     * @param siteName The site name as defined in the urlMap of the testingContext bean
     * @param pageUrl  The page url to be appended to the site name
     * @throws Exception
     */
    @Step
    public void open_page_append_url(String siteName, String pageUrl) throws Exception {
        String url = webElementsModel.getUrlMap().get(siteName) + pageUrl;
        logger.info("Opening Page: " + url);
        urlWebElement.open_page(url);
    }

    /**
     * This will open the web page from a passed url string
     *
     * @param webPageUrl The web page to open as a String
     * @throws Exception
     */
    @Step
    public void open_page_from_url(String webPageUrl) throws Exception {
        logger.info("Opening Page: " + webPageUrl);
        urlWebElement.open_page(webPageUrl);
    }

    /**
     * This will select the iframe from the passed field in the xml file
     *
     * @throws Exception If the iframe is not found or switched to
     */
    @Step
    public void switch_to_iframe(String field) throws Exception {
        iframeWebElement.select_the_frame(field, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public String getCurrentWindowHandle() throws Exception {
        String parentHandle = getDriver().getWindowHandle();
        return parentHandle;
    }

    @Step
    public void maximizeWindow() throws Exception {
        getDriver().manage().window().maximize();
    }

    @Step
    public void switch_to_window() throws Exception {
        logger.info("switch to new window");
        for (String winHandle : getDriver().getWindowHandles()) {
            logger.info("new window handle" + winHandle);
            getDriver().switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }
    }

    @Step
    public void switch_to_parent_window(String parentHandle) throws Exception {
        logger.info("current window handle is" + getDriver().getWindowHandle());
        getDriver().close();
        logger.info("switch to parent");
        getDriver().switchTo().window(parentHandle);
    }

    @Step
    public void switch_to_Tab() throws Exception {
        //Switching between tabs using CTRL + tab keys.
        getDriver().findElement(org.openqa.selenium.By.cssSelector("body")).sendKeys(Keys.CONTROL + "\t");
        //Switch to current selected tab's content
        getDriver().switchTo().defaultContent();
    }

    /**
     * Gets the current pages url as a string
     *
     * @return The url as a string
     */
    public String getCurrentUrl() {
        return urlWebElement.getCurrentUrl();
    }

    @Step
    public void switch_to_nxt_window() {
        urlWebElement.switchWindow();
    }

    @Step
    public void switchWindow(String title) {
        urlWebElement.switchWindowWithTitle(title);
    }

    @Step
    public void closeOtherWindows() {
        urlWebElement.closeOtherWindows();
    }

    @Step
    public boolean wait_fn(String strTime) {
        return urlWebElement.wait_function(strTime);
    }

    @Step
    public void switch_to_nxt_window(String win_Id) {
        urlWebElement.switchWindow(win_Id);
    }

    @Step
    public void closeBrowser() {
        urlWebElement.closeBrowser();
    }

    /**
     * This will close the current selected window, if its the only one open then it will close the browser
     */
    @Step
    public void closeCurrentWindow() {
        urlWebElement.closeCurrentWindow();
    }

    @Step
    public void click_the_link(String link) throws Exception {
        click_the_link(link, "");
    }

    @Step
    public void click_the_link(String link, String linkReplacementValue) throws Exception {
        clickWebElement.click_the_element(link, linkReplacementValue, getBeanFilePath(), webElementsModel.getClickSelector(), true);
    }

    @Step
    public void click_the_link_if_available(String link) throws Exception {
        clickWebElement.click_the_element(link, getBeanFilePath(), webElementsModel.getClickSelector(), false);
    }

    @Step
    public void click_drag_drop(String strElementToMove, String strElementReplaced) throws Exception {
        clickWebElement.drag_drop_element(strElementToMove, strElementReplaced, getBeanFilePath(), webElementsModel.getClickSelector(), false);
    }

    @Step
    public void is_number_of_characters_correct(String element, int number) throws Exception {
        generalElementValidations.isNumberOfCharactersCorrect(element, number, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    /**
     * This will click on a link and check to see if a java alert was produced
     *
     * @param link The field where the link is loacted in the xml file
     * @throws Exception If the link or alert is not done
     */
    @Step
    public void click_the_link_and_check_alert(String link) throws Exception {
        clickWebElement.click_the_element(link, getBeanFilePath(), webElementsModel.getClickSelector(), true);
        javaScriptWebElement.isJavascriptAlert();
    }


    @Step
    public void click_the_link__with_location_check_alert(String link, String ReplaceChar) throws Exception {
        clickWebElement.click_the_element(link, ReplaceChar, getBeanFilePath(), webElementsModel.getClickSelector(), true);
        logger.info("got alert");
        javaScriptWebElement.isJavascriptAlert();
        logger.info("accept alert");
    }


    @Step
    public void accept_alert() throws Exception {
        javaScriptWebElement.accept_alert();
    }

    /**
     * This will click on a link and check to see if a java alert was produced with the passed msg
     *
     * @param link     The field where the link is located in the xml file
     * @param alertMsg A message to check for in the alert as a String
     * @throws Exception If the link or alert is not done
     */
    @Step
    public void click_the_link_and_check_alert(String link, String alertMsg) throws Exception {
        clickWebElement.click_the_element(link, getBeanFilePath(), webElementsModel.getClickSelector(), true);
        javaScriptWebElement.isJavascriptAlert(alertMsg);
    }

    /**
     * @param link     The field where the link is located in the xml file
     * @param alertMsg A message to check for in the alert as a String
     * @throws Exception If the link or alert is not done
     */
    @Step
    public void click_the_link_and_assert_and_accept(String link, String alertMsg) throws Exception {
        clickWebElement.click_the_element(link, getBeanFilePath(), webElementsModel.getClickSelector(), true);
        javaScriptWebElement.assert_and_accept_JavascriptAlert(alertMsg);
    }

    /**
     * @param link              The field where the link is located in the xml file
     * @param replacementString A string to insert into the value found from the keyword, can be multiples split by a delimiter
     * @param alertMsg          A message to check for in the alert as a String
     * @throws Exception If the link or alert is not done
     */
    @Step
    public void click_the_link_and_check_alert(String link, String replacementString, String alertMsg) throws Exception {
        clickWebElement.click_the_element(link, replacementString, getBeanFilePath(), webElementsModel.getClickSelector(), true);
        javaScriptWebElement.assert_and_accept_JavascriptAlert(alertMsg);
    }

    /**
     * This Method can click on multiple elements separated by a delimiter '_'.
     *
     * @param buttonName A keyword location of an element in an xml file as a String
     * @throws Exception If the element is not present
     */
    @Step
    public void click_the_button(String buttonName) throws Exception {
        clickWebElement.click_the_element(buttonName, getBeanFilePath(), webElementsModel.getClickSelector(), true);
    }

    @Step
    public void click_multiple_buttons(String buttonName) throws Exception {
        clickWebElement.click_multiple_elements_if_exists_and_wait_for_ajax(this, buttonName, getBeanFilePath(), webElementsModel.getClickSelector(), false);
    }

    /**
     * This Method can click on multiple elements separated by a delimiter '_'.
     * <p>
     * Has the ability to substitute a value in the xml file with passed replace variables.
     *
     * @param buttonName    A keyword location of an element in an xml file as a String, can be multiples split by a delimiter.
     * @param replaceString A string to insert into the value found from the keyword, can be multiples split by a delimiter, replace String is ##
     * @throws Exception If the element is not present
     */
    @Step
    public void click_the_button(String buttonName, String replaceString) throws Exception {

        clickWebElement.click_the_element(buttonName, replaceString, getBeanFilePath(), webElementsModel.getClickSelector(), true);
    }


    @Step
    public void click_the_button_replace_char(String buttonName, String replaceString, String replaceChar) throws Exception {
        logger.info("repString=" + replaceString);
        logger.info("repChar=" + replaceChar);

        clickWebElement.click_the_element(buttonName, replaceString, getBeanFilePath(), webElementsModel.getClickSelector(), true, replaceChar);
    }

    @Step
    public void click_the_button(String buttonName, String replaceString, String replaceDelimeter) throws Exception {

        clickWebElement.click_the_element(buttonName, replaceString, replaceDelimeter, getBeanFilePath(), webElementsModel.getClickSelector(), true);
    }

    @Step
    public void click_the_button_and_wait_ajax(String buttonName, String replaceString) throws Exception {
        click_the_button(buttonName, replaceString);
        waitForAjaxBusyIdentifier();
    }

    @Step
    public void move_Slider(String element) throws Exception {
        clickWebElement.move_Slider(element, getBeanFilePath(), webElementsModel.getClickSelector(), true);
    }

    @Step
    public void move_to_Element(String element) throws Exception {
        clickWebElement.move_to_Element(element, getBeanFilePath(), webElementsModel.getClickSelector(), true);
    }

    @Step
    public void move_to_Element(String element, String ReplaceChar) throws Exception {
        clickWebElement.move_to_Element(element, ReplaceChar, getBeanFilePath(), webElementsModel.getClickSelector(), true);
    }

    @Step
    public void select_value(String value, String field) throws Exception {
        selectWebElement.select_the_element(value, field, getBeanFilePath(), webElementsModel.getComboSelector());
    }

    @Step
    public void select_value(String value, String field, String fieldReplacementValue) throws Exception {
        selectWebElement.select_the_element(value, field, fieldReplacementValue, getBeanFilePath(), webElementsModel.getComboSelector());
    }

    @Step
    public void type_the_value(String value, String field, boolean bPropertyFile) throws Exception {
        if (bPropertyFile) {
            value = webElementsModel.getUrlMap().get(value);
        }
        inputWebElement.input_the_element(value, field, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void type_the_value(String value, String field) throws Exception {
        inputWebElement.input_the_element(value, field, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void type_the_value(String value, String field, String fieldReplacementValue) throws Exception {
        inputWebElement.input_the_element(value, field, fieldReplacementValue, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void type_the_value_and_click_tab(String value, String field) throws Exception {
        inputWebElement.input_the_element_and_click_tab(value, field, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void type_generated_email_and_click_tab(String field) throws Exception {
        inputWebElement.input_the_element_and_click_tab(email, field, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void type_the_value_and_enter(String value, String field) throws Exception {
        inputWebElement.input_the_element_and_enter(value, field, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void type_the_value_without_clearing(String value, String field) throws Exception {
        inputWebElement.input_the_element_without_clearing(value, field, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void type_the_value_without_clearing(String value, String field, String fieldReplacementValue) throws Exception {
        inputWebElement.input_the_element_without_clearing(value, field, fieldReplacementValue, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void tick_the_checkbox(String field) throws Exception {
        checkBoxWebElement.tick_the_checkbox(field, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void tick_the_checkbox(String field, String fieldReplacementValue) throws Exception {
        checkBoxWebElement.tick_the_checkbox(field, fieldReplacementValue, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void tick_the_checkbox_and_check_alert(String field) throws Exception {
        checkBoxWebElement.tick_the_checkbox(field, getBeanFilePath(), webElementsModel.getInputSelector());
        javaScriptWebElement.isJavascriptAlert("", true);
    }

    @Step
    public void untick_the_checkbox(String field) throws Exception {
        checkBoxWebElement.untick_the_checkbox(field, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void untick_the_checkbox(String field, String fieldReplacementValue) throws Exception {
        checkBoxWebElement.untick_the_checkbox(field, fieldReplacementValue, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void untick_the_checkbox_and_check_alert(String field) throws Exception {
        checkBoxWebElement.untick_the_checkbox(field, getBeanFilePath(), webElementsModel.getInputSelector());
        javaScriptWebElement.isJavascriptAlert("", true);
    }

    @Step
    public Integer getTableSize(String tableName, String rowidentifier) throws Exception {
        Integer tableSize = tableWebElement.getTableSize(tableName, rowidentifier, getBeanFilePath(), webElementsModel.getTextSelector());

        return tableSize;
    }

    @Step
    public ArrayList<HashMap<String, String>> getTableData(String tableName) throws Exception {
        ArrayList<HashMap<String, String>> tableData = tableWebElement.getTableData(tableName, getBeanFilePath(),
                webElementsModel.getTextSelector());
        return tableData;
    }


    /**
     * This will assert as to whether there is the passed string found on the current page
     *
     * @param value The string to check for
     * @throws Exception
     */
    @Step
    public void assert_value(String value) throws Exception {
        textWebElement.isTextPresentOnPage(value);
    }

    /**
     * This will assert as to whether there is the passed string found in the passed field
     *
     * @param value The string to check for
     * @param field The field to check against
     * @throws Exception
     */
    @Step
    public void assert_value(String value, String field) throws Exception {
        textWebElement.isTextPresentAtLocation(value, field, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_value(String value, String field, String fieldReplacementValue) throws Exception {
        textWebElement.isTextPresentAtLocation(value, field, fieldReplacementValue, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    /**
     * This will assert whether there is any text present at the given location (field)
     *
     * @param field A keyword location in an xml file as a String - Can be multiple entries with a '__' delimiter
     * @throws Exception if text is not present
     */
    @Step
    public void assert_that_there_is_text_present_at_element(String field, String fieldReplacement) throws Exception {
        textWebElement.isAnyTextPresentAtLocation(field, fieldReplacement, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    /**
     * This will assert whether there is any text present at the given location (field)
     *
     * @param field A keyword location in an xml file as a String - Can be multiple entries with a '__' delimiter
     * @throws Exception if text is not present
     */
    @Step
    public void assert_that_there_is_text_present_at_element(String field) throws Exception {
        assert_that_there_is_text_present_at_element(field, "");
    }

    /**
     * This will assert whether there is title text present at the given location (field)
     *
     * @param field A keyword location in an xml file as a String - Can be multiple entries with a '__' delimiter
     * @throws Exception an exception if text is not present
     */
    @Step
    public void assert_attribute_text_present_at_TextElement(String value, String field, String attribute) throws Exception {
        textWebElement.isAttributeTextPresentAtLocation(value, field, attribute, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_text_element_present(String field, String fieldReplacement) throws Exception {
        generalElementValidations.isElementPresent(field, fieldReplacement, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_attribute_text_present_at_TextElement(String value, String field, String replaceString, String attribute) throws Exception {
        textWebElement.isAttributeTextPresentAtLocation(value, field, replaceString, attribute, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_attribute_text_present_at_InputElement(String value, String field, String attribute) throws Exception {
        textWebElement.isAttributeTextPresentAtLocation(value, field, attribute, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void assert_inputfield_value(String value, String field) throws Exception {
        textWebElement.isTextPresentAtLocation(value, field, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void assert_textfield_value(String value, String field) throws Exception {
        textWebElement.isTextPresentAtLocation(value, field, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_combofield_value(String value, String field) throws Exception {
        textWebElement.isTextPresentAtLocation(value, field, getBeanFilePath(), webElementsModel.getComboSelector());
    }

    @Step
    public void assert_image_value(String value, String field) throws Exception {
        imageValidations.isImagePresentAtLocation(field, value, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_checkboxfield_value(boolean checked, String field) throws Exception {
        checkBoxWebElement.isCheckboxChecked(checked, field, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void assert_ordered_table(String tableName, int colNum, DisplayOrderType orderType) throws Exception {
        tableWebElement.isTableDataPresentedInOrder(tableName, colNum, orderType, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_alt_text_ordered_table(String tableName, int colNum, DisplayOrderType orderType) throws Exception {
        tableWebElement.isTableDataPresentedInAltTextOrder(tableName, colNum, orderType, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_table_size(String tableName, int expectedRowCount, String rowidentifier) throws Exception {
        tableWebElement.isTableSizeCorrect(tableName, rowidentifier, expectedRowCount, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_table_cell_element(String tableName, int rowNumber, int colNumber, String expectedElement) throws Exception {
        tableWebElement.assertTableCellElement(tableName, rowNumber, colNumber, expectedElement, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_table_column_element(String tableName, String rowIdentifier, int colNumber, String expectedElement) throws Exception {
        tableWebElement.assertTableColumnElement(tableName, rowIdentifier, colNumber, expectedElement, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_text_in_multiple_columns_of_table(String tableName, String rowIdentifier, String columnNumbers, String expectedElement) throws Exception {
        tableWebElement.assertTextInMultipleColumnsOfTable(tableName, rowIdentifier, columnNumbers, expectedElement, getBeanFilePath(), webElementsModel.getTextSelector());

    }


    @Step
    public void assert_date_range_in_table_date(String tableName, String rowIdentifier, int colNumber, String datefrom, String dateto) throws Exception {

        tableWebElement.assertDateInRange(tableName, rowIdentifier, colNumber, datefrom, dateto, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_range_in_between(String tableName, double minvalue, double maxvalue, String rowIdentifier, int colNumber) throws Exception {
        tableWebElement.assertRangeInBetween(tableName, minvalue, maxvalue, rowIdentifier, colNumber, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_input_element_present(String field) throws Exception {
        assert_input_element_present(field, "");
    }

    @Step
    public void assert_input_element_present(String field, String fieldReplacement) throws Exception {
        generalElementValidations.isElementPresent(field, fieldReplacement, getBeanFilePath(), webElementsModel.getInputSelector());
    }

    @Step
    public void assert_multiple_elements_present_from_xml_textArea(String field) throws Exception {
        generalElementValidations.isElementPresent(field, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_multiple_elements_present_from_xml_textArea(String field, String fieldReplacement) throws Exception {
        generalElementValidations.isElementPresent(field, fieldReplacement, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_number_of_images_present_in_field_from_xml_textAreaField(String field, int numberOfElements) throws Exception {
        generalElementValidations.isNumberOfElementsByTagInFieldCorrect(field, numberOfElements, "img", getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_number_of_elements_present_from_xml_textAreaField(String field, int numberOfElements) throws Exception {
        generalElementValidations.isNumberOfElementsOnPageCorrect(field, numberOfElements, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_select_combo_element_present(String field) throws Exception {
        assert_select_combo_element_present(field, "");
    }

    @Step
    public int numberOfElementsOnPage(String field) throws Exception {
        int numberOfElements = generalElementValidations.numberOfElementsOnPage(field, getBeanFilePath(), webElementsModel.getTextSelector());
        return numberOfElements;
    }

    @Step
    public void assert_select_combo_element_present(String field, String fieldReplacement) throws Exception {
        generalElementValidations.isElementPresent(field, fieldReplacement, getBeanFilePath(), webElementsModel.getComboSelector());
    }

    @Step
    public void assert_element_present_from_xml_textArea(String field) throws Exception {
        assert_element_present_from_xml_textArea(field, "");
    }

    @Step
    public void assert_element_present_from_xml_textArea(String field, String fieldReplacement) throws Exception {
        generalElementValidations.isElementPresent(field, fieldReplacement, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_element_present_from_xml_textArea_rep(String field, String fieldReplacement, String replaceCharacter) throws Exception {
        generalElementValidations.isElementPresentReplace(field, fieldReplacement, getBeanFilePath(), webElementsModel.getTextSelector(), replaceCharacter);
    }

    @Step
    public void assert_click_element_present(String field) throws Exception {
        assert_click_element_present(field, "");
    }

    @Step
    public void assert_click_element_present(String field, String fieldReplacement) throws Exception {
        generalElementValidations.isElementPresent(field, fieldReplacement, getBeanFilePath(), webElementsModel.getClickSelector());
    }

    @Step
    public void assert_text_element_visible(String field) throws Exception {
        generalElementValidations.isElementVisible(field, "", getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_click_element_visible(String field) throws Exception {
        generalElementValidations.isElementVisible(field, "", getBeanFilePath(), webElementsModel.getClickSelector());
    }

    @Step
    public void assert_error_message_fail(String field) throws Exception {
        generalElementValidations.isErrorMessageNotVisible(field, "", getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_multiple_click_element_present(String field) throws Exception {
        generalElementValidations.isElementPresent(field, getBeanFilePath(), webElementsModel.getClickSelector());
    }

    /**
     * This asserts whether a new page has the passed url located in the passed xml field
     *
     * @param field The field where the url is located in the xml file
     * @throws Exception If a new page is not opened with the correct url
     */
    @Step
    public void assert_that_a_new_page_is_open_with_url(String field) throws Exception {
        urlWebElement.isCurrentWindowUrlCorrect(field, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public void assert_that_a_new_page_is_open_with_url_contains_keyword(String keyword) throws Exception {
        urlWebElement.isCurrentWindowUrlCorrect(keyword);
    }

    /**
     * This asserts whether a new window is present with the passed url located in the passed xml field
     *
     * @param field The field where the url is locacted in the xml file
     * @throws Exception If a new window is not opened with the correct url
     */
    @Step
    public void assert_that_a_new_window_is_open_with_url(String field) throws Exception {
        urlWebElement.isANewWindowOpenWithUrl(field, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    /**
     * This asserts whether a new window is present
     *
     * @throws Exception If there is no new window open
     */
    @Step
    public void assert_that_a_new_window_is_open() throws Exception {
        urlWebElement.isANewWindowOpen();
    }

    /**
     * This asserts whether a javascript alert is present
     *
     * @throws Exception If the alert is not shown
     */
    @Step
    public void assert_javascript_alert_is_present() throws Exception {
        javaScriptWebElement.isJavascriptAlert();
    }

    @Step
    public void assert_javascript_alert_is_present(boolean ignoreError) throws Exception {
        javaScriptWebElement.isJavascriptAlert("", ignoreError);
    }

    @Step
    public void assert_javascript_alert_is_present(String message, boolean ignoreError) throws Exception {
        javaScriptWebElement.isJavascriptAlert(message, ignoreError);
    }

    @Step
    public String assert_javascript_get_alert_text() throws Exception {
        return javaScriptWebElement.getAlert().getText();
    }

    /**
     * This will get the defined web element from the webElementsModel.loadtimeout map using the passed key value.
     * Then it will use this element and wait until it's disappeared before continuing.
     * Useful when the page needs to load before the test continues
     *
     * @param ajaxIdentifier The key value for the element in the webElementsModel.loadtimeout map
     * @throws Exception
     */
    @Step
    public void waitForAjax(String ajaxIdentifier) throws Exception {
        // Get the element to wait for and then wait for it
        //String ajaxElementId = webElementsModel.getLoadTimeout().get(ajaxIdentifier);
        //Ajax.waitForAjaxElement(ajaxElementId);
        ajaxWebElement.waitForAjaxElement(ajaxIdentifier);
    }

    public void waitForAjaxBusyIdentifier() throws Exception {
        waitForAjax("ajax-loader"); // FIXME identifier should be in properties file
    }

    @Step
    public void scrollDown() throws Exception {
        javaScriptWebElement.scrollDownToBottomOfPage();
    }

    @Step
    public void scrollToClickElement(String field) throws Exception{
        javaScriptWebElement.scrollToElement(field, getBeanFilePath(),webElementsModel.getClickSelector());
    }

    @Step
    public void assertButtonColor(String field, String expectedColor) throws Exception {
        String cssColor = javaScriptWebElement.retrieveCssValue(field, "background", getBeanFilePath(), webElementsModel.getClickSelector());
        boolean in = cssColor.contains(expectedColor);
        if (!in) {
            logger.error(expectedColor + " not in " + cssColor);
        }
        assertTrue(in);
    }

    @Step
    public void page_refresh() {
        urlWebElement.getDriver().navigate().refresh();
    }

    @Step
    public void selectAutoFillElementWithText(String autoText, int timeout, String autoSelectPath, String pathAppend) {
        urlWebElement.selectAutoFillElementWithText(autoText, timeout, autoSelectPath, pathAppend);
    }

    @Step
    public Boolean setcheckBoxWebElement(String field) throws Exception {
        Boolean isSelected = clickWebElement.getCheckBoxState(field, getBeanFilePath(), webElementsModel.getClickSelector(), true);
        return isSelected;
    }

    @Step
    public void HoverAndClick(String buttonName) throws Exception {
        clickWebElement.HoverAndClick(buttonName, getBeanFilePath(), webElementsModel.getClickSelector(), true);
    }

    @Step
    public void hoverMouseOverLink(String field) throws Exception {
        clickWebElement.hoverMouse(field, getBeanFilePath(), webElementsModel.getClickSelector(), true);
    }

    @Step
    public void WaitforAlertAndClickAccept() throws Exception {
        javaScriptWebElement.WaitAndAcceptTheAlert();
    }

    @Step
    public void Click_DropDown_SelectValue(String Dropdown, String Value) throws Exception {
        javaScriptWebElement.ClickDropDownAndSelectValue(Dropdown, Value);
    }

    @Step
    public void WaitTillVisible(String Element) throws Exception {
        javaScriptWebElement.WaitTillVisible(Element);
    }

    /*@Step
    public void WaitForElement (String Element)  throws Exception {

    }*/

    public String javaScriptExecutor(String strCommand) throws Exception {
        return javaScriptWebElement.javaScriptExecutor(strCommand);
    }

    @Step
    public String open_to_NewTab_GetText(String strUrl) {
        return urlWebElement.open_to_NewTab_GetText(strUrl);
    }

    @Step
    public String getColorFromElement(String field, String fieldReplacementValue) throws Exception {
        return textWebElement.getColorFromElement(field, fieldReplacementValue, getBeanFilePath(),
                webElementsModel.getTextSelector());
    }

    @Step
    public Boolean getCheckBoxState(String field) throws Exception {
        Boolean isSelected = clickWebElement.getCheckBoxState(field, getBeanFilePath(), webElementsModel.getClickSelector(), true);
        return isSelected;
    }

    @Step
    public void scrollTop() throws Exception {
        javaScriptWebElement.scrollDownToTopOfPage();
    }

    @Step
    public List<String> getTextFromMultipleElements(String field, String fieldReplacementValue) throws Exception {
        return textWebElement.listOfElements(field, fieldReplacementValue, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public int numberOfElementsOnPage(String field, String replacement, String delimiter) throws Exception {
        int numberOfElements = generalElementValidations.numberOfElementsOnPage(field, replacement, delimiter, getBeanFilePath(), webElementsModel.getTextSelector());
        return numberOfElements;
    }

    @Step
    public String getTextFromJavaScriptExecutorById(String field) throws Exception {
        return javaScriptWebElement.getTextFromElementById(field, getBeanFilePath(), webElementsModel.getTextSelector());
    }

    @Step
    public boolean assert_is_click_enabled(String link, String replacementString, String replacementDelimiter) throws Exception {
        boolean is_click_enabled = clickWebElement.assert_is_click_enabled(link, replacementString, replacementDelimiter, getBeanFilePath(), webElementsModel.getClickSelector(), false);
        return is_click_enabled;
    }

    @Step
    public void scrollDownToCoordinate(String x, String y) throws Exception {
        javaScriptWebElement.scrollDownToCoordinate(x, y);
    }

}

