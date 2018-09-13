package org.testing.framework.cucumber.balkansWebsite;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.steps.balkansWebsite.uisteps.HomePageSteps;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class HomePage extends PageObject {

    static Logger logger = LoggerFactory.getLogger(HomePage.class.getName());

    // Variables
    @Steps
    HomePageSteps homePageSteps;

    private Properties prop = new Properties();
    private static String COMMON_PROP_LOCATION = "/properties/common.properties";
    private static String ENV_PROP_LOCATION = "/properties/";

    public HomePage() throws IOException {
        prop = new Properties();
        prop.load(HomePage.class.getResourceAsStream(COMMON_PROP_LOCATION));
        logger.info("ENV file is : " + System.getProperty("env"));
        prop.load(HomePage.class.getResourceAsStream(ENV_PROP_LOCATION + System.getProperty("env") + ".properties"));
    }

    @Given("^I browse webSite using (.*) url$")
    public void openURL(String url) throws Exception {
        homePageSteps.open_site(url);
        homePageSteps.page_refresh();
    }

    @Then("^I should see the below mentioned (.*)?items on the Landing Page$")
    public void validateUIElementsPresentOnPage(String field, DataTable webElements) throws Exception {
        for (Map<String, String> dataMap : webElements.asMaps(String.class, String.class)) {
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                logger.info("Validate Element " + entry.getKey() + " \"" + entry.getValue() + "\" is present on the Page");
                homePageSteps.scrollToClickElement(entry.getKey() + "_" + entry.getValue());
                homePageSteps.assert_click_element_visible(entry.getKey() + "_" + entry.getValue());
            }
        }
    }

}
