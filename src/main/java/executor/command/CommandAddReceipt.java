package executor.command;

import interpreter.Parser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class CommandAddReceipt extends Command {
    Double cash;
    LocalDate date;
    ArrayList<String> tags;

    Double extractIncome(CommandType commandType, String userInput) {
        String incomeStr = Parser.parseForPrimaryInput(commandType, userInput);
        incomeStr = removeDollarSign(incomeStr);
        return Double.parseDouble(incomeStr);
    }

    LocalDate extractDate(String userInput) {
        String dateStr = Parser.parseForFlags("date", userInput);
        if (dateStr != null) {
            return LocalDate.parse(dateStr);
        } else {
            return LocalDate.now();
        }
    }

    ArrayList<String> extractTags(String userInput) {
        String tagsStr = Parser.parseForFlags("tags", userInput);
        if (tagsStr != null) {
            return new ArrayList<String>(Arrays.asList(tagsStr.split(" ")));
        } else {
            return new ArrayList<String>();
        }
    }

    String removeDollarSign(String input) {
        return input.trim().replace("$", "");
    }

    // -- Setters & Getters

    public void setCash(double cash) {
        this.cash = cash;
    }

    public Double getCash() {
        return this.cash;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }
}
