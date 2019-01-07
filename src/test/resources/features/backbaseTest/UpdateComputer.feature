@regression @updateComputer
Feature: Update Computer item in the Database

  @updateSuccess
  Scenario Outline: Successful update of computer item in the Database
    Given I browse webSite using COMPUTER_DATABASE_WEB_URL url
    And I delete the computer with Computer Name as <InitialComputerName>
    And I delete the computer with Computer Name as <UpdatedComputerName>
    And I validate 0 computers with name <InitialComputerName> exist in the system
    And I validate 0 computers with name <UpdatedComputerName> exist in the system
    When I add a new computer with expected status as <InitialExpectedStatus> and using below details
      | ComputerName     | <InitialComputerName>     |
      | IntroducedDate   | <InitialIntroducedDate>   |
      | DiscontinuedDate | <InitialDiscontinuedDate> |
      | Company_Selected | <InitialCompanyName>      |
    Then I validate 1 computers with name <InitialComputerName> exist in the system
    When I update computer <InitialComputerName> with expected status as <UpdatedExpectedStatus> and using below details
      | ComputerName     | <UpdatedComputerName>     |
      | IntroducedDate   | <UpdatedIntroducedDate>   |
      | DiscontinuedDate | <UpdatedDiscontinuedDate> |
      | Company_Selected | <UpdatedCompanyName>      |
    And I validate 0 computers with name <InitialComputerName> exist in the system
    And I validate 1 computers with name <UpdatedComputerName> exist in the system
    Then I validate that a computer with below mentioned details added in the database
      | ComputerName     | <UpdatedComputerName>     |
      | IntroducedDate   | <UpdatedIntroducedDate>   |
      | DiscontinuedDate | <UpdatedDiscontinuedDate> |
      | Company          | <UpdatedCompanyName>      |
    Then I delete the computer with Computer Name as <UpdatedComputerName>
    And I validate 0 computers with name <InitialComputerName> exist in the system
    And I validate 0 computers with name <UpdatedComputerName> exist in the system
    Examples:
      | InitialComputerName | InitialIntroducedDate | InitialDiscontinuedDate | InitialCompanyName | InitialExpectedStatus | UpdatedComputerName | UpdatedIntroducedDate | UpdatedDiscontinuedDate | UpdatedCompanyName | UpdatedExpectedStatus |
      | Test Computer 1     | 2019-01-15            | 2020-01-15              | Netronics          | Success               | Test Computer 2     | 2019-02-25            | 2020-02-25              | ASUS               | Success               |
      | Test Computer 1     |                       | 2020-01-15              | Netronics          | Success               | Test Computer 2     |                       | 2020-02-25              | ASUS               | Success               |
      | Test Computer 1     | 2019-01-15            |                         | Netronics          | Success               | Test Computer 2     | 2019-02-25            |                         | ASUS               | Success               |
      | Test Computer 1     | 2019-01-15            | 2020-01-15              |                    | Success               | Test Computer 2     | 2019-02-25            | 2020-02-25              |                    | Success               |

  @updateFail
  Scenario Outline: Fail update of computer item in the Database
    Given I browse webSite using COMPUTER_DATABASE_WEB_URL url
    And I delete the computer with Computer Name as <InitialComputerName>
    And I delete the computer with Computer Name as <UpdatedComputerName>
    And I validate 0 computers with name <InitialComputerName> exist in the system
    And I validate 0 computers with name <UpdatedComputerName> exist in the system
    When I add a new computer with expected status as <InitialExpectedStatus> and using below details
      | ComputerName     | <InitialComputerName>     |
      | IntroducedDate   | <InitialIntroducedDate>   |
      | DiscontinuedDate | <InitialDiscontinuedDate> |
      | Company_Selected | <InitialCompanyName>      |
    Then I validate 1 computers with name <InitialComputerName> exist in the system
    When I update computer <InitialComputerName> with expected status as <UpdatedExpectedStatus> and using below details
      | ComputerName     | <UpdatedComputerName>     |
      | IntroducedDate   | <UpdatedIntroducedDate>   |
      | DiscontinuedDate | <UpdatedDiscontinuedDate> |
      | Company_Selected | <UpdatedCompanyName>      |
    And I validate 1 computers with name <InitialComputerName> exist in the system
    And I validate 0 computers with name <UpdatedComputerName> exist in the system
    Then I delete the computer with Computer Name as <InitialComputerName>
    And I validate 0 computers with name <InitialComputerName> exist in the system
    And I validate 0 computers with name <UpdatedComputerName> exist in the system
    Examples:
      | InitialComputerName | InitialIntroducedDate | InitialDiscontinuedDate | InitialCompanyName | InitialExpectedStatus | UpdatedComputerName | UpdatedIntroducedDate | UpdatedDiscontinuedDate | UpdatedCompanyName | UpdatedExpectedStatus |
      | Test Computer 1     | 2019-01-15            | 2020-01-15              | Netronics          | Success               |                     | 2019-02-25            | 2020-02-25              | ASUS               | Fail                  |
      | Test Computer 1     |                       | 2020-01-15              | Netronics          | Success               | Test Computer 2     | 25-02-2019            | 2020-02-25              | ASUS               | Fail               |
      | Test Computer 1     | 2019-01-15            |                         | Netronics          | Success               | Test Computer 2     | 2019-02-25            | 25-02-2020              | ASUS               | Fail               |

  @updateCancels
  Scenario Outline: Cancel update of computer item in the Database
    Given I browse webSite using COMPUTER_DATABASE_WEB_URL url
    And I delete the computer with Computer Name as <InitialComputerName>
    And I delete the computer with Computer Name as <UpdatedComputerName>
    Then I validate 0 computers with name <InitialComputerName> exist in the system
    And I validate 0 computers with name <UpdatedComputerName> exist in the system
    When I add a new computer with expected status as <InitialExpectedStatus> and using below details
      | ComputerName     | <InitialComputerName>     |
      | IntroducedDate   | <InitialIntroducedDate>   |
      | DiscontinuedDate | <InitialDiscontinuedDate> |
      | Company_Selected | <InitialCompanyName>      |
    Then I validate 1 computers with name <InitialComputerName> exist in the system
    When I update computer <InitialComputerName> with expected status as <UpdatedExpectedStatus> and using below details
      | ComputerName     | <UpdatedComputerName>     |
      | IntroducedDate   | <UpdatedIntroducedDate>   |
      | DiscontinuedDate | <UpdatedDiscontinuedDate> |
      | Company_Selected | <UpdatedCompanyName>      |
    And I validate 1 computers with name <InitialComputerName> exist in the system
    And I validate 0 computers with name <UpdatedComputerName> exist in the system
    Then I delete the computer with Computer Name as <InitialComputerName>
    And I validate 0 computers with name <InitialComputerName> exist in the system
    And I validate 0 computers with name <UpdatedComputerName> exist in the system
    Examples:
      | InitialComputerName | InitialIntroducedDate | InitialDiscontinuedDate | InitialCompanyName | InitialExpectedStatus | UpdatedComputerName | UpdatedIntroducedDate | UpdatedDiscontinuedDate | UpdatedCompanyName | UpdatedExpectedStatus |
      | Test Computer 1     | 2019-01-15            | 2020-01-15              | Netronics          | Success               | Test Computer 2     | 2019-02-25            | 2020-02-25              | ASUS               | Cancel                |
      | Test Computer 1     |                       | 2020-01-15              | Netronics          | Success               | Test Computer 2     |                       | 2020-02-25              | ASUS               | Cancel                |
      | Test Computer 1     | 2019-01-15            |                         | Netronics          | Success               | Test Computer 2     | 2019-02-25            |                         | ASUS               | Cancel                |
      | Test Computer 1     | 2019-01-15            | 2020-01-15              |                    | Success               | Test Computer 2     | 2019-02-25            | 2020-02-25              |                    | Cancel                |
