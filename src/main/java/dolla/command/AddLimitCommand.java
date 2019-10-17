package dolla.command;

import dolla.DollaData;
import dolla.task.Limit;
import dolla.ui.LimitUi;

/**
 * AddLimitCommand is used to create a new Limit entity.
 */
public class AddLimitCommand extends Command {

    private Limit.LimitType type;
    private double amount;
    private Limit.Duration duration;

    public AddLimitCommand(Limit.LimitType type, double amount, Limit.Duration duration) {
        this.type = type;
        this.amount = amount;
        this.duration = duration;
    }

    @Override
    public void execute(DollaData dollaData) {
        Limit newLimit = new Limit(type, amount, duration);
        dollaData.addToLogList("limit", newLimit);
        LimitUi.echoAddLimit(newLimit);
    }
}
