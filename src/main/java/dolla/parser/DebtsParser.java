package dolla.parser;

import dolla.Ui;
import dolla.action.repeat;
import dolla.command.*;
import dolla.task.LogList;


public class DebtsParser extends Parser {
    private static int prevPosition;
    private static int undoFlag = 0;
    private static int redoFlag = 0;

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
            repeat.setUserInput("debt", inputLine); //setup repeat
            if(undoFlag == 1) {//undo input
                return new AddDebtsCommand(type, name, amount, description, prevPosition);
            } else if(redoFlag == 1) {
                return new AddDebtsCommand(type, name, amount, description, -2);
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

    public static void setPrevPosition(int prevPosition) {
        DebtsParser.prevPosition = prevPosition;
        undoFlag = 1;
    }

    public static void resetPrevPosition() {
        DebtsParser.prevPosition = -1;
        undoFlag = 0;
    }

    public static void setRedoFlag() {
        redoFlag = 1;
    }

    public static void resetRedoFlag() {
        redoFlag = 0;
    }
}
