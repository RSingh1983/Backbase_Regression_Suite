@temePage
Feature: Teme Page Links Pages Images Validations

  @temePageLinksImages @nightly
  Scenario: Teme Page - Validation of Images on links Pages of the Teme Page
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Teme element for Teme Page
    Then I validate images on all the links of the Teme Page