@regression @homePage
Feature: Home Page Images Validations

#  @issue:AJBW-443
  @homePageImages
  Scenario: Home Page - Validation of Images on the Home Page
    Given I browse webSite using BALKANS_URL url of Home Page
    Then I validate all the images are visible on the Home Page