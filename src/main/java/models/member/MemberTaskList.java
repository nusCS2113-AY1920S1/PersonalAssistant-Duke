package models.member;

import java.util.ArrayList;
import models.task.Task;
import models.task.TaskList;
import models.task.TaskState;

public class MemberTaskList {
    /**
     * Represents a list of tasks which are assigned to a member in a team.
     */
    private TaskList memberTaskList;

    public void addAssignedTask(Task task) {
        this.memberTaskList.addTask(task);
    }

    public int calculateTotalCreditEarned () {
        ArrayList<Task> allMemberTasks = this.memberTaskList.getTaskList();
        int totalCreditsEarned = 0;
        for (Task task : allMemberTasks) {
            if (task.getTaskState().equals(TaskState.DONE)) {
                totalCreditsEarned += task.getTaskCredit();
            }
        }
    }

}
