Feature: Hotel reservation service can list all hotels available

  Scenario: Listing all hotels available
    Given the getAll hotel reservation service is up
    When the getAll hotel endpoint is '/hotels'
    And the getAll hotel select method is GET
    And the getAll hotel request is sent
    Then there is at least one result for getAll hotel