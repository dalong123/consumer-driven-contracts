package com.xebia.cdc.consumer.cartcheckbatch.services.cart;

import au.com.dius.pact.consumer.*;
import au.com.dius.pact.model.MockProviderConfig;
import au.com.dius.pact.model.PactFragment;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CartServiceRestImplPactTest {
    private final ConsumerPactBuilder.PactDslWithProvider pactBuilder =
            ConsumerPactBuilder.consumer("cart-check-batch").hasPactWith("cart");

    private final MockProviderConfig cartConfig = MockProviderConfig.createDefault();

    @Test
    public void testEmptyCart() {
        final String userId = "notKnown";

        PactFragment fragment = pactBuilder
            .given("an unknown user with id '" + userId + "'")
            .uponReceiving("a request for the current cart")
                .path("/cart")
                .method("GET")
                .headers(createUserHeader(userId))
            .willRespondWith()
                .status(200)
                .matchHeader("Content-Type", "application/json;\\s*charset=UTF-8", "application/json; charset=UTF-8")
                .body("{\"items\": []}")
            .toFragment();

        testWithPact(fragment, cartConfig, config -> {
            Cart cart = new CartServiceRestImpl(config.url()).getCart(userId);

            assertNotNull(cart);
            assertNotNull(cart.getItems());
            assertTrue(cart.getItems().isEmpty());
        });
    }

    @Test
    public void testCartWithOneItem() {
        final String userId = "abc";

        PactFragment fragment = pactBuilder
            .given("a user with id '" + userId + "' with a cart with a single item")
            .uponReceiving("a request for the current cart")
                .path("/cart")
                .method("GET")
                .headers(createUserHeader(userId))
            .willRespondWith()
                .status(200)
                .matchHeader("Content-Type", "application/json;\\s*charset=UTF-8", "application/json; charset=UTF-8")
                .body(new PactDslJsonBody()
                    .array("items")
                        .object()
                            .stringValue("id", "IDC12")
                            .numberValue("quantity", 1)
                            .numberValue("quantity", 1)
                            .numberValue("price", 4995)
                            .numberValue("stock", 15)
                        .closeObject()
                    .closeArray()
                )
            .toFragment();

        testWithPact(fragment, cartConfig, config -> {
            Cart cart = new CartServiceRestImpl(config.url()).getCart(userId);

            assertNotNull(cart);
            assertNotNull(cart.getItems());
            assertEquals(1, cart.getItems().size());

            CartItem item = cart.getItems().get(0);
            assertNotNull(item);
            assertEquals("IDC12", item.getId());
            assertEquals(1, item.getQuantity());
            assertEquals(4995, item.getPrice());
            assertEquals(15, item.getStock());
        });
    }

    private Map<String, String> createUserHeader(String userId) {
        final Map<String, String> reqHeaders = new HashMap<>();
        reqHeaders.put("X-UserId", userId);
        return reqHeaders;
    }

    private void testWithPact(PactFragment fragment, MockProviderConfig config, TestRun testRun) {
        final VerificationResult result = fragment.runConsumer(config, testRun);
        check(result);
    }

    private void check(VerificationResult result) {
        if (result instanceof PactError) {
            throw new RuntimeException(((PactError) result).error());
        } else if (result instanceof PactMismatch) {
            fail("Pact mismatch, caused: " + ((PactMismatch) result).userError().get().getMessage());
        }
    }
}
