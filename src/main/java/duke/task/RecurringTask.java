package duke.task;

interface RecurringTask {
    default RecurringTask(String name) {
        super(name);
        type = 'R';
    }


}
