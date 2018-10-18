package org.testing.framework.cucumber.balkansWebsite;

import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.steps.balkansWebsite.uisteps.UživoPageSteps;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class UživoPage extends PageObject {

    static Logger logger = LoggerFactory.getLogger(UživoPage.class.getName());

    @Steps
    UživoPageSteps uživoPageSteps;

    public UživoPage() throws IOException { }

    @Then("^I validate live video is visible and plays on the Uživo Page$")
    public void validateLiveVideo() throws Exception {

        uživoPageSteps.scrollTop();
        uživoPageSteps.scrollToClickElementXCoordinate("SubSection1_Recent_Videos");

        // Switch to the LiveVideo Iframe
        uživoPageSteps.switch_to_iframe("LiveVideo_Iframe");

        // Validate Live Video is in Paused Mode
        logger.info("Class of Player is: " + uživoPageSteps.getAttributeValueFromClickElement("LiveVideo_Player","class"));
        assertTrue(uživoPageSteps.getAttributeValueFromClickElement("LiveVideo_Player","class").contains("paused-mode"));
        assertTrue(uživoPageSteps.getAttributeValueFromClickElement("LiveVideo_LowerPlayPauseButton","aria-label").contains("Play"));

        //Hover the mouse over the Video to display the Pause Button
        uživoPageSteps.hoverMouseOverLink("LiveVideo_MiddlePlayButton");

        //Play the Video by clicking the Middle Play Button
        uživoPageSteps.click_the_element("LiveVideo_MiddlePlayButton");

        // Validate Live Video is in Play Mode
        logger.info("Class of Video is : " + uživoPageSteps.getAttributeValueFromClickElement("LiveVideo_Player","class"));
        assertTrue(uživoPageSteps.getAttributeValueFromClickElement("LiveVideo_Player","class").contains("playing-mode"));
        assertTrue(uživoPageSteps.getAttributeValueFromClickElement("LiveVideo_LowerPlayPauseButton","aria-label").contains("Pause"));

        //Hover the mouse over the Video to display the Pause Button
        uživoPageSteps.hoverMouseOverLink("LiveVideo_PlayerVideo");

        // Pause the Video
        uživoPageSteps.click_the_element("LiveVideo_Player");

        // Validate Live Video is in Paused Mode
        assertTrue(uživoPageSteps.getAttributeValueFromClickElement("LiveVideo_Player","class").contains("paused-mode"));
        assertTrue(uživoPageSteps.getAttributeValueFromClickElement("LiveVideo_LowerPlayPauseButton","aria-label").contains("Play"));

        //Hove the mouse over the Video to display the Play Button
        uživoPageSteps.hoverMouseOverLink("LiveVideo_PlayerVideo");

        // Play the Video again
        uživoPageSteps.click_the_element("LiveVideo_LowerPlayPauseButton");

        // Validate Live Video is in Play Mode
        assertTrue(uživoPageSteps.getAttributeValueFromClickElement("LiveVideo_Player","class").contains("playing-mode"));
        assertTrue(uživoPageSteps.getAttributeValueFromClickElement("LiveVideo_LowerPlayPauseButton","aria-label").contains("Pause"));


    }

}
