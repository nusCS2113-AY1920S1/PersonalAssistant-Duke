package UserCode.Tasks;

import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import UserCode.Conditions.ConditionChecker;
import UserInterfaces.Ui;

public class IfTask extends Task {

    public IfTask(Condition condition, Action action) {
        super(condition, action);
    }

    @Override
    public int execute(Ui ui) {
        {
            if (checkCondition()) {
                return action.execute(ui);
            } else {
                return 0;
            }
        }
    }
}