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
            String limitType = findLimitType(inputArray[1]);
            double limitAmount = findLimitAmount();
            String limitDuration = findLimitDuration(inputArray[3]);
            boolean verifiedSetCommand;
            verifiedSetCommand = verifySetLimitCommand(limitType, limitAmount, limitDuration);
            if (verifiedSetCommand) {
                Limit limit = new Limit(limitType, limitAmount, limitDuration);
                Tag t = new Tag();
                t.handleTag(inputLine, inputArray, limit);
                return new AddLimitCommand(limitType, limitAmount, limitDuration);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(ParserStringList.COMMAND_REMOVE)) {
            return new RemoveCommand(mode, inputArray[1]);
        } else if (commandToRun.equals(COMMAND_MODIFY)) {
            if (verifyFullModifyCommand()) {
                // TODO: Update when ready
                //return new InitialModifyCommand(inputArray[1]);
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
        } else if (commandToRun.equalsIgnoreCase(ParserStringList.COMMAND_SORT)) {
            return new SortCommand(mode, inputArray[1]);
        } else {
            return invalidCommand();
        }
    }

    private String findLimitType(String inputType) {
        String limitType = "";
        try {
            if (inputType.equalsIgnoreCase(LIMIT_TYPE_S)) {
                limitType = LIMIT_TYPE_S;
            } else if (inputType.equalsIgnoreCase(LIMIT_TYPE_B)) {
                limitType = LIMIT_TYPE_B;
            } else {
                LimitUi.invalidLimitTypePrinter();
            }
        } catch (IndexOutOfBoundsException e) {
            LimitUi.invalidSetCommandPrinter();
        } catch (Exception e) {
            LimitUi.printErrorMsg();
        }
        return limitType;
    }

    private double findLimitAmount() {
        double amount = 0;
        try {
            amount = stringToDouble(inputArray[2]);
        } catch (IndexOutOfBoundsException e) {
            LimitUi.invalidSetCommandPrinter();
        } catch (NumberFormatException e) {
            LimitUi.invalidAmountPrinter();
        }
        return amount;
    }

    private String findLimitDuration(String inputDuration) {
        String limitDuration = "";
        try {
            if (inputDuration.equalsIgnoreCase((LIMIT_DURATION_D))) {
                limitDuration = LIMIT_DURATION_D;
            } else if (inputDuration.equalsIgnoreCase((LIMIT_DURATION_W))) {
                limitDuration = LIMIT_DURATION_W;
            } else if (inputDuration.equalsIgnoreCase((LIMIT_DURATION_M))) {
                limitDuration = LIMIT_DURATION_M;
            } else {
                LimitUi.invalidLimitDurationPrinter();
            }
        } catch (IndexOutOfBoundsException e) {
            LimitUi.invalidSetCommandPrinter();
        } catch (Exception e) {
            LimitUi.printErrorMsg();
        }
        return limitDuration;
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

    private Boolean verifySetLimitCommand(String limitType, double limitAmount, String limitDuration) {
        return verifyLimitType(limitType) && verifyLimitAmount(limitAmount) && verifyLimitDuration(limitDuration);
    }
}