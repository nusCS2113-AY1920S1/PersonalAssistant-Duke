package executor.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;
import java.math.BigDecimal;

/**
 * This command helps to convert user entered currency amount into another country's currency amount using api call
 */
public class CommandConvert extends Command {
    private String from = " ";
    private String to = " ";
    private Double amount = 0.00;
    private Double exchangeRate = 0.00;

    /**
     * Constructor for the CommandConvert class
     * @param userInput this is the userInput from the CLI
     */
    public CommandConvert(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.CURRENCY;
        this.amount = extractAmount(this.commandType, userInput);
        this.from = getFromUserInput(userInput);
        this.to = getToUserInput(userInput);
    }

    /**
     * removeDollarSign is a function to remove the dollar sign entered by the user. Parsing step 1
     * @param input this is the user entered input from the CLI
     * @return this will return a string without the dollar sign
     */
    private String removeDollarSign(String input) {
        return input.trim().replace("$", "");
    }

    /**
     * extractAmount parses the user input from CLI to get the amount which the user wishes to convert
     * @param commandType this is the type of command
     * @param userInput takes the user entered input from CLI
     * @return the amount which user wishes to convert is returned
     */
    private Double extractAmount(CommandType commandType, String userInput) {
        String amountStr = Parser.parseForPrimaryInput(commandType, userInput);
        amountStr = removeDollarSign(amountStr);
        return Double.parseDouble(amountStr);
    }

    /**
     * getFromUserInput parses user input for the flag "from"
     * @param userInput this is the user entered input from CLI
     * @return this function returns the 3 character unique string for the country from which the user wishes to convert his currency
     */
    private String getFromUserInput(String userInput) {
        String fromStr = Parser.parseForFlag("from", userInput);
       return fromStr;
    }

    /**
     * getToUserInput parses the user input for the flag "to"
     * @param userInput this is the user entered input from CLI
     * @return this function return the 3 character unique string for the country to which the user wishes to convert his currency
     */
    private String getToUserInput(String userInput) {
        String toStr = Parser.parseForFlag("to", userInput);
        return toStr;
    }

    //Converting


    private String getTheJson (String from , String to){

        try {
            URL url = new URL("https://api.ratesapi.io/api/latest?base=" + from + "&symbols=" + to);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = "";
            String completeJson = "";
            while (null != (str = br.readLine())) {
                System.out.println(str);
                completeJson += str;
            }

            return completeJson;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private Double convertCurrency(String from, String to, Double amount ){

        try {
            String json = getTheJson(from,to);
            System.out.println(json);
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            System.out.println(jsonObject.toString());
            String Rate = jsonObject.getAsJsonObject("rates").get(to).getAsString();
            BigDecimal exchangeRate = new BigDecimal(Rate);
            double exRate = exchangeRate.doubleValue();
            setExchangeRate(exRate);
            double convertedAmount = exRate * amount;
            return convertedAmount;
        } catch(Exception e){
            Ui.dukeSays(e.getMessage());
            Ui.dukeSays(Ui.LINE);
            Ui.dukeSays("Please enter in the following format Currency /from");
        }
        return null;
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

    @Override
    public void execute(TaskList taskList) {

    }

    private String result (Double convertedAmount){
        return "DUKE$$$ has converted " + this.from  +
                " " + "$" + this.amount + " " +
                "to" + " " +
                this.to + " " + "$" + convertedAmount + "\n";
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    private void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    private String rateUsed (){
        return "Exchange rate used = " + this.getExchangeRate().toString() + "\n";
    }


    @Override
    public void execute(Wallet wallet) {
        Double convertedAmount = this.convertCurrency(this.getFrom(), this.getTo(), this.getAmount());
        Ui.dukeSays(this.result(convertedAmount) + rateUsed() );
        Ui.printSeparator();
    }

}
