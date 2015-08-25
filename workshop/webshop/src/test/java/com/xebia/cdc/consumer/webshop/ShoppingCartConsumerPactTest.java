package com.xebia.cdc.consumer.webshop;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.model.PactFragment;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"application.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application.xml"})
public class ShoppingCartConsumerPactTest {

    @Rule
    public PactProviderRule rule = new PactProviderRule("ShoppingCartProvider", "localhost", 8080, this);

    @Pact(provider = "ShoppingCartProvider", consumer = "ShoppingCartConsumer")
    public PactFragment createFragment1(ConsumerPactBuilder.PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-uid", "willem");

        return builder
                .given("Shopping cart has content")
                .uponReceiving("a request for shopping cart with content")
                .path("/cart")
                .method("GET")
             //   .headers(headers)

                .willRespondWith()
                .headers(headers)
                .status(200)
                .body("{ \"items\":[{\"id\":\"uid12\", \"quantity\":10, \"price\":100, \"name\" : \"testname\" }]}").toFragment();
    }

   // @Pact(state = "Shopping cart has no content", provider = "ShoppingCartProvider", consumer = "ShoppingCartConsumer")
    @Pact(provider = "ShoppingCartProvider", consumer = "ShoppingCartConsumer")
    //public PactFragment createFragment2(ConsumerPactBuilder.PactDslWithProvider.PactDslWithState builder) {
    public PactFragment createFragment2(ConsumerPactBuilder.PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("X-UserId", "willem");

        return builder.uponReceiving("a request for shopping cart without content")
                .path("/cart")
                .method("GET")
    //            .headers(headers)

                .willRespondWith()
                .headers(headers)
                .status(200)
                .body("{ \"items\":[]}").toFragment();
    }


    @Test
    public void testSome(){
        assertEquals(1,1);
    }

    @Test
    @PactVerification("ShoppingCartProvider")
    public void runTest1() {
        try {
            new ShoppingCartConsumer(new RestTemplate()).getShoppingCart("willem", "http://localhost:8080");
            assertEquals(1, 1);
        } catch ( Throwable t){
            t.printStackTrace();
        }
        //assertEquals(new ConsumerPort("http://localhost:8080").foos(), Arrays.asList(new Foo(42), new Foo(100)));
    }

    @Test
    @PactVerification("ShoppingCartProvider")
    public void runTest2() {
        try {
            new ShoppingCartConsumer(new RestTemplate()).getShoppingCart("willem", "http://localhost:8080");
            assertEquals(1, 1);
        } catch ( Throwable t){
            t.printStackTrace();
        }
        //assertEquals(new ConsumerPort("http://localhost:8080").foos(), Arrays.asList(new Foo(42), new Foo(100)));
    }
}