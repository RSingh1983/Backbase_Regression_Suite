Feature: As a Balkans Site User I should be able to see and navigate through Interaktivno Page Elements:

  @live
  Scenario: Interaktivno Page - Validation of UI Elements
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Interaktivno element for Interaktivno Page
    Then I should see the below mentioned Section items on the Interaktivno Page
      | MainSection  | SubSection1       |
      | Interaktivno | Najnovije_vijesti |
    Then I validate all links and images on the Interaktivno Page