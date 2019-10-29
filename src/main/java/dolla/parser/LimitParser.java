package dolla.parser;

import dolla.command.SortCommand;
import dolla.command.RemoveLimitCommand;
import dolla.command.AddLimitCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.ShowListCommand;
import dolla.ui.LimitUi;

/**
 * This class handles all limit related parsing.
 */
public class LimitParser extends Parser {

    private static final String LIMIT_COMMAND_LIST = "limits";
    private static final String LIMIT_COMMAND_SET = "set";
    private static final String LIMIT_COMMAND_REMOVE = "remove";
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
            String limitType;
            double amount;
            String duration;
            int typeIndex = 1;
            int durationIndex = 3;
            try {
                limitType = typeFinder(typeIndex);
                amount = amountFinder();
                duration = durationFinder(durationIndex);
            } catch (IndexOutOfBoundsException e) {
                LimitUi.invalidSetCommandPrinter();
                return new ErrorCommand();
            } catch (NumberFormatException e) {
                LimitUi.invalidAmountPrinter();
                return new ErrorCommand();
            } catch (Exception e) {
                LimitUi.printErrorMsg();
                return new ErrorCommand();
            }
            return new AddLimitCommand(limitType, amount, duration);
        } else if (commandToRun.equalsIgnoreCase(LIMIT_COMMAND_REMOVE)) { //REMOVE DAILY BUDGET etc
            String limitType;
            String duration;
            int typeIndex = 2;
            int durationIndex = 1;
            try {
                //todo: check if limit exists before removing
                limitType = typeFinder(typeIndex);
                duration = durationFinder(durationIndex);
            } catch (IndexOutOfBoundsException e) {
                LimitUi.invalidRemoveCommandPrinter();
                return new ErrorCommand(); //todo: change to "limit does not exist" etc
            } catch (Exception e) {
                LimitUi.printErrorMsg();
                return new ErrorCommand();
            }
            return new RemoveLimitCommand(limitType, duration);
        } else if (commandToRun.equalsIgnoreCase(LIMIT_COMMAND_SORT)) {
            return new SortCommand(mode, inputArray[1]);
        }
        return null;
    }

    /**
     * Method finds the type of limit being mentioned.
     * @return SAVING or BUDGET
     */
    private String typeFinder(int index) {
        String limitType = "";
        String inputType;
        inputType = inputArray[index];
        if (inputType.equalsIgnoreCase(LIMIT_TYPE_S)) {
            limitType = LIMIT_TYPE_S;
        } else if (inputType.equalsIgnoreCase(LIMIT_TYPE_B)) {
            limitType = LIMIT_TYPE_B;
        }
        return limitType;
    }

    private double amountFinder() {
        return stringToDouble(inputArray[2]);
    }

    private String durationFinder(int index) {
        String limitDuration = "";
        String inputDuration;
        inputDuration = inputArray[index];
        if (inputDuration.equalsIgnoreCase((LIMIT_DURATION_D))) {
            limitDuration = LIMIT_DURATION_D;
        } else if (inputDuration.equalsIgnoreCase((LIMIT_DURATION_W))) {
            limitDuration = LIMIT_DURATION_W;
        } else if (inputDuration.equalsIgnoreCase((LIMIT_DURATION_M))) {
            limitDuration = LIMIT_DURATION_M;
        }
        return limitDuration;
    }
}
