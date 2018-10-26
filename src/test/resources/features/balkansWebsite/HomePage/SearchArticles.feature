@regression @homePage
Feature: Home Page Search Articles

  @homePageSearchArticles
  Scenario Outline: Home Page - Search For Articles on the Home Page
    Given I browse webSite using BALKANS_URL url of Home Page
    Then I search articles from section <articleSection> and validate search results on the Home Page
    Examples:
      | articleSection                |
      | Top_Left_Panel_New_Stories                   |
#      | MainArticle                   |
#      | General_HomePage_Featured     |
#      | Interative_Home_Page_Featured |
#      | Blog                          |
#      | MIŠLJENJA                     |
#      | INTERAKTIVNO                  |
#      | PROGRAMI                      |
#      | SPORT                         |
#      | EKONOMIJA                     |
#      | KULTURA                       |
#      | TEHNOLOGIJA                   |
#      | IZDVOJENI VIDEO               |
#      | NAJNOVIJI VIDEO               |
#      | FOTOGALERIJA                  |
