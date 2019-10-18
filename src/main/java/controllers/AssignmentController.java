package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import models.data.Project;
import models.task.Task;

public class AssignmentController {
    ArrayList<Integer> validIndexesToAssign;
    ArrayList<Integer> validMembersToUnassign;
    ArrayList<String> errorMessages;

    public AssignmentController() {
        this.validIndexesToAssign = new ArrayList<>();
        this.validMembersToUnassign = new ArrayList<>();
        this.errorMessages = new ArrayList<>();
    }

    public void parseAssignmentInput(Project projectToManage, String input) {
        //assign task -i 1 2 -to 1 2 3 -rm 4 5
        ArrayList<String> allIndexesToAssign = new ArrayList<>();
        ArrayList<String> allIndexesToUnassign = new ArrayList<>();
        ArrayList<String> allTasksIndexes = new ArrayList<>();

        String [] inputParts = input.split("-");

        for (String s : inputParts) {
            if ("i".equals(s.substring(0, 1))){
                allTasksIndexes = new ArrayList<>(Arrays.asList(s.split(" ")));
            } else if ("to".equals(s.substring(0, 2))) {
                allIndexesToAssign = new ArrayList<>(Arrays.asList(s.split(" ")));
            } else if ("rm".equals(s.substring(0,2))) {
                allIndexesToUnassign = new ArrayList<>(Arrays.asList(s.split(" ")));
            }
        }

        

    }

    public void manageAssignmentForATask(Task task) {

    }

    public boolean isValidTaskIndex() {

    }

}
