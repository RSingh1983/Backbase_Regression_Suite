@regression @uživoPage
Feature: Uživo Page Links and UI Elements Validations

  @uživoPageUIElements
  Scenario: Uživo Page - Validation of UI Elements
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Uživo element for Uživo Page
    Then I should see the below mentioned Section items on the Uživo Page
      | MainSection | SubSection1   | SubSection2     | SubSection3 |
      | Uživo       | Recent_Videos | Popularna_videa | Programi    |
#    Then I validate all links and images on the Uživo Page
    Then I validate live video is visible and plays on the Uživo Page