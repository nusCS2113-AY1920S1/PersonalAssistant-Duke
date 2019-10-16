package util.factories;

import models.data.IProject;
import models.data.NullProject;
import models.data.Project;

public class ProjectFactory implements IArchDukeFactory<IProject> {
    /**
     * Data Validation
     * Do not throw exceptions at places where errors are expected
     */
    @Override
    public IProject create(String input) {
        String[] allStrings = input.split(" ");
        if (input.isEmpty()) {
            System.out.println("IDK how you even got here");
            return new NullProject();
        } else if (allStrings.length > 3) {
            System.out.println("Too many arguments!");
            return new NullProject();
        } else if (allStrings.length < 3) {
            System.out.println("Too little arguments!");
            return new NullProject();
        } else if (allStrings[1].contains("n/") && allStrings[2].contains("n/")) {
            System.out.println("Please specify a name for your Project using n/PROJECT_NAME");
            return new NullProject();
        } else if (allStrings[1].contains("i/") && allStrings[2].contains("i/")) {
            System.out.println("Please specify the number of members in this project using i/NUMBER");
            return new NullProject();
        }
        int nameFlag = input.indexOf("n/");
        int numberFlag = input.indexOf("i/");
        String description;
        if (nameFlag < numberFlag) {
            description = input.substring(nameFlag + 2, numberFlag - 1);
        } else {
            description = input.substring(nameFlag + 1);
        }
        return new Project(description);
    }
}
