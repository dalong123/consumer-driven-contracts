package com.xebia.cdc.consumer.webshop;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven Ottenhoff (Dual-IT) on 21/08/15.
 * Copyright 2015
 */
public class ShoppingCartConsumer {


    private RestTemplate restTemplate;
    private String url;
    private String uid;

    public ShoppingCartConsumer(RestTemplate template){

        this.restTemplate = template;
    }

    public ShoppingCart getShoppingCart( String userId, String url ){

        this.uid = userId;
        this.url = url;

        //Create a list for the message converters

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

         //Add the Jackson Message converter
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-UserId", "willem");
        headers.add("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);



        //Add the message converters to the restTemplate
        restTemplate.setMessageConverters(messageConverters);

        //return restTemplate.getForObject(url + "/cart", ShoppingCart.class);
        return restTemplate.exchange(url + "/cart", HttpMethod.GET, entity, ShoppingCart.class).getBody();
    }

}
