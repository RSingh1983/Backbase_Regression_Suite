package org.testing.framework.cucumber.backbaseTest;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.steps.backbaseTest.uisteps.HomePageSteps;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class HomePage extends PageObject {

    @Steps
    HomePageSteps homePageSteps;

    static Logger logger = LoggerFactory.getLogger(HomePage.class.getName());

    public HomePage() throws IOException {
    }

    @Then("^I validate that a computer with below mentioned details added in the database$")
    public void validateComputerDetails(DataTable webElements) throws Exception {

        Map<String, String> computerDetails = webElements.asMap(String.class, String.class);

        //Search for the Computer in the database
        homePageSteps.type_the_value(computerDetails.get("ComputerName"), "Search_Computer_Textbox");
        homePageSteps.click_the_element("Filter_Computer_Button");

        //Validate the data of Computer Item
        int columnIndex = 1;
        for (Map.Entry<String, String> entry : computerDetails.entrySet()) {
            logger.info("Expected is :" + convertDateFormat(entry.getValue(),"yyyy-MM-dd","dd MMM yyyy"));
            Assert.assertEquals(convertDateFormat(entry.getValue(),"yyyy-MM-dd","dd MMM yyyy"), homePageSteps.getTextFromTextElement("ComputerDetails", Integer.toString(columnIndex)));
            columnIndex++;
        }
    }

    @Then("^I validate (.*) computers with name (.*) exist in the system$")
    public void validateComputerCount(String computerCount, String computerName) throws Exception {

        if (!computerName.equals("")) {
            //Search for the Computer in the database
            homePageSteps.type_the_value(computerName, "Search_Computer_Textbox");
            homePageSteps.click_the_element("Filter_Computer_Button");

            if (Integer.parseInt(computerCount) == 0)
                homePageSteps.assert_text_element_present("NoSearchedResults", "");
            else
                Assert.assertEquals(Integer.parseInt(computerCount), homePageSteps.numberOfElementsOnPage("NumberOfSearchedResults", "", ""));

        }
    }

    public String convertDateFormat(String dateString, String givenDateFormat, String expectedDateFormat) {

        String expectedDate = dateString.equals("") ?  "-" : dateString ;
        try {
            logger.info("Given date is " + expectedDate);

            DateFormat sdf = new SimpleDateFormat(givenDateFormat);
            Date date = sdf.parse(dateString);

            expectedDate = new SimpleDateFormat(expectedDateFormat).format(date);
            logger.info("Converted date is: " + expectedDate);

        } catch(ParseException e) {
            logger.info("Can't parse the date");
        }
        return expectedDate;
    }
}