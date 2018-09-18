Feature: As a Balkans Site User I should be able to see and navigate through Vrijeme Page Elements:

  @live
  Scenario: Vrijeme Page: Validation of UI Elements
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Vrijeme element for Vrijeme Page
    Then I should see the below mentioned Section items on the Vrijeme Page
      | MainSection | SubSection1       |
      | Vrijeme     | Najnovije_vijesti |