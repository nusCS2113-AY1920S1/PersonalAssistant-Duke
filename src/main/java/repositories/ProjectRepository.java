package repositories;


import controllers.ProjectFactory;
import models.data.IProject;

import java.util.ArrayList;

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
    public void deleteItem() {
        // TODO yet to be implemented
    }
}
