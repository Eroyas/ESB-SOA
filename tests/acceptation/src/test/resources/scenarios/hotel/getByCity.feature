Feature: Hotel reservation service can list all hotels available by city

  Scenario: Listing all hotels available in Paris
    Given the getByCity hotel reservation service is up
    When the getByCity hotel endpoint is '/hotels/city/Paris'
    And the getByCity hotel select method is GET
    And the getByCity hotel request is sent
    Then there is at least one result for getByCity hotel