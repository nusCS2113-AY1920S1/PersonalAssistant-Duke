package controllers;

import static util.constant.ConstantHelper.ASSIGN_TASKS_INSUFFICIENT_PARAMS_MESSAGE;
import static util.constant.ConstantHelper.ASSIGN_TASKS_NO_VALID_MEMBERS_MESSAGE;
import static util.constant.ConstantHelper.ASSIGN_TASKS_NO_VALID_TASKS_MESSAGE;
import static util.constant.ConstantHelper.COMMAND_ASSIGN_TASK;

import java.util.ArrayList;
import java.util.Arrays;
import models.member.IMember;
import models.member.Member;
import models.project.Project;
import models.task.Task;
import util.ParserHelper;

//@@author sinteary
public class AssignmentController {
    private ArrayList<String> errorMessages;
    private ArrayList<ArrayList<String>> successMessages;
    private ParserHelper parserHelper;
    private Project project;

    /**
     * A Controller class which parses the input from the user to get a list of valid
     * task and member indexes, and manages the assignment to and removal of tasks from members.
     * @param project The which the user is managing.
     */
    public AssignmentController(Project project) {
        this.errorMessages = new ArrayList<>();
        this.successMessages = new ArrayList<>();
        this.parserHelper = new ParserHelper();
        this.project = project;
    }

    /**
     * Handles assignment command by user. Collates messages to inform user of successful
     * assignments/unassignments, or any errors encountered.
     * @param input The input from the user.
     */
    public void assignAndUnassign(String input) {
        if (input.length() < COMMAND_ASSIGN_TASK.length()) {
            errorMessages.addAll(Arrays.asList(ASSIGN_TASKS_INSUFFICIENT_PARAMS_MESSAGE));
            return;
        }
        input = input.substring(COMMAND_ASSIGN_TASK.length()); //remove the "assign task " portion
        ArrayList<ArrayList<Integer>> assignmentParams = parserHelper.parseAssignmentParams(input, project);
        errorMessages.addAll(parserHelper.getErrorMessages());
        ArrayList<Integer> validTaskIndexes = assignmentParams.get(0);
        ArrayList<Integer> validAssignees = assignmentParams.get(1);
        ArrayList<Integer> validUnassignees = assignmentParams.get(2);

        if (validTaskIndexes.size() == 0) {
            errorMessages.addAll(Arrays.asList(ASSIGN_TASKS_NO_VALID_TASKS_MESSAGE));
            return;
        }

        if (validAssignees.size() == 0 && validUnassignees.size() == 0) {
            errorMessages.addAll(Arrays.asList(ASSIGN_TASKS_NO_VALID_MEMBERS_MESSAGE));
            return;
        }

        for (Integer taskIndex : validTaskIndexes) {
            Task task = project.getTask(taskIndex);
            ArrayList<String> successMessagesForEachTask = new ArrayList<>();
            successMessagesForEachTask.add("For task " + taskIndex + " (" + task.getTaskName() + "):");
            //assigning tasks
            if (!validAssignees.isEmpty()) {
                ArrayList<String> assignMessages = assign(validAssignees, task);
                successMessagesForEachTask.addAll(assignMessages);
            }
            //unassigning tasks
            if (!validUnassignees.isEmpty()) {
                ArrayList<String> unassignMessages = unassign(validUnassignees, task);
                successMessagesForEachTask.addAll(unassignMessages);
            }
            successMessages.add(successMessagesForEachTask);
        }
    }


    /**
     * Assign valid members to a particular task and updates the associated messages
     * relating to the assignment.
     * @param validAssignees ArrayList containing valid member index numbers, for assignees.
     * @param task Task to be assigned to members.
     * @return ArrayList of messages from task assignments.
     */
    private ArrayList<String> assign(ArrayList<Integer> validAssignees, Task task) {
        ArrayList<String> assignMessages = new ArrayList<>();
        for (Integer assigneeIndex : validAssignees) {
            IMember member = project.getMember(assigneeIndex);
            if (project.containsAssignment(task, (Member)member)) {
                assignMessages.add("Task has already been assigned to member "
                    + assigneeIndex + " ("
                    + member.getName() + ").");
            } else {
                project.createAssignment(task, (Member)member);
                assignMessages.add("Assigned to member "
                    + assigneeIndex + " ("
                    + member.getName() + ").");
            }
        }
        return assignMessages;
    }

    /**
     * Removes valid members from a particular task, and updates the associated messages
     * relating to the removal of assignment.
     * @param validUnassignees ArrayList containing valid member index numbers, for unassignees.
     * @param task Task to be unassigned.
     * @return ArrayList of messages from task assignments.
     */
    private ArrayList<String> unassign(ArrayList<Integer> validUnassignees, Task task) {
        ArrayList<String> unassignMessages = new ArrayList<>();
        for (Integer unassigneeIndex : validUnassignees) {
            IMember member = project.getMemberList().getMember(unassigneeIndex);
            if (!project.containsAssignment(task, (Member)member)) {
                unassignMessages.add("Task cannot be unassigned from member "
                    + unassigneeIndex + " (" + member.getName() + ") as it was "
                    + "not assigned in the first place!");
            } else {
                project.removeAssignment((Member)member, task);
                unassignMessages.add("Unassigned task from member " + unassigneeIndex
                    + " (" + member.getName() + ").");
            }
        }
        return unassignMessages;
    }

    /**
     * Returns messages about invalid inputs to be shown to the user.
     * @return an ArrayList of error messages that arise from invalid inputs.
     */
    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * Returns messages about successful assignments to be shown to the user.
     * @return an ArrayList of success messages indicating successful assignment.
     */
    public ArrayList<ArrayList<String>> getSuccessMessages() {
        return successMessages;
    }
}
