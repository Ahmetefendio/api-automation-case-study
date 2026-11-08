package com.nesine.api.support;

import com.nesine.api.model.PopularCouponsResponse;
import io.restassured.response.Response;

/**
 * Senaryo icindeki step'ler arasinda paylasilan state.
 * PicoContainer her senaryo icin yeni instance olusturdugundan paralel kosuda senaryolar birbirini etkilemez.
 */
public class ScenarioContext {

    private Response response;
    private PopularCouponsResponse body;

    public void setResponse(Response response) {
        this.response = response;
        this.body = null;
    }

    public Response response() {
        if (response == null) {
            throw new IllegalStateException("No request has been sent in this scenario");
        }
        return response;
    }

    public PopularCouponsResponse body() {
        if (body == null) {
            body = Json.read(response().asString(), PopularCouponsResponse.class);
        }
        return body;
    }
}
