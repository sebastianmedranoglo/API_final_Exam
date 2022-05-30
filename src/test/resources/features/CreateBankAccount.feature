# POC Based on mock service simulating a BankAccount Service

# User creation
Feature: POC Example - Put actions

  # Create a BankAccount based on a String - Request body
  Scenario: Create a new bank transaction based on body request
    When I create a new bank transaction using POST request body string "{\"name\":\"Sebastian\",\"lastName\":\"Medrano\",\"accountNumber\":454545,\"amount\":80.5,\"transactionType\":\"deposit\",\"email\":\"rvtmappeji@hotmail.com\",\"active\":false,\"country\":\"Colombia\",\"telephone\":\"3002957845\",\"id\":\"5\"}"
    Then I get the response code equals to 201


