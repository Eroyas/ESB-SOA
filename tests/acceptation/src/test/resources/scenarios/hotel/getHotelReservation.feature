Feature: Hotel reservation service can list an hotel for an reservation

  Scenario: Listing the hotel for an customer reservation in Paris for an Ibis hotel between the 01-10-2017 to 03-10-2017
    Given the getHotelReservation hotel reservation service is up
    When the getHotelReservation hotel hotel endpoint is '/hotels/Paris/Ibis/01-10-2017/03-10-2017'
    And the getHotelReservation hotel select method is GET
    And the getHotelReservation hotel request is sent
    Then there is at least one result for getHotelReservation hotel