package UserCode.Tasks;

import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import FrontEnd.Ui;

public class DoTask extends Task {

    public DoTask(Condition condition, Action action) {
        super(condition, action);
    }

    @Override
    public void execute(Ui ui) {
        {
            action.execute(ui);
        }
    }
}