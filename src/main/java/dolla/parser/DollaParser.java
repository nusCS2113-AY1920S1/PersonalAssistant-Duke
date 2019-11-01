package dolla.parser;

import dolla.Tag;
import dolla.Time;
import dolla.ui.DebtUi;
import dolla.command.AddDebtsCommand;
import dolla.command.AddEntryCommand;
import dolla.command.AddLimitCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.task.Debt;
import dolla.task.Entry;
import dolla.task.Limit;

import java.time.LocalDate;

//@@author omupenguin
public class DollaParser extends Parser {

    public DollaParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_DOLLA;
    }

    @Override
    public Command parseInput() {

        if (commandToRun.equals(ENTRY_COMMAND_ADD)) {
            if (verifyAddCommand()) {
                Tag t = new Tag();
                Entry entry = new Entry(inputArray[1], stringToDouble(inputArray[2]), description, date);
                t.handleTag(inputLine, inputArray, entry);
                return new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]), description, date, -1);
            } else {
                return new ErrorCommand();
            }
            
            /*
            switch(commandToRun) {
                case "income":
                case "expense":
                    return new AddExpenseCommand();
                default:
                    return new ErrorCommand();
            }
            */

        } else if (commandToRun.equals(DEBT_COMMAND_OWE) || commandToRun.equals(DEBT_COMMAND_BORROW)) {
            String type = commandToRun;
            String name = null;
            double amount = 0.0;
            LocalDate date = null;
            Tag t = new Tag();
            try {
                name = inputArray[1];
                amount = stringToDouble(inputArray[2]);

                String[] desc = inputLine.split(inputArray[2] + " ");
                String[] dateString = desc[1].split(" /due ");
                description = dateString[0];
                if (inputLine.contains(t.getPrefixTag())) {
                    String[] dateAndTag = dateString[1].split(t.getPrefixTag());
                    date = Time.readDate(dateAndTag[0].trim());
                } else {
                    date = Time.readDate(dateString[1].trim());
                }
            } catch (IndexOutOfBoundsException e) {
                DebtUi.printInvalidDebtFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            Debt debt = new Debt(type, name, amount, description, date);
            t.handleTag(inputLine, inputArray, debt);
            return new AddDebtsCommand(type, name, amount, description, date, -1);

        } else if (commandToRun.equals(ParserStringList.LIMIT_COMMAND_SET)) {
            if (verifySetLimitCommand()) {
                String typeStr = inputArray[1];
                double amountInt = findLimitAmount();
                String durationStr = inputArray[3];
                Limit limit = new Limit(typeStr, amountInt, durationStr);
                Tag t = new Tag();
                t.handleTag(inputLine, inputArray, limit);
                return new AddLimitCommand(typeStr, amountInt, durationStr);
            } else {
                return new ErrorCommand();
            }
        } else {
            return invalidCommand();
        }
    }
}