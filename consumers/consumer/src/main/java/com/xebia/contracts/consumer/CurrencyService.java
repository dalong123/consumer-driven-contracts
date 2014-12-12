package com.xebia.contracts.consumer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public interface CurrencyService {
    @Nonnegative float convert(@Nonnegative float amount, @Nonnull String srcCurrency, @Nonnull String destCurrency);
}
