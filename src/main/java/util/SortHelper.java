package util;

import models.task.Task;

import java.util.ArrayList;
import java.util.Comparator;

public class SortHelper {
    public static ArrayList<String> sortTaskName(ArrayList<Task> taskList) {
        ArrayList<String> taskDetails = new ArrayList<>();
        taskList.sort(Comparator.comparing(Task::getTaskName));
        int taskIndex = 1;
        for (Task task : taskList) {
            taskDetails.add(taskIndex + ". " + task.getDetails());
            taskIndex++;
        }
        return taskDetails;
    }

    public static ArrayList<String> sortTaskIndex(ArrayList<Task> taskList) {
        ArrayList<String> taskDetails = new ArrayList<>();
        /*
            Empty method
         */
        return taskDetails;
    }

    public static ArrayList<String> sortTaskDueDate(ArrayList<Task> taskList) {
        ArrayList<String> taskDetails = new ArrayList<>();
        /*
            Empty method
         */
        return taskDetails;
    }

    public static ArrayList<String> sortTaskPriority(ArrayList<Task> taskList) {
        ArrayList<String> taskDetails = new ArrayList<>();
        taskList.sort((task1, task2) -> task2.getTaskPriority() - task1.getTaskPriority());
        int taskIndex = 1;
        for (Task task : taskList) {
            taskDetails.add(taskIndex + ". " + task.getDetails());
            taskIndex++;
        }
        return taskDetails;
    }

    public static ArrayList<String> sortTaskCredit(ArrayList<Task> taskList) {
        ArrayList<String> taskDetails = new ArrayList<>();
        taskList.sort((task1, task2) -> task2.getTaskCredit() - task1.getTaskCredit());
        int taskIndex = 1;
        for (Task task : taskList) {
            taskDetails.add(taskIndex + ". " + task.getDetails());
            taskIndex++;
        }
        return taskDetails;
    }

    public static ArrayList<String> sortTaskMembers(ArrayList<Task> taskList) {
        ArrayList<String> taskDetails = new ArrayList<>();
        /*
            Empty method
         */
        return taskDetails;
    }

    public static ArrayList<String> sortTaskState(ArrayList<Task> taskList, String state) {
        ArrayList<String> taskDetails = new ArrayList<>();
        int taskIndex = 1;
        for (Task task : taskList) {
            if (state.equals(task.getTaskState().toString())) {
                taskDetails.add(taskIndex + ". " + task.getDetails());
            }
            taskIndex++;
        }
        return taskDetails;
    }
}
