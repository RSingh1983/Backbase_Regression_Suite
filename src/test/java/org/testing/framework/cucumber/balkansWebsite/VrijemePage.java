package org.testing.framework.cucumber.balkansWebsite;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.steps.balkansWebsite.uisteps.InteraktivnoPageSteps;
import org.testing.framework.steps.balkansWebsite.uisteps.VrijemePageSteps;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class VrijemePage extends PageObject {

    static Logger logger = LoggerFactory.getLogger(VrijemePage.class.getName());

    @Steps
    VrijemePageSteps vrijemePageSteps;

    public VrijemePage() throws IOException { }

}
