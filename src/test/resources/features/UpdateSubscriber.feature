# POC Based on mock service simulating a Subscription Service

# Update information from API
Feature: POC Example - Update actions


  # UPDATE User by data table
  Scenario: Update User based by id with data table information
    Given I get the response from the endpoint
    When I get the response code equals to 200
    Then I UPDATE the bank transaction by id with information
      | id | name | lastName | accountNumber  | amount | transactionType | email  | active | country | telephone |
      | 10 | Sebastian | Medrano | 1454545452 | 80.5 | deposit | fsdfdsffdsfsdf@globant.com | false | Colombia | 3002957845|