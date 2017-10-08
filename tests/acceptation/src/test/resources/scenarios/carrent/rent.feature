Feature: Car Rental Service can list cities with at least one agency

  Scenario: Listing cities with at least one agency
    When the endpoint is '/carrent'
    And the select method is GET
    And the request is sent
    Then there is at least one result

  Scenario Outline: Listing the agencies for several cities
    When the endpoint is '/carrent'
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

  Scenario: Rent a car
    Given the carUID 562
    And the starting date is "2018-01-01"
    And the ending date is "2018-01-10"
    When the endpoint is '/rentacar'
    And the select method is POST
    And the request to rent a car is sent
    Then the status reponse is "ok"
    And the total price is 2378.4