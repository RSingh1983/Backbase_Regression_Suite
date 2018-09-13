package org.testing.framework.cucumber.balkansWebsite;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.steps.balkansWebsite.uisteps.HomePageSteps;
import org.testing.framework.steps.balkansWebsite.uisteps.VijestiPageSteps;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class VijestiPage extends PageObject {

    static Logger logger = LoggerFactory.getLogger(VijestiPage.class.getName());

    // Variables
    @Steps
    VijestiPageSteps vijestiPageSteps;

    private Properties prop = new Properties();
    private static String COMMON_PROP_LOCATION = "/properties/common.properties";
    private static String ENV_PROP_LOCATION = "/properties/";

    public VijestiPage() throws IOException {
        prop = new Properties();
        prop.load(VijestiPage.class.getResourceAsStream(COMMON_PROP_LOCATION));
        logger.info("ENV file is : " + System.getProperty("env"));
        prop.load(VijestiPage.class.getResourceAsStream(ENV_PROP_LOCATION + System.getProperty("env") + ".properties"));
    }

    @When("^I hover mouse over (.*) element for the Vijesti Page$")
    public void hoverMouse(String field) throws Exception {
        vijestiPageSteps.assert_click_element_visible(field);
        vijestiPageSteps.hoverMouseOverLink(field);
    }

    @Then("^I should see the below mentioned (.*)?items on the Vijesti Page$")
    public void validateUIElementsPresentOnPage(String field, DataTable webElements) throws Exception {
        for (Map<String, String> dataMap : webElements.asMaps(String.class, String.class)) {
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                logger.info("Validate Element " + entry.getKey() + " \"" + entry.getValue() + "\" is present on the Page");
                vijestiPageSteps.assert_click_element_visible(entry.getKey() + "_" + entry.getValue());
            }
        }
    }

    @Then("^I hover mouse over (.*) element of (.*), I should see (.*) link on the Vijesti Page$")
    public void hoverMouseAndValidateLink(String hoverField, String mainMenu, String validationLinkField) throws Exception {
        vijestiPageSteps.hoverMouseOverLink(hoverField);
        vijestiPageSteps.assert_click_element_visible(validationLinkField);
    }
}
