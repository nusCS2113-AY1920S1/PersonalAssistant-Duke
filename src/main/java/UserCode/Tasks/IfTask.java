package UserCode.Tasks;

import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import FrontEnd.Ui;

public class IfTask extends Task {

    public IfTask(Condition condition, Action action) {
        super(condition, action);
    }

    @Override
    public void execute(Ui ui) {
        {
            if (condition.check()) {
                action.execute(ui);
            } else {
                ui.show("Condition not fulfilled, not executing task!");
            }
        }
    }
}