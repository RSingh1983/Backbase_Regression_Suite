Feature: As a Balkans Site User I should be able to Navigate through following details on the Home Page:
  1. All the Articles of New Stories in Left Panel.
  2. All the links and images on the Article's Page.

  @newStoriespanel
  Scenario: Home Page - Validation of New Stories Pane
    Given I browse webSite using BALKANS_URL url of Home Page
    Then I click on each story of Top_Left_Panel_New_Stories Panel and validate links and images on each page