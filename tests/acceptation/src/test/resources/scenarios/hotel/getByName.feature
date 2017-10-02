Feature: Hotel reservation service can list all hotels available by name

  Scenario: Listing all Ibis hotels available
    Given the getByName hotel reservation service is up
    When the getByName hotel hotel endpoint is '/hotels/name/Ibis'
    And the getByName hotel select method is GET
    And the getByName hotel request is sent
    Then there is at least one result for getByName hotel