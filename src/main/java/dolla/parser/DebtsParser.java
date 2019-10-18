package dolla.parser;

import dolla.Time;
import dolla.Ui;
import dolla.command.*;
import dolla.task.LogList;


public class DebtsParser extends Parser {
    private static LogList debtList;

    public DebtsParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode, String inputLine) {
        if (commandToRun.equals("debts")) { //show debt list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals("owe") || commandToRun.equals("borrow")) {
            String type = commandToRun;
            String name = null;
            double amount = 0.0;
            try {
                name = inputArray[1];
                amount = stringToDouble(inputArray[2]);

                String[] desc = inputLine.split(inputArray[2] + " ");
                String dateString[] = desc[1].split(" /due ");
                description = dateString[0];
                date = Time.readDateTime(dateString[1]);

            } catch (IndexOutOfBoundsException e) {
                Ui.printInvalidDebtFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            return new AddDebtsCommand(type, name, amount, description, date);

        } else if (commandToRun.equals("search")) {
            String content = inputArray[1];
            return new SearchCommand(mode, content);

        } else if (commandToRun.equals("sort")) {
            return new SortCommand(mode, inputArray[1]);

        } else {
            return invalidCommand();
        }
    }
}
