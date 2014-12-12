package com.xebia.contracts.consumer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class RestCurrencyConverterService implements CurrencyService {
    public RestCurrencyConverterService(String url) {
    }

    @Override
    public float convert(@Nonnegative float amount, @Nonnull String srcCurrency, @Nonnull String destCurrency) {
        return 0;
    }
}