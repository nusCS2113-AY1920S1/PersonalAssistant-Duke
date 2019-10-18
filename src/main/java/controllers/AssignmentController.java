package controllers;

import java.util.ArrayList;
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
        ArrayList<Integer> allIndexesToAssign = new ArrayList<>();
        ArrayList<Integer> allIndexesToUnassign = new ArrayList<>();
        String [] inputParts = input.split("-");

        //flags that indicate which components of input are present
        int taskIndexes = -1;
        int assigneesIndexes = -1;
        int unassigneesIndexes = -1;

        //if present, flag which component contains index numbers
        for (int i = 0; i < inputParts.length; i++) {
            switch (inputParts[i].substring(0, 2)) {
                case "i ":
                    taskIndexes = i;
                    break;
                case "to":
                    assigneesIndexes = i;
                    break;
                case "rm":
                    unassigneesIndexes = i;
                    break;
                default:
            }
        }





    }

    public void manageAssignmentForATask(Task task) {

    }

}
