package dolla.command;

import dolla.model.DollaData;
import dolla.command.action.state.LimitState;
import dolla.command.action.Redo;
import dolla.command.action.state.UndoStateList;
import dolla.model.Limit;
import dolla.model.LimitList;
import dolla.model.Record;
import dolla.ui.LimitUi;

import static dolla.parser.ParserStringList.SPACE;

//@@author Weng-Kexin
public class AddLimitCommand extends Command {

    private String type;
    private double amount;
    private String duration;
    private static final String mode = MODE_LIMIT;

    /**
     * Instantiates a new AddLimitCommand.
     * @param type type of limit
     * @param amount amount of limit
     * @param duration duration of limit
     */
    public AddLimitCommand(String type, double amount, String duration) {
        this.type = type;
        this.amount = amount;
        this.duration = duration;
    }

    @Override
    public void execute(DollaData dollaData) {
        Limit newLimit = new Limit(type, amount, duration);
        LimitList limitList = (LimitList) dollaData.getRecordListObj(mode);
        UndoStateList.addState(new LimitState(limitList.get()), mode);///////////////////////////////////////
        Redo.clearRedoState(mode);

        int duplicateLimitIndex = limitList.findExistingRecordIndex(dollaData, newLimit, mode);
        if (recordDoesNotExist(duplicateLimitIndex)) {
            dollaData.addToRecordList(mode, newLimit);
            LimitUi.echoAddRecord(newLimit);
        } else {
            Record existingLimit = limitList.getFromList(duplicateLimitIndex);
            LimitUi.existingLimitPrinter(existingLimit);
        }
    }

    @Override
    public String getCommandInfo() {
        return type + SPACE + amount + SPACE + duration;
    }
}