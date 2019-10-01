package controllers;

import models.data.IProject;
import models.data.NullProject;
import models.data.Project;

import java.util.ArrayList;
import java.util.Arrays;

public class ProjectFactory implements IArchDukeFactory<IProject> {
    /**
     * Data Validation
     * Do not throw exceptions at places where errors are expected
     * TODO refactoring this portion to a NullProject such that Repository can detect
     * TODO and return false. Leave to controller to handle false or true and throw it to View
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
        String description = null;
        String members = null;
        if (nameFlag < numberFlag) {
            members = input.substring(numberFlag + 2);
            description = input.substring(nameFlag + 2, numberFlag - 1);
        } else {
            members = input.substring(numberFlag + 2, nameFlag - 1);
            description = input.substring(nameFlag + 2);
        }
        return new Project(description, members);
    }
}
