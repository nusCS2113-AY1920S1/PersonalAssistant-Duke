package leduc.exception;

import leduc.task.Task;

import java.util.ArrayList;

public class ConflictDateException extends DukeException {
    ArrayList<Task> tasks;
    public ConflictDateException(ArrayList<Task> tasks){
        super();
        this.tasks = tasks;
    }
    @Override
    public String print() {
        String conflictTasks = "";
        for (Task t : tasks){
            conflictTasks += "\n\t\t\t" + t.toString();
        }
        return "\t ConflictDateException:\n\t\t ☹ OOPS!!! There is a date conflict with this event :" +
                conflictTasks;
    }
}
