package executor.command;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;
import java.math.BigDecimal;


public class CommandCurrency extends Command {
    private String from = " ";
    private String to = " ";
    private Double amount = 0.00;

    public CommandCurrency(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.CURRENCY;
        this.amount = extractAmount(this.commandType, userInput);
        this.from = getFromUserInput(userInput);
        this.to = getToUserInput(userInput);
    }

    //Parsing

    private String removeDollarSign(String input) {
        return input.trim().replace("$", "");
    }

    private Double extractAmount(CommandType commandType, String userInput) {
        String amountStr = Parser.parseForPrimaryInput(commandType, userInput);
        amountStr = removeDollarSign(amountStr);
        return Double.parseDouble(amountStr);
    }

    private String getFromUserInput(String userInput) {
        String fromStr = Parser.parseForFlag("from", userInput);
       return fromStr;
    }

    private String getToUserInput(String userInput) {
        String toStr = Parser.parseForFlag("to", userInput);
        return toStr;
    }

    //Converting
    private Double convertCurrency(String from, String to, Double amount ){

        try {
            String json = "https://api.ratesapi.io/api/latest?base=" + from + "&symbols=" + to;
            System.out.println(json.toString());
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            System.out.println(jsonObject.toString());
            String Rate = jsonObject.getAsJsonObject("rates").get(to).getAsString();
            BigDecimal exchangeRate = new BigDecimal(Rate);
            double exRate = exchangeRate.doubleValue();
            double convertedAmount = exRate * amount;
            if(convertedAmount > 0.0){
                return convertedAmount;
            }
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


    @Override
    public void execute(Wallet wallet) {
        Double convertedAmount = this.convertCurrency(this.getFrom(), this.getTo(), this.getAmount());
        Ui.dukeSays(this.result(convertedAmount));
        Ui.printSeparator();
    }

}
