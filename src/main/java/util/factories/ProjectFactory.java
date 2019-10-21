package util.factories;

import models.data.IProject;
import models.data.NullProject;
import models.data.Project;

public class ProjectFactory implements IArchDukeFactory<IProject> {
    /**
     * Creation of an IProject object.
     * Project may be a NullProject due to errors in input. Validation of project will be handled by Repository
     * @param input : User input
     * @return Method will return an IProject object
     */
    @Override
    public IProject create(String input) {
        if (input.length() < 9) {
            System.out.println("Please specify the command correctly in the format create -n PROJECT_NAME");
            return new NullProject();
        } else if (!input.contains("-n")) {
            System.out.println("Please specify the n/ flag before your PROJECT_NAME using -n PROJECT_NAME");
            return new NullProject();
        } else if (input.length() < 10) {
            System.out.println("Please remember to specify the project name after the -n flag!");
            return new NullProject();
        }
        String projectName = input.substring(10);
        return new Project(projectName);
    }
}
