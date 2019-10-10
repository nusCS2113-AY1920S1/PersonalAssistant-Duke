package dolla;

import dolla.command.Command;
import dolla.task.Task;
import dolla.task.TaskList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;


public abstract class Reminder extends Command {
    protected LocalDate today;

    //@Override

    /**
     * TODO: [placeholder].
     * @param tasks TODO: [placeholder]
     */
    public void execute(TaskList tasks) {
        ArrayList<Task> tasksDueSoon = new ArrayList<>();
        today = LocalDate.now();
        for (int i = 0; i < tasks.size(); i++) {
            Task currTask = tasks.getFromList(i); //get the task from the list
            LocalDate get = currTask.getDate().toLocalDate(); //get the time of the task
            LocalDate check = today.plusDays(2);//remind the user 2 days before
            if (check.compareTo(get) >= 0) {
                tasksDueSoon.add(currTask);
            }
        }
        tasksDueSoon.sort(Comparator.comparing(Task::getDate));

        ArrayList<String> msg = new ArrayList<String>();
        msg.add("REMINDER!!! COMING SOON TASKS:");
        for (int i = 0; i < tasksDueSoon.size(); i++) {
            msg.add((i + 1) + "."  + tasksDueSoon.get(i).getTask());
        }
        Ui.printMsg(msg);
    }
}
