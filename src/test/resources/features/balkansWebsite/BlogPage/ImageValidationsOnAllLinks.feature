@blogPage
Feature: Interaktivno Page Links Pages Images Validations

  @blogPageLinksImages @nightly
  Scenario: Blog Page - Validation of Images on links Pages of the Blog Page
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Blog element for Blog Page
    Then I validate images on all the links of the Blog Page