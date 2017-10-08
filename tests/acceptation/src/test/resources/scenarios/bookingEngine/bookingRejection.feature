Feature: Reject a booking

  Background:
    Given Booking: Service available at localhost:8080/tta-booking/booking

  Scenario: Reject booking
    Given Booking: A request with type set to submit
    And Booking: with the booking id set to 34567
    And Booking: with the booking identity set to Harvey Specter harvey@specter.psl
    And Booking: with the booking flight set to Emirates 001
    And Booking: with the booking car set to Ferrari 40
    And Booking: with the booking hotel set to NY-PentHouse 999
    When Booking: the request for submission sent

    Given Booking: A request with type set to reject
    And Booking: with the id set to 34567
    Then Booking: the request for rejection sent

    Then Booking: there is one booking retrievable with id 34567
    And Booking: the status is REJECTED
    And Booking: the flight is Emirates 001
    And Booking: the car is Ferrari 40
    And Booking: the identity is Harvey Specter harvey@specter.psl
    And Booking: the hotel is NY-PentHouse 999




