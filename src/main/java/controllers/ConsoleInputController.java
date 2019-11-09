package controllers;

import static util.constant.ConstantHelper.DEFAULT_HORI_BORDER_LENGTH;
import static util.constant.ConstantHelper.PROJECT_COMMAND_BYE;
import static util.constant.ConstantHelper.PROJECT_COMMAND_CREATE;
import static util.constant.ConstantHelper.PROJECT_COMMAND_DELETE;
import static util.constant.ConstantHelper.PROJECT_COMMAND_HELP;
import static util.constant.ConstantHelper.PROJECT_COMMAND_LIST;
import static util.constant.ConstantHelper.PROJECT_COMMAND_MANAGE;

import java.util.ArrayList;
import java.util.Scanner;
import repositories.ProjectRepository;
import util.log.ArchDukeLogger;
import util.uiformatter.CommandHelper;
import util.uiformatter.ViewHelper;

public class ConsoleInputController implements IController {

    private ProjectRepository projectRepository;
    private String managingProjectIndex;
    private ViewHelper viewHelper;
    private CommandHelper commandHelper;

    //@@author Lucria
    /**
     * Constructor.
     * @param projectRepository : takes in a projectRepository.
     */
    public ConsoleInputController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.managingProjectIndex = "";
        this.viewHelper = new ViewHelper();
        this.commandHelper = new CommandHelper();
    }

    /**
     * Method that is called upon receiving commands from CLI.
     * @param input : Input typed by user into CLI
     */
    @Override
    public String[] onCommandReceived(String input) {
        try {
            ArchDukeLogger.logInfo(ConsoleInputController.class.getName(), "User input: '" + input + "'");
            Scanner inputReader = new Scanner(input);
            String command;
            if (inputReader.hasNext()) {
                command = inputReader.next();
            } else {
                return new String[] {"No input detected! Type \"help\" for a list of commands!"};
            }

            switch (command) {
            case PROJECT_COMMAND_BYE:
                return end();
            case PROJECT_COMMAND_CREATE:
                return commandCreate(input);
            case PROJECT_COMMAND_LIST:
                return commandList();
            case PROJECT_COMMAND_MANAGE:
                return commandManage(inputReader);
            case PROJECT_COMMAND_DELETE:
                return commandDelete(inputReader);
            case PROJECT_COMMAND_HELP:
                return commandHelp();
            default:
                return new String[] {"Invalid inputs. Please refer to User Guide or type help!"};
            }
        } catch (NullPointerException err) {
            return new String[] {"Please delete any corrupted .json saved data and try again!"};
        }
    }

    //@@author Lucria
    /**
     * Creates a new project with a given name and a number of numbers.
     * @param input To read the input from the user.
     */
    private String[] commandCreate(String input) {
        ArchDukeLogger.logDebug(ConsoleInputController.class.getName(), "[commandCreate] User input: '" + input + "'");
        boolean isProjectCreated = projectRepository.addToRepo(input);
        if (!isProjectCreated) {
            return new String[] {"Creation of Project failed. Please check parameters given!"};
        } else {
            return new String[] {"Project created!"};
        }
    }

    /**
     * Method called when users wishes to view all Projects
     * that are currently created or stored.
     */
    private String[] commandList() {
        ArchDukeLogger.logDebug(ConsoleInputController.class.getName(), "[commandList]");
        ArrayList<ArrayList<String>> allProjectsDetails = projectRepository.getAllProjectsDetailsForTable();
        if (allProjectsDetails.size() == 0) {
            return new String[] {"You currently have no projects!"};
        } else {
            return viewHelper.consolePrintMultipleTables(allProjectsDetails, DEFAULT_HORI_BORDER_LENGTH,
                    1, "Here are all the Projects you are managing:");
        }
    }

    /**
     * Manage the project.
     * @param inputReader To read the input from the user.
     */
    private String[] commandManage(Scanner inputReader) {
        ArchDukeLogger.logDebug(ConsoleInputController.class.getName(), "[commandManage] User input: " + inputReader);
        if (inputReader.hasNext()) {
            this.managingProjectIndex = inputReader.next();
            try {
                ArchDukeLogger.logInfo(ConsoleInputController.class.getName(), "Managing project: "
                        + projectRepository.getItem(Integer.parseInt(managingProjectIndex)).getName());
                return new String[] {"Now managing "
                        + projectRepository.getItem(Integer.parseInt(managingProjectIndex)).getName()};
            } catch (IndexOutOfBoundsException err) {
                return new String[] {"Please enter the correct index of an existing Project!"};
            } catch (NumberFormatException err) {
                return new String[]
                {"The project \"" + managingProjectIndex + "\" does not exist!",
                 "Please ensure the project index number exists and is an integer."};
            }
        } else {
            return new String[] {"Please enter a project number!"};
        }
    }

    //@@author Lucria
    /**
     * Deletes a project.
     * @param inputReader To read the input from the user.
     */
    private String[] commandDelete(Scanner inputReader) {
        ArchDukeLogger.logDebug(ConsoleInputController.class.getName(), "[commandDelete] User input: " + inputReader);
        if (inputReader.hasNext()) {
            String projectInput = inputReader.next();
            try {
                int projectIndex = Integer.parseInt(projectInput);
                boolean isProjectDeleted = this.projectRepository.deleteItem(projectIndex);
                if (isProjectDeleted) {
                    return new String[]{"Project " + projectIndex + " has been deleted"};
                } else {
                    return new String[]{"Index out of bounds! Please check project index!"};
                }
            } catch (NumberFormatException err) {
                return new String[]
                {"Invalid project index: " + projectInput,
                 "Please ensure that the project number is an integer, and that it exists in the repo!"};
            }
        } else {
            return new String[] {"Please enter a project number to delete"};
        }
    }

    //@@author seanlimhx
    /**
     * Displays the set of the commands which can be used.
     */
    private String[] commandHelp() {
        return viewHelper.consolePrintTable(commandHelper.getCommandsForConsole(), DEFAULT_HORI_BORDER_LENGTH);
    }

    //@@author Lucria
    /**
     * Method to be called when user says bye to exit the program.
     */
    private String[] end() {
        ArchDukeLogger.logInfo(ConsoleInputController.class.getName(), "ArchDuke have stopped.");
        return new String[] { "Bye. Hope to see you again soon!" };
    }

    public String getManagingProjectIndex() {
        return managingProjectIndex;
    }

}
