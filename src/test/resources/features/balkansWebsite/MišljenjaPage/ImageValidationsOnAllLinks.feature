@mišljenjaPage
Feature: Mišljenja Page Links Pages Images Validations

  @mišljenjaPageLinksImages @nightly
  Scenario: Mišljenja Page - Validation of Images on links Pages of the Mišljenja Page
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Mišljenja element for Mišljenja Page
    Then I validate images on all the links of the Mišljenja Page