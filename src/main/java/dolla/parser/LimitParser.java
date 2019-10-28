package dolla.parser;

import dolla.Tag;
import dolla.command.Command;
import dolla.command.AddLimitCommand;
import dolla.command.ShowListCommand;
import dolla.command.ErrorCommand;
import dolla.task.Limit;
import dolla.ui.LimitUi;

/**
 * This class handles all limit related parsing (set, edit).
 */
public class LimitParser extends Parser {

    public LimitParser(String inputLine) {
        super(inputLine);
    }

    protected static final String LIMIT_COMMAND_LIST = "limits";
    protected static final String LIMIT_COMMAND_SET = "set";
    //protected static final String LIMIT_COMMAND_EDIT = "edit";

    protected static final String LIMIT_TYPE_S = "saving";
    protected static final String LIMIT_TYPE_B = "budget";

    protected static final String LIMIT_DURATION_D = "daily";
    protected static final String LIMIT_DURATION_W = "weekly";
    protected static final String LIMIT_DURATION_M = "monthly";

    @Override
    public Command handleInput(String mode, String inputLine) {
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
            Limit limit = new Limit(limitType, amount, duration);
            Tag t = new Tag();
            t.handleTag(inputLine, inputArray, limit); //todo: change
            return new AddLimitCommand(limitType, amount, duration);
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
        if (inputType.equalsIgnoreCase(LIMIT_TYPE_S)) { //todo: exception handling
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
        } //todo: have a throw exception
        return limitDuration;
    }
}
