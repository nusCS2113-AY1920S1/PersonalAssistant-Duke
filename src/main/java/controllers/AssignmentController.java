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
    private Project project;

    public AssignmentController(Project project) {
        this.validMembersToAssign = new ArrayList<>();
        this.validMembersToUnassign = new ArrayList<>();
        this.validTaskIndexes = new ArrayList<>();
        this.errorMessages = new ArrayList<>();
        this.successMessages = new ArrayList<>();
        this.project = project;
    }

    public void assignAndUnassign(String input) {
        parseAssignmentInput(input);
        for (Integer taskIndex : validTaskIndexes) {
            Task task = project.getTask(taskIndex);
            successMessages.add("For task " + taskIndex + " (" + task.getTaskName() + "):");

            //assigning tasks
            for (Integer assigneeIndex : validMembersToAssign) {
                Member member = project.getMembers().getMember(assigneeIndex);
                if (task.isAlreadyAssignedTo(member)){
                    successMessages.add("Task has already been assigned to member "
                    + assigneeIndex + " (" + member.getName() +").");
                } else {
                    project.addTaskToMemberTaskList(task, member);
                    successMessages.add("Assigned to member " + assigneeIndex +
                        " (" + member.getName() +").");
                }
            }
            //unassigning tasks
            for (Integer unassigneeIndex : validMembersToUnassign) {
                Member member = project.getMembers().getMember(unassigneeIndex);
                if (!task.isAlreadyAssignedTo(member)){
                    successMessages.add("Task cannot be unassigned from member "
                        + unassigneeIndex + " (" + member.getName() +") as it was "
                        + "not assigned in the first place!");
                } else {
                    project.unassignMemberFromTask(member, task);
                    successMessages.add("Unassigned task from member " + unassigneeIndex +
                        " (" + member.getName() +").");
                }
            }
            successMessages.add("\n");
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
            String [] part = s.split(" ");
            switch (part[0]) {
                case "i":
                    allTasksIndexes = new ArrayList<>(Arrays.asList(part));
                    allTasksIndexes.remove("i");
                    break;
                case "to":
                    allIndexesToAssign = new ArrayList<>(Arrays.asList(part));
                    allIndexesToAssign.remove("to");
                    break;
                case "rm":
                    allIndexesToUnassign = new ArrayList<>(Arrays.asList(s.split(" ")));
                    allIndexesToUnassign.remove("rm");
                    break;
                default:
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
                    errorMessages.add("The member with index " + taskNumber + " does not exist.");
                }
            } catch (NumberFormatException e) {
                errorMessages.add("Could not find member: " + memberIndex);
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

    public ArrayList<String> getSuccessMessages() {
        return successMessages;
    }


}
