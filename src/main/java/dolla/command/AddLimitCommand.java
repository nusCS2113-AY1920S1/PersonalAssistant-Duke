package dolla.command;

import dolla.DollaData;
import dolla.ModeStringList;
import dolla.task.Limit;
import dolla.task.LimitList;
import dolla.ui.LimitUi;

/**
 * AddLimitCommand is used to create a new Limit entity.
 */
//@@author Weng-Kexin
public class AddLimitCommand extends Command {

    private String type;
    private double amount;
    private String duration;
    private static String mode;

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
        mode = ModeStringList.MODE_LIMIT;
    }

    @Override
    public void execute(DollaData dollaData) {
        Limit newLimit = new Limit(type, amount, duration);
        LimitList limitList = (LimitList) dollaData.getRecordList(mode);
        //todo: need to add budget and show and deduct money every time there is an expense entry
        int existingLimitIndex = limitList.findExistingLimit(dollaData, newLimit, mode);
        if (existingLimitIndex == - 1) {
            dollaData.addToRecordList(mode, newLimit);
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