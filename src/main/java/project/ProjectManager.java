package project;

import storage.Storage;
import common.AlphaNUSException;
import payment.Payee;
import payment.Payments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

//@@author leowyh
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
     * @param projectindex Index of project to go to.
     * @return Returns the project object of the project to go to.
     */
    public String gotoProject(int projectindex) {
        Project gotoProject = (Project) projectmap.values().toArray()[projectindex];
        currentprojectname = (gotoProject).projectname;
        return currentprojectname;
    }

    /**
     * Method to go to the project in the projectmap.
     * @param projectname Name of project to go to.
     * @return Returns the project object of the project to go to.
     */
    public String gotoProject(String projectname) {
        currentprojectname = projectmap.get(projectname).projectname;
        return currentprojectname;
    }

    /**
     * Lists all projects in the projectmap.
     * @return Returns an ArrayList of projects.
     */
    public ArrayList<Project> listProjects() {
        ArrayList<Project> projectslist = new ArrayList<>();
        projectslist.addAll(projectmap.values());
        return projectslist;
    }

    //@@author lijiayu980606
    /**
     * Assigns a budget to the project.
     * @param projectname Name of the project.
     * @param amount Amo
     */
    public void assignBudget(String projectname, Double amount) {
        projectmap.get(projectname).addBudget(amount);
    }

    //@@author leowyh
    public HashMap<String, Payee> getCurrentProjectManagerMap() {
        return projectmap.get(currentprojectname).managermap;
    }

    /**
     * Load backup projects and removes previously added projects.
     * Also sets currentproject to null.
     * @param projectmap HashMap of projects from backup.
     */
    public void loadBackup(LinkedHashMap<String, Project> projectmap) {
        this.projectmap = projectmap;
        this.currentprojectname = null;
    }

    //@@author karansarat
    /**
     * Clears the dictionary and updates it with current vocabulary.
     * @param dict the dictionary containing vocabulary
     */
    public void updateDict(Set<String> dict) {
        dict.clear();
        for (String currProjectName : this.projectmap.keySet()) {
            for (Payee payee : this.projectmap.get(currProjectName).managermap.values()) {
                payee.payeeToDict(dict);
                for (Payments payment : payee.payments) {
                    payment.paymentToDict(dict);
                }
            }
        }
    }
}
