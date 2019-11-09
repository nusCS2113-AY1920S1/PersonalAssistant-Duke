package dolla.parser;

import dolla.command.AddLimitCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.modify.InitialModifyCommand;
import dolla.command.ShowListCommand;
import dolla.command.ShowRemainingLimitCommand;
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
            if (verifyShowRemainingLimitCommand()) {
                return new ShowRemainingLimitCommand(duration, type);
            } else {
                LimitUi.invalidShowRemainingLimitPrinter();
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(ParserStringList.LIMIT_COMMAND_SET)) {
            if (verifySetCommand()) {
                return new AddLimitCommand(type, amount, duration);
            } else {
                LimitUi.invalidSetCommandPrinter();
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
            if (verifyLimitSearchCommand()) {
                return new SearchCommand(mode, inputArray[1], inputArray[2]);
            } else {
                return new ErrorCommand();
            }
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

    private Boolean verifyShowRemainingLimitCommand() {
        try {
            duration = verifyLimitDuration(inputArray[1]);
            type = verifyLimitType(inputArray[2]);
        } catch (IndexOutOfBoundsException | DollaException e) {
            return false;
        }
        return true;
    }

    protected static String verifyLimitType(String limitType) throws DollaException {
        if (limitType.equals(LIMIT_TYPE_S) || limitType.equals(LIMIT_TYPE_B)) {
            return limitType;
        } else {
            throw new DollaException(DollaException.invalidLimitType());
        }
    }

    protected static String verifyLimitDuration(String limitDuration) throws DollaException {
        if (limitDuration.equals(LIMIT_DURATION_D)
            || limitDuration.equals(LIMIT_DURATION_W)
            || limitDuration.equals(LIMIT_DURATION_M)) {
            return limitDuration;
        } else {
            throw new DollaException(DollaException.invalidLimitDuration());
        }
    }
}