package org.testing.framework.backend.webElements.pageElements;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.backend.webElements.locator.WebElementLocator;
import org.testing.framework.properties.LoadProjectProperties;
import org.testing.framework.steps.uiSteps.UISteps;

public class Click extends PageObject {

    static Logger logger = LoggerFactory.getLogger(Click.class.getName());

    public Click(WebDriver driver) {
        super(driver);
    }


    /**
     * To click on all web elements located by one or multiple keywords (delimited with _)
     * Wait ajax busy identifier after each click.
     */
    public void click_multiple_elements_if_exists_and_wait_for_ajax(UISteps steps, final String keyword, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
            throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
        for (String singleKey : keywordList) {
            int nbElements = elementLocator.getElementCount(singleKey, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
            for (int i = 0; i < nbElements; i++) {
                WebElement elementFound = elementLocator.locateElement(singleKey, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
                if (elementFound != null) {
                    elementFound.click();
                    steps.waitForAjaxBusyIdentifier();
                }
            }
        }
    }

    public void move_Slider(final String keyword, int xoffset, int yoffset, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
            throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();

        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));

        //WebElement otherElementFound= elementLocator.locateElement("MOVETOELEMENT", beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);

        for (String singleKey : keywordList) {

            WebElement elementFound = elementLocator.locateElement(singleKey, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
            if (elementFound != null) {
                Actions move = new Actions(getDriver());
                move.clickAndHold(elementFound).moveByOffset(xoffset, yoffset).release().build().perform();


            }
        }
    }

    public void doubleClick(final String keyword, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
            throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();

        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));


        for (String singleKey : keywordList) {

            WebElement elementFound = elementLocator.locateElement(singleKey, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
            if (elementFound != null) {
                Actions actions = new Actions(getDriver());
                actions.doubleClick(elementFound);
            }
        }
    }


    /**
     * This Method can click on multiple elements separated by a delimiter '_'
     *
     * @param keyword                 A keyword location of an element in an xml file as a String
     * @param beanFileName            The name and path of the xml file to look in as a String
     * @param beanPath                The element node type location in the xml file as a String
     * @param raiseErrorIfUnavailable Raise an error if unavailable
     * @throws Exception If the element is not present
     */
    public void click_the_element(final String keyword, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
            throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();

//        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
//
//        for (String singleKey : keywordList) {

            WebElement elementFound = elementLocator.locateElement(keyword, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);

            if (elementFound != null) {
                elementFound.click();
            }
//        }
    }

//    /**
//     * This Method can click on multiple elements separated by a delimiter '_'.
//     * <p>
//     * Has the ability to substitute a value in the xml file with passed replace variables.
//     *
//     * @param keyword                 A keyword location of an element in an xml file as a String
//     * @param replaceString           A string to insert into the value found from the keyword, can be multiples split by a delimiter, replace String is ##
//     * @param beanFileName            The name and path of the xml file to look in as a String
//     * @param beanPath                The element node type location in the xml file as a String
//     * @param raiseErrorIfUnavailable Raise an error if unavailable
//     * @throws Exception If the element is not present
//     */
//    public void click_the_element(final String keyword, final String replaceString, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
//            throws Exception {
//
//        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
//        String[] replaceList = replaceString.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
//
//        // If the lists are the same length then do the checking, else throw an error
//        if (keywordList.length == replaceList.length) {
//
//            // Set up the variables for the loop
//            int count = keywordList.length;
//            WebElementLocator elementLocator = WebElementLocator.getInstance();
//
//            // For each item in the lists
//            for (int i = 0; i < count; i++) {
//
//                WebElement elementFound = elementLocator.locateElement(keywordList[i], replaceList[i],
//                        LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
//
//                if (elementFound != null) {
//                    elementFound.click();
//                }
//            }
//        } else {
//            throw new Exception("Keyword list and replace list count does not match");
//        }
//    }

    /**
     * This Method can click on multiple elements separated by a delimiter '_'.
     * <p>
     * Has the ability to substitute a value in the xml file with passed replace variables.
     *
     * @param keyword                 A keyword location of an element in an xml file as a String
     * @param replaceString           A string to insert into the value found from the keyword, can be multiples split by a delimiter, replace String is ##
     * @param beanFileName            The name and path of the xml file to look in as a String
     * @param beanPath                The element node type location in the xml file as a String
     * @param raiseErrorIfUnavailable Raise an error if unavailable
     * @throws Exception If the element is not present
     */
    public void click_the_element(final String keyword, final String replaceString, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
            throws Exception {

//        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
//        String[] replaceList = replaceString.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
//
//         If the lists are the same length then do the checking, else throw an error
//        if (keywordList.length == replaceList.length) {
//
            // Set up the variables for the loop
//            int count = keywordList.length;
            WebElementLocator elementLocator = WebElementLocator.getInstance();

            // For each item in the lists
//            for (int i = 0; i < count; i++) {

                WebElement elementFound = elementLocator.locateElement(keyword, replaceString,
                        LoadProjectProperties.getStringProperty(LoadProjectProperties.REPLACE_CHARACTER), beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);

                if (elementFound != null) {
                    elementFound.click();
                }
//            }
//        } else {
//            throw new Exception("Keyword list and replace list count does not match");
//        }
    }

//    public void click_the_element(final String keyword, final String replaceString, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable, final String replaceString2)
//            throws Exception {
//
//        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
//        String[] replaceList = replaceString.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
//
//        // If the lists are the same length then do the checking, else throw an error
//        if (keywordList.length == replaceList.length) {
//
//            // Set up the variables for the loop
//            int count = keywordList.length;
//            WebElementLocator elementLocator = WebElementLocator.getInstance();
//
//            // For each item in the lists
//            for (int i = 0; i < count; i++) {
//                WebElement elementFound = elementLocator.locateElement(keywordList[i], replaceList[i],
//                        replaceString2, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
//
//                if (elementFound != null) {
//                    elementFound.click();
//                }
//            }
//        } else {
//            throw new Exception("Keyword list and replace list count does not match");
//        }
//    }

//    /**
//     * This Method can click on multiple elements separated by a delimiter '_'.
//     * <p>
//     * Has the ability to substitute multiple values in the xml file with passed replace variables.
//     *
//     * @param keyword                 A keyword location of an element in an xml file as a String
//     * @param replaceString           A string to insert into the value found from the keyword, can be multiples split by a delimiter, replace String is ##
//     * @param replaceDelimeter        delimeter to be replaced by the given string
//     * @param beanFileName            The name and path of the xml file to look in as a String
//     * @param beanPath                The element node type location in the xml file as a String
//     * @param raiseErrorIfUnavailable Raise an error if unavailable
//     * @throws Exception If the element is not present
//     */
//    public void click_the_element(final String keyword, final String replaceString, final String replaceDelimeter, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
//            throws Exception {
//
//        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
//        String[] replaceList = replaceString.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
//
//        // If the lists are the same length then do the checking, else throw an error
//        if (keywordList.length == replaceList.length) {
//
//            // Set up the variables for the loop
//            int count = keywordList.length;
//            logger.info("keyword count: " + count);
////            System.out.println("Keywords: "+keywordList[0]+"--"+keywordList[1]);
//
//            WebElementLocator elementLocator = WebElementLocator.getInstance();
//
//            // For each item in the lists
//            for (int i = 0; i < count; i++) {
//
//                WebElement elementFound = elementLocator.locateElement(keywordList[i], replaceList[i],
//                        replaceDelimeter, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
//
//                if (elementFound != null) {
//                    elementFound.click();
//                }
//            }
//        } else {
//            throw new Exception("Keyword list and replace list count does not match");
//        }
//    }

    public Boolean wait_the_element(final String keyword, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
            throws Exception {
        Boolean flag = false;

        WebElementLocator elementLocator = WebElementLocator.getInstance();

        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));

        for (String singleKey : keywordList) {

            WebElement elementFound = elementLocator.locateElement(singleKey, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);

            if (elementFound != null) {
                flag = true;
            }
        }
        return flag;
    }

    public void move_Slider(final String keyword, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
            throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();

        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));

        for (String singleKey : keywordList) {

            WebElement elementFound = elementLocator.locateElement(singleKey, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);

            if (elementFound != null) {
                Actions move = new Actions(getDriver());
                Action action = move.dragAndDropBy(elementFound, 40, 0).build();
                action.perform();


//                Thread.sleep(500);
//                move.moveToElement(elementFound).

//                ((JavascriptExecutor) getDriver()).executeScript( "arguments[0].scrollIntoView();",elementFound);
//
//                Actions builder = new Actions(getDriver());
//
//                Action dragAndDrop = builder.clickAndHold(elementFound) .moveToElement(otherElementFound).release(otherElementFound) .build();
//
//                dragAndDrop.perform();

            }
        }
    }


    public void move_to_Element(final String keyword, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
            throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();

        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));

        int i = 0;
        for (String singleKey : keywordList) {

            WebElement elementFound = elementLocator.locateElement(singleKey, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
            if (elementFound != null) {
                Actions move = new Actions(getDriver());
                move.moveToElement(elementFound);
                Thread.sleep(5000);
                //move.click();
                if (i == 0) {
                    move.build().perform();
                }
                Thread.sleep(5000);
            }
        }
    }


    public void move_to_Element(final String keyword, final String repString, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
            throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();

        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));

        int i = 0;
        for (String singleKey : keywordList) {
            WebElement elementFound = elementLocator.locateElement(singleKey, repString, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
            if (elementFound != null) {
                Actions move = new Actions(getDriver());
                move.moveToElement(elementFound);
                Thread.sleep(5000);
                //move.click();
                if (i == 0) {
                    move.build().perform();
                }
                Thread.sleep(5000);
            }
        }
    }


    public void drag_drop_element(String strElementToMove, String strElementReplaced, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable) throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();

        WebElement element2 = null, element = null;
        String[] keywordList = strElementToMove.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
        String[] keywordList2 = strElementReplaced.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));

        logger.info("First Element Creation");
        for (String singleKey : keywordList) {
            element = elementLocator.locateElement(singleKey, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
        }
        logger.info("Element1 " + element.isDisplayed());
        logger.info("Second Element Creation");
        for (String singleKey2 : keywordList2) {
            element2 = elementLocator.locateElement(singleKey2, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
        }
        logger.info("Element2 " + element2.isDisplayed());

        Point coordinates2 = element2.getLocation();
        Point coordinates1 = element.getLocation();
        int offsetX = coordinates2.getX() - coordinates1.getX();
        int offsetY = coordinates2.getY() - coordinates1.getY();
        logger.info(offsetX + "," + offsetY);

        WebDriver driver = getDriver();
        Actions builder = new Actions(driver);

        // viewUpdateForDisplayMobileSteps.assert_click_element_present(strElementToMove);
        // WebElement element2=driver.findElement(By.xpath(strElementReplaced));
        /*builder.clickAndHold(element)
                .moveToElement(element2,50,50)
                .release()
                .build()
                .perform();*/

        new Actions(driver).dragAndDropBy(element, 20, offsetY + 2).build().perform();
        logger.info("Action Performed");
    }

    public void hoverAndClick(final String keyword, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
            throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
        for (String singleKey : keywordList) {
            WebElement elementFound = elementLocator.locateElement(singleKey, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
            if (elementFound != null) {
                Actions action = new Actions(getDriver());
                WebElement hover = getDriver().findElement(By.xpath(keyword));
                action.moveToElement(hover).build().perform();
                getDriver().findElement(By.xpath(keyword)).click();
            }
        }
    }

//    public void hoverMouse(final String keyword, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
//            throws Exception {
//        WebElementLocator elementLocator = WebElementLocator.getInstance();
//        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
//        for (String singleKey : keywordList) {
//            WebElement elementFound = elementLocator.locateElement(keyword, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);
//            if (elementFound != null) {
//                Actions action = new Actions(getDriver());
//                WebElement hover = getDriver().findElement(By.xpath(keyword));
//                action.moveToElement(hover).build().perform();
//            }
//        }
//    }


    public void hoverMouse(final String field, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        String[] fieldList = field.split(
                LoadProjectProperties.getStringProperty(
                        LoadProjectProperties.ASSERT_TEXT_DELIMITER));
        for (String singleField : fieldList) {
            WebElement element = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);
//            logger.info("Element Text is: " + element.getText());
            if (element != null) {
                Actions action = new Actions(getDriver());
                action.moveToElement(element).build().perform();
            }
        }
    }

    public Boolean getCheckBoxState(final String keyword, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        Boolean getCheckBoxState = false;
        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));

        for (String singleKey : keywordList) {

            WebElement elementFound = elementLocator.locateElement(singleKey, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);

            if (elementFound != null) {
                getCheckBoxState = elementFound.isSelected();
            }
        }
        return getCheckBoxState;
    }

    public boolean assert_is_click_enabled(final String keyword, final String replaceString, final String replaceDelimeter, final String beanFileName, final String beanPath, final boolean raiseErrorIfUnavailable)
            throws Exception {

        String[] keywordList = keyword.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
        String[] replaceList = replaceString.split(LoadProjectProperties.getStringProperty(LoadProjectProperties.ASSERT_TEXT_DELIMITER));
        boolean buttonEnabled = false;

        // If the lists are the same length then do the checking, else throw an error
        if (keywordList.length == replaceList.length) {

            // Set up the variables for the loop
            int count = keywordList.length;
            logger.info("keyword count: " + count);

            WebElementLocator elementLocator = WebElementLocator.getInstance();

            // For each item in the lists
            for (int i = 0; i < count; i++) {

                WebElement elementFound = elementLocator.locateElement(keywordList[i], replaceList[i],
                        replaceDelimeter, beanFileName, beanPath, getDriver(), raiseErrorIfUnavailable);

                if (elementFound != null) {
                    buttonEnabled = elementFound.isEnabled();

                }
            }
        } else {
            throw new Exception("Keyword list and replace list count does not match");
        }
        return buttonEnabled;
    }
}

