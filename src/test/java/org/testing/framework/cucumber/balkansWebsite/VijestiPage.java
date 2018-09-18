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

    @Steps
    VijestiPageSteps vijestiPageSteps;

    public VijestiPage() throws IOException {}

    @When("^I hover mouse over (.*) element for the Vijesti Page$")
    public void hoverMouse(String field) throws Exception {
        vijestiPageSteps.assert_click_element_visible(field);
        vijestiPageSteps.hoverMouseOverLink(field);
    }

    @Then("^I hover mouse over (.*) element of (.*), I should see (.*) link on the Vijesti Page$")
    public void hoverMouseAndValidateLink(String hoverField, String mainMenu, String validationLinkField) throws Exception {
        vijestiPageSteps.hoverMouseOverLink(hoverField);
        vijestiPageSteps.assert_click_element_visible(validationLinkField);
    }
}
