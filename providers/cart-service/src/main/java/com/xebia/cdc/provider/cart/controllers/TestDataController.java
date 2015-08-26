package com.xebia.cdc.provider.cart.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xebia.cdc.provider.cart.db.Cart;
import com.xebia.cdc.provider.cart.db.CartItem;
import com.xebia.cdc.provider.cart.db.CartItemRepository;
import com.xebia.cdc.provider.cart.db.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/given")
public class TestDataController {
    private static final Logger LOG = LoggerFactory.getLogger(TestDataController.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @RequestMapping(method = RequestMethod.POST)
    public void setState(@RequestBody StateChange stateChange) {
        LOG.info("Setting state to \"{}\"", stateChange.getState());

        // first clean up
        cartRepository.deleteAll();
        cartItemRepository.deleteAll();

        switch (stateChange.getState()) {
            case "a user with id 'abc' with a cart with a single item":
            case "a cart with a single item":
                CartItem item = new CartItem("IDC12", 1, 4995, "Philishave Model PS-4863");
                Cart cart = new Cart("abc", Arrays.asList(item));

                cartRepository.save(cart);
                cartItemRepository.save(item);
                break;
            case "an unknown user with id 'notKnown'":
                // nothing to do here
                break;
            default:
                throw new IllegalArgumentException("Unknown state: " + stateChange.getState());
        }
    }

    public static class StateChange {
        private final String state;

        public StateChange(@JsonProperty("state") String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
    }
}
