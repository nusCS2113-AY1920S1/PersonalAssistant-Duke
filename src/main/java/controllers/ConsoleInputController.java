package controllers;

import controllers.temp.TaskFactory;
import exceptions.DukeException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import models.data.IProject;
import models.temp.tasks.ITask;
import models.temp.tasks.TaskList;
import repositories.ProjectRepository;
import views.CLIView;

public class ConsoleInputController implements IViewController {

    private CLIView consoleView;
    private TaskFactory taskFactory;
    private TaskList taskList;
    private String filePath = "src/main/saves/savefile.txt";
    private ProjectRepository projectRepository;
    private ProjectInputController projectInputController;

    /**
     * Constructor.
     * @param view : takes in a View model, in this case a command line view.
     */
    public ConsoleInputController(CLIView view) {
        this.consoleView = view;
        this.taskFactory = new TaskFactory();
        this.taskList = new TaskList();
        this.projectRepository = new ProjectRepository();
        this.projectInputController = new ProjectInputController(this.consoleView, this.projectRepository);
    }

    /**
     * Method that is called upon receiving commands from CLI.
     * @param input : Input typed by user into CLI
     */
    @Override
    public void onCommandReceived(String input) {
        Scanner inputReader = new Scanner(input);
        String command = inputReader.next();

        switch (command) {
        case "bye":
            consoleView.end();
            break;
        case "list":
            consoleView.printAllTasks(taskList);
            break;
        case "done":
        case "delete":
            if (inputReader.hasNext()) {
                switch (command) {
                case "done":
                    consoleView.markDone(taskList, input);
                    saveData();
                    break;
                case "delete":
                    consoleView.deleteTask(taskList, input);
                    saveData();
                    break;
                default:
                }
            } else {
                consoleView.consolePrint("Oops! Please enter task number.");
            }
            break;
        case "find":
            try {
                consoleView.findTask(taskList, input);
            } catch (ArrayIndexOutOfBoundsException newException) {
                consoleView.invalidCommandMessage(newException);
            }
            break;
        case "confirm":
            try {
                consoleView.confirmTentativeTask(taskList, input);
            } catch (ClassCastException e) {
                System.out.println("This task is already confirmed!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Please input an available index!");
            }
            break;
        case "event":
        case "todo":
        case "deadline":
        case "doafter":
        case "tentative":
            try {
                ITask newTask = taskFactory.createTask(input);
                boolean anomaly = taskList.addToList(newTask);
                consoleView.addMessage(newTask, taskList, anomaly);
                saveData();
            } catch (DukeException newException) {
                consoleView.invalidCommandMessage(newException);
            }
            break;
        case "create":
            // Creation of a new project with a given name and a number of numbers
            boolean isProjectCreated = projectRepository.addToRepo(input);
            if (!isProjectCreated) {
                consoleView.consolePrint("Creation of Project failed. Please check parameters given!");
            } else {
                consoleView.consolePrint("Project created!");
            }
            break;
        case "manage":
            if (inputReader.hasNext()) {
                this.projectInputController.manageProject(inputReader.next());
            } else {
                consoleView.consolePrint("Please enter a project number!");
            }
            break;
        case "view":
            ArrayList<IProject> allProjects = projectRepository.getAll();
            consoleView.viewAllProjects(allProjects);
            break;
        default:
            consoleView.consolePrint("Invalid inputs. Please refer to User Guide or type help!");
        }
    }
    // TODO refactor saving data and reading data to repository/database

    /**
     * Method that is called in order to saveData to persistent storage.
     */
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

    /**
     * Method that is called when reading data from persistent storage.
     * If file read is empty, will throw EOFException which is suppressed.
     * If file read is corrupted, will throw IOException which is suppressed also.
     */
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
