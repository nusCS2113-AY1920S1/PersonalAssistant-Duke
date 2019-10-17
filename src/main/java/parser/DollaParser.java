package parser;

import dolla.Ui;
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
                return new AddEntryCommand(entryType, amount, description, date);
                 */
                return new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]), description, date);
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

        } else {
            return invalidCommand();
        }
    }

    /**
     * Checks if the first word after 'add' is either 'income' or 'expense'.
     * @param s String to be analysed.
     * @return Either 'expense' or 'income' if either are passed in.
     * @throws Exception
     */
    private String verifyType(String s) throws Exception {
        if (s.equals("income") || s.equals("expense")) {
            return s;
        } else {
            Ui.printInvalidEntryType();
            throw new Exception("invalid type");
        }
    }

    /**
     * Returns true if no error occurs while creating the required variables for 'addEntryCommand'.
     * Also splits description and time components in the process.
     * @return true if no error occurs.
     */
    private boolean verifyAddCommand() {
        try {
            verifyType(inputArray[1]);
            stringToDouble(inputArray[2]);
            splitDescTime();
        } catch (IndexOutOfBoundsException e) {
            Ui.printInvalidEntryFormatError();
            return false;
        } catch (Exception e) {
            return false; // If error occurs, stop the method!
        }
        System.out.println("Add command is ok");
        return true;
    }
}
