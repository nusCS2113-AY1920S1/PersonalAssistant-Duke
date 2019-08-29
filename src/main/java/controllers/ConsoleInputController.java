package controllers;

import exceptions.DukeException;
import models.ITask;
import models.TaskList;
import views.CLIView;

import java.io.*;

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

            out.flush();
            out.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData() {
        try {
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(file);

            this.taskList = (TaskList) in.readObject();

            in.close();
            file.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.getSuppressed();
        }
    }
}
