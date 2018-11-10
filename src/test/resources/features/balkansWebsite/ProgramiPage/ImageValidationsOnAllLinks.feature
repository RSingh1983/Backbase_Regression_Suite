@programiPage
Feature: Home Page Links Pages Images Validations

  @programiPageLinksImages @nightly
  Scenario: Programi Page - Validation of Images on links Pages of the Programi Page
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Programi element for Programi Page
    Then I validate images on all the links of the Programi Page