package controllers;

import static util.constant.ConstantHelper.ASSIGN_TASK_COMMAND;

import java.util.ArrayList;
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
     * Does the actual assignment of task by calling on the project and establishing the
     * necessary links between tasks and members. Collates messages to inform user of
     * successful assignments/unassignments.
     * @param input The input from the user.
     */
    public void assignAndUnassign(String input) {
        assert input != null;
        if (input.length() < ASSIGN_TASK_COMMAND.length()) {
            errorMessages.add("Insufficient parameters!"
                + "Indicate the tasks and members whom you wish to assign or remove!");
            errorMessages.add("Format is \"assign task -i TASK_INDEX [-to MEMBER_INDEX] [-rm MEMBER_INDEX]\"");
            errorMessages.add("You must either assign a task to someone, or remove, or both!");
            return;
        }
        input = input.substring(ASSIGN_TASK_COMMAND.length()); //remove the "assign task " portion
        ArrayList<ArrayList<Integer>> assignmentParams = parserHelper.parseAssignmentParams(input, project);
        errorMessages.addAll(parserHelper.getErrorMessages());
        ArrayList<Integer> validTaskIndexes = assignmentParams.get(0);
        ArrayList<Integer> validAssignees = assignmentParams.get(1);
        ArrayList<Integer> validUnassignees = assignmentParams.get(2);

        if (validTaskIndexes.size() == 0) {
            errorMessages.add("No valid task numbers detected. Cannot assign/unassign any tasks.");
            errorMessages.add("Please check that the task index number(s) are valid, and input them in "
                + "this format: -i TASK_INDEX1 [TASK_INDEX2]");
            return;
        }

        if (validAssignees.size() == 0 && validUnassignees.size() == 0) {
            errorMessages.add("No valid member indexes detected. No tasks can be assigned.");
            errorMessages.add("Please check that you are using valid member indexes.");
            errorMessages.add("Also ensure that the correct flags are used: "
                + "'-to' for assignees, '-rm' for unassignees.");
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
     * Assign valid members to a particular task and updates the associated success or error messages
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
     * Removes valid members from a particular task, and updates the associated success or error messages
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
