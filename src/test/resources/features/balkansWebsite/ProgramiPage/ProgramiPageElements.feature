@regression @programiPage
Feature: Programi Page Links and UI Elements Validations

  @programiPageUIElements
  Scenario: Programi Main Page - Validation of UI Elements
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I hover mouse over Header_Menu_Programi element for the Programi Page
    Then I should see the below mentioned SideBar Menu items on the Programi Page
      | SiderBar_Menu1      | SiderBar_Menu2 | SiderBar_Menu3 | SiderBar_Menu4 | SiderBar_Menu5 | SiderBar_Menu6    | SiderBar_Menu7 |
      | AL_JAZEERA_OBJEKTIV | DOKUMENTARCI   | KONTEKST       | NOVE_EMISIJE   | ONI_POBJEĐUJU  | RECITE_AL_JAZEERI | KONTRASTI      |
    And I hover mouse over SiderBar_Menu1_AL_JAZEERA_OBJEKTIV element of Header_Menu_Programi, I should see VisitPage_Link1_Više_KONTRASTI link on the Programi Page
    And I hover mouse over SiderBar_Menu2_DOKUMENTARCI element of Header_Menu_Programi, I should see VisitPage_Link2_Više_DOKUMENTARCI link on the Programi Page
    And I hover mouse over SiderBar_Menu3_KONTEKST element of Header_Menu_Programi, I should see VisitPage_Link3_Više_KONTEKST link on the Programi Page
    And I hover mouse over SiderBar_Menu4_NOVE_EMISIJE element of Header_Menu_Programi, I should see VisitPage_Link4_Više_NOVE_EMISIJE link on the Programi Page
    And I hover mouse over SiderBar_Menu5_ONI_POBJEĐUJU element of Header_Menu_Programi, I should see VisitPage_Link5_Više_ONI_POBJEĐUJU link on the Programi Page
    And I hover mouse over SiderBar_Menu6_RECITE_AL_JAZEERI element of Header_Menu_Programi, I should see VisitPage_Link6_Više_RECITE_AL_JAZEERI link on the Programi Page
    And I hover mouse over SiderBar_Menu7_KONTRASTI element of Header_Menu_Programi, I should see VisitPage_Link7_Više_KONTRASTI link on the Programi Page

  @programiPageUIElements
  Scenario Outline: Programi All Pages - Validation of UI Elements
    Given  I browse webSite using BALKANS_URL url of Home Page
    When I hover mouse over Header_Menu_Programi element for the Programi Page
    And I click the <SideBarMenu> element for Programi Page
    Then I validate all links and images on the Programi Page <SideBarMenu>
    Examples:
      | SideBarMenu                        |
      | SiderBar_Menu1_AL_JAZEERA_OBJEKTIV |
      | SiderBar_Menu2_DOKUMENTARCI        |
      | SiderBar_Menu3_KONTEKST            |
      | SiderBar_Menu4_NOVE_EMISIJE        |
      | SiderBar_Menu5_ONI_POBJEĐUJU       |
      | SiderBar_Menu6_RECITE_AL_JAZEERI   |
      | SiderBar_Menu7_KONTRASTI           |