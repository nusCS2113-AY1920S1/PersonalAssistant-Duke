package controllers;

import exceptions.DukeException;
import models.ITask;
import models.TaskList;
import views.CLIView;

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
    public void onCommandReceived(String input) {
        if (input.equals("bye")) {
            consoleView.end();
        } else if (input.equals("list")) {
            consoleView.printAllTasks(taskList);
        } else if (input.contains("done")) {
            consoleView.markDone(taskList, input);
        } else {
            // TODO refactor this to repository (3T architecture)
            try {
                ITask newTask = taskFactory.createTask(input);
                taskList.addToList(newTask);
                consoleView.addMessage(newTask, taskList);
            } catch (DukeException newException) {
                consoleView.invalidCommandMessage(newException);
            }
        }
    }
}
