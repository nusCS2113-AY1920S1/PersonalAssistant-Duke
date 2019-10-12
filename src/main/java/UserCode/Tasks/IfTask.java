package UserCode.Tasks;

import UserCode.Actions.Action;
import UserCode.Condition;
import UserCode.Tasks.Task;

public class IfTask extends Task {

    public IfTask(Condition condition, Action action) {
        super(condition, action);
    }
}