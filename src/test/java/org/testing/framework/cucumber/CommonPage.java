package org.testing.framework.cucumber;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.steps.balkansWebsite.uisteps.*;
import org.testing.framework.steps.uiSteps.UISteps;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.fail;

public class CommonPage extends PageObject {

    static Logger logger = LoggerFactory.getLogger(CommonPage.class.getName());

    // Variables
    UISteps uiSteps;

    @Steps
    BlogPageSteps blogPageSteps;

    @Steps
    HomePageSteps homePageSteps;

    @Steps
    InteraktivnoPageSteps interaktivnoPageSteps;

    @Steps
    MišljenjaPageSteps mišljenjaPageSteps;

    @Steps
    ProgramiPageSteps programiPageSteps;

    @Steps
    TemePageSteps temePageSteps;

    @Steps
    VideoPageSteps videoPageSteps;

    @Steps
    VijestiPageSteps vijestiPageSteps;

    @Steps
    VrijemePageSteps vrijemePageSteps;


    public CommonPage() throws IOException {
    }

    private UISteps getUISteps(String page) {

        if (StringUtils.containsIgnoreCase(page, "Blog")) {
            uiSteps = blogPageSteps;
        }
        if (StringUtils.containsIgnoreCase(page, "Home")) {
            uiSteps = homePageSteps;
        }
        if (StringUtils.containsIgnoreCase(page, "Interaktivno")) {
            uiSteps = interaktivnoPageSteps;
        }
        if (StringUtils.containsIgnoreCase(page, "Mišljenja")) {
            uiSteps = mišljenjaPageSteps;
        }
        if (StringUtils.containsIgnoreCase(page, "Programi")) {
            uiSteps = programiPageSteps;
        }
        if (StringUtils.containsIgnoreCase(page, "Teme")) {
            uiSteps = temePageSteps;
        }
        if (StringUtils.containsIgnoreCase(page, "Video")) {
            uiSteps = videoPageSteps;
        }
        if (StringUtils.containsIgnoreCase(page, "Vijesti")) {
            uiSteps = vijestiPageSteps;
        }
        if (StringUtils.containsIgnoreCase(page, "Vrijeme")) {
            uiSteps = vrijemePageSteps;
        }

        return uiSteps;
    }

    @Given("^I browse webSite using (.*) url of (.*)$")
    public void openURL(String url, String page) throws Exception {
        getUISteps(page).open_site(url);
        getUISteps(page).page_refresh();
    }

    @When("^I click the (.*) element for (.*)$")
    public void clickElement(String field, String page) throws Exception {
        getUISteps(page).assert_click_element_visible(field);
        getUISteps(page).click_the_element(field);
    }

    @Then("^I should see the below mentioned (.*)?items on the (.*)$")
    public void validateUIElementsPresentOnPage(String field, String page, DataTable webElements) throws Exception {
        for (Map<String, String> dataMap : webElements.asMaps(String.class, String.class)) {
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                logger.info("Validate Element " + entry.getKey() + " \"" + entry.getValue() + "\" is present on the Page");
                getUISteps(page).scrollToClickElement(entry.getKey() + "_" + entry.getValue());
                getUISteps(page).assert_click_element_visible(entry.getKey() + "_" + entry.getValue());
            }
        }
    }

    @Then("^I validate all links and images on the (.*)$")
    public void validateLinksAndImagesOnPage(String page) throws Exception {
        // To-Do Validate all the anchors on the page are accessible
        int anchorIndex = 0;
        if (getUISteps(page).checkIfElementPresent("Main_Page_Anchors")) {
            for (String anchor : getUISteps(page).getAttributeValueFromAllClickElement("Main_Page_Anchors", "href")) {
                logger.info("Validating Anchor " + ++anchorIndex + " of " + page + " : " + anchor);
                if (anchor != null) {
                    if (!(anchor.contains("www.linkedin.com/company")) && !(anchor.contains("javascript")) && !(anchor.contains("mailto:?subject"))) {
                        RestAssured.given().
                                urlEncodingEnabled(false).
                                when().get(anchor).
                                then().assertThat().statusCode(200);
                    }
                }
            }
        } else {
            logger.info("No Anchors exist on the main " + page);
        }

        for (String anchor : getUISteps(page).getAttributeValueFromAllClickElement("Footer_Anchors", "href")) {
            logger.info("Validating Anchor " + ++anchorIndex + " of " + page + " : " + anchor);
            if (anchor != null) {
                if (!(anchor.contains("www.linkedin.com/company")) && !(anchor.contains("javascript")) && !(anchor.contains("mailto:?subject"))) {
                    RestAssured.given().
                            urlEncodingEnabled(false).
                            when().get(anchor).
                            then().assertThat().statusCode(200);
                }
            }
        }

        //To-Do Validate src of all images is accessible
        int imageIndex = 0;
        // Validate all the images on the Main Page Content
        if (getUISteps(page).checkIfElementPresent("Main_Page_Images")) {
            for (String imageSrc : getUISteps(page).getAttributeValueFromAllClickElement("Main_Page_Images", "src")) {
                logger.info("Validating Image " + ++imageIndex + " of " + page + " : " + imageSrc);
                if (imageSrc != "") {
                    RestAssured.given().
                            urlEncodingEnabled(false).
                            when().get(imageSrc).
                            then().assertThat().statusCode(200);
                    getUISteps(page).assertImagePresent("ImagePlaceholderForVisibility", imageSrc);
                } else {
                    fail("src tag of the image is blank");
                }
            }
        } else {
            logger.info("No Images exist on the Footer of " + page);
        }

        // Validate all the images on the Footer Content
        for (String imageSrc : homePageSteps.getAttributeValueFromAllClickElement("Footer_Images", "src")) {
            logger.info("Validating Image " + ++imageIndex + " of " + page + " : " + imageSrc);
            if (imageSrc != "") {
                RestAssured.given().
                        urlEncodingEnabled(false).
                        when().get(imageSrc).
                        then().assertThat().statusCode(200);
                homePageSteps.assertImagePresent("ImagePlaceholderForVisibility", imageSrc);
            } else {
                fail("src tag of the image is blank");
            }
        }
    }
}
