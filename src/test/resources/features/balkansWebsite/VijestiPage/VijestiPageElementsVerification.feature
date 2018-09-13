Feature: As a Balkans Site User I should be able to see and navigate through Vijesti Menu options:

  @live
  Scenario: Validation of Vijesti Page Elements
    Given  I browse webSite using BALKANS_URL url
    When I hover mouse over Header_Menu_Vijesti element for the Vijesti Page
    Then I should see the below mentioned SideBar Menu items on the Vijesti Page
      | SiderBar_Menu1 | SiderBar_Menu2 | SiderBar_Menu3 | SiderBar_Menu4 | SiderBar_Menu5 | SiderBar_Menu6|
      | Balkan         | Svijet         | KULTURA        | TEHNOLOGIJA    | EKONOMIJA      | SPORT         |
    And I hover mouse over SiderBar_Menu1_Balkan element of Header_Menu_Vijesti, I should see VisitPage_Link1_Balkan link on the Vijesti Page
    And I hover mouse over SiderBar_Menu2_Svijet element of Header_Menu_Vijesti, I should see VisitPage_Link2_Svijet link on the Vijesti Page
    And I hover mouse over SiderBar_Menu3_KULTURA element of Header_Menu_Vijesti, I should see VisitPage_Link3_KULTURA link on the Vijesti Page
    And I hover mouse over SiderBar_Menu4_TEHNOLOGIJA element of Header_Menu_Vijesti, I should see VisitPage_Link4_TEHNOLOGIJA link on the Vijesti Page
    And I hover mouse over SiderBar_Menu5_EKONOMIJA element of Header_Menu_Vijesti, I should see VisitPage_Link5_EKONOMIJA link on the Vijesti Page
    And I hover mouse over SiderBar_Menu6_SPORT element of Header_Menu_Vijesti, I should see VisitPage_Link6_SPORT link on the Vijesti Page