package executor.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

import java.math.BigDecimal;

/**
 * This command helps to convert user entered currency amount into another country's currency amount using api call.
 */
public class CommandConvert extends Command {
    private String from;
    private String to;
    private Double amount;
    private String use;
    private Double exchangeRate = 0.00;

    /**
     * Constructor for the CommandConvert class.
     * @param userInput this is the userInput from the CLI
     */
    public CommandConvert(String userInput) {
        super();
        this.userInput = userInput;
        this.commandType = CommandType.CONVERT;
        this.description = "Command that converts the user input cash amount from"
                + "one currency to another and prints it on the User Interface.\n"
                + "FORMAT : convert <amount> /from <Base currency ISO e.g USD > /to <Required Currency ISO e.g SGD>";
    }

    @Override
    public void execute(StorageManager storageManager) {
        Double convertedAmount;
        String outputStr;
        try {
            this.amount = extractAmount(this.commandType, userInput);
            this.from = getCurrencyCovertFrom(userInput);
            this.use = ""; // whether to use "from code" or "to code" for fetching exchange rate from json
            this.to = getCurrencyConvertTo(userInput);
            convertedAmount = this.convertCurrency(this.getFrom(), this.getTo(), this.getAmount());
            outputStr = this.getPrintableResult(convertedAmount);
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(outputStr);
    }

    /**
     * extractAmount parses the user input from CLI to get the amount which the user wishes to convert.
     * @param commandType this is the type of command
     * @param userInput takes the user entered input from CLI
     * @return the amount which user wishes to convert is returned
     */
    private Double extractAmount(CommandType commandType, String userInput) throws DukeException {
        String amountStr = Parser.parseForPrimaryInput(commandType, userInput);
        try {
            return Double.parseDouble(amountStr);
        } catch (Exception e) {
            throw new DukeException("Please enter a valid amount\n");
        }
    }

    /**
     * getCurrencyConvertFrom parses user input for the flag "from".
     * @param userInput this is the user entered input from CLI
     * @return this function returns the 3 character unique string representing the currency to convert from
     */
    private String getCurrencyCovertFrom(String userInput) {
        String fromStr = Parser.parseForFlag("from", userInput);
        if (fromStr != null) {
            return fromStr.toUpperCase();
        }
        return null;
    }

    /**
     * getCurrencyConvertTo parses the user input for the flag "to".
     * @param userInput this is the user entered input from CLI
     * @return this function return the 3 character unique string representing the currency to covert to
     */
    private String getCurrencyConvertTo(String userInput) {
        String toStr = Parser.parseForFlag("to", userInput);
        if (toStr != null) {
            return toStr.toUpperCase();
        }
        return null;
    }

    /**
     * consultCurrencyApi is a function which gets the JSON object from the api.
     * @param from String is the country code from which we are converting currency
     * @param to String is the country code to which we are converting the currency
     * @return this function returns a string version of the json object or else it will return null
     */
    private String consultCurrencyApi(String from, String to) throws DukeException {
        try {
            String link = generateApiUrl(from, to);
            URL url = new URL(link);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            String completeJson = "";
            while (null != (line = reader.readLine())) {
                completeJson += line;
            }
            return completeJson;
        } catch (DukeException e) {
            throw e;
        } catch (Exception e) {
            throw new DukeException("Exchange rate data is unavailable \n"
                    + "1. Please ensure you have active internet access. \n"
                    + "2. Also please follow the correct format for currency conversion"
                    + " available under CONVERT if you type help on the CLI. \n"
                    + "3. Please ensure that you enter proper ISO 4217 Country codes. \n");
        }
    }

    /**
     * deriveExchangeRateFromJson helps to get the required exchange rate based on country code from json.
     * @param json String
     * @param countryCode String the 3 character unique string
     * @return the exchange rate in Double, is obtained from the string json and returned
     */
    private Double deriveExchangeRateFromJson(String json, String countryCode) throws Exception {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        String rate = jsonObject.getAsJsonObject("rates").get(countryCode).getAsString();
        BigDecimal exchangeRate = new BigDecimal(rate);
        return exchangeRate.doubleValue();
    }

    /**
     * isConvertFromOrToEur helps to check if either of the from or to is EUR.
     * @param from String is the country code from which we are converting currency
     * @param to String is the country code to which we are converting the currency
     * @return function returns true if either of to or from is EUR
     */
    private boolean isConvertFromOrToEur(String from, String to) {
        return from.equals("EUR") || to.equals("EUR");
    }

    /**
     * generateApiURL helps to create the correct url link for json fetch.
     * @param from String is the country code from which we are converting currency
     * @param to String is the country code to which we are converting the currency
     * @return function returns the correct URL link string based on from and to strings
     */
    private String generateApiUrl(String from, String to) throws Exception {
        boolean isEur = isConvertFromOrToEur(from,to);
        String url;
        if (isEur) {
            if (this.from.equals("EUR")) {
                url = "https://api.exchangeratesapi.io/latest?symbols=" + this.to;
                setUse(this.to);
            } else {
                url = "https://api.exchangeratesapi.io/latest?symbols=" + this.from;
                setUse(this.from);
            }
        } else {
            url = "https://api.exchangeratesapi.io/latest?symbols=" + this.from + "," + this.to;
        }
        return url;
    }

    /**
     *  convertCurrencyToOrFromEUR helps to convert currencies from base to EUR or vice versa.
     * @param rate Double is the exchange rate derived from json
     * @param from String is the country code from which we are converting currency
     * @param amount Double is the amount of money user wishes to convert
     * @return function returns the converted currency in double
     */
    private Double convertCurrencyToOrFromEur(Double rate, String from, Double amount) throws Exception {
        if (from.equals("EUR")) {
            Double convertedAmount = amount * rate;
            Double originalToOutputRate = convertedAmount / amount;
            setExchangeRate(originalToOutputRate);
            return convertedAmount;
        } else {
            Double convertedAmount = amount / rate;
            Double originalToOutputRate = convertedAmount / amount;
            setExchangeRate(originalToOutputRate);
            return convertedAmount;
        }
    }

    /**
     * convertNonEURCurrencies helps to convert non EUR currencies where from and to are not EUR.
     * @param json String is the derived from the api
     * @param from String is the country code from which we are converting currency
     * @param to String is the country code to which we are converting the currency
     * @param amount Double is the amount of money user wishes to convert
     * @return function returns the converted currency for non EUR conversions in Double
     */
    private Double convertNonEurCurrencies(String json, String from, String to, Double amount) throws Exception {
        Double fromRate = deriveExchangeRateFromJson(json, from); // exRate for country to convert from
        Double toRate = deriveExchangeRateFromJson(json, to); // exRate for country to convert to
        Double amountInEur = amount / fromRate; // changes the given amount in base currency to EUR
        Double convertedAmount = amountInEur * toRate; // changes from EUR to required currency
        Double originalToOutputExRate = convertedAmount / amount;
        // exchange rate for the conversion from base to required currency
        setExchangeRate(originalToOutputExRate);
        return convertedAmount;
    }


    /**
     * convertCurrency is a function used to loop through the string version of the json.
     * @param from String is the country code from which we are converting currency
     * @param to String is the country code to which we are converting the currency
     * @param amount this is amount of currency which the user wants to convert
     * @return this function returns the converted amount of the currency or else returns null
     */
    private Double convertCurrency(String from, String to, Double amount) throws DukeException {
        try {
            String json = consultCurrencyApi(from, to);
            if (json != null) {
                if (this.use.equals("")) {
                    return convertNonEurCurrencies(json, from, to, amount);
                } else {
                    Double rate = deriveExchangeRateFromJson(json, this.use);
                    return convertCurrencyToOrFromEur(rate, from, amount);
                }
            }
        } catch (DukeException e) {
            throw e;
        } catch (Exception e) {
            String errorMessage = "DUKE$$$ could not understand the input. \n"
                    + "Please follow the following formatting to convert : \n"
                    + "For example : convert <amount> /from USD /to SGD \n"
                    + "\n";
            throw new DukeException(errorMessage);
        }
        return null;
    }

    /**
     * Returns the a printable version of the output to be printed to the User.
     * @param convertedAmount this is amount which was converted into
     * @return string of output is returned
     */
    private String getPrintableResult(Double convertedAmount) throws DukeException {
        if (convertedAmount == null) {
            throw new DukeException("Unable to convert "
                    + this.amount
                    + " from "
                    + this.from
                    + " to "
                    + this.to
                    + ". Please try again :) \n"
            );
        }
        convertedAmount = roundByDecimalPlace(convertedAmount, 2);
        return "DUKE$$$ has converted " + this.from
                + " " + roundByDecimalPlace(this.amount, 2) + " "
                + "to" + " "
                + this.to + " " + convertedAmount + "\n"
                + rateUsed();
    }

    /**
     * roundByDecimalPlace is basically a function to round the values to a specific number of decimal places.
     * @param value this is the value which we want to round
     * @param places number of decimal places
     * @return the rounded double value is returned as the output
     */
    private double roundByDecimalPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    /**
     * rateUsed is a function that helps to display the exchange rate which was used for the conversion.
     * @return string version of the exchange rate is displayed
     */
    private String rateUsed() {
        return "Exchange rate used = " + this.getExchangeRate().toString() + "\n";
    }

    private String getFrom() {
        return from;
    }

    private String getTo() {
        return to;
    }

    private Double getAmount() {
        return amount;
    }

    private Double getExchangeRate() {
        return exchangeRate;
    }

    private void setExchangeRate(Double exchangeRate) {
        exchangeRate = roundByDecimalPlace(exchangeRate, 3);
        this.exchangeRate = exchangeRate;
    }

    private void setUse(String use) {
        this.use = use;
    }
}
