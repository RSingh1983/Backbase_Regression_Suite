package org.testing.framework.cucumber.balkansWebsite;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.steps.balkansWebsite.uisteps.ProgramiPageSteps;
import org.testing.framework.steps.balkansWebsite.uisteps.VijestiPageSteps;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class ProgramiPage extends PageObject {

    static Logger logger = LoggerFactory.getLogger(ProgramiPage.class.getName());

    @Steps
    ProgramiPageSteps programiPageSteps;

    public ProgramiPage() throws IOException { }

    @When("^I hover mouse over (.*) element for the Programi Page$")
    public void hoverMouse(String field) throws Exception {
        programiPageSteps.assert_click_element_visible(field);
        programiPageSteps.hoverMouseOverLink(field);
    }

    @Then("^I hover mouse over (.*) element of (.*), I should see (.*) link on the Programi Page$")
    public void hoverMouseAndValidateLink(String hoverField, String mainMenu, String validationLinkField) throws Exception {
        programiPageSteps.scrollToClickElement(mainMenu);
        programiPageSteps.hoverMouseOverLink(mainMenu);
        programiPageSteps.hoverMouseOverLink(hoverField);
        programiPageSteps.assert_click_element_visible(validationLinkField);
    }
}
