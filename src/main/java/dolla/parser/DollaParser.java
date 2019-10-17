package dolla.parser;

import dolla.Ui;
import dolla.command.AddDebtsCommand;
import dolla.command.AddEntryCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;

public class DollaParser extends Parser {

    public DollaParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode, String inputLine) {

        if (commandToRun.equals("add")) {
            if (verifyAddCommand() == true) {
                /*
                String entryType = null;
                double amount = 0.0;
                entryType = inputArray[1];
                amount = stringToDouble(inputArray[2]);
                description = inputArray[3];
                splitDescTime();
            } catch (IndexOutOfBoundsException e) {
                Ui.printInvalidEntryFormatError();
                return new AddEntryCommand(entryType, amount, description, date);
                 */
                return new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]), inputArray[3], date);
                // TODO: ^ Check which is the proper way to write oop
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

        } else if (commandToRun.equals("owe") || commandToRun.equals("borrow")) {
            String type = commandToRun;
            String name = null;
            double amount = 0.0;
            try {
                name = inputArray[1];
                amount = stringToDouble(inputArray[2]);
                description = inputArray[3];
            } catch (IndexOutOfBoundsException e) {
                Ui.printInvalidDebtFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            return new AddDebtsCommand(type, name, amount, description);
        } else {
            return invalidCommand();
        }
    }

}
