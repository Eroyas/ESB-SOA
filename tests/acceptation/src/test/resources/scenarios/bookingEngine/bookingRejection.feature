Feature: Reject a booking

  Background:
    Given Service available at localhost:8080/tta-booking/booking

  Scenario: Reject booking
    Given A request with type set to submit
    And with the booking id set to 34567
    And with the booking identity set to Harvey Specter harvey@specter.psl
    And with the booking flight set to Emirates 001
    And with the booking car set to Ferrari 40
    And with the booking hotel set to NY-PentHouse 999
    When the request for submission sent

    Given A request with type set to reject
    And with the id set to 34567
    Then the request for rejection sent

    Then there is one booking retrievable with id 34567
    And the status is REJECTED
    And the flight is Emirates 001
    And the car is Ferrari 40
    And the identity is Harvey Specter harvey@specter.psl
    And the hotel is NY-PentHouse 999




