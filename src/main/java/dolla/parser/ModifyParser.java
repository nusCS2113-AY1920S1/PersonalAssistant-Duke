package dolla.parser;

import dolla.Time;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.modify.FullModifyDebtCommand;
import dolla.command.modify.FullModifyEntryCommand;
import dolla.command.modify.FullModifyLimitCommand;
import dolla.command.modify.RevertFromModifyCommand;
import dolla.ui.DebtUi;
import dolla.ui.LimitUi;

//@@author omupenguin
public class ModifyParser extends Parser {

    private String modeToModify;
    private static final String CANCEL_MODIFY = "CANCEL";

    public ModifyParser(String mode, String inputLine) {
        super(inputLine);
        this.mode = mode;
    }

    @Override
    public Command parseInput() {

        if (checkCancellation()) {
            return new RevertFromModifyCommand();
        }

        getModeToModify();

        switch (modeToModify) {
        case MODE_ENTRY:
            if (verifyAddCommand()) {
                return new FullModifyEntryCommand(inputArray[1], amount, inputArray[3], date);
            } else {
                return new ErrorCommand();
            }

        case MODE_DEBT:
            if (verifyDebtCommand()) {
                return new FullModifyDebtCommand(type, name, amount, description, date);
            } else {
                return new ErrorCommand();
            }

        case MODE_LIMIT:
            if (verifySetCommand()) {
                String typeStr = inputArray[1];
                String durationStr = inputArray[3];
                return new FullModifyLimitCommand(typeStr, this.amount, durationStr);
            } else {
                LimitUi.invalidSetCommandPrinter();
                return new ErrorCommand();
            }
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
