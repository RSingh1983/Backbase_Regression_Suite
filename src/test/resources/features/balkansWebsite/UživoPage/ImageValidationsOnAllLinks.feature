@uživoPage
Feature: Uživo Page Links Pages Images Validations

  @uživoPageLinksImages @nightly
  Scenario: Uživo Page - Validation of Images on links Pages of the Uživo Page
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Uživo element for Uživo Page
    Then I validate images on all the links of the Uživo Page