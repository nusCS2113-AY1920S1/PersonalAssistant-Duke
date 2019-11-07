package dolla.parser;

import dolla.Time;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.modify.FullModifyDebtCommand;
import dolla.command.modify.FullModifyEntryCommand;
import dolla.command.modify.RevertFromModifyCommand;
import dolla.exception.DollaException;
import dolla.ui.DebtUi;

//@@author omupenguin
public class ModifyParser extends Parser {

    private String modeToModify;
    private static final String CANCEL_MODIFY = "CANCEL";

    public ModifyParser(String mode, String inputLine) {
        super(inputLine);
        this.mode = mode;
    }

    @Override
    public Command parseInput() throws DollaException {

        if (checkCancellation()) {
            return new RevertFromModifyCommand();
        }

        getModeToModify();

        switch (modeToModify) {
        case MODE_ENTRY:
            if (verifyAddCommand()) {
                return new FullModifyEntryCommand(inputArray[1], stringToDouble(inputArray[2]), inputArray[3], date);
            } else {
                return new ErrorCommand();
            }
        case MODE_DEBT:
            // need to compress
            String type = commandToRun;
            String name;
            double amount;
            try {
                name = inputArray[1];
                amount = stringToDouble(inputArray[2]);
                String[] desc = inputLine.split(inputArray[2] + " ");
                String[] dateString = desc[1].split(" /due ");
                description = dateString[0];
                date = Time.readDate(dateString[1]);
            } catch (IndexOutOfBoundsException e) {
                DebtUi.printInvalidDebtFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand();
            }
            return new FullModifyDebtCommand(type, name, amount, description, date);
        case MODE_LIMIT:
            /* TODO
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
             */
        case MODE_SHORTCUT:
            // TODO
        default:
            return new ErrorCommand();
        }

    }

    private boolean checkCancellation() {
        return inputLine.equals(CANCEL_MODIFY);
    }

    private void getModeToModify() {
        modeToModify = mode.split(" ")[1];
    }
}
