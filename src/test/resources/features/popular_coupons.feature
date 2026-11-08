@popular-coupons
Feature: Popular coupons
  GET /v1/PopularCoupons returns the most played coupons.

  @smoke
  Scenario: Popular coupons are listed successfully
    When I request popular coupons with eventCount "0"
    Then the response status code should be 200
    And the response should match the "popular-coupons" schema
    And the response should be mapped to the coupon model without errors
    And the coupon list should not be empty

  Scenario: Event count of each coupon matches its event list
    When I request popular coupons with eventCount "0"
    Then the event count of each coupon should match the number of its events

  Scenario: Minimum odd is never greater than maximum odd
    When I request popular coupons with eventCount "0"
    Then no event should have a minimum odd greater than its maximum odd

  Scenario Outline: Event type is consistent with sport id for eventCount <eventCount>
    When I request popular coupons with eventCount "<eventCount>"
    Then event types should match their sport ids
      | Football   | 1       |
      | Basketball | 2       |

    Examples:
      | eventCount |
      | 0          |
      | 1          |
      | 2          |
      | 3          |
      | 4          |
      | 5          |

  Scenario: Coupons are ordered by popularity
    When I request popular coupons with eventCount "0"
    Then coupons should be sorted by play count in descending order
    And order indexes should be sequential starting from 1

  Scenario: Coupons in the list are unique
    When I request popular coupons with eventCount "0"
    Then coupon ids should be unique

  Scenario: Minimum event date of a coupon is its earliest event start
    When I request popular coupons with eventCount "0"
    Then the minimum event date of each coupon should equal its earliest event start date
