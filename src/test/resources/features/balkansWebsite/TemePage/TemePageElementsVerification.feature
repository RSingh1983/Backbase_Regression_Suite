Feature: As a Balkans Site User I should be able to see and navigate through Teme Page Elements:

  @live @temePage
  Scenario: Teme Page - Validation of UI Elements
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Teme element for Teme Page
    Then I should see the below mentioned Section items on the Teme Page
      | MainSection | SubSection1 | SubSection2 | SubSection3 | SubSection4 |
      | TEME        | SVE         | Članci      | Video       | Blog        |
    Then I validate all links and images on the Teme Page
