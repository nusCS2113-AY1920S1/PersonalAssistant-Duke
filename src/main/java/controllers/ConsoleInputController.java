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
            consoleView.printAllTasks(taskList);
        } else if (input.contains("done")) {
            consoleView.markDone(taskList, input);
        } else {
            ITask newTask = taskFactory.createTask(input);
            // TODO refactor this to repository (3T architecture)
            taskList.addToList(newTask);
            consoleView.addMessage(newTask, taskList);
        }
    }
}
