@regression @blogPage
Feature: Blog Page Links and UI Elements Validations

  @blogPageUIElements
  Scenario: Blog Page - Validation of UI Elements
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Blog element for Blog Page
    Then I should see the below mentioned Section items on the Blog Page
      | MainSection |  SubSection2 |
      | Blog        |  Top_blogeri |
    Then I validate all links and images on the Blog Page