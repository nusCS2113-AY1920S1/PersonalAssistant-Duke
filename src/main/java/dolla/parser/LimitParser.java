package dolla.parser;

import dolla.command.*;

/**
 * This class handles all limit related parsing.
 */
public class LimitParser extends Parser {

    public LimitParser(String inputLine) {
        super(inputLine);
    }

    protected enum CommandType {
        LIMITS, SET, REMOVE
    }

    @Override
    public Command handleInput(String mode, String inputLine) {
        if (commandToRun.equals(CommandType.LIMITS)) { //show limit list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals(CommandType.SET)) { //add limit
            Limit.LimitType limitType = null;
            double amount = 0.0;
            Limit.Duration duration = null;
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

        } else if (commandToRun.equals(CommandType.REMOVE)) { //REMOVE DAILY BUDGET etc
            //remove limit command
            Limit.LimitType limitType = null;
            Limit.Duration duration = null;
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
    private Limit.LimitType typeFinder() {
        Limit.LimitType limitType = null;
        String limitTypeStr = inputArray[1];
        if (limitTypeStr.equals(Limit.LimitType.SAVING)) {
            limitType = Limit.LimitType.SAVING;
        } else if (limitTypeStr.equals(Limit.LimitType.BUDGET)) {
            limitType = Limit.LimitType.BUDGET;
        }
        return limitType;
    }

    private double amountFinder() {
        double amount = 0.0;
        amount = stringToDouble(inputArray[2]);
        return amount;
    }

    private Limit.Duration durationFinder() {
        Limit.Duration duration = null;
        String durationStr = inputArray[3];
        if (durationStr.equals(Limit.Duration.DAY)) {
            duration = duration.DAY;
        } else if (durationStr.equals(Limit.Duration.WEEK)) {
            duration = duration.WEEK;
        } else if (durationStr.equals(Limit.Duration.MONTH)) {
            duration = duration.MONTH;
        }
        return duration;
    }
}
