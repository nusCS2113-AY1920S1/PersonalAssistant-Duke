package dolla.command;

import dolla.DollaData;
import dolla.ModeStringList;
import dolla.task.Limit;
import dolla.task.LimitList;
import dolla.ui.LimitUi;

/**
 * AddLimitCommand is used to create a new Limit entity.
 */
public class AddLimitCommand extends Command {

    private String type;
    private double amount;
    private String duration;

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
        LimitList limitList = (LimitList) dollaData.getRecordList(ModeStringList.MODE_LIMIT);
        //todo: need to add budget and show and deduct money every time there is an expense entry
        int existingLimitIndex = limitList.findExistingLimitIndex(dollaData, type, duration);
        int NON_EXISTING_INDEX = -1;
        if (existingLimitIndex == NON_EXISTING_INDEX) {
            dollaData.addToRecordList(ModeStringList.MODE_LIMIT, newLimit);
            LimitUi.echoAddRecord(newLimit);
        } else {
            Limit existingLimit = (Limit) limitList.getFromList(existingLimitIndex);
            LimitUi.existingLimitPrinter(existingLimit);
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}