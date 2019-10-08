package models.task;

import models.member.Member;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class TaskList {
    private PriorityQueue<Task> taskList;

    /**
     * Class representing a list with all task sort from the highest priority to the lowest.
     */
    public TaskList() {
        Comparator<Task> taskPriority = (task1, task2) -> {
            if (task1.getTaskPriority() < task2.getTaskPriority()) {
                return 1;
            } else if (task1.getTaskPriority() > task2.getTaskPriority()) {
                return -1;
            }
            return 0;
        };

        this.taskList = new PriorityQueue<>(taskPriority);
    }

    /**
     * Adds a new task to the list of this project.
     * @param task A new task to be added to the project.
     */
    public void addTask(Task task){
        taskList.add(task);
    }

    /**
     * Returns an ArrayList with String descriptions of task details.
     * @return An ArrayList with String descriptions of task details.
     */
    public ArrayList<String> getAllTaskDetails() {
        ArrayList<String> taskDetails = new ArrayList<>();
        for (Task task : taskList) {
            taskDetails.add(task.getDetails());
        }
        return taskDetails;
    }
}
