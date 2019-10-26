package controllers;

import repositories.ProjectRepository;
import util.log.DukeLogger;
import views.CLIView;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleInputController implements IController {

    private CLIView consoleView;
    private ProjectRepository projectRepository;
    private ProjectInputController projectInputController;

    /**
     * Constructor.
     * @param view : takes in a View model, in this case a command line view.
     */
    public ConsoleInputController(CLIView view) {
        this.consoleView = view;
        this.projectRepository = new ProjectRepository();
        this.projectInputController = new ProjectInputController(this.consoleView, this.projectRepository);
    }

    /**
     * Method that is called upon receiving commands from CLI.
     * @param input : Input typed by user into CLI
     */
    @Override
    public void onCommandReceived(String input) {
        DukeLogger.logInfo(ConsoleInputController.class, "input:'" + input + "'");
        Scanner inputReader = new Scanner(input);
        String command = inputReader.next();

        switch (command) {
        case "bye":
            consoleView.end();
            break;
        case "create":
            commandCreate(input);
            break;
        case "list":
            commandList();
            break;
        case "manage":
            commandManage(inputReader);
            break;
        case "delete":
            commandDelete(inputReader);
            break;
        case "help":
            commandHelp();
            break;
        default:
            consoleView.consolePrint("Invalid inputs. Please refer to User Guide or type help!");
            break;
        }
    }

    /**
     * Creates a new project with a given name and a number of numbers.
     * @param input To read the input from the user.
     */
    private void commandCreate(String input) {
        boolean isProjectCreated = projectRepository.addToRepo(input);
        if (!isProjectCreated) {
            consoleView.consolePrint("Creation of Project failed. Please check parameters given!");
        } else {
            consoleView.consolePrint("Project created!");
        }
    }

    /**
     * Lists all the projects.
     */
    private void commandList() {
        ArrayList<ArrayList<String>> allProjectsDetails = projectRepository.getAllProjectsDetailsForTable();
        consoleView.viewAllProjects(allProjectsDetails);
    }

    /**
     * Manage the project.
     * @param inputReader To read the input from the user.
     */
    private void commandManage(Scanner inputReader) {
        if (inputReader.hasNext()) {
            this.projectInputController.onCommandReceived(inputReader.next());
        } else {
            consoleView.consolePrint("Please enter a project number!");
        }
    }

    /**
     * Deletes a project.
     * @param inputReader To read the input from the user.
     */
    private void commandDelete(Scanner inputReader) {
        if (inputReader.hasNext()) {
            int projectIndex = Integer.parseInt(inputReader.next());
            boolean isProjectDeleted = this.projectRepository.deleteItem(projectIndex);
            if (isProjectDeleted) {
                consoleView.consolePrint("Project " + projectIndex + " has been deleted");
            } else {
                consoleView.consolePrint("Index out of bounds! Please check project index!");
            }
        } else {
            consoleView.consolePrint("Please enter a project number to delete");
        }
    }

    /**
     * Displays the set of the commands which can be used.
     */
    private void commandHelp() {
        // TODO help page displaying all commands available
        // Not implemented
    }
}
