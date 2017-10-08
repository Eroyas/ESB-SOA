Feature: Submit a booking

  Background:
    Given Service available at localhost:8080/tta-booking/booking

  Scenario: Submit booking
    Given A request with type set to submit
      And with the booking id set to 12345
      And with the booking identity set to John Doe john@doe.com
      And with the booking flight set to AirFrance 447
      And with the booking car set to Mercedes 90
      And with the booking hotel set to Hilton 666
    When the request for submission sent

    Then there is one booking retrievable with id 12345
      And the status is WAITING_APPROVAL
      And the flight is AirFrance 447
      And the car is Mercedes 90
      And the identity is John Doe john@doe.com
      And the hotel is Hilton 666




