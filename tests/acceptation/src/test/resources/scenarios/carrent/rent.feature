Feature: Car Rental Service can list cities with at least one agency

  Scenario: Listing cities with at least one agency
    Given the car rent service is up
    When the endpoint is '/rent'
      And the select method is GET
      And the request is sent
    Then there is at least one result