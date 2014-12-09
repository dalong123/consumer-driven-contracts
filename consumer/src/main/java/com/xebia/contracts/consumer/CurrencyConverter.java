package com.xebia.contracts.consumer;

import javax.annotation.Nonnull;

public class CurrencyConverter {

    public CurrencyConverter(@Nonnull CurrencyService service) {
    }

    public @Nonnull String convert(@Nonnull String description) {
        throw new RuntimeException("not implemented");
    }
}
