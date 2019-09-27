package Task;

import Actions.Action;
import Places.Farm;
import UserInterfaces.Ui;

public class Task {
    protected Condition condition;
    protected Action action;

    public Task(Condition condition, Action action) {
        this.condition = condition;
        this.action = action;
    }

    boolean checkCondition() {
        return false;
    }

    public int execute() {
        return action.execute();
    }
}
