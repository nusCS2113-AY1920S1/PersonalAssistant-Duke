package UserCode.Tasks;

import UserCode.Actions.Action;
import UserCode.Conditions.Condition;

public class IfTask extends Task {

    public IfTask(Condition condition, Action action) {
        super(condition, action);
    }
}