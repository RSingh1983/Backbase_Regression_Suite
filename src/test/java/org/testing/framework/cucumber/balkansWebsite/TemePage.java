package org.testing.framework.cucumber.balkansWebsite;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.steps.balkansWebsite.uisteps.TemePageSteps;
import org.testing.framework.steps.balkansWebsite.uisteps.VijestiPageSteps;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class TemePage extends PageObject {

    static Logger logger = LoggerFactory.getLogger(TemePage.class.getName());

    // Variables
    @Steps
    TemePageSteps temePageSteps;

    private Properties prop = new Properties();
    private static String COMMON_PROP_LOCATION = "/properties/common.properties";
    private static String ENV_PROP_LOCATION = "/properties/";

    public TemePage() throws IOException {
        prop = new Properties();
        prop.load(TemePage.class.getResourceAsStream(COMMON_PROP_LOCATION));
        logger.info("ENV file is : " + System.getProperty("env"));
        prop.load(TemePage.class.getResourceAsStream(ENV_PROP_LOCATION + System.getProperty("env") + ".properties"));
    }

    @When("^I click the (.*) element for Teme Page$")
    public void clickElement(String field) throws Exception {
        temePageSteps.assert_click_element_visible(field);
        temePageSteps.click_the_link(field);
    }

    @Then("^I should see the below mentioned (.*)?items on the Teme Page$")
    public void validateUIElementsPresentOnPage(String field, DataTable webElements) throws Exception {
        for (Map<String, String> dataMap : webElements.asMaps(String.class, String.class)) {
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                logger.info("Validate Element " + entry.getKey() + " \"" + entry.getValue() + "\" is present on the Page");
                temePageSteps.scrollToClickElement(entry.getKey() + "_" + entry.getValue());
                temePageSteps.assert_click_element_visible(entry.getKey() + "_" + entry.getValue());
            }
        }
    }
}
