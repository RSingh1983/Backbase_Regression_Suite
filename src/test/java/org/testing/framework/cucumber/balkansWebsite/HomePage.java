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

            //Validate Header of the Article
            logger.info("Validate Header of the Article");
            if(articleType.equalsIgnoreCase("Normal Article")) {
                logger.info("Validate Header for Normal Article : " + linkText);
                assertTrue(linkText.equals(homePageSteps.getTextFromTextElement("Normal_Article_Header")));

                // Validate the Social Media sharing options appear on the Article Page
                logger.info("Validate Social Media Icons For Normal Article");
                homePageSteps.assert_click_element_visible("Normal_Article_Share_Facebook");
                homePageSteps.assert_click_element_visible("Normal_Article_Share_Twitter",linkText.split(" ")[0]);
                homePageSteps.assert_click_element_visible("Normal_Article_Share_Mail",linkText.split(" ")[0]);

            } else {
                logger.info("Validate Header for Video Article : " + linkText);
                homePageSteps.scrollToClickElement("Video_Article_Header");
                assertTrue(linkText.equals(homePageSteps.getTextFromTextElement("Video_Article_Header")));

                // Validate the Social Media sharing options appear on the Article Page
                logger.info("Validate Social Media Icons For Video Article");
                homePageSteps.assert_click_element_visible("Video_Article_Share_Facebook");
                homePageSteps.assert_click_element_visible("Video_Article_Share_Twitter",linkText.split(" ")[0]);
                homePageSteps.assert_click_element_visible("Video_Article_Share_Mail",linkText.split(" ")[0]);
            }

             // To-Do Validate all the anchors on the page are accessible
            int anchorIndex = 0;
            for(String anchor: homePageSteps.getAttributeValueFromAllClickElement("Main_Page_Anchors","href")) {
                logger.info("Validating Anchor " + ++anchorIndex + " of Article Page " + articleIndex + " : " + anchor);
                if (anchor != null ) {
                    if (!(anchor.contains("www.linkedin.com/company")) && !(anchor.contains("javascript")) && !(anchor.contains("mailto:?subject"))) {
                        RestAssured.given().
                                urlEncodingEnabled(false).
                                when().get(anchor).
                                then().assertThat().statusCode(200);
                    }
                }
            }

            for(String anchor: homePageSteps.getAttributeValueFromAllClickElement("Footer_Anchors","href")) {
                logger.info("Validating Anchor " + ++anchorIndex + " of Article Page " + articleIndex + " : " + anchor);
                if (anchor != null ) {
                    if (!(anchor.contains("www.linkedin.com/company")) && !(anchor.contains("javascript")) && !(anchor.contains("mailto:?subject"))) {
                        RestAssured.given().
                                urlEncodingEnabled(false).
                                when().get(anchor).
                                then().assertThat().statusCode(200);
                    }
                }
            }

            //To-Do Validate src of all images is accessible
            int imageIndex = 0;
            // Validate all the images on the Main Page Content
            for(String imageSrc: homePageSteps.getAttributeValueFromAllClickElement("Main_Page_Images","src")) {
                logger.info("Validating Image " + ++imageIndex + " of Article Page " + articleIndex + " : " + imageSrc);
                if(imageSrc!="") {
                    RestAssured.given().
                            urlEncodingEnabled(false).
                            when().get(imageSrc).
                            then().assertThat().statusCode(200);
                    homePageSteps.assertImagePresent("ImagePlaceholderForVisibility", imageSrc);
                } else{
                    fail("src tag of the image is blank");
                }
            }

            // Validate all the images on the Footer Content
            for(String imageSrc: homePageSteps.getAttributeValueFromAllClickElement("Footer_Images","src")) {
                logger.info("Validating Image " + ++imageIndex + " of Article Page " + articleIndex + " : " + imageSrc);
                if(imageSrc!="") {
                    RestAssured.given().
                            urlEncodingEnabled(false).
                            when().get(imageSrc).
                            then().assertThat().statusCode(200);
                    homePageSteps.assertImagePresent("ImagePlaceholderForVisibility", imageSrc);
                } else{
                    fail("src tag of the image is blank");
                }
            }

            logger.info("Navigate back to Home Page");
            homePageSteps.click_the_element("Home_Page_Logo");

            logger.info("Scroll to Element: " + field + " with index: " + articleIndex);
            homePageSteps.scrollToClickElement(field, Integer.toString(articleIndex));

        }

    }

}
