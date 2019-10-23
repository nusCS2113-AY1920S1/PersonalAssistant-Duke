package duke.tasklist;
import duke.task.Task;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class MyDayList {
    private ArrayList<Task> taskList;
    public MyDayList() {
        taskList = new ArrayList<Task>();
    }

    public void add(Task task) {
        taskList.add(task);
        System.out.println("Congratulations! You have added a task to Your Day");
        int taskCount = taskList.size();
        if (taskCount == 1) {
            System.out.println("Now you have " + taskCount + " task in the list.");
        } else {
            System.out.println("Now you have " + taskCount + " tasks in the list.");
        }
    }

    public void refreshMyDayList() {
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            if(ChronoUnit.DAYS.between(t.getCreatedDate(), LocalDateTime.now()) > 0) {
                taskList.remove(i);
            }
        }
    }
    //TODO discuss implementation
}
