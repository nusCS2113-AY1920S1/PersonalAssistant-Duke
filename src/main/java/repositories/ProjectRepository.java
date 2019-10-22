package repositories;

import models.data.IProject;
import models.data.Project;
import util.factories.ProjectFactory;
import util.log.DukeLogger;

import java.util.ArrayList;

public class ProjectRepository implements IRepository<Project> {
    private ArrayList<Project> allProjects;
    private ProjectFactory projectFactory;

    public ProjectRepository() {
        this.allProjects = new ArrayList<>();
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
            return true;
        } catch (IndexOutOfBoundsException err) {
            return false;
        }
    }
}
