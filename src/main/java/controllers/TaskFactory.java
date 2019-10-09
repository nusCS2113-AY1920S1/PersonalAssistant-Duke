package controllers;

import models.task.State;
import models.task.Task;

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
                        null, Integer.parseInt(taskDetails[3].trim()), State.OPEN);
        } else if (taskDetails.length == 5 && input.contains(" d/")) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
            Date dueDate = formatter.parse(taskDetails[3]);
            newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                                dueDate, Integer.parseInt(taskDetails[4].trim()), State.OPEN);
        } else if (taskDetails.length == 5 && input.contains(" s/")) {
            State newState = convertStringToState(taskDetails[4]);
            newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                        null, Integer.parseInt(taskDetails[3].trim()), newState);
        } else if (taskDetails.length == 6) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
            Date dueDate = formatter.parse(taskDetails[3]);
            State newState = convertStringToState(taskDetails[5].trim());
            newTask = new Task(taskDetails[1].trim(), Integer.parseInt(taskDetails[2].trim()),
                                dueDate, Integer.parseInt(taskDetails[4].trim()), newState);
        }
        return newTask;
    }

    /**
     * Converts string input for state into enum State object.
     * @param inputState String input of state.
     * @return enum State object.
     */
    public State convertStringToState(String inputState) {
        if (inputState.equals("done")) {
            return State.DONE;
        } else if (inputState.equals("todo")) {
            return State.TODO;
        } else if (inputState.equals("doing")) {
            return State.DOING;
        } else {
            return State.OPEN;
        }
    }
}
