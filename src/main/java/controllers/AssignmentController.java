package controllers;

import java.util.ArrayList;

import models.member.IMember;
import models.project.Project;
import models.member.Member;
import models.task.Task;
import util.ParserHelper;

//@@author sinteary
public class AssignmentController {
    private ArrayList<String> errorMessages;
    private ArrayList<String> successMessages;
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
        if (input.length() < 12) {
            errorMessages.add("Insufficient parameters!"
                + "Indicate the tasks and members whom you wish to assign or remove!");
            errorMessages.add("Format is \"assign task -i TASK_INDEX -to [MEMBER_INDEX] -rm [MEMBER_INDEX]\"");
            errorMessages.add("You must either assign a task to someone, or remove, or both!");
            return;
        }
        input = input.substring(12); //remove the "assign task " portion
        ArrayList<ArrayList<Integer>> assignmentParams = parserHelper.parseAssignmentParams(input, project);
        errorMessages.addAll(parserHelper.getErrorMessages());
        ArrayList<Integer> validTaskIndexes = assignmentParams.get(0);
        ArrayList<Integer> validAssignees = assignmentParams.get(1);
        ArrayList<Integer> validUnassignees = assignmentParams.get(2);

        if (validTaskIndexes.size() == 0) {
            errorMessages.add("No valid task numbers detected. Cannot assign any tasks.");
            errorMessages.add("Please input valid task numbers in this format: -i TASK_INDEX");
            return;
        }

        if (validAssignees.size() == 0 && validUnassignees.size() == 0) {
            errorMessages.add("Insufficient parameters! Indicate the members whom you wish to assign or remove!");
            errorMessages.add("Format is \"assign task -i TASK_INDEX -to [MEMBER_INDEX] -rm [MEMBER_INDEX]\"");
            errorMessages.add("You must either assign a task to someone, or remove, or both!");
            return;
        }

        Project project = this.project;
        for (Integer taskIndex : validTaskIndexes) {
            Task task = project.getTask(taskIndex);
            successMessages.add("For task " + taskIndex + " (" + task.getTaskName() + "):");

            //assigning tasks
            for (Integer assigneeIndex : validAssignees) {
                IMember member = project.getMembers().getMember(assigneeIndex);
                if (project.containsAssignment(task, (Member)member)) {
                    successMessages.add("Task has already been assigned to member "
                        + assigneeIndex + " ("
                        + member.getName() + ").");
                } else {
                    project.createAssignment(task, (Member)member);
                    successMessages.add("Assigned to member "
                        + assigneeIndex + " ("
                        + member.getName() + ").");
                }
            }
            //unassigning tasks
            for (Integer unassigneeIndex : validUnassignees) {
                IMember member = project.getMembers().getMember(unassigneeIndex);
                if (!project.containsAssignment(task, (Member)member)) {
                    successMessages.add("Task cannot be unassigned from member "
                        + unassigneeIndex + " (" + member.getName() + ") as it was "
                        + "not assigned in the first place!");
                } else {
                    project.removeAssignment((Member)member, task);
                    successMessages.add("Unassigned task from member " + unassigneeIndex
                        + " (" + member.getName() + ").");
                }
            }
        }
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
    public ArrayList<String> getSuccessMessages() {
        return successMessages;
    }
}
