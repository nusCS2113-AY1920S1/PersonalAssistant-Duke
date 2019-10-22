package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.data.Project;
import repositories.ProjectRepository;
import util.log.DukeLogger;
import views.CLIView;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleInputController implements IController {

    private CLIView consoleView;
    private ProjectRepository projectRepository;
    private ProjectInputController projectInputController;
    private String filePath = System.getProperty("user.dir") + "/savedProjects.json";

    /**
     * Constructor.
     * @param view : takes in a View model, in this case a command line view.
     */
    public ConsoleInputController(CLIView view) {
        this.consoleView = view;
        loadProjectsData();
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
        case "list":
            ArrayList<ArrayList<String>> allProjectsDetails = projectRepository.getAllProjectsDetailsForTable();
            consoleView.viewAllProjects(allProjectsDetails);
            break;
        case "create":
            // Creation of a new project with a given name and a number of numbers
            boolean isProjectCreated = projectRepository.addToRepo(input);
            if (!isProjectCreated) {
                consoleView.consolePrint("Creation of Project failed. Please check parameters given!");
            } else {
                consoleView.consolePrint("Project created!");
                saveProjectsData();
            }
            break;
        case "manage":
            if (inputReader.hasNext()) {
                this.projectInputController.onCommandReceived(inputReader.next());
            } else {
                consoleView.consolePrint("Please enter a project number!");
            }
            break;
        case "delete":
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
            break;
        case "help":
            // TODO help page displaying all commands available
            // Not implemented
            consoleView.consolePrint("Not implemented");
            break;
        default:
            consoleView.consolePrint("Invalid inputs. Please refer to User Guide or type help!");
            break;
        }
    }


    private void saveProjectsData() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            DukeLogger.logDebug(ConsoleInputController.class, "Saving to file.");
            FileWriter fileWriter = new FileWriter(filePath);
            gson.toJson(this.projectRepository, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            DukeLogger.logDebug(ConsoleInputController.class, "File saved.");
        } catch (IOException err) {
            DukeLogger.logError(ConsoleInputController.class, "savedProjects file is not found.");
            consoleView.consolePrint("savedProjects file does not exist or is not created!");
        }
    }

    private void loadProjectsData() {
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader(filePath)) {
            DukeLogger.logDebug(ConsoleInputController.class, "Loading saved file.");
            this.projectRepository = gson.fromJson(fileReader, ProjectRepository.class);
            if (this.projectRepository == null) {
                this.projectRepository = new ProjectRepository();
            }
            DukeLogger.logDebug(ConsoleInputController.class, "Saved file loaded.");
        } catch (IOException err) {
            DukeLogger.logError(ConsoleInputController.class, "Saved file not loaded");
            this.projectRepository = new ProjectRepository();
        }
    }
}
