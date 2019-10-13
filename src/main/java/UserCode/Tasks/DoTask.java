package UserCode.Tasks;

import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import UserInterfaces.Ui;

public class DoTask extends Task {

    public DoTask(Action action) {
        super(Condition.TRUE, action);
    }

    @Override
    public int execute(Ui ui) {
        {
            return action.execute(ui);
        }
    }
}