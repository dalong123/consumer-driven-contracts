package com.xebia.cdc.consumer.webshop;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application.xml"})
public class ShoppingCartConsumerTest {

    private ShoppingCartConsumer consumer;
    private MockRestServiceServer mockServer;
    private RestTemplate restTemplate;

    @Before
    public void setUp(){

        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo("http://localhost/cart")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{ \"items\" : [ {\"id\" : \"uid12\",\"quantity\" : 10,\"price\" : 100,\"name\" : \"testname\" } ] }", MediaType.APPLICATION_JSON));
    }


    @Test
    public void testGetShoppingCart() {

        consumer = new ShoppingCartConsumer(restTemplate);

        ShoppingCart cart = consumer.getShoppingCart("willem", "http://localhost");
        assertEquals(1, 1);

        mockServer.verify();
    }

}
