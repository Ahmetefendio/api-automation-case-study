package com.nesine.api.steps;

import com.nesine.api.model.Coupon;
import com.nesine.api.model.Event;
import com.nesine.api.support.ScenarioContext;
import io.cucumber.java.en.Then;
import org.assertj.core.api.SoftAssertions;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CouponSteps {

    private final ScenarioContext context;

    public CouponSteps(ScenarioContext context) {
        this.context = context;
    }

    @Then("the response should be mapped to the coupon model without errors")
    public void verifyMapping() {
        assertThat(context.body().getSc()).isEqualTo(200);
        assertThat(context.body().getEl()).isNull();
    }

    @Then("the coupon list should not be empty")
    public void verifyNotEmpty() {
        assertThat(coupons()).isNotEmpty();
    }

    @Then("the event count of each coupon should match the number of its events")
    public void verifyEventCountMatchesEvents() {
        SoftAssertions.assertSoftly(softly -> coupons().forEach(coupon ->
                softly.assertThat(coupon.getEvents())
                        .as("events of coupon %s", coupon.getCouponId())
                        .hasSize(coupon.getEventCount())));
    }

    @Then("every coupon should have exactly {int} events")
    public void verifyExactEventCount(int expected) {
        assertThat(coupons()).allSatisfy(coupon -> {
            assertThat(coupon.getEventCount()).as("eventCount of %s", coupon.getCouponId()).isEqualTo(expected);
            assertThat(coupon.getEvents()).as("events of %s", coupon.getCouponId()).hasSize(expected);
        });
    }

    @Then("every coupon should have at least {int} events")
    public void verifyMinEventCount(int min) {
        assertThat(coupons()).allSatisfy(coupon ->
                assertThat(coupon.getEvents()).as("events of %s", coupon.getCouponId()).hasSizeGreaterThanOrEqualTo(min));
    }

    @Then("no event should have a minimum odd greater than its maximum odd")
    public void verifyMinOddNotGreaterThanMaxOdd() {
        SoftAssertions.assertSoftly(softly -> events().forEach(event ->
                softly.assertThat(event.getMinOdd())
                        .as("minOdd of event %s (%s)", event.getCode(), event.getName())
                        .isLessThanOrEqualTo(event.getMaxOdd())));
    }

    @Then("event types should match their sport ids")
    public void verifyTypeAndSportId(Map<String, Integer> sportIdsByType) {
        SoftAssertions.assertSoftly(softly -> events().stream()
                .filter(event -> sportIdsByType.containsKey(event.getType()))
                .forEach(event -> softly.assertThat(event.getSportId())
                        .as("sportId of %s event %s", event.getType(), event.getCode())
                        .isEqualTo(sportIdsByType.get(event.getType()))));

        // tabloda olmayan sporlar da gelebilir, onlar icin tip ve sportId bire bir eslesmeli
        Map<String, Set<Integer>> sportIdsByEventType = events().stream().collect(
                Collectors.groupingBy(Event::getType, Collectors.mapping(Event::getSportId, Collectors.toSet())));
        Map<Integer, Set<String>> eventTypesBySportId = events().stream().collect(
                Collectors.groupingBy(Event::getSportId, Collectors.mapping(Event::getType, Collectors.toSet())));

        assertThat(sportIdsByEventType).allSatisfy((type, ids) -> assertThat(ids).as("sportIds of %s", type).hasSize(1));
        assertThat(eventTypesBySportId).allSatisfy((id, types) -> assertThat(types).as("types of sportId %s", id).hasSize(1));
    }

    @Then("coupons should be sorted by play count in descending order")
    public void verifySortedByCount() {
        assertThat(coupons())
                .extracting(Coupon::getCount)
                .isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Then("order indexes should be sequential starting from {int}")
    public void verifyOrderIndexes(int start) {
        List<Coupon> coupons = coupons();
        List<Integer> expected = IntStream.range(start, start + coupons.size()).boxed().toList();

        assertThat(coupons).extracting(Coupon::getOrderIndex).containsExactlyElementsOf(expected);
    }

    @Then("coupon ids should be unique")
    public void verifyUniqueCouponIds() {
        assertThat(coupons()).extracting(Coupon::getCouponId).doesNotHaveDuplicates();
    }

    @Then("the minimum event date of each coupon should equal its earliest event start date")
    public void verifyMinEventDate() {
        SoftAssertions.assertSoftly(softly -> coupons().forEach(coupon -> {
            long earliestStart = coupon.getEvents().stream().mapToLong(Event::getStartDateEpoch).min().orElseThrow();
            softly.assertThat(coupon.getMinEventDate().toInstant().toEpochMilli())
                    .as("minEventDate of coupon %s", coupon.getCouponId())
                    .isEqualTo(earliestStart);
        }));
    }

    private List<Coupon> coupons() {
        List<Coupon> coupons = context.body().getD();
        assertThat(coupons).as("coupon list").isNotEmpty();
        return coupons;
    }

    private List<Event> events() {
        return coupons().stream().flatMap(coupon -> coupon.getEvents().stream()).toList();
    }
}
