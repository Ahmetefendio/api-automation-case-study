package com.nesine.api.steps;

import com.nesine.api.client.PopularCouponsClient;
import com.nesine.api.model.ApiError;
import com.nesine.api.support.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps {

    private final ScenarioContext context;
    private final PopularCouponsClient client;

    public CommonSteps(ScenarioContext context, PopularCouponsClient client) {
        this.context = context;
        this.client = client;
    }

    @When("I request popular coupons with eventCount {string}")
    public void requestWithEventCount(String eventCount) {
        context.setResponse(client.getPopularCoupons(eventCount));
    }

    @When("I request popular coupons without eventCount")
    public void requestWithoutEventCount() {
        context.setResponse(client.getPopularCoupons());
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expected) {
        assertThat(context.response().statusCode()).isEqualTo(expected);
    }

    @Then("the response should match the {string} schema")
    public void verifySchema(String schemaName) {
        context.response().then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/" + schemaName + ".json"));
    }

    @Then("the response should contain error code {int} with body status {int}")
    public void verifyBusinessError(int errorCode, int bodyStatus) {
        var body = context.body();
        assertThat(body.getSc()).isEqualTo(bodyStatus);
        assertThat(body.getD()).isNull();
        assertThat(body.getEl()).extracting(ApiError::getC).containsExactly(errorCode);
    }
}
