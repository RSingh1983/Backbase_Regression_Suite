package org.testing.framework.cucumber.balkansWebsite;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.eclipse.jetty.client.ValidatingConnectionPool;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.steps.balkansWebsite.uisteps.HomePageSteps;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class HomePage extends PageObject {

    static Logger logger = LoggerFactory.getLogger(HomePage.class.getName());

    @Steps
    HomePageSteps homePageSteps;

    public HomePage() throws IOException { }

    @Then("^I click on each story of (.*) Panel and validate the Page Headers and Social sharing options$")
    public void validatePageDetailsAfterLinkClick(String field) throws Exception {
        for(int index = 1; index<=8; index++ ) {

            //Get Header Text of the link
            String linkText = homePageSteps.getTextFromClickElement(field, Integer.toString(index));
            logger.info("Text of Link on index " + index +" is: " + linkText);

            //Get Type of Article
            String articleType = homePageSteps.getAttributeValueFromClickElement(field, Integer.toString(index),"href").contains("/video/") ? "Video Article" : "Normal Article";
            logger.info("Type of Article is: " + articleType);

            //Click the Link Article
            logger.info("Click the Article Link with index: " + index);
            homePageSteps.click_the_element(field, Integer.toString(index));

            //Validate Header of the Article
            logger.info("Validate Header of the Article");
            if(articleType.equalsIgnoreCase("Normal Article")) {
                assertTrue(linkText.equals(homePageSteps.getTextFromTextElement("Normal_Article_Header")));

                // Validate the Social Media sharing options appear on the Article Page
                logger.info("Validate Social Media Icons For Normal Article");
                homePageSteps.assert_click_element_visible("Normal_Article_Share_Facebook");
                homePageSteps.assert_click_element_visible("Normal_Article_Share_Twitter",linkText.split(" ")[0]);
                homePageSteps.assert_click_element_visible("Normal_Article_Share_Mail",linkText.split(" ")[0]);

//                //To-Do Validate all the anchors and links on the page are accessbile
//                for(String anchor: homePageSteps.getAttributeValueFromAllClickElement("All_Anchors","href")) {
//                    logger.info("Validating Anchor: " + anchor);
//                    RestAssured.
//                            when().get(anchor).
//                            then().assertThat().statusCode(200);
//                }
//
//                for(String link: homePageSteps.getAttributeValueFromAllClickElement("All_Links","href")) {
//                    logger.info("Validating Link: " + link);
//                    RestAssured.
//                            when().get(link).
//                            then().assertThat().statusCode(200);
//                }
//                //To-Do Validate src of all images is accessible
//                for(String image: homePageSteps.getAttributeValueFromAllClickElement("All_Images","src")) {
//                    logger.info("Validating Image: " + image);
//                    RestAssured.
//                            when().get(image).
//                            then().assertThat().statusCode(200);
//                }

            } else {
                logger.info("Validate Header for Video Article");

                // Validate the Social Media sharing options appear on the Article Page
                logger.info("Validate Social Media Icons For Video Article");
                homePageSteps.assert_click_element_visible("Video_Article_Share_Facebook");
                homePageSteps.assert_click_element_visible("Video_Article_Share_Twitter",linkText.split(" ")[0]);
                homePageSteps.assert_click_element_visible("Video_Article_Share_Mail",linkText.split(" ")[0]);
            }

            logger.info("Navigate back to Home Page");
            homePageSteps.click_the_element("Home_Page_Logo");

            logger.info("Scroll to Element: " + field + " with index: " + index);
            homePageSteps.scrollToClickElement(field, Integer.toString(index));

        }

    }

}
