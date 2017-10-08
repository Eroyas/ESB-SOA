Feature: Submit a booking

  Background:
    Given Booking: Service available at localhost:8080/tta-booking/booking

  Scenario: Submit booking
    Given Booking: A request with type set to submit
      And Booking: with the booking id set to 12345
      And Booking: with the booking identity set to John Doe john@doe.com
      And Booking: with the booking flight set to AirFrance 447
      And Booking: with the booking car set to Mercedes 90
      And Booking: with the booking hotel set to Hilton 666
    When Booking: the request for submission sent

    Then Booking: there is one booking retrievable with id 12345
      And Booking: the status is WAITING_APPROVAL
      And Booking: the flight is AirFrance 447
      And Booking: the car is Mercedes 90
      And Booking: the identity is John Doe john@doe.com
      And Booking: the hotel is Hilton 666




