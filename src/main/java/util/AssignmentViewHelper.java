package util;

import models.member.IMember;
import models.project.Project;
import models.member.Member;
import models.task.Task;

import java.util.ArrayList;
import java.util.HashMap;

import static util.constant.ConstantHelper.DEFAULT_HORI_BORDER_LENGTH;

//@@author sinteary
public class AssignmentViewHelper {
    private static ViewHelper viewHelper = new ViewHelper();

    /**
     * Returns output to show viewer the task assignments of members.
     * @param membersToView List of valid member index numbers.
     * @param project THe project being managed.
     * @return An array containing information requested by the user.
     */
    public static String[] getMemberOutput(ArrayList<Integer> membersToView,
        Project project) {
        ArrayList<ArrayList<String>> totalOutputToPrint = new ArrayList<>();

        HashMap<Member, ArrayList<Task>> memberAndIndividualTasks = project.getMembersIndividualTaskList();
        if (memberAndIndividualTasks.keySet().isEmpty()) {
            ArrayList<String> outputToPrint = new ArrayList<>();
            outputToPrint.add("No members in project yet.");
            outputToPrint.add("Please add members and assign them tasks before using this command!");
            return outputToPrint.toArray(new String[0]);
        }
        for (Integer index : membersToView) {
            ArrayList<String> outputToPrint = new ArrayList<>();
            IMember member = project.getMembers().getMember(index);
            outputToPrint.add(member.getName());
            if (memberAndIndividualTasks.get(member).size() == 0) {
                outputToPrint.add("No tasks assigned yet.");
            } else {
                int currentNumber = 1;
                for (Task task : memberAndIndividualTasks.get(member)) {
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

        HashMap<Task, ArrayList<Member>> tasksAndAssignedMembers = project.getTasksAndAssignedMembers();
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
            if (tasksAndAssignedMembers.get(task).size() == 0) {
                outputToPrint.add("No members assigned yet.");
            } else {
                int currentNumber = 1;
                for (Member member : tasksAndAssignedMembers.get(task)) {
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
