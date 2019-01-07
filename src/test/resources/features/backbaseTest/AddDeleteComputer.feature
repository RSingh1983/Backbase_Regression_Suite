@regression @createComputer
Feature: Create a new Computer item in the Database

  @createSuccess
  Scenario Outline: Successful creation of computer item in the Database
    Given I browse webSite using COMPUTER_DATABASE_WEB_URL url
    And I delete the computer with Computer Name as <ComputerName>
    And I validate 0 computers with name <ComputerName> exist in the system
    When I add a new computer with expected status as <ExpectedStatus> and using below details
      | ComputerName     | <ComputerName>     |
      | IntroducedDate   | <IntroducedDate>   |
      | DiscontinuedDate | <DiscontinuedDate> |
      | Company_Selected | <CompanyName>      |
    And I validate 1 computers with name <ComputerName> exist in the system
    Then I validate that a computer with below mentioned details added in the database
      | ComputerName     | <ComputerName>     |
      | IntroducedDate   | <IntroducedDate>   |
      | DiscontinuedDate | <DiscontinuedDate> |
      | Company          | <CompanyName>      |
    Then I delete the computer with Computer Name as <ComputerName>
    And I validate 0 computers with name <ComputerName> exist in the system
    Examples:
      | ComputerName    | IntroducedDate | DiscontinuedDate | CompanyName | ExpectedStatus |
      | Test Computer 1 | 2019-01-15     | 2020-01-15       | Netronics   | Success        |
      | Test Computer 1 |                | 2020-01-15       | Netronics   | Success        |
      | Test Computer 1 | 2019-01-15     |                  | Netronics   | Success        |
      | Test Computer 1 | 2019-01-15     | 2020-01-15       |             | Success        |

  @createFails
  Scenario Outline: Failed creation of computer item in the Database
    Given I browse webSite using COMPUTER_DATABASE_WEB_URL url
    And I delete the computer with Computer Name as <ComputerName>
    When I add a new computer with expected status as <ExpectedStatus> and using below details
      | ComputerName     | <ComputerName>     |
      | IntroducedDate   | <IntroducedDate>   |
      | DiscontinuedDate | <DiscontinuedDate> |
      | Company_Selected | <CompanyName>      |
    And I validate 0 computers with name <ComputerName> exist in the system
    Examples:
      | ComputerName    | IntroducedDate | DiscontinuedDate | CompanyName | ExpectedStatus |
      |                 | 2019-01-15     | 2020-01-15       | Netronics   | Fail           |
      | Test Computer 1 | 15-01-2019     | 2020-01-15       | Netronics   | Fail           |
      | Test Computer 1 | 2019-01-15     | 15-01-2020       | Netronics   | Fail           |

  @createCancels
  Scenario Outline: Cancel creation of computer item in the Database
    Given I browse webSite using COMPUTER_DATABASE_WEB_URL url
    And I delete the computer with Computer Name as <ComputerName>
    When I add a new computer with expected status as <ExpectedStatus> and using below details
      | ComputerName     | <ComputerName>     |
      | IntroducedDate   | <IntroducedDate>   |
      | DiscontinuedDate | <DiscontinuedDate> |
      | Company_Selected | <CompanyName>      |
    And I validate 0 computers with name <ComputerName> exist in the system
    Examples:
      | ComputerName    | IntroducedDate | DiscontinuedDate | CompanyName | ExpectedStatus |
      | Test Computer 1 | 2019-01-15     | 2020-01-15       | Netronics   | Cancel         |
      | Test Computer 1 |                | 2020-01-15       | Netronics   | Cancel         |
      | Test Computer 1 | 2019-01-15     |                  | Netronics   | Cancel         |
      | Test Computer 1 | 2019-01-15     | 2020-01-15       |             | Cancel         |