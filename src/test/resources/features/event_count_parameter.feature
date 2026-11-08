@popular-coupons @query-param
Feature: eventCount query parameter
  eventCount filters coupons by number of events: 1-4 exact match, 5 means five or more, 0 means all.

  Scenario Outline: Coupons are filtered by an exact event count of <eventCount>
    When I request popular coupons with eventCount "<eventCount>"
    Then the response status code should be 200
    And the response should match the "popular-coupons" schema
    And every coupon should have exactly <eventCount> events

    Examples:
      | eventCount |
      | 1          |
      | 2          |
      | 3          |
      | 4          |

  Scenario: eventCount 5 returns coupons with five or more events
    When I request popular coupons with eventCount "5"
    Then the response status code should be 200
    And the response should match the "popular-coupons" schema
    And every coupon should have at least 5 events

  Scenario: Coupons are listed when eventCount is not sent
    When I request popular coupons without eventCount
    Then the response status code should be 200
    And the response should match the "popular-coupons" schema
    And the coupon list should not be empty

  @negative
  Scenario Outline: Out of range eventCount "<eventCount>" returns a business error
    When I request popular coupons with eventCount "<eventCount>"
    Then the response status code should be 200
    And the response should match the "error-response" schema
    And the response should contain error code 101 with body status 400

    Examples:
      | eventCount |
      | -1         |
      | 6          |
      | 100        |
      | 2147483647 |

  @negative
  Scenario Outline: Malformed eventCount "<eventCount>" is rejected
    When I request popular coupons with eventCount "<eventCount>"
    Then the response status code should be 400

    Examples:
      | eventCount |
      | abc        |
      | 1.5        |
      |            |
      | 2147483648 |
