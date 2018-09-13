package org.testing.framework.backend.webElements.validations;

import net.serenitybdd.core.pages.PageObject;
import org.testing.framework.backend.webElements.locator.WebElementLocator;
import org.testing.framework.properties.LoadProjectProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ImageValidations extends PageObject {

    static Logger logger = LoggerFactory.getLogger(ImageValidations.class.getName());

    public ImageValidations(WebDriver driver) {
        super(driver);
    }

    public void isImagePresentAtLocation(final String field, final String keyword, final String beanFileName, final String beanPath) throws Exception {
        String[] keywordList = keyword.split(
                LoadProjectProperties.getStringProperty(
                        LoadProjectProperties.ASSERT_TEXT_DELIMITER));

        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);
        List<WebElement> imageElements = element.findElements(By.tagName("img"));
        List<String> altTexts = new ArrayList<String>();
        for (WebElement imageElement : imageElements) {
            if (imageElement.getAttribute("alt") != null) {
                logger.info("Found image: " + imageElement.getAttribute("alt"));
                altTexts.add(imageElement.getAttribute("alt").trim());
            }
        }
        boolean allKeywordsPresent = false;
        for (String singleKeyword : keywordList) {
            allKeywordsPresent = altTexts.contains(singleKeyword.trim());
            if (!allKeywordsPresent) {
                break;
            }
        }
        assertTrue(allKeywordsPresent);
    }

}
