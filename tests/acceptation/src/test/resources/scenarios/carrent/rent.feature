Feature: Car Rental Service can list cities with at least one agency

  Scenario: Listing cities with at least one agency
    When the endpoint is '/rent'
    And the select method is GET
    And the request is sent
    Then there is at least one result

  Scenario Outline: Listing the agencies for several cities
    When the endpoint is '/rent'
    And the city is '<city>'
    And the select method is GET
    And the request is sent
    Then there are <nb_agencies> result
    Examples:
      | city      | nb_agencies |
      | Paris     | 34          |
      | Marseille | 40          |
      | Lyon      | 33          |
      | Toulouse  | 25          |
      | Nice      | 36          |
      | Nantes    | 35          |