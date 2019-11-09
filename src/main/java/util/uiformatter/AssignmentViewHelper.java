package util.uiformatter;

import static util.constant.ConstantHelper.COMMAND_VIEW_ASSIGNMENTS;
import static util.constant.ConstantHelper.COMMAND_VIEW_ASSIGNMENTS_MEMBER_FLAG;
import static util.constant.ConstantHelper.COMMAND_VIEW_ASSIGNMENTS_TASK_FLAG;
import static util.constant.ConstantHelper.DEFAULT_HORI_BORDER_LENGTH;
import static util.constant.ConstantHelper.VALID_VIEW_ASSIGNMENT_LENGTH;

import controllers.ProjectInputController;
import java.util.ArrayList;
import java.util.HashMap;
import models.member.IMember;
import models.project.Project;
import models.task.ITask;
import models.task.Task;
import util.ParserHelper;
import util.log.ArchDukeLogger;

//@@author sinteary
public class AssignmentViewHelper {
    private static ViewHelper viewHelper = new ViewHelper();
    private ParserHelper parserHelper;
    /**
     * Class that assists with retrieving and formatting information about task assignments in a project.
     */

    public AssignmentViewHelper() {
        this.parserHelper = new ParserHelper();
    }

    /**
     * Handles the user input and returns the necessary output.
     * @param projectCommand The full user input.
     * @param projectToManage The project being managed.
     * @return An array of Strings containing requested information about task assignments.
     */
    public String[] viewAssignments(String projectCommand, Project projectToManage) {
        if (projectCommand.length() <= VALID_VIEW_ASSIGNMENT_LENGTH) {
            return (new String[] {
                "Please input the parameters to view assignments:",
                "**\t-m for viewing by member, -t for viewing by task.",
                "**\t\"all\" to view all assignments + or enter selected task/member index numbers.",
                "You may refer to the user guide or enter \"help\" for the list of possible commands."
            });
        }
        String input = projectCommand.substring(COMMAND_VIEW_ASSIGNMENTS.length());
        if (COMMAND_VIEW_ASSIGNMENTS_MEMBER_FLAG.equals(input.substring(0,2))) {
            return viewMembersAssignments(projectToManage, projectCommand.substring(VALID_VIEW_ASSIGNMENT_LENGTH));
        } else if (COMMAND_VIEW_ASSIGNMENTS_TASK_FLAG.equals(input.substring(0,2))) {
            return viewTasksAssignments(projectToManage, projectCommand.substring(VALID_VIEW_ASSIGNMENT_LENGTH));
        } else {
            return (new String[]
                {"Could not understand your command! Please use:",
                    "**\t-m for viewing by member, -t for viewing by task.",});
        }
    }

    /**
     * Returns a list of members' individual list of tasks.
     * @param projectToManage the project being managed.
     * @param projectCommand The command by the user containing index numbers of the members to view.
     */
    private String[] viewMembersAssignments(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(),
            "[projectViewMembersAssignments] User input: '" + projectCommand + "'");
        ArrayList<Integer> validMembers = parserHelper.parseMembersIndexes(projectCommand,
            projectToManage.getNumOfMembers());
        if (!parserHelper.getErrorMessages().isEmpty()) {
            return parserHelper.getErrorMessages().toArray(new String[0]);
        }
        return getMemberOutput(validMembers, projectToManage);
    }

    /**
     * Returns a list of tasks and the members assigned to them.
     * @param projectToManage The project to manage.
     * @param projectCommand The user input.
     */
    private String[] viewTasksAssignments(Project projectToManage, String projectCommand) {
        ArchDukeLogger.logDebug(ProjectInputController.class.getName(),
            "[projectViewTasksAssignments] User input: '" + projectCommand + "'");
        ArrayList<Integer> validTasks = parserHelper.parseTasksIndexes(projectCommand,
            projectToManage.getNumOfTasks());
        if (!parserHelper.getErrorMessages().isEmpty()) {
            return parserHelper.getErrorMessages().toArray(new String[0]);
        }
        return getTaskOutput(validTasks, projectToManage);
    }

    /**
     * Returns output to show viewer the task assignments of members.
     * @param membersToView List of valid member index numbers.
     * @param project THe project being managed.
     * @return An array containing information requested by the user.
     */
    public static String[] getMemberOutput(ArrayList<Integer> membersToView,
        Project project) {
        ArrayList<ArrayList<String>> totalOutputToPrint = new ArrayList<>();
        HashMap<String, ArrayList<String>> memberAndIndividualTasks = project.getMembersIndividualTaskList();
        if (memberAndIndividualTasks.keySet().isEmpty()) {
            ArrayList<String> outputToPrint = new ArrayList<>();
            outputToPrint.add("No members in project yet.");
            outputToPrint.add("Please add members and assign them tasks before using this command!");
            return outputToPrint.toArray(new String[0]);
        }
        for (Integer index : membersToView) {
            ArrayList<String> outputToPrint = new ArrayList<>();
            IMember member = project.getMember(index);
            outputToPrint.add(member.getName());
            if (memberAndIndividualTasks.get(member.getMemberID()).size() == 0) {
                outputToPrint.add("No tasks assigned yet.");
            } else {
                int currentNumber = 1;
                for (String taskID : memberAndIndividualTasks.get(member.getMemberID())) {
                    ITask task = project.getTaskFromID(taskID);
                    outputToPrint.add(currentNumber + ". " + task.getDetailsForAssignmentTable());
                    outputToPrint.add("");
                    currentNumber++;
                }
                outputToPrint.remove(outputToPrint.lastIndexOf(""));
            }
            totalOutputToPrint.add(outputToPrint);
        }
        return viewHelper.consolePrintMultipleTables(totalOutputToPrint, DEFAULT_HORI_BORDER_LENGTH, 2,
                "Here are each member's tasks:");
    }

    /**
     * Returns output to show viewer the task assignments of members.
     * @param tasksToView List of valid task index numbers.
     * @param project Project to be managed.
     * @return An Array containing information requested by the user.
     */
    public static String[] getTaskOutput(ArrayList<Integer> tasksToView, Project project) {
        HashMap<String, ArrayList<String>> tasksAndAssignedMembers = project.getTasksAndAssignedMembers();
        if (tasksAndAssignedMembers.keySet().isEmpty()) {
            ArrayList<String> outputToPrint = new ArrayList<>();
            outputToPrint.add("No tasks in project yet.");
            outputToPrint.add("Please add tasks and assign them to members before using this command!");
            return outputToPrint.toArray(new String[0]);
        }
        ArrayList<ArrayList<String>> totalOutputToPrint = new ArrayList<>();
        for (Integer index : tasksToView) {
            Task task = project.getTask(index);
            ArrayList<String> outputToPrint = new ArrayList<>();
            outputToPrint.add(task.getDetailsForAssignmentTable());
            if (tasksAndAssignedMembers.get(task.getTaskID()).size() == 0) {
                outputToPrint.add("No members assigned yet.");
            } else {
                int currentNumber = 1;
                for (String memberID : tasksAndAssignedMembers.get(task.getTaskID())) {
                    IMember member = project.getMemberFromID(memberID);
                    outputToPrint.add(currentNumber + ". " + member.getName());
                    currentNumber++;
                }
            }
            totalOutputToPrint.add(outputToPrint);
        }
        return viewHelper.consolePrintMultipleTables(totalOutputToPrint, DEFAULT_HORI_BORDER_LENGTH, 2,
                "Here are the members assigned to each task:");
    }

}
