package repositories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controllers.ConsoleInputController;
import models.data.IProject;
import models.data.Project;
import util.factories.ProjectFactory;
import util.log.DukeLogger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectRepository implements IRepository<Project> {
    private ArrayList<Project> allProjects;
    private ProjectFactory projectFactory;
    private String filePath = System.getProperty("user.dir") + "/savedProjects.json";

    public ProjectRepository() {
        loadProjectsData();
        this.projectFactory = new ProjectFactory();
    }

    @Override
    public ArrayList<Project> getAll() {
        return allProjects;
    }

    @Override
    public boolean addToRepo(String input) {
        IProject newProject = projectFactory.create(input);
        DukeLogger.logDebug(ProjectRepository.class, "New project created with name: '"
                + newProject.getDescription() + "'");
        if (newProject.getDescription() == null || newProject.getMembers() == null) {
            return false;
        }
        Project newlyCreatedProject = (Project) newProject;
        allProjects.add(newlyCreatedProject);
        saveProjectsData();
        return true;
    }

    /**
     * Method to retrieve a Project from ArrayList of Projects.
     * @param projectNumber : Index of Project that user wishes to retrieve
     * @return Returns the Project object desired by user
     */
    public Project getItem(int projectNumber) {
        return this.allProjects.get(projectNumber - 1);
    }

    /**
     * Method for deletion of projects.
     * @param projectNumber : Index of project that user wishes to delete
     * @return Returns a boolean that states whether the project is deleted successfully
     */
    public boolean deleteItem(int projectNumber) {
        try {
            this.allProjects.remove(projectNumber - 1);
            saveProjectsData();
            return true;
        } catch (IndexOutOfBoundsException err) {
            saveProjectsData();
            return false;
        }
    }

    /**
     * Method to get all project details in a suitable form for CLIView to print in a table form.
     * @return ArrayList of details to be presented in each table, with each element as an ArrayList
     *         containing each row in the table.
     */
    public ArrayList<ArrayList<String>> getAllProjectsDetailsForTable() {
        ArrayList<ArrayList<String>> toPrintAll = new ArrayList<>();
        for (int projNum = 0; projNum < allProjects.size(); projNum++) {
            ArrayList<String> toPrint = new ArrayList<>();
            toPrint.add("Project " + (projNum + 1) + ": " + allProjects.get(projNum).getDescription());
            toPrint.add("Members: ");
            if (allProjects.get(projNum).getNumOfMembers() == 0) {
                toPrint.add(" --");
            } else {
                for (int memberIndex = 1; memberIndex <= allProjects.get(projNum).getNumOfMembers();memberIndex++) {
                    toPrint.add(" " + allProjects.get(projNum).getMembers().getMember(memberIndex).getDetails());
                }
                toPrint.add("");
            }
            if (allProjects.get(projNum).getNumOfTasks() == 0) {
                toPrint.add("Next Deadline: ");
                toPrint.add(" --");
            } else {
                String[] detailsClosestDeadlineTask = allProjects.get(projNum).getTasks().getClosestDeadlineTask();
                toPrint.add("Next Deadline: " + detailsClosestDeadlineTask[0]);
                for (int i = 1; i < detailsClosestDeadlineTask.length; i++) {
                    toPrint.add(" - " + detailsClosestDeadlineTask[i]);
                }
                toPrint.add("");
            }
            toPrint.add("Overall Progress: ");
            if (allProjects.get(projNum).getNumOfTasks() == 0) {
                toPrint.add(" --");
            } else {
                String[] detailsOverallProgress = allProjects.get(projNum).getTasks().getOverallProgress();
                for (String detail : detailsOverallProgress) {
                    toPrint.add(" - " + detail);
                }
            }
            toPrintAll.add(toPrint);
        }
        return toPrintAll;
    }

    /**
     * Method that is responsible for saving Projects Data by using GSON library to convert to a human editable JSON
     * file.
     */
    private void saveProjectsData() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            DukeLogger.logDebug(ConsoleInputController.class, "Saving to file.");
            FileWriter fileWriter = new FileWriter(filePath);
            gson.toJson(this.allProjects, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            DukeLogger.logDebug(ConsoleInputController.class, "File saved.");
        } catch (IOException err) {
            DukeLogger.logError(ConsoleInputController.class, "savedProjects file is not found.");
        }
    }

    /**
     * Method responsible for loading Projects Data from hard coded directory where savedProjects.json file is located
     */
    private void loadProjectsData() {
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader(filePath)) {
            DukeLogger.logDebug(ConsoleInputController.class, "Loading saved file.");
            this.allProjects = gson.fromJson(fileReader, new TypeToken<ArrayList<Project>>(){}.getType());
            if (this.allProjects == null) {
                this.allProjects = new ArrayList<>();
            }
            DukeLogger.logDebug(ConsoleInputController.class, "Saved file loaded.");
        } catch (IOException err) {
            DukeLogger.logError(ConsoleInputController.class, "Saved file not loaded");
            this.allProjects = new ArrayList<>();
        }
    }
}
