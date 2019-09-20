public class Task {
    protected String condition; //may change to enum in the future
    protected String task; //may change to enum in the future

    public Task(String condition, String task) {
        this.condition = condition;
        this.task = task;
    }

    boolean checkCondition() {
        return false;
    }

    public void execute() {

    }
}
