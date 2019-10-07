package util.repositories;


import controllers.ProjectFactory;
import java.util.ArrayList;
import models.data.IProject;

public class ProjectRepository implements IRepository<IProject> {
    private ArrayList<IProject> allProjects;
    private ProjectFactory projectFactory;

    public ProjectRepository() {
        this.allProjects = new ArrayList<>();
        this.projectFactory = new ProjectFactory();
    }

    @Override
    public ArrayList<IProject> getAll() {
        return allProjects;
    }

    @Override
    public boolean addToRepo(String input) {
        IProject newProject = projectFactory.create(input);
        if (newProject.getDescription() == null || newProject.getMembers() == null) {
            return false;
        }
        allProjects.add(newProject);
        return true;
    }

    @Override
    public IProject getProject(int projectNumber) {
        return this.allProjects.get(projectNumber - 1);
    }

    @Override
    public void deleteItem() {
        // TODO yet to be implemented
    }
}
