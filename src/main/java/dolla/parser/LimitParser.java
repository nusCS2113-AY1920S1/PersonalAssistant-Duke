package dolla.parser;

import dolla.command.AddLimitCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.modify.InitialModifyCommand;
import dolla.command.ShowListCommand;
import dolla.command.ShowRemainingBudgetCommand;
import dolla.command.RemoveCommand;
import dolla.command.SearchCommand;
import dolla.command.SortCommand;
import dolla.command.ActionCommand;
import dolla.command.modify.PartialModifyLimitCommand;

import dolla.exception.DollaException;

import dolla.ui.LimitUi;
import dolla.ui.SearchUi;

//@@author Weng-Kexin
public class LimitParser extends Parser {

    protected LimitParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_LIMIT;
    }

    @Override
    public Command parseInput() {
        if (commandToRun.equals(ParserStringList.LIMIT_COMMAND_LIST)) {
            return new ShowListCommand(mode);
        } else if (commandToRun.equals(ParserStringList.LIMIT_COMMAND_REMAINING)) {
            if (verifyShowRemainingBudgetCommand()) {
                String duration = inputArray[1];
                return new ShowRemainingBudgetCommand(duration);
            } else {
                LimitUi.invalidShowFormat();
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(ParserStringList.LIMIT_COMMAND_SET)) {
            if (verifySetCommand()) {
                return new AddLimitCommand(type, amount, duration);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(ParserStringList.COMMAND_REMOVE)) {
            if (verifyRemove()) {
                return new RemoveCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_MODIFY)) {
            if (verifyFullModifyCommand()) {
                return new InitialModifyCommand(inputArray[1]);
            } else if (verifyPartialModifyCommand()) {
                return new PartialModifyLimitCommand(modifyRecordNum, type, amount, duration);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(ParserStringList.COMMAND_SEARCH)) {
            String component = null;
            String content = null;
            try {
                if (verifyDebtSearchComponent(inputArray[1]) && inputArray[2] != null) {
                    component = inputArray[1];
                    content = inputArray[2];
                } else {
                    SearchUi.printInvalidDebtSearchComponent();
                }
            } catch (NullPointerException e) {
                SearchUi.printInvalidSearchFormat();
                return new ErrorCommand();
            } catch (IndexOutOfBoundsException e) {
                SearchUi.printInvalidSearchFormat();
                return new ErrorCommand();
            }
            return new SearchCommand(mode, component, content);
        } else if (commandToRun.equals(ParserStringList.COMMAND_SORT)) {
            if (verifySort()) {
                return new SortCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_REDO)
                || commandToRun.equals(COMMAND_UNDO)
                || commandToRun.equals(COMMAND_REPEAT)) {
            return new ActionCommand(mode, commandToRun);
        } else {
            return invalidCommand();
        }
    }

    private Boolean verifyShowRemainingBudgetCommand() {
        boolean isValid = true;
        try {
            type = verifyIsBudget(inputArray[2]);
            duration = verifyDuration(inputArray[1]);
        } catch (IndexOutOfBoundsException | DollaException e) {
            isValid = false;
        }
        return isValid;
    }

    private static String verifyIsBudget(String s) throws DollaException {
        if (s.equals(LIMIT_TYPE_B)) {
            return s;
        } else {
            throw new DollaException(MODE_LIMIT);
        }
    }

    private static String verifyDuration(String duration) throws DollaException {
        if (duration.equals(LIMIT_DURATION_D)
            || duration.equals(LIMIT_DURATION_W)
            || duration.equals(LIMIT_DURATION_M)) {
            return duration;
        } else {
            throw new DollaException(DollaException.invalidLimitDuration());
        }
    }
}