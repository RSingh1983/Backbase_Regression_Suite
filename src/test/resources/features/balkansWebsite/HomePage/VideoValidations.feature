Feature: As a Balkans Site User I should be able to Navigate through following details on the Home Page:
  1. Live Video should Play
  2. All Videos on the Page play correctly

  @live @homePage @videoValidations
  Scenario: Home Page - Validation of Live and Other Videos on the Home Page
    Given I browse webSite using BALKANS_URL url of Home Page
    Then I validate live video is visible and plays on the Home Page