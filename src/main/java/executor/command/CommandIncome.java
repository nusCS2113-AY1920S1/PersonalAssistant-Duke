package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.IncomeReceipt;
import ui.Receipt;
import ui.Ui;
import ui.Wallet;

import java.util.ArrayList;
import java.util.Date;

public class CommandIncome extends Command {

    private String userInput;
    private Double income;
    private Date date;
    private ArrayList<String> tags;

    /**
     * Constructor for CommandIncome subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandIncome(String userInput) {
        this.commandType = CommandType.IN;
        this.userInput = userInput;

    }

    @Override
    public void execute(TaskList taskList) {
    }

    @Override
    public void execute(Wallet wallet) {
        parseUserInput();
        IncomeReceipt r = new IncomeReceipt(this.income, this.date, this.tags);
    }

    private String parseForTags(String temp) {
        int indexBackslash = temp.indexOf('/');
        // Check if '/' exists
        if (indexBackslash < 0) {
            return temp;
        }
        // Check if any tags exist
        int indexMsg = temp.indexOf(' ', indexBackslash);
        if (indexMsg < 0) {
            return temp;
        }

        String[] splitDetails = temp.split(temp.substring(indexBackslash, indexMsg), 2);
        temp = splitDetails[0];
        if (splitDetails.length > 1) {
            storeTags(splitDetails[1]);
        }
        return temp;
    }

    public void parseUserInput() {
        userInput = Parser.removeStr(this.commandType.toString().trim(), userInput);
        String temp = Parser.removeStr(this.commandType.toString().trim(), this.userInput);
    }

    private String removeDollarSign(String input) {
        return input.trim().replace("$", "");
    }

    private void storeTags(String tagString) {
        String[] tagArray = tagString.split(" ");
        if (tagArray.length > 1) {
            for (int index = 1; index < tagArray.length; ++index) {
                this.tags.add(tagArray[index]);
            }
        }
    }

    public void setCommandType(CommandType in) {
        this.commandType = in;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Double getIncome() {
        return this.income;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }
}
