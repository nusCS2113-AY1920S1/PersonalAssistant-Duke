package dolla.parser;

import dolla.command.*;
import dolla.task.Limit;
import dolla.ui.LimitUi;

/**
 * This class handles all limit related parsing.
 */
public class LimitParser extends Parser {

    public LimitParser(String inputLine) {
        super(inputLine);
    }

    String[] CommandType = {"limits", "set", "remove"};
    String[] LimitType = {"saving", "budget"};
    String[] Duration = {"day", "week", "month"};


    @Override
    public Command handleInput(String mode, String inputLine) {
        if (commandToRun.equals(CommandType[0])) { //show limit list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals(CommandType[1])) { //add limit
            String limitType = null;
            double amount = 0.0;
            String duration = null;
            try {
                limitType = typeFinder();
                amount = amountFinder();
                duration = durationFinder();
            } catch (IndexOutOfBoundsException e) {
                LimitUi.invalidFormatPrinter();
                return new ErrorCommand();
            } catch (Exception e) {
                LimitUi.printErrorMsg();
                return new ErrorCommand();
            }
            return new AddLimitCommand(limitType, amount, duration);

        } else if (commandToRun.equals(CommandType[2])) { //REMOVE DAILY BUDGET etc
            //remove limit command
            String limitType = null;
            String duration = null;
            try {
                limitType = typeFinder();
                duration = durationFinder();
            } catch (IndexOutOfBoundsException e) {
                LimitUi.invalidFormatPrinter();
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
        String limitType = null;
        String limitTypeStr = inputArray[1];
        if (limitTypeStr.equals(LimitType[0])) {
            limitType = LimitType[0];
        } else if (limitTypeStr.equals(LimitType[1])) {
            limitType = LimitType[1];
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
        String durationStr = inputArray[3];
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
