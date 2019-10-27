package project;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class ProjectManager {


    /**
     * Adds a new project to the project map.
     * @param projectname Name of the project to add.
     * @param projectmap LinkedHashMap of projects.
     * @return Returns the project object of the added project.
     */
    public static Project addProject(String projectname, LinkedHashMap<String, Project> projectmap) {
        Project newProject = new Project(projectname);
        projectmap.put(projectname, newProject);
        return newProject; //TODO --> handle exceptions when the same projectname is added.
    }

    /**
     * Deletes a project in the project map.
     * @param projectname Name of the project to delete.
     * @param projectmap LinkedHashMap of projects.
     * @return Returns the project object of the deleted project.
     */
    public static Project deleteProject(String projectname, LinkedHashMap<String, Project> projectmap) {
        Project deletedProject = projectmap.get(projectname);//TODO check if project exists
        projectmap.remove(projectname); //TODO assert projectname does not exist
        return deletedProject;
    }

    /**
     * Method to go to the project in the projectmap.
     * @param projectname Name of project to go to.
     * @param projectmap LinkedHashMap of projects.
     * @return Returns the project object of the project to go to.
     */
    public static Project gotoProject(String projectname, LinkedHashMap<String, Project> projectmap) {
        return projectmap.get(projectname);
    }

    /**
     * Lists all projects in the projectmap.
     * @param projectmap LinkedHashMap of projects.
     * @return Returns an ArrayList of projects.
     */
    public static ArrayList<Project> listProjects(LinkedHashMap<String, Project> projectmap) {
        ArrayList<Project> projectslist = new ArrayList<>();
        for (Project project: projectmap.values()){
            projectslist.add(project);
        }
        return projectslist;
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
    public static Project allocateBudget(String projectname, double budget, LinkedHashMap<String, Project> projectmap) {
        Project projectallocated = projectmap.get(projectname);
        projectmap.get(projectname).budget = budget;
        return projectallocated; //TODO --> allocates budget to a project
    }
}
