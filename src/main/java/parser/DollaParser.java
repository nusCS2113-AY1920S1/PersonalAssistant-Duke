package parser;

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
            String entryType = null;
            double amount = 0.0;
            try {
                entryType = verifyType(inputArray[1]);
                amount = stringToDouble(inputArray[2]);
                description = inputArray[3];
                splitDescTime();
            } catch (IndexOutOfBoundsException e) {
                Ui.printInvalidEntryFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand(); // If error occurs, stop the method!
            }

            return new AddEntryCommand(entryType, amount, description, date);

            /*
            switch(commandToRun) {
                case "income":
                case "expense":
                    return new AddExpenseCommand();
                default:
                    return new ErrorCommand();
            }
            */

        }
        else if(commandToRun.equals("owe") || commandToRun.equals("borrow")) {
            String type = commandToRun;
            String name = null;
            double amount = 0.0;
            try {
                name = inputArray[1];
                amount = stringToDouble(inputArray[2]);
                description = inputArray[3];
            } catch (IndexOutOfBoundsException e){
                Ui.printInvalidDebtFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            return new AddDebtsCommand(type, name, amount, description);
        }
        else {
            return invalidCommand();
        }
    }

    public static String verifyType(String s) throws Exception {
        if (s.equals("income") || s.equals("expense")) {
            return s;
        }
//        else if(s.equals("owe") || s.equals("borrow")){
//            return s;
//        }
            else {
            Ui.printInvalidEntryType();
            throw new Exception("invalid type");
        }
    }
}
