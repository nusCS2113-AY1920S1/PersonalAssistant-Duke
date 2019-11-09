package dolla.parser;

import dolla.Time;
import dolla.ui.DebtUi;
import dolla.command.AddDebtsCommand;
import dolla.command.AddEntryCommand;
import dolla.command.AddLimitCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;

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
                return new AddEntryCommand(inputArray[1], amount, description, date);
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
            try {
                name = inputArray[1];
                amount = stringToDouble(inputArray[2]);

                String[] desc = inputLine.split(inputArray[2] + SPACE);
                String[] dateString = desc[1].split(" /due ");
                description = dateString[0];
                date = Time.readDate(dateString[1].trim());
            } catch (IndexOutOfBoundsException e) {
                DebtUi.printInvalidDebtFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            return new AddDebtsCommand(type, name, amount, description, date);

        } else if (commandToRun.equals(ParserStringList.LIMIT_COMMAND_SET)) {
            if (verifySetCommand()) {
                return new AddLimitCommand(type, amount, duration);
            } else {
                return new ErrorCommand();
            }
        } else {
            return invalidCommand();
        }
    }
}