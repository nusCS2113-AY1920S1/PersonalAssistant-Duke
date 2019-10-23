package dolla.parser;

import dolla.Time;
import dolla.Ui;
import dolla.command.AddDebtsCommand;
import dolla.command.AddEntryCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;

import java.time.LocalDate;

public class DollaParser extends Parser {

    public DollaParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode, String inputLine) {

        if (commandToRun.equals("add")) {
            if (verifyAddCommand() == true) {
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

        } else if (commandToRun.equals("owe") || commandToRun.equals("borrow")) {
            String type = commandToRun;
            String name = null;
            double amount = 0.0;
            LocalDate date = null;
            try {
                name = inputArray[1];
                amount = stringToDouble(inputArray[2]);

                String[] desc = inputLine.split(inputArray[2] + " ");
                String[] dateString = desc[1].split(" /due ");
                description = dateString[0];
                date = Time.readDate(dateString[1]);

            } catch (IndexOutOfBoundsException e) {
                Ui.printInvalidDebtFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            return new AddDebtsCommand(type, name, amount, description, date, -1);
        } else {
            return invalidCommand();
        }
    }
}