package controllers;

import models.ITask;
import models.TaskList;
import views.CLIView;

import java.io.IOException;

public class ConsoleInputController implements IViewController {

    private CLIView consoleView;
    private TaskFactory taskFactory;
    private TaskList taskList;

    public ConsoleInputController(CLIView view) {
        this.consoleView = view;
        this.taskFactory = new TaskFactory();
        this.taskList = new TaskList();
    }

    @Override
    public void onCommandReceived(String input) throws IOException {
        if (input.equals("bye")) {
            consoleView.end();
        } else if (input.equals("list")) {
            taskList.showAllTasks();
        } else if (input.contains("done")) {
            String[] allInputs = input.split(" ");
            System.out.println("Nice! I've marked this task as done:");

            for (String i : allInputs) {
                if (!i.equals("done")) {
                    int index = Integer.parseInt(i) - 1;
                    ITask chosenToDos = taskList.getTask(index);
                    chosenToDos.markAsDone();
                    System.out.println("[" + chosenToDos.getStatusIcon() + "] " + chosenToDos.getDescription());
                }
            }
        } else {
            ITask newTask = taskFactory.createTask(input);
            // TODO refactor this to repository (3T architecture)
            taskList.addToList(newTask);
            System.out.println("Got it. I've added this task:");
            System.out.print("\t");
            System.out.println("[" + newTask.getInitials() + "]"
                    + "[" + newTask.getStatusIcon() + "] "
                    + newTask.getDescription()
            );
            String grammerTasks = taskList.getNumOfTasks() > 1 ? "tasks" : "task";
            System.out.println("Now you have " + taskList.getNumOfTasks() + " " + grammerTasks + " in your list.");
        }
    }
}
