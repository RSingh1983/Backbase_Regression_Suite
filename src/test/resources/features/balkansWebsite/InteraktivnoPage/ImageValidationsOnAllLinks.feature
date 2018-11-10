@interaktivnoPage
Feature: Interaktivno Page Links Pages Images Validations

  @interaktivnoPageLinksImages @nightly
  Scenario: Interaktivno Page - Validation of Images on links Pages of the Interaktivno Page
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Interaktivno element for Interaktivno Page
    Then I validate images on all the links of the Interaktivno Page