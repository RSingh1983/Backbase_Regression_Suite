@regression @homePage
Feature: Home Page New Stories Validation

  @homePageNewStories
  Scenario: Home Page - Validation of New Stories Pane
    Given I browse webSite using BALKANS_URL url of Home Page
    Then I click on each story of Top_Left_Panel_New_Stories Panel and validate links and images on each page