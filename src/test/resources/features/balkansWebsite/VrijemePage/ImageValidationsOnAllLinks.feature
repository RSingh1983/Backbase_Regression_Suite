@vrijemePage
Feature: Vrijeme Page Links Pages Images Validations

  @vrijemePageLinksImages @nightly
  Scenario: Vrijeme Page - Validation of Images on links Pages of the Vrijeme Page
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Vrijeme element for Vrijeme Page
    Then I validate images on all the links of the Vrijeme Page