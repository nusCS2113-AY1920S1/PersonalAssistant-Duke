package controllers;

import exceptions.DukeException;
import models.ITask;
import models.TaskList;
import views.CLIView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ConsoleInputController implements IViewController {

    private CLIView consoleView;
    private TaskFactory taskFactory;
    private TaskList taskList;
    private String filePath = ".\\src\\main\\java\\saves\\duke.txt";

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
            saveData();
        } else {
            // TODO refactor this to repository (3T architecture)
            try {
                ITask newTask = taskFactory.createTask(input);
                taskList.addToList(newTask);
                consoleView.addMessage(newTask, taskList);
                saveData();
            } catch (DukeException newException) {
                consoleView.invalidCommandMessage(newException);
            }
        }
    }

    public void saveData() {
        try {
            FileOutputStream file = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(taskList);

            out.close();
            file.close();
            System.out.println("Object has been serialized");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
