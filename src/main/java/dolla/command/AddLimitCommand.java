package dolla.command;

import dolla.DollaData;
import dolla.task.Limit;
import dolla.ui.LimitUi;

/**
 * AddLimitCommand is used to create a new Limit entity.
 */
public class AddLimitCommand extends Command {

    private String type;
    private double amount;
    private String duration;

    public AddLimitCommand(String type, double amount, String duration) {
        this.type = type;
        this.amount = amount;
        this.duration = duration;
    }

    @Override
    public void execute(DollaData dollaData) {
        Limit newLimit = new Limit(type, amount, duration);
        dollaData.addToLogList("limit", newLimit);
        //need to add budget and show
        LimitUi.echoAddLimit(newLimit);
    }
}
