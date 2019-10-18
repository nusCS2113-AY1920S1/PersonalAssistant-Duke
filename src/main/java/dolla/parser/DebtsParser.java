package dolla.parser;

import dolla.Ui;
import dolla.command.*;
import dolla.task.LogList;


public class DebtsParser extends Parser {
    private static int prevPosition;
    private static int undoFlag = 0;

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
                description = desc[1];

            } catch (IndexOutOfBoundsException e) {
                Ui.printInvalidDebtFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            if(undoFlag == 1) {//undo input
                undoFlag = 0;
                return new AddDebtsCommand(type, name, amount, description, prevPosition);
            } else {//normal input, prePosition is -1
                return new AddDebtsCommand(type, name, amount, description, -1);
            }
        } else if (commandToRun.equals("search")) {
            String content = inputArray[1];
            return new SearchCommand(mode, content);
        } else if (commandToRun.equals("sort")) {
            return new SortCommand(mode, inputArray[1]);
        } else if (commandToRun.equals("remove")) {
            return new RemoveCommand(mode, inputArray[1]);
        } else if (commandToRun.equals("redo") || commandToRun.equals("undo") || commandToRun.equals("repeat")) {
            return new AddActionCommand(mode, commandToRun);
        } else {
            return invalidCommand();
        }
    }

    public static void setPrePosition(int prePosition) {
        DebtsParser.prevPosition = prePosition;
        undoFlag = 1;
    }
}
