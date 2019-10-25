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

/**
 * DebtsParser is a class that handles the input command and
 * execute the command according to the command under the debt mode.
 */
public class DebtsParser extends Parser {

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

    /**
     * This method will process and return a "add" command for debt.
     * @param type the type of input. i.e. owe or borrow.
     * @param name the name of the borrower/lender
     * @param amount the amount borrowed/lent
     * @return an AddDebtsCommand with respect to the nature of the input.
     */
    private Command processAdd(String type, String name, double amount) {
        Command addDebt;
        Repeat.setRepeatInput("debt", inputLine); //setup repeat
        if (undoFlag == 1) { //undo input
            addDebt = new AddDebtsCommand(type, name, amount, description, date, prevPosition);
        } else if (redoFlag == 1) {
            addDebt = new AddDebtsCommand(type, name, amount, description, date, -2);
        } else { //normal input, prePosition is -1
            addDebt = new AddDebtsCommand(type, name, amount, description, date, -1);
        }
        return addDebt;
    }
}
