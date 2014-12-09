package com.xebia.contracts.consumer;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyConverter {
    private static final Pattern INPUT_PATTERN = Pattern.compile(
            "^(?<amount>\\d+\\.\\d{1,2}) (?<srcCurr>[A-Z]{3}) to (?<dstCurr>[A-Z]{3})$");

    @Nonnull
    private final CurrencyService currencyService;

    public CurrencyConverter(@Nonnull CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public @Nonnull String convert(@Nonnull String description) {
        Matcher matcher = INPUT_PATTERN.matcher(description);

        if (!matcher.matches()) {
            return "Invalid input: \"" + description + '"';
        }

        float amount = Float.parseFloat(matcher.group("amount"));
        String srcCurrency = matcher.group("srcCurr");
        String dstCurrency = matcher.group("dstCurr");

        float converted = currencyService.convert(amount, srcCurrency, dstCurrency);
        return String.format(Locale.US, "%.2f %s = %.2f %s", amount, srcCurrency, converted, dstCurrency);
    }
}
