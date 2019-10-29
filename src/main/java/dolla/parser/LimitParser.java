package dolla.parser;

import dolla.Tag;
import dolla.command.Command;
import dolla.command.SortCommand;
//import dolla.command.RemoveLimitCommand;
import dolla.command.AddLimitCommand;
import dolla.command.ErrorCommand;
import dolla.task.Limit;
import dolla.command.ShowListCommand;
import dolla.ui.LimitUi;

/**
 * This class handles all limit related parsing (set, edit).
 */
//@@author Weng-Kexin
public class LimitParser extends Parser {

    private static final String LIMIT_COMMAND_LIST = "limits";
    private static final String LIMIT_COMMAND_SET = "set";
    //private static final String LIMIT_COMMAND_REMOVE = "remove";
    private static final String LIMIT_COMMAND_SORT = "sort";

    private static final String LIMIT_TYPE_S = "saving";
    private static final String LIMIT_TYPE_B = "budget";

    private static final String LIMIT_DURATION_D = "daily";
    private static final String LIMIT_DURATION_W = "weekly";
    private static final String LIMIT_DURATION_M = "monthly";

    public LimitParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode) {
        if (commandToRun.equalsIgnoreCase(LIMIT_COMMAND_LIST)) { //show limit list todo:resolve bug
            return new ShowListCommand(mode);
        } else if (commandToRun.equalsIgnoreCase(LIMIT_COMMAND_SET)) { //add limit
            String limitType = findLimitType();
            double limitAmount = findLimitAmount();
            String limitDuration = findLimitDuration();
            boolean verifiedCommand = verifySetLimitCommand(limitType, limitAmount, limitDuration);
            if (verifiedCommand) {
                Limit limit = new Limit(limitType, limitAmount, limitDuration);
                Tag t = new Tag(); //only checks for tag if command is correct
                t.handleTag(inputLine, inputArray, limit);
                return new AddLimitCommand(limitType, limitAmount, limitDuration);
            } else { //todo: handle exception
                return new ErrorCommand();
            }
        /*
        } else if (commandToRun.equalsIgnoreCase(LIMIT_COMMAND_REMOVE)) { //REMOVE DAILY BUDGET etc
            String limitType;
            String duration;
            int durationIndex = 1;
            try {
                //todo: check if limit exists before removing
                limitType = findLimitType();
                duration = durationFinder(durationIndex);
            } catch (IndexOutOfBoundsException e) {
                LimitUi.invalidRemoveCommandPrinter();
                return new ErrorCommand(); //todo: change to "limit does not exist" etc
            } catch (Exception e) {
                LimitUi.printErrorMsg();
                return new ErrorCommand();
            }
            return new RemoveLimitCommand(limitType, duration);
         */
        } else if (commandToRun.equalsIgnoreCase(LIMIT_COMMAND_SORT)) {
            return new SortCommand(mode, inputArray[1]);
        }
        return null;
    }

    private String findLimitType() {
        String limitType = null;
        String inputType;
        try {
            inputType = inputArray[1];
            if (inputType.equalsIgnoreCase(LIMIT_TYPE_S)) {
                limitType = LIMIT_TYPE_S;
            } else if (inputType.equalsIgnoreCase(LIMIT_TYPE_B)) {
                limitType = LIMIT_TYPE_B;
            } else {
                LimitUi.invalidLimitTypePrinter();
                throw new Exception(); //todo:change to limit exception
            }
        } catch (IndexOutOfBoundsException e) {
            LimitUi.invalidSetCommandPrinter();
            throw new IndexOutOfBoundsException();
        } catch (Exception e) {
            LimitUi.printErrorMsg();
        }
        return limitType;
    }

    private double findLimitAmount() {
        double amount = 0;
        try {
            amount = stringToDouble(inputArray[2]);
        } catch (IndexOutOfBoundsException e) { //todo: change to limit Exception
            LimitUi.invalidSetCommandPrinter();
        } catch (NumberFormatException e) {
            LimitUi.invalidAmountPrinter();
        }
        return amount;
    }

    private String findLimitDuration() {
        String limitDuration = null;
        String inputDuration;
        try {
            inputDuration = inputArray[3];
            if (inputDuration.equalsIgnoreCase((LIMIT_DURATION_D))) {
                limitDuration = LIMIT_DURATION_D;
            } else if (inputDuration.equalsIgnoreCase((LIMIT_DURATION_W))) {
                limitDuration = LIMIT_DURATION_W;
            } else if (inputDuration.equalsIgnoreCase((LIMIT_DURATION_M))) {
                limitDuration = LIMIT_DURATION_M;
            } else {
                LimitUi.invalidLimitDurationPrinter();
                throw new Exception(); //todo:change to limit exception
            }
        } catch (IndexOutOfBoundsException e) {
            LimitUi.invalidSetCommandPrinter();
            throw new IndexOutOfBoundsException();
        } catch (Exception e) {
            LimitUi.printErrorMsg();
        }
        return limitDuration;
    }

    private Boolean verifySetLimitCommand(String limitType, double limitAmount, String limitDuration) {
        boolean isCorrect = true;
        if (limitType == null) {
            isCorrect = false;
        }
        if (limitAmount == 0) {
            isCorrect = false;
        }
        if (limitDuration == null) {
            isCorrect = false;
        }
        return isCorrect;
    }
}