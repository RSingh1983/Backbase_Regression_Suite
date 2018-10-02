Feature: As a Balkans Site User I should be able to see and navigate through Video Page Elements:

  @live @video
  Scenario: Video Page - Validation of UI Elements
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I click the Header_Menu_Video element for Video Page
    Then I should see the below mentioned Section items on the Video Page
      | MainSection | SubSection1 | SubSection2 | SubSection3 | SubSection4 | SubSection5 | SubSection6 |
      | Video       | BALKAN      | SVIJET      | EKONOMIJA   | SPORT       | GOSTI       | REPORTERI   |
    Then I validate all links and images on the Video Page