package com.xebia.contracts.consumer;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.ConsumerPactTest;
import au.com.dius.pact.model.PactFragment;
import org.apache.http.entity.ContentType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RestCurrencyServiceContractTest extends ConsumerPactTest {
    @Override
    protected PactFragment createFragment(ConsumerPactBuilder.PactDslWithProvider builder) {
        return builder
                //.given("a test")
                .uponReceiving("a request for conversion")
                .path("/convert/1.00/EUR/to/USD")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body("{\"amount\": 1.24}", ContentType.APPLICATION_JSON).toFragment();
    }

    @Override
    protected String providerName() {
        return "CurrencyConverter";
    }

    @Override
    protected String consumerName() {
        return "CLI CurrencyConverter";
    }

    @Override
    protected void runTest(String url) {
        CurrencyService client = new RestCurrencyConverterService(url);
        float response = client.convert(1f, "EUR", "USD");
        assertTrue("The returned amount should be greater than zero (was: " + response + ')', response > 0);
        assertEquals("The returned amount should be exactly what we set in the spec", 1.24f, response, 0f);
    }
}
