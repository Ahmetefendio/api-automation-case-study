package com.nesine.api.client;

import com.nesine.api.config.Config;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.DecoderConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PopularCouponsClient {

    private static final String PATH = "/v1/PopularCoupons";

    // RestAssured'in static ayarlari (baseURI vb.) paralel kosuda paylasildigi icin
    // her client kendi spec'ini kullaniyor.
    private final RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri(Config.baseUrl())
            .setAccept(ContentType.JSON)
            // Sunucu "deflate" icin zlib header'i olmayan raw deflate donuyor, Java bunu acamiyor. Sadece gzip istiyoruz.
            .setConfig(RestAssuredConfig.config()
                    .decoderConfig(DecoderConfig.decoderConfig().contentDecoders(DecoderConfig.ContentDecoder.GZIP))
                    .httpClient(HttpClientConfig.httpClientConfig()
                            .setParam("http.connection.timeout", Config.timeoutMs())
                            .setParam("http.socket.timeout", Config.timeoutMs())))
            .addFilter(new AllureRestAssured())
            .build();

    public Response getPopularCoupons(String eventCount) {
        return given(spec)
                .queryParam("eventCount", eventCount)
                .get(PATH);
    }

    public Response getPopularCoupons() {
        return given(spec).get(PATH);
    }
}
