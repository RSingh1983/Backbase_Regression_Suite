Feature: As a Balkans Site User I should be able to Navigate through following details on the Home Page:
  1. All the Menu Items.
  2. All the articles and blogs.
  3. All the footer elements.

  @live @homePage
  Scenario: Home Page: Validation of UI Elements
    Given  I browse webSite using BALKANS_URL url of Home Page
    Then I should see the below mentioned Header Menu items on the Landing Page
      | Header_Menu1 | Header_Menu2 | Header_Menu3 | Header_Menu4 | Header_Menu5 | Header_Menu6 | Header_Menu7 | Header_Menu8 | Header_Menu9 |
      | Vijesti      | Teme         | Mišljenja    | Programi     | Video        | Blog         | Interaktivno | Vrijeme      | Uživo        |
    Then I should see the below mentioned Header Social Media items on the Landing Page
      | Header1  | Header2 | Header3     | Header4   | Header5  | Header6 | Header7 | Header8     |
      | Facebook | Twitter | Google_Plus | Instagram | Linkedin | Youtube | RSS     | Dailymotion |
    Then I should see the below mentioned Aritcles & Sections items on the Landing Page
      | Link      | SideBar      | Article1 | Article2   | Section1 | Section2  | Section3 | Section4    | Section5        |
      | POPULARNO | NOVI_NASLOVI | InCenter | InTopRight | Programi | EKONOMIJA | KULTURA  | TEHNOLOGIJA | Izdvojeni_video |
    Then I should see the below mentioned Sticky Header after Scrolling items on the Landing Page
      | Sticky_Header_Menu1 | Sticky_Header_Menu2 | Sticky_Header_Menu3 | Sticky_Header_Menu4 | Sticky_Header_Menu5 | Sticky_Header_Menu6 | Sticky_Header_Menu7 | Sticky_Header_Menu8 | Sticky_Header_Menu9 |
      | Vijesti             | Teme                | Mišljenja           | Programi            | Video               | Blog                | Interaktivno        | Vrijeme             | Uživo               |
    Then I should see the below mentioned Footer items on the Landing Page
      | Footer_Menu1 | Footer_Menu2   | Footer_Menu3       |
      | Al_Jazeera   | Mobilne_usluge | Korisnička_pravila |
    Then I should see the below mentioned Footer Section-1 items on the Landing Page
      | Footer_Section1 | Footer_Section1_SubMenu1 | Footer_Section1_SubMenu2 | Footer_Section1_SubMenu3 | Footer_Section1_SubMenu4 | Footer_Section1_SubMenu5 | Footer_Section1_SubMenu6 |
      | O_nama          | O_nama                   | Kako_nas_gledati         | Odredbe_i_uvjeti         | Politika_Privatnosti     | Politika_kolačića        | Postavke_kolačića        |
    Then I should see the below mentioned Footer Section-2 items on the Landing Page
      | Footer_Section2 | Footer_Section2_SubMenu1 | Footer_Section2_SubMenu2 | Footer_Section2_SubMenu3 | Footer_Section2_SubMenu4 | Footer_Section2_SubMenu5 | Footer_Section2_SubMenu6 | Footer_Section2_SubMenu7 | Footer_Section2_SubMenu8 |
      | Naši_kanali     | Al_Jazeera_Arabic        | Al_Jazeera_English       | Al_Jazeera_Mubasher      | Al_Jazeera_Dokumentarni  | Al_Jazeera_Balkans       | Al_Jazeera_Turk          | AJ_+                     | AJ+_Arabi                |
    Then I should see the below mentioned Footer Section-3 items on the Landing Page
      | Footer_Section3 | Footer_Section3_SubMenu1      | Footer_Section3_SubMenu2      | Footer_Section3_SubMenu3                                | Footer_Section3_SubMenu4 |
      | Naša_mreža      | Al_Jazeerin_Centar_za_studije | Al_Jazeerin_Medijski_institut | Al_Jazeerin_Centar_za_ljudska_prava_i_građanske_slobode | Al_Jazeera_Forum         |
