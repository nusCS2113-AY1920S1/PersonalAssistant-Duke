package dolla.parser;

import dolla.Time;
import dolla.Ui;
import dolla.command.Command;
import dolla.command.AddActionCommand;
import dolla.command.ShowListCommand;
import dolla.command.ErrorCommand;
import dolla.command.AddDebtsCommand;
import dolla.command.SortCommand;
import dolla.command.SearchCommand;
import dolla.command.RemoveCommand;

import java.time.LocalDate;

/**
 * DebtsParser is a class that handles the input command and
 * execute the command according to the command under the debt mode.
 */
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
            if (undoFlag == 1) { //Undo input
                undoFlag = 0;
                return new AddDebtsCommand(type, name, amount, description, date, prevPosition);
            } else { //normal input, prePosition is -1
                return new AddDebtsCommand(type, name, amount, description, date, -1);
            }
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

    /**
     * Set the previous position of a log in the list.
     * @param prePosition the index of the log in the list
     */
    public static void setPrePosition(int prePosition) {
        DebtsParser.prevPosition = prePosition;
        undoFlag = 1;
    }
}
