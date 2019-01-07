package org.testing.framework.cucumber.backbaseTest;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.steps.backbaseTest.uisteps.AddUpdateComputerPageSteps;
import org.testing.framework.steps.backbaseTest.uisteps.HomePageSteps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class AddUpdateComputerPage extends PageObject {

    @Steps
    AddUpdateComputerPageSteps addUpdateComputerPageSteps;

    @Steps
    HomePageSteps homePageSteps;

    static Logger logger = LoggerFactory.getLogger(AddUpdateComputerPage.class.getName());

    public AddUpdateComputerPage() throws IOException {
    }

    @When("^I add a new computer with expected status as (.*) and using below details$")
    public void addNewComputer(String expectedStatus, DataTable webElements) throws Exception {

        Map<String, String> computerDetails = webElements.asMap(String.class, String.class);
        ArrayList<String> classNames = new ArrayList<>();

        // Click the button to add a new Computer
        homePageSteps.click_the_element("Add_New_Computer_Button");

        //Get details of computer passed from feature file and populate in the desired fields
        for (Map.Entry<String, String> entry : computerDetails.entrySet()) {
            addUpdateComputerPageSteps.type_or_select_the_value(entry.getValue(), entry.getKey());
        }

        if (expectedStatus.toLowerCase().contains("success")) {
            addUpdateComputerPageSteps.click_the_element("Create_Computer_Button");
            Assert.assertEquals("Done! Computer " + computerDetails.get("ComputerName") + " has been created", homePageSteps.getTextFromTextElement("Add_Computer_Success_Message"));
        } else if (expectedStatus.toLowerCase().contains("fail")) {

            addUpdateComputerPageSteps.click_the_element("Create_Computer_Button");

            // Get class details for all elements after clicking Submit button
            for (Map.Entry<String, String> entry : computerDetails.entrySet()) {
                classNames.add(addUpdateComputerPageSteps.getAttributeValueFromTextElement(entry.getKey(), "class"));
            }
            addUpdateComputerPageSteps.click_the_element("Cancel_Create_Computer_Button");
            Assert.assertTrue(classNames.contains("clearfix error"));
        } else {  // Cancellation Scenario
            addUpdateComputerPageSteps.click_the_element("Cancel_Create_Computer_Button");
        }
    }

    @When("^I update computer (.*) with expected status as (.*) and using below details$")
    public void updateComputer(String existingComputerName, String expectedStatus, DataTable webElements) throws Exception {

        Map<String, String> computerDetails = webElements.asMap(String.class, String.class);
        ArrayList<String> classNames = new ArrayList<>();

        // Search for the Computer
        homePageSteps.type_the_value(existingComputerName, "Search_Computer_Textbox");
        homePageSteps.click_the_element("Filter_Computer_Button");

        // Open the details page for the Computer
        homePageSteps.click_the_element("ComputerName", existingComputerName);

        //Get details of computer passed from feature file and populate in the desired fields
        for (Map.Entry<String, String> entry : computerDetails.entrySet()) {
            addUpdateComputerPageSteps.type_or_select_the_value(entry.getValue(), entry.getKey());
        }

        if (expectedStatus.toLowerCase().contains("success")) {
            addUpdateComputerPageSteps.click_the_element("Save_Updated_Computer_Button");
            Assert.assertEquals("Done! Computer " + computerDetails.get("ComputerName") + " has been updated", homePageSteps.getTextFromTextElement("Add_Computer_Success_Message"));
        } else if (expectedStatus.toLowerCase().contains("fail")) {
            addUpdateComputerPageSteps.click_the_element("Save_Updated_Computer_Button");
            // Get class details for all elements after clicking Submit button
            for (Map.Entry<String, String> entry : computerDetails.entrySet()) {
                classNames.add(addUpdateComputerPageSteps.getAttributeValueFromTextElement(entry.getKey(), "class"));
            }
            addUpdateComputerPageSteps.click_the_element("Cancel_Create_Computer_Button");
            Assert.assertTrue(classNames.contains("clearfix error"));
        } else {
            addUpdateComputerPageSteps.click_the_element("Cancel_Create_Computer_Button");
        }
    }

    @Then("^I delete the computer with Computer Name as (.*)$")
    public void deleteComputer(String computerName) throws Exception {

        // Search for the Computer
        homePageSteps.type_the_value(computerName, "Search_Computer_Textbox");
        homePageSteps.click_the_element("Filter_Computer_Button");

        if(!computerName.equals("") && !homePageSteps.getTextFromTextElement("NoSearchedResultsLabel","").equalsIgnoreCase("No computers found")) {
//        if (!homePageSteps.is_text_element_present("NoSearchedResults", "")) {
            // Open the details page for the Computer
            homePageSteps.click_the_element("ComputerName", computerName);
            addUpdateComputerPageSteps.click_the_element("Delete_Computer_Button");

            //Validate Success message
            Assert.assertEquals("Done! Computer has been deleted", homePageSteps.getTextFromTextElement("Add_Computer_Success_Message"));
        }
    }
}
