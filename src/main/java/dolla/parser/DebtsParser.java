package dolla.parser;

import dolla.Time;
import dolla.Ui;
import dolla.action.Repeat;
import dolla.command.Command;
import dolla.command.AddActionCommand;
import dolla.command.ShowListCommand;
import dolla.command.ErrorCommand;
import dolla.command.AddDebtsCommand;
import dolla.command.SortCommand;
import dolla.command.SearchCommand;
import dolla.command.RemoveCommand;

import java.time.LocalDate;

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
            String name;
            double amount;
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
            return processAdd(type, name, amount);
        } else if (commandToRun.equals("search")) {
            String component = inputArray[1];
            String content = inputArray[2];
            return new SearchCommand(mode, component, content);
        } else if (commandToRun.equals("sort")) {
            return new SortCommand(mode, inputArray[1]);
        } else if (commandToRun.equals("remove")) {
            return new RemoveCommand(mode, inputArray[1]);
        } else if (commandToRun.equals("redo") || commandToRun.equals("undo") || commandToRun.equals("Repeat")) {
            return new AddActionCommand(mode, commandToRun);
        } else {
            return invalidCommand();
        }
    }

    private Command processAdd(String type, String name, double amount) {
        Command addDebt;
        Repeat.setUserInput("debt", inputLine); //setup repeat
        if(undoFlag == 1) {//undo input
            addDebt = new AddDebtsCommand(type, name, amount, description, date, prevPosition);
        } else if(redoFlag == 1) {
            addDebt = new AddDebtsCommand(type, name, amount, description, date, -2);
        } else {//normal input, prePosition is -1
            addDebt = new AddDebtsCommand(type, name, amount, description, date, -1);
        }
        return addDebt;
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
