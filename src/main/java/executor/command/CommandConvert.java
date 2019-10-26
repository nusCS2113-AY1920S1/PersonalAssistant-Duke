package executor.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;
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
        this.userInput = userInput;
        this.commandType = CommandType.CONVERT;
        this.amount = extractAmount(this.commandType, userInput);
        this.from = getCurrencyCovertFrom(userInput);
        this.use = "";
        this.to = getCurrencyConvertTo(userInput);
        this.description = "Command that converts the user input cash amount from"
                + " one currency to another and prints it on the User Interface.";
    }


    @Override
    public void execute(Wallet wallet) {
        Double convertedAmount = this.convertCurrency(this.getFrom(), this.getTo(), this.getAmount());
        Ui.dukeSays(this.result(convertedAmount) + rateUsed());
        Ui.printSeparator();
    }


    @Override
    public void execute(TaskList taskList) {

    }


    /**
     * extractAmount parses the user input from CLI to get the amount which the user wishes to convert.
     * @param commandType this is the type of command
     * @param userInput takes the user entered input from CLI
     * @return the amount which user wishes to convert is returned
     */
    private Double extractAmount(CommandType commandType, String userInput) {
        String amountStr = Parser.parseForPrimaryInput(commandType, userInput);
        return Double.parseDouble(amountStr);
    }

    /**
     * getCurrencyConvertFrom parses user input for the flag "from".
     * @param userInput this is the user entered input from CLI
     * @return this function returns the 3 character unique string representing the currency to convert from
     */
    private String getCurrencyCovertFrom(String userInput) {
        String fromStr = Parser.parseForFlag("from", userInput);
        return fromStr;
    }

    /**
     * getCurrencyConvertTo parses the user input for the flag "to".
     * @param userInput this is the user entered input from CLI
     * @return this function return the 3 character unique string representing the currency to covert to
     */
    private String getCurrencyConvertTo(String userInput) {
        String toStr = Parser.parseForFlag("to", userInput);
        return toStr;
    }

    /**
     * consultCurrencyApi is a function which gets the JSON object from the api.
     * @param from String is the country code from which we are converting currency
     * @param to String is the country code to which we are converting the currency
     * @return this function returns a string version of the json object or else it will return null
     */
    private String consultCurrencyApi(String from, String to) {
        String Link = generateApiURL(from,to);
        System.out.println(Link);
        try {
            URL url = new URL(Link);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            String completeJson = "";
            while (null != (line = reader.readLine())) {
                completeJson += line;
            }
            return completeJson;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Double deriveExchangeRateFromJson(String json, String countryCode){
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        String rate = jsonObject.getAsJsonObject("rates").get(countryCode).getAsString();
        BigDecimal exchangeRate = new BigDecimal(rate);
        double exRate = exchangeRate.doubleValue();
        return exRate;
    }

    private Double amountAfterConversion(Double rate, Double amount){
        return rate * amount;
    }

    private boolean isConvertFromOrToEUR (String from, String to){
        return from.equals("EUR") || to.equals("EUR");
    }

    private String generateApiURL (String from , String to){
        boolean isEUR = isConvertFromOrToEUR(from,to);
        String URL;
        if(isEUR){
            if(this.from.equals("EUR")){
                URL = "https://api.exchangeratesapi.io/latest?symbols=" + this.to;
                setUse(this.to);
            } else {
                URL = "https://api.exchangeratesapi.io/latest?symbols=" + this.from;
                setUse(this.from);
            }
        } else {
            URL = "https://api.exchangeratesapi.io/latest?symbols=" + this.from +"," + this.to;
        }
        return URL;
    }


    /**
     * convertCurrency is a function used to loop through the string version of the json.
     * @param from String is the country code from which we are converting currency
     * @param to String is the country code to which we are converting the currency
     * @param amount this is amount of currency which the user wants to convert
     * @return this function returns the converted amount of the currency or else returns null
     */
    private Double convertCurrency(String from, String to, Double amount) {
        try {
            String json = consultCurrencyApi(from,to);
            if (json != null) {
                if(this.use.equals("")){
                    Double fromRate = deriveExchangeRateFromJson(json,from);
                    Double toRate = deriveExchangeRateFromJson(json,to);
                    Double amountInEUR = amount / fromRate;
                    Double convertedAmount = amountAfterConversion(toRate, amountInEUR);
                    Double originalToOutputRate = convertedAmount/amount;
                    setExchangeRate(originalToOutputRate);
                    return convertedAmount;

                } else {
                    Double Rate = deriveExchangeRateFromJson(json,this.use);
                    Double convertedAmount = amount / Rate;
                    Double originalToOutputRate = convertedAmount/amount;
                    setExchangeRate(originalToOutputRate);
                    return convertedAmount;
                }
            }
        } catch (Exception e) {
            Ui.dukeSays(e.getMessage());
            Ui.dukeSays(Ui.LINE);
            Ui.dukeSays("Please enter in the following format : "
                    + "convert 2000 /from USD /to EUR");
        }
        return null;
    }

    /**
     * result basically returns the to.string() version of the output that the user will be shown
     * @param convertedAmount this is amount which was converted into
     * @return string of output is returned
     */
    private String result(Double convertedAmount) {
        convertedAmount = roundByDecimalPlace(convertedAmount, 2);
        return "DUKE$$$ has converted " + this.from
                + " " + roundByDecimalPlace(this.amount, 2) + " "
                + "to" + " "
                + this.to + " " + convertedAmount + "\n";
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

    public Double getExchangeRate() {
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
