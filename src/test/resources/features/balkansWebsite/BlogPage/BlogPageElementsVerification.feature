Feature: As a Balkans Site User I should be able to see and navigate through Blog Page Elements:

  @live
  Scenario: Validation of Blog Page Elements
    Given  I browse webSite using BALKANS_URL url
    When I click the Header_Menu_Blog element for Blog Page
    Then I should see the below mentioned Section items on the Blog Page
      | MainSection | SubSection1 | SubSection2 |
      | Blog        | Popular     | Top_blogeri |