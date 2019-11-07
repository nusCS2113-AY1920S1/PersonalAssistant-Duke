package dolla.parser;

import dolla.Tag;
import dolla.Time;
import dolla.exception.DollaException;
import dolla.ui.DebtUi;
import dolla.command.AddDebtsCommand;
import dolla.command.AddEntryCommand;
import dolla.command.AddLimitCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.model.Debt;
import dolla.model.Entry;
import dolla.ui.LimitUi;

import java.time.LocalDate;

//@@author omupenguin
public class DollaParser extends Parser {

    public DollaParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_DOLLA;
    }

    @Override
    public Command parseInput() throws DollaException {

        if (commandToRun.equals(ENTRY_COMMAND_ADD)) {
            if (verifyAddCommand()) {
                Tag tag = new Tag();
                Entry entry = new Entry(inputArray[1], amount, description, date, EMPTY_STR);
                tag.handleTag(entry);
                return new AddEntryCommand(inputArray[1], amount,
                        description, date, tag.getTagName());
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

                String[] desc = inputLine.split(inputArray[2] + SPACE);
                String[] dateString = desc[1].split(" /due ");
                description = dateString[0];
                if (inputLine.contains(COMPONENT_TAG)) {
                    String[] dateAndTag = dateString[1].split(COMPONENT_TAG);
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
            Debt debt = new Debt(type, name, amount, description, date, EMPTY_STR);
            t.handleTag(debt);
            return new AddDebtsCommand(type, name, amount, description, date, t.getTagName());

        } else if (commandToRun.equals(ParserStringList.LIMIT_COMMAND_SET)) {
            if (verifySetLimitCommand()) {
                String typeStr = inputArray[1];
                String durationStr = inputArray[3];
                return new AddLimitCommand(typeStr, amount, durationStr);
            } else {
                LimitUi.invalidSetCommandPrinter();
                return new ErrorCommand();
            }
        } else {
            return invalidCommand();
        }
    }
}