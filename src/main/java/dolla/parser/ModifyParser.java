package dolla.parser;

import dolla.Time;
import dolla.command.AddDebtsCommand;
import dolla.command.AddLimitCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.ModifyDebtCommand;
import dolla.command.ModifyEntryCommand;
import dolla.ui.DebtUi;
import dolla.ui.LimitUi;

//@@author: omupenguin
public class ModifyParser extends Parser {

    protected static final String MODE_ENTRY = "entry";
    protected static final String MODE_LIMIT = "limit";
    protected static final String MODE_DEBT = "debt";
    protected static final String MODE_SHORTCUT = "shortcut";

    public ModifyParser(String inputLine) {
        super(inputLine);
        this.mode = mode;
    }

    @Override
    public Command parseInput() {
        String modeToModify = mode.split(" ")[1];

        switch (modeToModify) {
            case MODE_ENTRY:
                if (verifyAddCommand() == true) {
                    return new ModifyEntryCommand(inputArray[1], stringToDouble(inputArray[2]), inputArray[3], date);
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
                return new ModifyDebtCommand(type, name, amount, description, date);
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
}
