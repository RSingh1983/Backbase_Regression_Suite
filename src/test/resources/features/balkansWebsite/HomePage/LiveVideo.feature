@regression @homePage
Feature: Home Page Live Video Validations

  @homePageLiveVideo
  Scenario: Home Page - Validation of Live and Other Videos on the Home Page
    Given I browse webSite using BALKANS_URL url of Home Page
    Then I validate live video is visible and plays on the Home Page