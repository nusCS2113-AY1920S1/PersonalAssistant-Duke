package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandCurrency extends Command {
    private String from = " ";
    private String to = " ";
    private int amount = 0;

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

    private Integer extractAmount(CommandType commandType, String userInput) {
        String amountStr = Parser.parseForPrimaryInput(commandType, userInput);
        amountStr = removeDollarSign(amountStr);
        return Integer.parseInt(amountStr);
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
    private Double findExchangeRateAndConvert(String from, String to, int amount) {
        try {
            //Yahoo Finance API
            URL url = new URL("https://api.ratesapi.io/api/latest?base="+from+"&symbols="+to +" HTTP/2");
            System.out.println(url.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = reader.readLine();
            if (line.length() > 0) {
                return Double.parseDouble(line) * amount;
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    private String getFrom() {
        return from;
    }

    private String getTo() {
        return to;
    }

    private int getAmount() {
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
        Double convertedAmount = this.findExchangeRateAndConvert(this.getFrom(), this.getTo(), this.getAmount());
        Ui.dukeSays(this.result(convertedAmount));
        Ui.printSeparator();
    }

}
