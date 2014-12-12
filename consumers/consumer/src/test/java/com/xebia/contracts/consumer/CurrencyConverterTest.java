package com.xebia.contracts.consumer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CurrencyConverterTest {
    @Test
    public void testSingleUsdToEur() {
        CurrencyService service = mock(CurrencyService.class);
        when(service.convert(15.2f, "USD", "EUR")).thenReturn(12.28f);

        CurrencyConverter converter = new CurrencyConverter(service);
        String result = converter.convert("15.2 USD to EUR");
        assertEquals("15.20 USD = 12.28 EUR", result);
    }

    @Test
    public void testSingleEurToUsd() {
        CurrencyService service = mock(CurrencyService.class);
        when(service.convert(12.28f, "EUR", "USD")).thenReturn(15.2f);

        CurrencyConverter converter = new CurrencyConverter(service);
        String result = converter.convert("12.28 EUR to USD");
        assertEquals("12.28 EUR = 15.20 USD", result);
    }

    @Test
    public void testInvalidInput() {
        CurrencyService service = mock(CurrencyService.class);
        CurrencyConverter converter = new CurrencyConverter(service);

        String result = converter.convert("1.234 USD to EUR");
        assertEquals("Invalid input: \"1.234 USD to EUR\"", result);
    }
}
