package controllers;

import models.task.Task;

public class TaskFactory {

    /**
     * Method to create a new task and put it into a list.
     * @param input Input containing the information about the task and priority.
     * @return Task as an object
     */
    public Task createTask(String input) {
        System.out.println(input);
        String [] taskDetails = input.split("[a-z]\\/");
        return new Task(taskDetails[1], Integer.parseInt(taskDetails[2]));
    }
}
