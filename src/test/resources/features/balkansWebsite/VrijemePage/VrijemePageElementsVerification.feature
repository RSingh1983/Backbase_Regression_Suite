@regression @vrijemePage
Feature: Vrijeme Page Links and UI Elements Validations

  @vrijemePageUIElements
  Scenario: Vrijeme Page - Validation of UI Elements
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Vrijeme element for Vrijeme Page
    Then I should see the below mentioned Section items on the Vrijeme Page
      | MainSection | SubSection1       |
      | Vrijeme     | Najnovije_vijesti |
    Then I validate all links and images on the Vrijeme Page