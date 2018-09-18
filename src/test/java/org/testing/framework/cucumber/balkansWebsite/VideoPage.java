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

    @Steps
    VideoPageSteps videoPageSteps;

    public VideoPage() throws IOException {}
}
