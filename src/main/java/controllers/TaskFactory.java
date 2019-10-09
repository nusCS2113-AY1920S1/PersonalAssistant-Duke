package controllers;


import models.task.Task;
import models.task.TaskState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskFactory {

    /**
     * Method to create a new task and put it into a list.
     * @param input Input containing the information about the task and priority.
     * @return Task as an object
     */
    public Task createTask(String input) throws ParseException {
        String [] taskDetails = input.split("[tpdcs]\\/");
        Task newTask = null;
        if (taskDetails.length == 4) {
            newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                        null, Integer.parseInt(taskDetails[3].trim()), TaskState.OPEN);
        } else if (taskDetails.length == 5 && input.contains(" d/")) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
            Date dueDate = formatter.parse(taskDetails[3]);
            newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                                dueDate, Integer.parseInt(taskDetails[4].trim()), TaskState.OPEN);
        } else if (taskDetails.length == 5 && input.contains(" s/")) {
            TaskState newTaskState = convertStringToTaskState(taskDetails[4]);
            newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                        null, Integer.parseInt(taskDetails[3].trim()), newTaskState);
        } else if (taskDetails.length == 6) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
            Date dueDate = formatter.parse(taskDetails[3]);
            TaskState newTaskState = convertStringToTaskState(taskDetails[5].trim());
            newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                                dueDate, Integer.parseInt(taskDetails[4].trim()), newTaskState);
        }
        return newTask;
    }

    /**
     * Converts string input for state into enum TaskState object.
     * @param inputState String input of state.
     * @return enum TaskState object.
     */
    public TaskState convertStringToTaskState(String inputState) {
        if (inputState.equals("done")) {
            return TaskState.DONE;
        } else if (inputState.equals("todo")) {
            return TaskState.TODO;
        } else if (inputState.equals("doing")) {
            return TaskState.DOING;
        } else {
            return TaskState.OPEN;
        }
    }
}
