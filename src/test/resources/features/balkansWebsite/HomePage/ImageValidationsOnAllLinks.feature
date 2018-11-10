@homePage
Feature: Home Page Links Pages Images Validations

  @homePageLinksImages @nightly
  Scenario: Home Page - Validation of Images on links Pages of the Home Page
    Given I browse webSite using BALKANS_URL url of Home Page
    Then I validate images on all the links of the Home Page