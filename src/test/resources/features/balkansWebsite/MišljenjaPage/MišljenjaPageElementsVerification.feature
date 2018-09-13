Feature: As a Balkans Site User I should be able to see and navigate through Mišljenja Page elements:

  @live
  Scenario: Validation of Mišljenja Page Elements
    Given  I browse webSite using BALKANS_URL url
    When I click the Header_Menu_Mišljenja element for Mišljenja Page
    Then I should see the below mentioned Section items on the Mišljenja Page
      | MainSection | SubSection1 | SubSection2       |
      | Mišljenja   | Top_autori  | Najnovije_vijesti |