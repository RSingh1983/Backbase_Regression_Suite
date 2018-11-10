@videoPage
Feature: Video Page Links Pages Images Validations

  @videoPageLinksImages @nightly
  Scenario: Video Page - Validation of Images on links Pages of the Video Page
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Video element for Video Page
    Then I validate images on all the links of the Video Page