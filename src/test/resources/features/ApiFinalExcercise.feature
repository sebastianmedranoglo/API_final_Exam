# POC Based on mock service simulating a BankAccount Service

# API test
Feature: POC Example - API Final Exercise tests

  Scenario: Verify the endpoint is empty
    Given I get the response from the endpoint "https://628d251da3fd714fd03fffa2.mockapi.io/bank/api/v1/account"
    When I DELETE all of them if exist
    And  I get the response from the endpoint "https://628d251da3fd714fd03fffa2.mockapi.io/bank/api/v1/account"
    Then I get the response code equals to 200
    And I shouldn't see any bank account


  Scenario: Initialize POJO with 10 random data
    Given I get the response from the endpoint "https://628d251da3fd714fd03fffa2.mockapi.io/bank/api/v1/account"
    When I POST new bank accounts with random data, avoiding duplicated email
      | id | name | lastName | accountNumber  | amount | transactionType | email  | active | country | telephone |
      | 1 | Sebastian | Medrano | 1454545452 | 80.5 | deposit | @gmail.com | false | Colombia | 3002957845|
      | 2 | Sebastian | Medrano | 1454545452 | 80.5 | deposit | @hotmail.com | false | Colombia | 3002957845|
      | 3 | Sebastian | Medrano | 1454545452 | 80.5 | deposit | @globant.com | false | Colombia | 3002957845|
      | 4 | Sebastian | Medrano | 1454545452 | 80.5 | deposit | @gmail.com | false | Colombia | 3002957845|
      | 5 | Sebastian | Medrano | 1454545452 | 80.5 | deposit | @hotmail.com | false | Colombia | 3002957845|
      | 6 | Sebastian | Medrano | 1454545452 | 80.5 | deposit | @globant.com | false | Colombia | 3002957845|
      | 7 | Sebastian | Medrano | 1454545452 | 80.5 | deposit | @gmail.com | false | Colombia | 3002957845|
      | 8 | Sebastian | Medrano | 1454545452 | 80.5 | deposit | @hotmail.com | false | Colombia | 3002957845|
      | 9 | Sebastian | Medrano | 1454545452 | 80.5 | deposit | @globant.com | false | Colombia | 3002957845|
      | 10 | Sebastian | Medrano | 1454545452 | 80.5 | deposit | @globant.com | false | Colombia | 3002957845|
    Then I get the response code equals to 201

    Scenario: Not duplicate email accounts
      Given I get the response from the endpoint "https://628d251da3fd714fd03fffa2.mockapi.io/bank/api/v1/account"
      When I DELETE duplicate email accounts
      When I get the response from the endpoint "https://628d251da3fd714fd03fffa2.mockapi.io/bank/api/v1/account"
      Then I shouldn't see any duplicated email accounts

    Scenario: Update existing account number
      Given I get the response from the endpoint "https://628d251da3fd714fd03fffa2.mockapi.io/bank/api/v1/account"
      When I UPDATE account number 454545 and id 5
      When I get the response from the endpoint "https://628d251da3fd714fd03fffa2.mockapi.io/bank/api/v1/account"
      Then I should see account number 454545 and id 5
