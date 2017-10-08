Feature: Flight reservation Service can list flights going from a city to another on a day (or at least give one flight)


  Background:
    Given The Flight reservation service deployed on localhost:9090

  Scenario: Booking a flight from Paris using a "simple" itinerary request

    Given a customer identified as palatine-234
    And a starting point defined as Paris
    And an ending point defined as Nice
    And a date of departure defined as: 2017-10-25
    And the simple reservation method is selected
    And the service is called
    Then the flight price is 30.0
    And the answer is associated to palatine-234
    And the booking date is set


  Scenario: Booking a flight from Lyon using a "simple" itinerary request

    Given a customer identified as diamond-1
    And a starting point defined as Lyon
    And an ending point defined as Nice
    And a date of departure defined as: 2017-10-25
    And the simple reservation method is selected
    And the service is called
    Then the flight price is 40.0
    And the answer is associated to diamond-1
    And the booking date is set
