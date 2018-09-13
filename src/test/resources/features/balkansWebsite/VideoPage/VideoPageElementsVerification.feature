Feature: As a Balkans Site User I should be able to see and navigate through Video Page Elements:

  @live
  Scenario: Validation of Video Page Elements
    Given  I browse webSite using BALKANS_URL url
    When I click the Header_Menu_Video element for Video Page
    Then I should see the below mentioned Section items on the Video Page
      | MainSection | SubSection1 | SubSection2 | SubSection3 | SubSection4 | SubSection5 | SubSection6 |
      | Video       | BALKAN      | SVIJET      | EKONOMIJA   | SPORT       | GOSTI       | REPORTERI   |