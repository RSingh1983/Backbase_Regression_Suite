package org.testing.framework.cucumber.backbaseTest;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.steps.backbaseTest.uisteps.*;
import org.testing.framework.steps.uiSteps.UISteps;

import java.io.IOException;

public class CommonPage extends PageObject {

    static Logger logger = LoggerFactory.getLogger(CommonPage.class.getName());

    // Variables
    UISteps uiSteps;

    @Steps
    AddUpdateComputerPageSteps addUpdateComputerPageSteps;

    @Steps
    HomePageSteps homePageSteps;

    public CommonPage() throws IOException {
    }

    @Given("^I browse webSite using (.*) url$")
    public void openURL(String url) throws Exception {
        homePageSteps.open_site(url);
    }

    @Before
    public void beforeScenario(){
        // Delete any computer item, if it already exist

    }

}
