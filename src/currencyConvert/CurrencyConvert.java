package currencyConvert;

import java.util.Scanner;

public class CurrencyConvert {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String REQ_AMOUNT_TXT = "Enter an amount to convert: ";
    private static final String REQ_CURRENCY_FROM_TXT = "Enter currency from (E, D, P, or Y):";
    private static final String REQ_CURRENCY_TO_TXT = "Enter currency to (E, D, P, or Y):";

    public static void main(String[] args) {
        // 1. Display the initial text of the program
        init();

        // 2. Ask for the amount of money to convert
        float amount = requestAmount(REQ_AMOUNT_TXT);

        // 3. Request the currency to convert
        String fromCurrency = requestCurrency(REQ_CURRENCY_FROM_TXT);
        String toCurrency = requestCurrency(REQ_CURRENCY_TO_TXT);

        // 4. Convert currency
        float convertedAmount = convertCurrency(amount, fromCurrency, toCurrency);

        // 5. Returns the coin symbol
        String fromSymbol = getSymbol(fromCurrency);
        String toSymbol = getSymbol(toCurrency);

        // 6. Round the value
        double roundedAmount = round(convertedAmount, (byte) 2);

        // Display result
        System.out.println(amount + fromSymbol + " = " + roundedAmount + toSymbol);
    }

    private static void init() {
        System.out.println("""
                **********************************************************************
                * CURRENCY CONVERSION                    © Federico Sánchez Vidarte  *
                *                                                                    *
                * This program converts currency between:                            *
                * EURO, DOLLAR, POUND and YEN                                        *
                **********************************************************************
                """);
    }

    private static float requestAmount(String text) {
        System.out.println(text);
        while (true) {
            try {
                return Float.parseFloat(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again :)");
            }
        }
    }

    private static String requestCurrency(String text) {
        String response;
        while (true) {
            System.out.println(text);
            response = scanner.nextLine().trim().toUpperCase();
            if (response.equals("E") || response.equals("D") || response.equals("P") || response.equals("Y")) {
                return response;
            } else {
                System.out.println("Invalid Currency. Please try again :)");
            }
        }
    }

    private static float convertCurrency(float amount, String from, String to) {
        float euroToDollar = 1.1f, euroToPound = 0.85f, euroToYen = 129.53f;
        float rateFromEuro = switch (from) {
            case "E" -> 1f;
            case "D" -> 1 / euroToDollar;
            case "P" -> 1 / euroToPound;
            case "Y" -> 1 / euroToYen;
            default -> 0f;
        };
        float rateToEuro = switch (to) {
            case "E" -> 1f;
            case "D" -> euroToDollar;
            case "P" -> euroToPound;
            case "Y" -> euroToYen;
            default -> 0f;
        };
        return amount * rateFromEuro * rateToEuro;
    }

    private static String getSymbol(String currency) {
        return switch (currency) {
            case "E" -> "\u20AC"; // Euro
            case "D" -> "\u0024"; // Dollar
            case "P" -> "\u00A3"; // Pound
            case "Y" -> "\u00A5"; // Yen
            default -> "";
        };
    }

    private static double round(double num, byte dec) {
        double factor = Math.pow(10, dec);
        return Math.round(num * factor) / factor;
    }
}
