package project;

import java.util.HashMap;

public abstract class ProjectManager {


    /**
     * Adds a new project to the project map.
     * @param projectname Name of the project to add.
     * @param projectmap HashMap of projects.
     * @return Returns the project object of the added project.
     */
    public static Project addProject(String projectname, HashMap<String, Project> projectmap) {
        Project newProject = new Project(projectname);
        projectmap.put(projectname, newProject);
        return newProject; //TODO --> handle exceptions when the same projectname is added.
    }

    /**
     * Deletes a project in the project map.
     * @param projectname Name of the project to delete.
     * @param projectmap HashMap of projects.
     * @return Returns the project object of the deleted project.
     */
    public static Project deleteProject(String projectname, HashMap<String, Project> projectmap) {
        Project deletedProject = projectmap.get(projectname);//TODO check if project exists
        projectmap.remove(projectname); //TODO assert projectname does not exist
        return deletedProject;
    }

    /**
     * Method to go to the project in the projectmap.
     * @param projectname Name of project to go to.
     * @param projectmap HashMap of projects.
     * @return Returns the project object of the project to go to.
     */
    public static Project gotoProject(String projectname, HashMap<String, Project> projectmap) {
        return projectmap.get(projectname);
    }

    //TODO --> list project
    public static void listProjects() {

    }

    //TODO --> adds spending for project when adding payments
    public static void addSpending() {

    }

    //TODO --> subtracts spending for project when adding payments
    public static void subtractSpending() {

    }

    //TODO --> assign budget
    public static void assignBudget() {

    }

    /**
     * Allocates budget to a project.
     * @param projectname Name of the project.
     * @param budget Budget allocated to the project.
     * @param projectmap Hashmap of projects.
     * @return Returns the Project object the budget is allocated to.
     */
    public static Project allocateBudget(String projectname, double budget, HashMap<String, Project> projectmap) {
        Project projectallocated = projectmap.get(projectname);
        projectmap.get(projectname).budget = budget;
        return projectallocated; //TODO --> allocates budget to a project
    }
}
