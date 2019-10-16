package util.factories;


import models.task.Task;
import models.task.TaskState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskFactory {

    /**
     * Method to create a new task and put it into a list.
     * @param input Input containing the information about the task and priority.
     * @return Task as an object
     */
    public Task createTask(String input) throws ParseException {
        String [] taskDetails = input.split("[tpdcsr]\\/");
        Task newTask = null;
        if (!input.contains(" d/") && !input.contains(" s/")) {
            if (taskDetails.length == 4) {
                newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                        null, Integer.parseInt(taskDetails[3].trim()), TaskState.OPEN,
                        null);
            } else {
                ArrayList<String> taskRequirements = parseTaskRequirements(taskDetails, 4);
                newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                        null, Integer.parseInt(taskDetails[3].trim()), TaskState.OPEN,
                        taskRequirements);
            }

        } else if (input.contains(" d/") && !input.contains(" s/")) {
            if (taskDetails.length == 5) {
                Date dueDate = getDateObject(taskDetails[3]);
                newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                        dueDate, Integer.parseInt(taskDetails[4].trim()), TaskState.OPEN, null);
            } else {
                Date dueDate = getDateObject(taskDetails[3]);
                ArrayList<String> taskRequirements = parseTaskRequirements(taskDetails, 5);
                newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                        dueDate, Integer.parseInt(taskDetails[4].trim()), TaskState.OPEN, taskRequirements);
            }
        } else if (!input.contains(" d/") && input.contains(" s/")) {
            if (taskDetails.length == 5) {
                TaskState newTaskState = convertStringToTaskState(taskDetails[4]);
                newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                        null, Integer.parseInt(taskDetails[3].trim()), newTaskState, null);
            } else {
                TaskState newTaskState = convertStringToTaskState(taskDetails[4]);
                ArrayList<String> taskRequirements = parseTaskRequirements(taskDetails, 5);
                newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                        null, Integer.parseInt(taskDetails[3].trim()), newTaskState, taskRequirements);
            }
        } else if (input.contains(" d/") && input.contains(" s/")) {
            if (taskDetails.length == 6) {
                Date dueDate = getDateObject(taskDetails[3]);
                TaskState newTaskState = convertStringToTaskState(taskDetails[5].trim());
                newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                        dueDate, Integer.parseInt(taskDetails[4].trim()), newTaskState, null);
            } else {
                Date dueDate = getDateObject(taskDetails[3]);
                TaskState newTaskState = convertStringToTaskState(taskDetails[5].trim());
                ArrayList<String> taskRequirements = parseTaskRequirements(taskDetails, 6);
                newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                        dueDate, Integer.parseInt(taskDetails[4].trim()), newTaskState, taskRequirements);
            }

        }
        return newTask;
    }

    /**
     * Creates ArrayList of Strings containing task requirements.
     * @param taskDetails  Details of new task.
     * @param startIndexOfTaskRequirements index from which the content in taskDetails will be task requirements.
     * @return ArrayList of type String containing task requirements.
     */
    private ArrayList<String> parseTaskRequirements(String[] taskDetails, int startIndexOfTaskRequirements) {
        ArrayList<String> taskRequirements = new ArrayList<String>();
        for (int i = startIndexOfTaskRequirements; i < taskDetails.length; i++) {
            taskRequirements.add(taskDetails[i].trim());
        }
        return taskRequirements;
    }

    private Date getDateObject(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.parse(dateString);
    }

    /**
     * Converts string input for state into enum TaskState object.
     * @param inputState String input of state.
     * @return enum TaskState object.
     */
    private TaskState convertStringToTaskState(String inputState) {
        switch (inputState) {
        case "done":
            return TaskState.DONE;
        case "todo":
            return TaskState.TODO;
        case "doing":
            return TaskState.DOING;
        default:
            return TaskState.OPEN;
        }
    }
}
