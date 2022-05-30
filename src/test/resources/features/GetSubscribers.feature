 #POC Based on mock service simulating a Subscription Service

# Get information from API
Feature: POC Example - Get actions

  # Get request scenario using a implicit endpoint
  @myScenario
  Scenario: Testing an Endpoint - Get action using JSON resource
    Given I get the response from the endpoint
    Then I get the response code equals to 200

  # Get request scenario using endpoint by parameter
  Scenario: Testing an Endpoint - Get action using string parameter
    Given I get the response from the endpoint "https://628d251da3fd714fd03fffa2.mockapi.io/bank/api/v1/account"
    Then I get the response code equals to 200

  # Get request scenario using endpoint by parameter
  Scenario: Testing an Endpoint - Get action using string parameter by resource
    Given I get the endpoint by resource "bankAccount_endpoint"
    Then I get the response code equals to 200

  # Get request scenario using data table by parameter
  Scenario Outline: Testing an Endpoint - Get action using example table
    Given I get the response from the endpoint <Endpoint>
    Then I get the response code equals to <Status>

    Examples:
      | Endpoint                                                  | Status |
      | "https://628d251da3fd714fd03fffa2.mockapi.io/bank/api/v1/account" | 200    |
      | "https://628d251da3fd714fd03fffa2.mockapi.io/bank/api/v1/account" | 200    |
      | "https://628d251da3fd714fd03fffa2.mockapi.io/bank/api/v1/account" | 200    |


  # Get request scenario using data table by parameter with key
  Scenario Outline: Testing an Endpoint - Get action using example table with key
    Given I get the response from the endpoint file with key <Key>
    Then I get the response code equals to <Status>

    Examples:
      | Key            | Status |
      | "bankAccount_endpoint"  | 200    |
      | "bankAccount_endpoint" | 200    |
      | "bankAccount_endpoint" | 200    |


