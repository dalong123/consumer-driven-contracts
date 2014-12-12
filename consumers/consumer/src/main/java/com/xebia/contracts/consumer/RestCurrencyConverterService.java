package com.xebia.contracts.consumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Locale;

@ThreadSafe
public class RestCurrencyConverterService implements CurrencyService {
    private final String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public RestCurrencyConverterService(String url) {
        baseUrl = url + "/convert";
    }

    @Override
    public float convert(@Nonnegative float amount, @Nonnull String srcCurrency, @Nonnull String destCurrency) {
        String url = String.format("%s/%.2f/%s/to/%s", baseUrl, amount, srcCurrency, destCurrency);
        Response response = restTemplate.getForObject(url, Response.class);
        return response.getAmount();
    }
    
    private static final class Response {
        @Nonnegative
        private final float amount;

        @JsonCreator
        public Response(@JsonProperty("amount") @Nonnegative float amount) {
            this.amount = amount;
        }

        @Nonnegative
        public float getAmount() {
            return amount;
        }
    }
}