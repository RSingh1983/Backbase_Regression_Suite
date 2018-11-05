package org.testing.framework.cucumber.balkansWebsite;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.eclipse.jetty.client.ValidatingConnectionPool;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.cucumber.CommonPage;
import org.testing.framework.steps.balkansWebsite.uisteps.HomePageSteps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.urlEncodingEnabled;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class HomePage extends PageObject {

    static Logger logger = LoggerFactory.getLogger(HomePage.class.getName());

    @Steps
    HomePageSteps homePageSteps;

    public HomePage() throws IOException { }

    @Then("^I click on each story of (.*) Panel and validate links and images on each page$")
    public void validatePageDetailsAfterLinkClick(String field) throws Exception {
        for(int articleIndex = 1; articleIndex<=8; articleIndex++ ) {

            //Get Header Text of the link
            String linkText = homePageSteps.getTextFromClickElement(field, Integer.toString(articleIndex));
            logger.info("Text of Link on index " + articleIndex +" is: " + linkText);

            //Get Type of Article
            String articleType = homePageSteps.getAttributeValueFromClickElement(field, Integer.toString(articleIndex),"href").contains("/video/") ? "Video Article" : "Normal Article";
            logger.info("Type of Article is: " + articleType);

            //Click the Link Article
            logger.info("Click the Article Link with index: " + articleIndex);
            homePageSteps.click_the_element(field, Integer.toString(articleIndex));

            if(articleType.equalsIgnoreCase("Normal Article")) {

                logger.info("***************************************************************** Normal Article Validation ******************************************************");

                // Validate the Header of the Article
                logger.info("Page Header - Normal Article: " + homePageSteps.getTextFromClickElement("Normal_Article_Header"));
                assertTrue(linkText.equals(homePageSteps.getTextFromClickElement("Normal_Article_Header")));

                if(!linkText.toLowerCase().contains("video")) {

                    if(homePageSteps.is_text_element_present("Article_MainImage","")) {
                        // Validate the Main image is present on the Page
                        logger.info("Validate Main Image of Article is Present");
                        homePageSteps.assert_text_element_visible("Article_MainImage");
                    }

                    // Validate the Social Media sharing options appear on the Article Page
                    logger.info("Validate Social Media Icons For Normal Article");
                    homePageSteps.assert_click_element_visible("Normal_Article_Share_Facebook");
                    homePageSteps.assert_click_element_visible("Normal_Article_Share_Twitter", linkText.split(" ")[0].replace("'", ""));
                    homePageSteps.assert_click_element_visible("Normal_Article_Share_Mail", linkText.split(" ")[0].replace("'", ""));
                }
            } else {
                logger.info("***************************************************************** Video Article Validation ******************************************************");

                // Validate the Header of the Article
                logger.info("Page Header - Video Article: " + homePageSteps.getTextFromClickElement("Video_Article_Header"));
                homePageSteps.scrollToClickElementXCoordinate("Video_Article_Header");
                assertTrue(linkText.equals(homePageSteps.getTextFromClickElement("Video_Article_Header")));


                // Check if Video is embedded as an iFrame
                if(homePageSteps.is_text_element_present("Article_MainVideo_Iframe","")) {

                    logger.info("*****Video is embedded as an iFrame*****");
                    // Switch to the LiveVideo Iframe
                    homePageSteps.switch_to_iframe("Article_MainVideo_Iframe");
                    homePageSteps.scrollToClickElementXCoordinate("LiveVideo_LowerPlayPauseButton");

                    // Validate Video is in Paused Mode
                    logger.info("Validate Video is in Paused mode");
                    String videoClass = homePageSteps.getAttributeValueFromClickElement("LiveVideo_Player", "class");
                    logger.info("Class of Player is: " + videoClass);
                    assertTrue(videoClass.contains("paused-mode"));

                    //Play the Video by clicking the Middle Play Button
                    logger.info("Play the Video");
                    homePageSteps.click_the_element("LiveVideo_MiddlePlayButton");

                    // Validate Live Video is in Play Mode
                    logger.info("Validate Video is in Play mode");
                    videoClass = homePageSteps.getAttributeValueFromClickElement("LiveVideo_Player", "class");
                    logger.info("Class of Player is: " + videoClass);
                    assertTrue(videoClass.contains("playing-mode"));

                    // Pause the Video
                    logger.info("Pause the Video");
                    homePageSteps.click_the_element("LiveVideo_Player");

                    // Validate Live Video is in Paused Mode
                    logger.info("Validate Video is in Paused mode");
                    videoClass = homePageSteps.getAttributeValueFromClickElement("LiveVideo_Player", "class");
                    logger.info("Class of Player is: " + videoClass);
                    assertTrue(videoClass.contains("paused-mode"));

                    // Switch Back to Main Page
                    homePageSteps.switch_to_window();
                } else {

                    logger.info("*****Video is directly embedded in HTML*****");

                    logger.info("Validate if video has completed");
                    String videoClass = homePageSteps.getAttributeValueFromClickElement("VJS_PlayerVideo", "class");
                    logger.info("Class of Video is : " + videoClass);
                    if (videoClass.contains("vjs-ended")){
                        logger.info("Play the video again if it has completed");
                        homePageSteps.click_the_element("VJS_PlayerVideo");
                    }

                    // Validate Video is in Play Mode
                    logger.info("Validate Video is in Play Mode");
                    videoClass = homePageSteps.getAttributeValueFromClickElement("VJS_PlayerVideo", "class");
                    logger.info("Class of Player is: " + videoClass);
                    assertTrue(videoClass.contains("vjs-playing"));

                    //Pause the Video
                    logger.info("Pause the Video");
                    homePageSteps.click_the_element("VJS_PlayerVideo");

                    // Validate Video is in Pause Mode
                    logger.info("Validate Video is in Pause Mode");
                    videoClass = homePageSteps.getAttributeValueFromClickElement("VJS_PlayerVideo", "class");
                    logger.info("Class of Player is: " + videoClass);
                    assertTrue(videoClass.contains("vjs-paused"));

                }

                logger.info("********************* Social Media Icons Validation ******************************************************");
                // Validate the Social Media sharing options appear on the Article Page
                logger.info("Validate Social Media Icons For Video Article");
                homePageSteps.assert_click_element_visible("Video_Article_Share_Facebook");
                homePageSteps.assert_click_element_visible("Video_Article_Share_Twitter",linkText.split(" ")[0]);
                homePageSteps.assert_click_element_visible("Video_Article_Share_Mail",linkText.split(" ")[0]);

            }

            logger.info("********************* Anchors Validation ******************************************************");

//             To-Do Validate all the anchors on the page are accessible
            int anchorIndex = 0;
            for (Map.Entry<String,String>  anchor : homePageSteps.getAttributeValueFromAllClickElement("Main_Page_Anchors", "href").entrySet()) {
                logger.info("Validating Anchor " + ++anchorIndex + " of "+ articleIndex + " Article Page: " + anchor.getValue());
                if (anchor.getValue() != null) {
                    if (!(anchor.getValue().contains("www.linkedin.com/company")) && !(anchor.getValue().contains("javascript")) && !(anchor.getValue().contains("mailto:?subject"))) {
                        RestAssured.given().
                                urlEncodingEnabled(false).
                                when().get(anchor.getValue()).
                                then().assertThat().statusCode(200);
                    }
                }
            }

            for (Map.Entry<String,String>  anchor : homePageSteps.getAttributeValueFromAllClickElement("Footer_Anchors", "href").entrySet()) {
                logger.info("Validating Anchor " + ++anchorIndex + " of "+ articleIndex + " Article Page: " + anchor.getValue());
                if (anchor.getValue() != null) {
                    if (!(anchor.getValue().contains("www.linkedin.com/company")) && !(anchor.getValue().contains("javascript")) && !(anchor.getValue().contains("mailto:?subject"))) {
                        RestAssured.given().
                                urlEncodingEnabled(false).
                                when().get(anchor.getValue()).
                                then().assertThat().statusCode(200);
                    }
                }
            }

            logger.info("********************* Image Validation ******************************************************");

//            To-Do Validate src of all images is accessible
            int imageIndex = 0;
            // Validate all the images on the Main Page Content
            for (Map.Entry<String,String> imageSrc : homePageSteps.getAttributeValueFromAllClickElement("Main_Page_Images", "src").entrySet()) {
                logger.info("Validating Image " + ++imageIndex + " of  " + articleIndex + " Article Page: : " + imageSrc.getKey());
                if (imageSrc.getKey() != "") {
                    RestAssured.given().
                            urlEncodingEnabled(false).
                            when().get(imageSrc.getValue()).
                            then().assertThat().statusCode(200);
                    homePageSteps.scrollToClickElement("ImagePlaceholderForVisibility", imageSrc.getKey());
                    homePageSteps.assertImagePresent("ImagePlaceholderForVisibility", imageSrc.getKey());
                } else {
                    fail("src tag of the image is blank");
                }
            }

            // Validate all the images on the Footer Content
            for (Map.Entry<String,String> imageSrc : homePageSteps.getAttributeValueFromAllClickElement("Footer_Images", "src").entrySet()) {
                logger.info("Validating Image " + ++imageIndex + " of " + articleIndex + " Article Page: : " + imageSrc.getKey());
                if (imageSrc.getKey() != "") {
                    RestAssured.given().
                            urlEncodingEnabled(false).
                            when().get(imageSrc.getValue()).
                            then().assertThat().statusCode(200);
                    homePageSteps.scrollToClickElement("ImagePlaceholderForVisibility", imageSrc.getKey());
                    homePageSteps.assertImagePresent("ImagePlaceholderForVisibility", imageSrc.getKey());
                } else {
                    fail("src tag of the image is blank");
                }
            }

            logger.info("Navigate back to Home Page");
            homePageSteps.click_the_element("Home_Page_Logo");

            logger.info("Scroll to Element: " + field + " with index: " + articleIndex);
            homePageSteps.scrollToClickElement(field, Integer.toString(articleIndex));

        }

    }

    @Then("^I validate all the images are visible on the Home Page$")
    public void validateImageVisible() throws Exception {
        homePageSteps.assertAllImagesPresent("Main_Page_Images");
    }

    @Then("^I validate live video is visible and plays on the Home Page$")
    public void validateLiveVideo() throws Exception {

        homePageSteps.scrollTop();

        // Validate the Header for live video is present
        homePageSteps.assert_text_element_visible("LiveVideo_HeaderLabel");

        // Validate the Footer link for Programs of the live Video is present
        homePageSteps.assert_click_element_visible("LiveVideo_FooterLink");

        // Switch to the LiveVideo Iframe
        homePageSteps.switch_to_iframe("LiveVideo_Iframe");

        // Pause the Video if its already Playing
        if(homePageSteps.getAttributeValueFromClickElement("LiveVideo_Player","class").contains("playing-mode")){
            //Hove the mouse over the Video to display the Pause Button
            homePageSteps.hoverMouseOverLink("LiveVideo_PlayerVideo");

            // Pause the Video
            homePageSteps.click_the_element("LiveVideo_Player");

        }
        // Validate Live Video is in Paused Mode
        logger.info("Pause the live video");
        String videoClass = homePageSteps.getAttributeValueFromClickElement("LiveVideo_Player","class");
        logger.info("Class of Player is: " + videoClass);
        assertTrue(videoClass.contains("paused-mode"));

        //Play the Video
        logger.info("Play the live video");
        homePageSteps.click_the_element("LiveVideo_Player");

        // Validate Live Video is in Play Mode
        logger.info("Play the live video");
        videoClass = homePageSteps.getAttributeValueFromClickElement("LiveVideo_Player","class");
        logger.info("Class of Player is: " + videoClass);
        assertTrue(videoClass.contains("playing-mode"));

    }

    @Then("^I search articles from section (.*) and validate search results on the Home Page$")
    public void searchAndValidateArticlesFromSections(String sectionName)  throws Exception {

        for(int articleIndex = 1; articleIndex<=8; articleIndex++ ) {
            homePageSteps.scrollTop();

            //Get Header Text of the link
            String linkText = homePageSteps.getTextFromClickElement(sectionName, Integer.toString(articleIndex));
            logger.info("Text of Link on index " + articleIndex + " is: " + linkText);

            homePageSteps.click_the_element("SearchButton");
            homePageSteps.type_the_value_and_enter(linkText.split(":").length >=2 ? linkText.split(":")[1].replaceAll("\\?+$", ""): linkText.split(":")[0].replaceAll("\\?+$",""),"Article_Search_TextBox");

            ArrayList<String> searchResults = new ArrayList<>();
            for (String searchResult: homePageSteps.getTextFromMultipleElements("Article_SearchResults", "")) {
                logger.info("Search Result: "+ searchResult);
                searchResults.add(searchResult);
            }

            //Validate the searched Article appeared in the results
            logger.info("Search results are: " + String.valueOf(searchResults));
            logger.info("Validate the search results contains the link text");
            assertTrue(searchResults.contains(linkText));
            logger.info("Navigate back to Home Page");
            homePageSteps.scrollTop();
            homePageSteps.click_the_element("Home_Page_Logo");

            logger.info("Scroll to Element: " + sectionName + " with index: " + articleIndex);
            homePageSteps.scrollToClickElement(sectionName, Integer.toString(articleIndex));
        }


    }

}
