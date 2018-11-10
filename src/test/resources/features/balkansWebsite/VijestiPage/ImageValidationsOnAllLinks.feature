@vijestiPage
Feature: Vijesti Page Links Pages Images Validations

  @vijestiPageLinksImages @nightly
  Scenario: Vijesti Page - Validation of Images on links Pages of the Vijesti Page
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Vijesti element for Vijesti Page
    Then I validate images on all the links of the Vijesti Page