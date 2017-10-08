Feature: Approve a booking

  Background:
    Given Service available at localhost:8080/tta-booking/booking

  Scenario: Approve booking
    Given A request with type set to submit
    And with the booking id set to 23456
    And with the booking identity set to Foo Bar foobar@2000.com
    And with the booking flight set to AirCanada 893
    And with the booking car set to Porsche 911
    And with the booking hotel set to Radison 765
    When the request for submission sent

    Given A request with type set to validate
    And with the id set to 23456
    Then the request for approval sent

    Then there is one booking retrievable with id 23456
    And the status is APPROVED
    And the flight is AirCanada 893
    And the car is Porsche 911
    And the identity is Foo Bar foobar@2000.com
    And the hotel is Radison 765




