package repositories;

import models.data.Project;
import util.factories.ProjectFactory;
import java.util.ArrayList;
import models.data.IProject;
import util.log.DukeLogger;

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

    public Project getItem(int projectNumber) {
        return this.allProjects.get(projectNumber - 1);
    }

    public void deleteItem(int projectNumber) {
        this.allProjects.remove(projectNumber - 1);
    }
}
