Feature: As a Balkans Site User I should be able to Navigate through following details on the Home Page:
  1. All the images should be visible

#  @issue:AJBW-443
  @live @imageValidations
  Scenario: Home Page - Validation of Images on the Home Page
    Given I browse webSite using BALKANS_URL url of Home Page
    Then I validate all the images are visible on the Home Page