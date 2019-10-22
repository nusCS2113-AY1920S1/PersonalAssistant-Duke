package dolla.parser;

import dolla.command.*;
import dolla.ui.LimitUi;

/**
 * This class handles all limit related parsing.
 */
public class LimitParser extends Parser {

    public LimitParser(String inputLine) {
        super(inputLine);
    }

    protected final static String LIMIT_COMMAND_LIST = "limits";
    protected final static String LIMIT_COMMAND_SET = "set";
    protected final static String LIMIT_COMMAND_REMOVE = "remove";

    protected final static String LIMIT_TYPE_S = "saving";
    protected final static String LIMIT_TYPE_B = "budget";

    protected final static String LIMIT_DURATION_D = "day";
    protected final static String LIMIT_DURATION_W = "week";
    protected final static String LIMIT_DURATION_M = "month";

    String[] CommandType = {"limits", "set", "remove"};
    String[] LimitType = {"saving", "budget"};
    String[] Duration = {"day", "week", "month"};


    @Override
    public Command handleInput(String mode, String inputLine) {
        if (commandToRun.equalsIgnoreCase(LIMIT_COMMAND_LIST)) { //show limit list todo:resolve bug
            return new ShowListCommand(mode);
        } else if (commandToRun.equalsIgnoreCase(LIMIT_COMMAND_SET)) { //add limit
            String limitType = null;
            double amount = 0.0;
            String duration = null;
            try {
                limitType = typeFinder();
                amount = amountFinder();
                duration = durationFinder();
            } catch (IndexOutOfBoundsException e) {
                LimitUi.invalidSetCommandPrinter();
                return new ErrorCommand();
            } catch (Exception e) {
                LimitUi.printErrorMsg();
                return new ErrorCommand();
            }
            return new AddLimitCommand(limitType, amount, duration);
        } else if (commandToRun.equalsIgnoreCase(LIMIT_COMMAND_REMOVE)) { //REMOVE DAILY BUDGET etc
            //remove limit command
            String limitType = null;
            String duration = null;
            try {
                limitType = typeFinder();
                duration = durationFinder();
            } catch (IndexOutOfBoundsException e) {
                LimitUi.invalidSetCommandPrinter();
                return new ErrorCommand(); //todo: change to "limit does not exist" etc
            } catch (Exception e) {
                LimitUi.printErrorMsg();
                return new ErrorCommand();
            }
            return new RemoveLimitCommand(limitType, duration);
        }
        return null;
    }

    private void handleAdding() {

    }

    private void handleRemoving() {

    }

    /**
     * Method finds the type of limit being mentioned.
     * @return SAVING or BUDGET
     */
    private String typeFinder() {
        String limitType = "";
        String inputStr = "";
        try {
            inputStr = inputArray[1];
            if (inputStr.equalsIgnoreCase(LIMIT_TYPE_S)) {
                limitType = LIMIT_TYPE_S;
            } else if (inputStr.equalsIgnoreCase(LIMIT_TYPE_B)) {
                limitType = LIMIT_TYPE_B;
            }
        } catch (IndexOutOfBoundsException e) {
            //todo: handle exception
        } catch (Exception e) {
            //todo: handle exception
        }
        return limitType;
    }

    private double amountFinder() {
        double amount = 0.0;
        amount = stringToDouble(inputArray[2]);
        return amount;
    }

    private String durationFinder() {
        String duration = null;
        String durationStr = inputArray[4];
        if (durationStr.equals(Duration[0])) {
            duration = Duration[0];
        } else if (durationStr.equals(Duration[1])) {
            duration = Duration[0];
        } else if (durationStr.equals(Duration[2])) {
            duration = Duration[2];
        }
        return duration;
    }
}
