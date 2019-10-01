package controllers;

import models.data.IProject;
import models.data.Project;

import java.util.ArrayList;

public class ProjectFactory implements IArchDukeFactory<IProject> {
    @Override
    public IProject create(String input) {
        /**
         * Data Validation
         * Do not throw exceptions at places where errors are expected
         * TODO refactoring this portion to a NullProject such that Repository can detect
         * TODO and return false. Leave to controller to handle false or true and throw it to View
         */
        String[] allStrings = input.split(" ");
        if (input.isEmpty()) {
            System.out.println("IDK how you even got here");
        } else if (allStrings.length > 3) {
            System.out.println("Too many arguments!");
        }

        // TODO parsing for project creation
        return new Project(allStrings[1], allStrings[2]);
    }
}
