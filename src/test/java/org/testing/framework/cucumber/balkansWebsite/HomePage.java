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

    @Steps
    HomePageSteps homePageSteps;

    public HomePage() throws IOException { }

}
