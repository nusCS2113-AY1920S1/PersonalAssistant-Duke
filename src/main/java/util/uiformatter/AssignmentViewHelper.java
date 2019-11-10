package util.uiformatter;

import static util.constant.ConstantHelper.COMMAND_VIEW_ASSIGNMENTS;
import static util.constant.ConstantHelper.COMMAND_VIEW_ASSIGNMENTS_MEMBER_FLAG;
import static util.constant.ConstantHelper.COMMAND_VIEW_ASSIGNMENTS_TASK_FLAG;
import static util.constant.ConstantHelper.DEFAULT_HORI_BORDER_LENGTH;
import static util.constant.ConstantHelper.VALID_VIEW_ASSIGNMENT_LENGTH;
import static util.constant.ConstantHelper.VIEW_ASSIGNMENTS_INSUFFICIENT_PARAMS_MESSAGE;
import static util.constant.ConstantHelper.VIEW_ASSIGNMENTS_INVALID_FLAG_MESSAGE;
import static util.constant.ConstantHelper.VIEW_ASSIGNMENTS_NO_MEMBERS_MESSAGE;
import static util.constant.ConstantHelper.VIEW_ASSIGNMENTS_NO_TASKS_MESSAGE;

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
            ArchDukeLogger.logError(AssignmentViewHelper.class.getName(), "[viewAssignments]: "
                + "invalid input length");
            return (VIEW_ASSIGNMENTS_INSUFFICIENT_PARAMS_MESSAGE);
        }
        String input = projectCommand.substring(COMMAND_VIEW_ASSIGNMENTS.length());
        if (COMMAND_VIEW_ASSIGNMENTS_MEMBER_FLAG.equals(input.substring(0,2))) {
            return viewMembersAssignments(projectToManage, projectCommand.substring(VALID_VIEW_ASSIGNMENT_LENGTH));
        } else if (COMMAND_VIEW_ASSIGNMENTS_TASK_FLAG.equals(input.substring(0,2))) {
            return viewTasksAssignments(projectToManage, projectCommand.substring(VALID_VIEW_ASSIGNMENT_LENGTH));
        } else {
            ArchDukeLogger.logError(AssignmentViewHelper.class.getName(), "[viewAssignments]: "
                + "wrong flag used (not -m or -t)");
            return (VIEW_ASSIGNMENTS_INVALID_FLAG_MESSAGE);
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
        HashMap<String, ArrayList<String>> memberAndIndividualTasks = projectToManage.getMembersIndividualTaskList();
        if (memberAndIndividualTasks.keySet().isEmpty()) {
            return VIEW_ASSIGNMENTS_NO_MEMBERS_MESSAGE;
        }
        ArrayList<ArrayList<String>> memberAssignmentInfo = getMemberOutput(validMembers, projectToManage);
        return getFormattedOutputForMember(memberAssignmentInfo);
    }

    /**
     * Returns output to show viewer the task assignments of members.
     * @param membersToView List of valid member index numbers.
     * @param project THe project being managed.
     * @return An array containing information requested by the user.
     */
    private static ArrayList<ArrayList<String>> getMemberOutput(ArrayList<Integer> membersToView,
        Project project) {
        HashMap<String, ArrayList<String>> memberAndIndividualTasks = project.getMembersIndividualTaskList();
        ArrayList<ArrayList<String>> totalMemberOutputToPrint = new ArrayList<>();
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
            totalMemberOutputToPrint.add(outputToPrint);
        }
        return totalMemberOutputToPrint;
    }

    public String[] getFormattedOutputForMember(ArrayList<ArrayList<String>> totalOutputToPrint) {
        return viewHelper.consolePrintMultipleTables(totalOutputToPrint, DEFAULT_HORI_BORDER_LENGTH, 2,
            "Here are each member's tasks:");
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
        HashMap<String, ArrayList<String>> tasksAndAssignedMembers = projectToManage.getTasksAndAssignedMembers();
        if (tasksAndAssignedMembers.keySet().isEmpty()) {
            return VIEW_ASSIGNMENTS_NO_TASKS_MESSAGE;
        }
        ArrayList<ArrayList<String>> taskAssignmentInfo = getTaskOutput(validTasks, projectToManage);
        return getFormattedOutputForTask(taskAssignmentInfo);
    }

    /**
     * Returns output to show viewer the task assignments of members.
     * @param tasksToView List of valid task index numbers.
     * @param project Project to be managed.
     * @return An Array containing information requested by the user.
     */
    private static ArrayList<ArrayList<String>> getTaskOutput(ArrayList<Integer> tasksToView, Project project) {
        HashMap<String, ArrayList<String>> tasksAndAssignedMembers = project.getTasksAndAssignedMembers();
        ArrayList<ArrayList<String>> totalTaskOutputToPrint = new ArrayList<>();
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
            totalTaskOutputToPrint.add(outputToPrint);
        }
        return totalTaskOutputToPrint;
    }

    public String[] getFormattedOutputForTask(ArrayList<ArrayList<String>> totalOutputToPrint) {
        return viewHelper.consolePrintMultipleTables(totalOutputToPrint, DEFAULT_HORI_BORDER_LENGTH, 2,
            "Here are the members assigned to each task:");
    }

}
