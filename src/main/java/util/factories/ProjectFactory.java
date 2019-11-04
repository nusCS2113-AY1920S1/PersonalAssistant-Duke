package util.factories;

import models.project.IProject;
import models.project.NullProject;
import models.project.Project;

//@@author Lucria
public class ProjectFactory implements IArchDukeFactory<IProject> {
    /**
     * Creation of an IProject object.
     * Project may be a NullProject due to errors in input. Validation of project will be handled by Repository
     * @param input : User input
     * @return Method will return an IProject object
     */
    @Override
    public IProject create(String input) {
        if (input.length() < 8) {
            return new NullProject();
        }
        String projectName = input.substring(7);
        return new Project(projectName);
    }
}
