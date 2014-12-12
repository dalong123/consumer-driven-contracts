package com.xebia.contracts.consumer;

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
        Response response = restTemplate.getForObject(baseUrl, Response.class);
        return response.getAmount();
    }
    
    private static final class Response {
        public float getAmount() {
            return 0;
        }
    }
}