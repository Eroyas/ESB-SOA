Feature: Approve a booking

  Background:
    Given Booking: Service available at localhost:8080/tta-booking/booking

  Scenario: Approve booking
    Given Booking: A request with type set to submit
    And Booking: with the booking id set to 23456
    And Booking: with the booking identity set to Foo Bar foobar@2000.com
    And Booking: with the booking flight set to AirCanada 893
    And Booking: with the booking car set to Porsche 911
    And Booking: with the booking hotel set to Radison 765
    When Booking: the request for submission sent

    Given Booking: A request with type set to validate
    And Booking: with the id set to 23456
    Then Booking: the request for approval sent

    Then Booking: there is one booking retrievable with id 23456
    And Booking: the status is APPROVED
    And Booking: the flight is AirCanada 893
    And Booking: the car is Porsche 911
    And Booking: the identity is Foo Bar foobar@2000.com
    And Booking: the hotel is Radison 765




