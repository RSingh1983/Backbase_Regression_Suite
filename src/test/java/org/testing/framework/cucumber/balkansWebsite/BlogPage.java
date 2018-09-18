package org.testing.framework.cucumber.balkansWebsite;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.cucumber.CommonPage;
import org.testing.framework.steps.balkansWebsite.uisteps.BlogPageSteps;

import java.io.IOException;
import java.util.Properties;

public class BlogPage extends PageObject {

    static Logger logger = LoggerFactory.getLogger(BlogPage.class.getName());

    @Steps
    BlogPageSteps blogPageSteps;

    public BlogPage() throws IOException { }

}
