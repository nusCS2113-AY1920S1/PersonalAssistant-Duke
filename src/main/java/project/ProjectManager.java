package project;

import java.util.HashMap;

public class ProjectManager {
    HashMap<String, Project> projectmap;

    //TODO --> add project
    public void addProject(String projectname, double budget, HashMap<String, Project> projectmap){
        Project projectNew = new Project(); 
    }

    //TODO --> deletes project
    public void deleteProject(){

    }

    //TODO --> adds spending for project when adding payments
    public void addSpending(){

    }

    //TODO --> subtracts spending for project when adding payments
    public void subtractSpending(){

    }

    /**
     * Allocates budget to a project
     * @param projectname Name of the project.
     * @param budget Budget allocated to the project.
     * @param projectmap Hashmap of projects.
     * @return Returns the Project object the budget is allocated to.
     */
    public Project allocateBudget(String projectname, double budget, HashMap<String, Project> projectmap){
        Project projectallocated = projectmap.get(projectname);
        projectmap.get(projectname).budget = budget;
        return projectallocated; //TODO --> allocates budget to a project
    }
}
