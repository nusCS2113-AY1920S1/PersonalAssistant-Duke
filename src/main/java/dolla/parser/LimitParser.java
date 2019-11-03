package dolla.parser;

import dolla.Tag;

import dolla.command.Command;

import dolla.command.AddLimitCommand;
import dolla.command.RemoveCommand;
import dolla.command.ShowListCommand;
import dolla.command.ErrorCommand;
import dolla.command.SortCommand;
import dolla.command.SearchCommand;

import dolla.task.Limit;
import dolla.ui.LimitUi;
import dolla.ui.SearchUi;
import dolla.ui.Ui;

/**
 * This class handles all limit related parsing (set, edit, remove).
 */
//@@author Weng-Kexin
public class LimitParser extends Parser {

    private static final String LIMIT_TYPE_S = "saving";
    private static final String LIMIT_TYPE_B = "budget";

    private static final String LIMIT_DURATION_D = "daily";
    private static final String LIMIT_DURATION_W = "weekly";
    private static final String LIMIT_DURATION_M = "monthly";

    protected LimitParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_LIMIT;
    }

    @Override
    public Command parseInput() {
        if (commandToRun.equals(ParserStringList.LIMIT_COMMAND_LIST)) {
            return new ShowListCommand(mode);
        } else if (commandToRun.equals(ParserStringList.LIMIT_COMMAND_SET)) {
            boolean verifiedSetCommand = verifySetLimitCommand();
            if (verifiedSetCommand) {
                String typeStr = inputArray[1];
                double amountInt = findLimitAmount();
                String durationStr = inputArray[3];
                Limit limit = new Limit(typeStr, amountInt, durationStr);
                Tag t = new Tag();
                t.handleTag(inputLine, inputArray, limit);
                return new AddLimitCommand(typeStr, amountInt, durationStr);
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
                // TODO: Update when ready
                //return new InitialModifyCommand(inputArray[1]);
                Ui.printUpcomingFeature();
                return new ErrorCommand();
            } else if (verifyPartialModifyCommand()) {
                // TODO:
                return new ErrorCommand();
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(ParserStringList.COMMAND_SEARCH)) {
            String component;
            String content;
            try {
                component = verifySearchCommand(inputArray[1]);
                content = inputArray[2];
            } catch (IndexOutOfBoundsException e) {
                SearchUi.printInvalidSearchFormat();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            return new SearchCommand(mode, component, content);
        } else if (commandToRun.equals(ParserStringList.COMMAND_SORT)) {
            if (verifySort()) {
                return new SortCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else {
            return invalidCommand();
        }
    }

    private double findLimitAmount() {
        double amount = 0;
        try {
            amount = stringToDouble(inputArray[2]);
        } catch (NumberFormatException e) {
            LimitUi.invalidAmountPrinter();
        }
        return amount;
    }

    private Boolean verifyLimitType(String limitType) {
        return limitType.equals(LIMIT_TYPE_S)
               || limitType.equals(LIMIT_TYPE_B);
    }

    private Boolean verifyLimitDuration(String limitDuration) {
        return limitDuration.equals(LIMIT_DURATION_D)
               || limitDuration.equals(LIMIT_DURATION_W)
               || limitDuration.equals(LIMIT_DURATION_M);
    }

    private Boolean verifyLimitAmount(double limitAmount) {
        return (limitAmount != 0);
    }

    private Boolean verifySetLimitCommand() {
        boolean isValid;
        try {
            String typeStr = inputArray[1];
            double amountInt = findLimitAmount();
            String durationStr = inputArray[3];
            isValid = verifyLimitType(typeStr) && verifyLimitAmount(amountInt) && verifyLimitDuration(durationStr);
        } catch (IndexOutOfBoundsException e) {
            LimitUi.invalidSetCommandPrinter();
            isValid = false;
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }
}