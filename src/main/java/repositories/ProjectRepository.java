package repositories;

import exceptions.DukeException;
import models.project.IProject;
import models.project.Project;
import util.factories.ProjectFactory;
import util.json.JsonConverter;
import util.log.ArchDukeLogger;

import java.util.ArrayList;
import java.util.HashMap;

//@@author Lucria
public class ProjectRepository implements IRepository<Project> {
    private ArrayList<Project> allProjects;
    private ProjectFactory projectFactory = new ProjectFactory();
    private JsonConverter jsonConverter = new JsonConverter();

    /**
     * Constructor of ProjectRepository.
     * It first gets resources from packaged jar.
     * Next, it loads any projects data in the current working directory.
     * Lastly, it adds any resources found in packaged jar into the data found in current working directory.
     */
    public ProjectRepository() {
        ArrayList<Project> projectsFromResource = jsonConverter.getResourcesInJar();
        allProjects = jsonConverter.loadAllProjectsData();
        allProjects.addAll(projectsFromResource);
        allProjects.forEach((i) -> jsonConverter.saveProject(i));
    }

    @Override
    public ArrayList<Project> getAll() {
        return allProjects;
    }

    @Override
    public boolean addToRepo(String input) {
        IProject newProject = projectFactory.create(input);
        ArchDukeLogger.logDebug(ProjectRepository.class.getName(), "New project created with name: '"
                + newProject.getName() + "'");
        if (newProject.getName() == null || newProject.getMemberList() == null) {
            return false;
        }
        Project newlyCreatedProject = (Project) newProject;
        allProjects.add(newlyCreatedProject);
        jsonConverter.saveProject(newlyCreatedProject);
        return true;
    }

    /**
     * Method to retrieve a Project from ArrayList of Projects.
     * @param indexNumber : Index of Project that user wishes to retrieve
     * @return Returns the Project object desired by user
     */
    public Project getItem(int indexNumber) {
        return this.allProjects.get(indexNumber - 1);
    }

    /**
     * Method responsible for deleting an old JSON and creating a new JSON file after a Project renames itself.
     * @param project : Project that is being renamed.
     * @param input : New name for the Project.
     * @return : Returns a boolean flag stating whether the deletion and recreation of JSon was successful or not.
     */
    public boolean updateItem(Project project, String input) {
        try {
            jsonConverter.deleteProject(project);
        } catch (DukeException e) {
            return false;
        }
        project.setName(input);
        jsonConverter.saveProject(project);
        return true;
    }

    /**
     * Method to force save an Object to the Data layer.
     * @param project : Object to be saved.
     */
    public void saveToRepo(Project project) {
        jsonConverter.saveProject(project);
    }

    /**
     * Method for deletion of projects.
     * @param indexNumber : Index of project that user wishes to delete
     * @return Returns a boolean that states whether the project is deleted successfully
     */
    public boolean deleteItem(int indexNumber) {
        try {
            jsonConverter.deleteProject(allProjects.get(indexNumber - 1));
            this.allProjects.remove(indexNumber - 1);
            return true;
        } catch (IndexOutOfBoundsException err) {
            return false;
        } catch (DukeException err) {
            this.allProjects.remove(indexNumber - 1);
            return false;
        }
    }

    //@@author seanlimhx
    /**
     * Method to get all project details in a suitable form for CLIView to print in a table form.
     * @return ArrayList of details to be presented in each table, with each element as an ArrayList
     *         containing each row in the table.
     */
    public ArrayList<ArrayList<String>> getAllProjectsDetailsForTable() {
        ArrayList<ArrayList<String>> responseModel = new ArrayList<>();
        for (int projNum = 0; projNum < allProjects.size(); projNum++) {
            ArrayList<String> toPrint = new ArrayList<>();
            toPrint.add("Project " + (projNum + 1) + ": " + allProjects.get(projNum).getName());
            toPrint.add("Members: ");
            if (allProjects.get(projNum).getNumOfMembers() == 0) {
                toPrint.add(" --");
            } else {
                for (int memberIndex = 1; memberIndex <= allProjects.get(projNum).getNumOfMembers(); memberIndex++) {
                    toPrint.add(" " + allProjects.get(projNum).getMemberList().getMember(memberIndex).getDetails());
                }
                toPrint.add("");
            }
            if (allProjects.get(projNum).getNumOfTasks() == 0) {
                toPrint.add("Next Deadline: ");
                toPrint.add(" --");
            } else {
                String[] detailsClosestDeadlineTask = allProjects.get(projNum).getTaskList().getClosestDeadlineTask();
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
                String[] detailsOverallProgress = allProjects.get(projNum).getTaskList().getOverallProgress();
                for (String detail : detailsOverallProgress) {
                    toPrint.add(" - " + detail);
                }
            }
            responseModel.add(toPrint);
        }
        return responseModel;
    }

    //@@author Lucria
    /**
     * Method for getting the details about the currently managed Project for ProjectInputController.
     * @param selectedProject : current Project that is being managed
     * @return : Returns an ArrayList of String for printing in a user friendly format
     */
    public ArrayList<String> getProjectDetailsForTable(Project selectedProject) {
        ArrayList<String> responseModel = new ArrayList<>();
        responseModel.add("Project " + (allProjects.indexOf(selectedProject) + 1) + ": " + selectedProject.getName());
        responseModel.add("Members: ");
        if (selectedProject.getNumOfMembers() == 0) {
            responseModel.add(" --");
        } else {
            for (int memberIndex = 1; memberIndex <= selectedProject.getNumOfMembers(); memberIndex++) {
                responseModel.add(" " + selectedProject.getMemberList().getMember(memberIndex).getDetails());
            }
            responseModel.add("");
        }
        responseModel.add("Tasks:");
        if (selectedProject.getNumOfTasks() == 0) {
            responseModel.add(" --");
            responseModel.add("Next Deadline: ");
            responseModel.add(" --");
        } else {
            for (int taskIndex = 1; taskIndex <= selectedProject.getNumOfTasks(); taskIndex++) {
                responseModel.add(" " + taskIndex + ". " + selectedProject.getTask(taskIndex).getDetails());
            }
            responseModel.add("");
            String[] detailsClosestDeadlineTask = selectedProject.getTaskList().getClosestDeadlineTask();
            responseModel.add("Next Deadline: " + detailsClosestDeadlineTask[0]);
            for (int i = 1; i < detailsClosestDeadlineTask.length; i++) {
                responseModel.add(" - " + detailsClosestDeadlineTask[i]);
            }
            responseModel.add("");
        }
        responseModel.add("Overall Progress: ");
        if (selectedProject.getNumOfTasks() == 0) {
            responseModel.add(" --");
        } else {
            String[] detailsOverallProgress = selectedProject.getTaskList().getOverallProgress();
            for (String detail : detailsOverallProgress) {
                responseModel.add(" - " + detail);
            }
        }
        return responseModel;
    }

    public HashMap<Integer, Integer> getAllTasksInCurrentMonth(Project project) {
        return project.getTaskList().getTasksWithinCurrentMonth();
    }
}
