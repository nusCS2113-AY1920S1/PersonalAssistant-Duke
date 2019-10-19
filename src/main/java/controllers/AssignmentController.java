package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import models.data.Project;
import models.member.Member;
import models.task.Task;

public class AssignmentController {
    private ArrayList<Integer> validMembersToAssign;
    private ArrayList<Integer> validMembersToUnassign;
    private ArrayList<Integer> validTaskIndexes;
    private ArrayList<String> errorMessages;
    private ArrayList<String> successMessages;
    Project project;

    public AssignmentController(Project project) {
        this.validMembersToAssign = new ArrayList<>();
        this.validMembersToUnassign = new ArrayList<>();
        this.validTaskIndexes = new ArrayList<>();
        this.errorMessages = new ArrayList<>();
        this.project = project;
    }

    public void assignAndUnassign(String input) {
        parseAssignmentInput(input);
        for (Integer taskIndex : validTaskIndexes) {
            Task task = project.getTask(taskIndex);
            successMessages.add("For task " + taskIndex + " (" + task.getTaskName() + "):");
            for (Integer assigneeIndex : validMembersToAssign) {
                Member member = project.getMembers().getMember(assigneeIndex);
                if (task.isAlreadyAssignedTo(member)){
                    successMessages.add("Task has already been assigned to member "
                    + assigneeIndex + " (" + member.getName() +").");
                } else {
                    successMessages.add("Assigned to member " + assigneeIndex +
                        " (" + member.getName() +").");

                }
            }

        }

    }

    /**
     * Makes sense of user input for task assignment. Parses input String to get valid task and member index
     * numbers, as well as error messages for invalid index numbers.
     * @param input
     */
    public void parseAssignmentInput(String input) {
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

        getValidTaskIndexes(allTasksIndexes); //shortlists all valid task numbers
        if (!allIndexesToAssign.isEmpty()) {
            getValidAssignees(allIndexesToAssign);
        }
        if (!allIndexesToUnassign.isEmpty()) {
            getValidUnassignees(allIndexesToUnassign);
        }

        //check if any assignees and unassignees are the same, remove if necessary
        checkForSameMemberIndexes();
    }

    private void checkForSameMemberIndexes() {
        for (Integer index: validMembersToAssign) {
            if (validMembersToUnassign.contains(index)) {
                errorMessages.add("Cannot assign and unassign task to member " + index + " ("
                + project.getMembers().getMember(index).getName() + ") at the same time");
                validMembersToAssign.remove(validMembersToAssign.indexOf(index));
                validMembersToUnassign.remove(validMembersToUnassign.indexOf(index));
            }
        }
    }

    public boolean isValidTaskIndex(int taskNumber) {
        return taskNumber > 0 && taskNumber <= project.getNumOfTasks();
    }

    public void getValidTaskIndexes(ArrayList<String> allTaskIndexes) {
        for (String taskIndex : allTaskIndexes) {
            try {
                Integer taskNumber = Integer.parseInt(taskIndex);
                if (isValidTaskIndex(taskNumber)) {
                    validTaskIndexes.add(taskNumber);
                } else {
                    errorMessages.add("The task " + taskNumber + " does not exist.");
                }
            } catch (NumberFormatException e) {
                errorMessages.add("Could not find task: " + taskIndex);
                errorMessages.add("Please ensure that task numbers are integers");
            }
        }
    }

    private void getValidMemberIndexes(ArrayList<String> allMemberIndexes, ArrayList<Integer> validIndexes) {
        for (String memberIndex : allMemberIndexes) {
            try {
                Integer taskNumber = Integer.parseInt(memberIndex);
                if (isValidTaskIndex(taskNumber)) {
                    validIndexes.add(taskNumber);
                } else {
                    errorMessages.add("The task " + taskNumber + " does not exist.");
                }
            } catch (NumberFormatException e) {
                errorMessages.add("Could not find task: " + memberIndex);
                errorMessages.add("Please ensure that member index numbers are integers");
            }
        }
    }

    private void getValidAssignees(ArrayList<String> allIndexesToAssign) {
        getValidMemberIndexes(allIndexesToAssign, this.validMembersToAssign);
    }

    private void getValidUnassignees(ArrayList<String> allIndexesToUnassign) {
        getValidMemberIndexes(allIndexesToUnassign, this.validMembersToUnassign);
    }

    public ArrayList<Integer> getValidTaskIndexes() {
        return validTaskIndexes;
    }

    public ArrayList<Integer> getValidMembersToAssign() {
        return validMembersToAssign;
    }

    public ArrayList<Integer> getValidMembersToUnassign(){
        return validMembersToUnassign;
    }

    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }


}
