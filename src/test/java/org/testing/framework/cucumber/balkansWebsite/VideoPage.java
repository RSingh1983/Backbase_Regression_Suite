package org.testing.framework.cucumber.balkansWebsite;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.steps.balkansWebsite.uisteps.VideoPageSteps;
import org.testing.framework.steps.balkansWebsite.uisteps.VijestiPageSteps;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class VideoPage extends PageObject {

    static Logger logger = LoggerFactory.getLogger(VideoPage.class.getName());

    // Variables
    @Steps
    VideoPageSteps videoPageSteps;

    private Properties prop = new Properties();
    private static String COMMON_PROP_LOCATION = "/properties/common.properties";
    private static String ENV_PROP_LOCATION = "/properties/";

    public VideoPage() throws IOException {
        prop = new Properties();
        prop.load(VideoPage.class.getResourceAsStream(COMMON_PROP_LOCATION));
        logger.info("ENV file is : " + System.getProperty("env"));
        prop.load(VideoPage.class.getResourceAsStream(ENV_PROP_LOCATION + System.getProperty("env") + ".properties"));
    }

    @When("^I click the (.*) element for Video Page$")
    public void clickElement(String field) throws Exception {
        videoPageSteps.assert_click_element_visible(field);
        videoPageSteps.click_the_link(field);
    }

    @Then("^I should see the below mentioned (.*)?items on the Video Page$")
    public void validateUIElementsPresentOnPage(String field, DataTable webElements) throws Exception {
        for (Map<String, String> dataMap : webElements.asMaps(String.class, String.class)) {
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                logger.info("Validate Element " + entry.getKey() + " \"" + entry.getValue() + "\" is present on the Page");
                videoPageSteps.scrollToClickElement(entry.getKey() + "_" + entry.getValue());
                videoPageSteps.assert_click_element_visible(entry.getKey() + "_" + entry.getValue());
            }
        }
    }
}
