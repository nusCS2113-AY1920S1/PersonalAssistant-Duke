package models.commands;

import models.tasks.ITask;
import models.tasks.TaskList;

public class DeleteCommand implements ICommand {

    private String input;

    public DeleteCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList taskList) {
        String[] allInputs = input.split(" ");
        System.out.println("\tNoted. I've removed this task:");
        System.out.print("\t  ");
        for (String i : allInputs) {
            if (!i.equals("delete")) {
                int index = Integer.parseInt(i) - 1;
                ITask chosenTask = taskList.getTask(index);
                taskList.deleteFromList(chosenTask);
                System.out.println("[" + chosenTask.getStatusIcon() + "] " + chosenTask.getDescription());
            }
        }
        String grammerTasks = taskList.getNumOfTasks() > 1 ? "tasks" : "task";
        System.out.println("\tNow you have " + taskList.getNumOfTasks() + " " + grammerTasks + " in the list.");
    }
}
