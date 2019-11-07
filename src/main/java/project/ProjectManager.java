package project;

import command.Storage;
import common.AlphaNUSException;
import payment.Payee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ProjectManager {
    public String currentprojectname;
    public LinkedHashMap<String, Project> projectmap;

    /**
     * Initialises a project manager instance to manage the projects.
     */
    public ProjectManager() throws AlphaNUSException {
        Storage storage = new Storage();
        this.currentprojectname = storage.readFromcurrentprojectnameFile();
        this.projectmap = storage.readFromProjectsFile();
    }

    /**
     * Adds a new project to the project map.
     * @param projectname Name of the project to add.
     * @return Returns the project object of the added project.
     */
    public Project addProject(String projectname) {
        Project newProject = new Project(projectname);
        projectmap.put(projectname, newProject);
        if (currentprojectname == null) {
            currentprojectname = newProject.projectname;
        }
        return newProject;
    }

    /**
     * Adds a new project with budget amount to the project map.
     * @param projectname Name of the project to add.
     * @param projectamount Amount of budget for the project.
     * @return Returns the project object of the added project.
     */
    public Project addProject(String projectname, double projectamount) {
        Project newProject = new Project(projectname, projectamount);
        projectmap.put(projectname, newProject);
        if (currentprojectname == null) {
            currentprojectname = newProject.projectname;
        }
        return newProject;
    }

    /**
     * Deletes a project in the project map.
     * @param projectname Name of the project to delete.
     * @return Returns the project object of the deleted project.
     */
    public Project deleteProject(String projectname) {
        Project deletedProject = projectmap.get(projectname);//TODO check if project exists
        if (currentprojectname == deletedProject.projectname) {
            currentprojectname = null;
        }
        projectmap.remove(projectname); //TODO assert projectname does not exist
        return deletedProject;
    }

    /**
     * Method to go to the project in the projectmap.
     * @param projectindex Name of project to go to.
     * @return Returns the project object of the project to go to.
     */
    public String gotoProject(int projectindex) {
        Project gotoProject = (Project) projectmap.values().toArray()[projectindex];
        currentprojectname = (gotoProject).projectname;
        return currentprojectname;
    }

    /**
     * Lists all projects in the projectmap.
     * @return Returns an ArrayList of projects.
     */
    public ArrayList<Project> listProjects() throws AlphaNUSException {
        ArrayList<Project> projectslist = new ArrayList<>();
        projectslist.addAll(projectmap.values());
        return projectslist;
    }

    //TODO --> adds spending for project when adding payments
    public void addSpending() {

    }

    //TODO --> subtracts spending for project when adding payments
    public static void subtractSpending() {

    }

    //TODO --> assign budget
    public void assignBudget(String projectname, Double amount) {
        projectmap.get(projectname).addBudget(amount);
    }

    /**
     * Allocates budget to a project.
     * @param projectname Name of the project.
     * @param budget Budget allocated to the project.
     * @return Returns the Project object the budget is allocated to.
     */
    public Project allocateBudget(String projectname, double budget) {
        Project projectallocated = projectmap.get(projectname);
        projectmap.get(projectname).budget = budget;
        return projectallocated; //TODO --> allocates budget to a project
    }

    /**
     * Returns the current project being edited.
     * @return Returns current project.
     */
    public String getcurrentprojectname() {
        return currentprojectname;
    }

    public HashMap<String, Payee> getCurrentProjectManagerMap() {
        return projectmap.get(currentprojectname).managermap;
    }
}
