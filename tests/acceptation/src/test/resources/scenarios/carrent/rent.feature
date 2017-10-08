Feature: Car Rental Service can list cities with at least one agency

  Scenario: Listing cities with at least one agency
    When the endpoint is '/car-rental/cities'
    And the select method is GET
    And the request is sent
    Then there is at least one result

  Scenario Outline: Listing the car for a given city
    When the endpoint is '/car-rental/search'
    And the city is '<city>'
    And the select method is GET
    And the request is sent
    Then there are <nb_agencies> result
    Examples:
      | city      | nb_agencies |
      | Paris     | 159          |
      | Marseille | 181          |
      | Lyon      | 164          |
      | Toulouse  | 176          |
      | Nice      | 165          |
      | Nantes    | 155          |

  Scenario: Rent a car
    Given the carUID 562
    And the starting date is "2018-01-01"
    And the ending date is "2018-01-10"
    When the endpoint is '/car-rental/rentacar'
    And the select method is POST
    And the request to rent a car is sent
    Then the status reponse is "ok"
    And the total price is 713.52