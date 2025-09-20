package com.example.forex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ForexConverter {

    private final Map<String, Double> rates = new HashMap<>();

    public ForexConverter(String csvFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                rates.put(parts[0], Double.parseDouble(parts[1]));
            }
        }
    }

    public double convertFromINR(double amount, String currency) {
        if (!rates.containsKey(currency)) {
            throw new IllegalArgumentException("Unsupported currency: " + currency);
        }
        return amount * rates.get(currency);
    }

    public double convertToINR(double amount, String currency) {
        if (!rates.containsKey(currency)) {
            throw new IllegalArgumentException("Unsupported currency: " + currency);
        }
        return amount / rates.get(currency);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("Usage: java ForexConverter <csv-file> <amount> <currency>");
            return;
        }

        String csvFile = args[0];
        double amount = Double.parseDouble(args[1]);
        String currency = args[2];

        ForexConverter fx = new ForexConverter(csvFile);

        System.out.println(amount + " INR in " + currency + ": " +
                fx.convertFromINR(amount, currency));
        System.out.println("100 " + currency + " in INR: " +
                fx.convertToINR(100, currency));
    }
}
