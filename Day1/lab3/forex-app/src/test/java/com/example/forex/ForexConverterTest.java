package com.example.forex;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class ForexConverterTest {

    @Test
    public void testConvertFromINR() throws IOException {
        ForexConverter fx = new ForexConverter("rates.csv");
        double usd = fx.convertFromINR(1000, "USD");
        assertEquals(12.0, usd, 0.5);
    }

    @Test
    public void testConvertToINR() throws IOException {
        ForexConverter fx = new ForexConverter("rates.csv");
        double inr = fx.convertToINR(12, "USD");
        assertEquals(1000.0, inr, 50.0);
    }

    @Test
    public void testUnsupportedCurrency() throws IOException {
        ForexConverter fx = new ForexConverter("rates.csv");
        assertThrows(IllegalArgumentException.class, () -> fx.convertFromINR(1000, "XYZ"));
    }
}
