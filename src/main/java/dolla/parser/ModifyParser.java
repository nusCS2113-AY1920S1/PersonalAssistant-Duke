package dolla.parser;

import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.modify.FullModifyDebtCommand;
import dolla.command.modify.FullModifyEntryCommand;
import dolla.command.modify.FullModifyLimitCommand;
import dolla.command.modify.RevertFromModifyCommand;
import dolla.ui.LimitUi;

//@@author omupenguin
public class ModifyParser extends Parser {

    private String modeToModify;
    private static final String MODIFY_CANCEL = "CANCEL";

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
                return new FullModifyEntryCommand(type, amount, description, date);
            } else {
                return new ErrorCommand();
            }

        case MODE_DEBT:
            if (verifyDebtCommand()) {
                return new FullModifyDebtCommand(commandToRun, inputArray[1], amount, description, date);
            } else {
                return new ErrorCommand();
            }

        case MODE_LIMIT:
            if (verifySetCommand()) {
                String typeStr = inputArray[1];
                String durationStr = inputArray[3];
                return new FullModifyLimitCommand(typeStr, amount, durationStr);
            } else {
                LimitUi.invalidSetCommandPrinter();
                return new ErrorCommand();
            }

        default:
            return new ErrorCommand();
        }

    }

    private boolean checkCancellation() {
        return inputLine.equals(MODIFY_CANCEL);
    }

    private void getModeToModify() {
        modeToModify = mode.split(" ")[1];
    }
}
