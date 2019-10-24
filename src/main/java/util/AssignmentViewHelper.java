package util;

import models.data.Project;
import models.member.Member;
import models.task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignmentViewHelper {
    /**
     * Returns output to show viewer the task assignments of members.
     * @param membersToView List of valid member index numbers.
     * @param project THe project being managed.
     * @return An ArrayList containing information requested by the user.
     */
    public static ArrayList<String> getMemberOutput(ArrayList<Integer> membersToView,
        Project project) {
        ArrayList<String> outputToPrint = new ArrayList<String>();
        outputToPrint.add("Here are each member's tasks:");
        HashMap<Member, ArrayList<Task>> memberAndIndividualTasks = project.getMembersIndividualTaskList();
        for (Integer index : membersToView) {
            Member member  = project.getMembers().getMember(index);
            outputToPrint.add("Tasks assigned to " + member.getName());
            if (memberAndIndividualTasks.get(member).size() == 0) {
                outputToPrint.add("No tasks assigned yet.");
            } else {
                int currentNumber = 1;
                for (Task task : memberAndIndividualTasks.get(member)) {
                    outputToPrint.add(currentNumber + ". " + task.getDetails());
                    currentNumber++;
                }
            }
        }
        return outputToPrint;
    }

    /**
     * Returns output to show viewer the task assignments of members.
     * @param tasksToView List of valid task index numbers.
     * @param project Project to be managed.
     * @return An ArrayList containing information requested by the user.
     */
    public static ArrayList<String> getTaskOutput(ArrayList<Integer> tasksToView, Project project) {
        ArrayList<String> outputToPrint = new ArrayList<String>();
        outputToPrint.add("Here are the members assigned to each task:");
        HashMap<Task, ArrayList<Member>> tasksAndAssignedMembers = project.getTasksAndAssignedMembers();
        for (Integer index : tasksToView) {
            Task task = project.getTask(index);
            outputToPrint.add(task.getDetails());
            if (tasksAndAssignedMembers.get(task).size() == 0) {
                outputToPrint.add("No members assigned yet.");
            } else {
                int currentNumber = 1;
                outputToPrint.add("Members assigned to task " + index + " (" + task.getDetails() + ")");
                for (Member member : tasksAndAssignedMembers.get(task)) {
                    outputToPrint.add(currentNumber + ". " + member.getName());
                    currentNumber++;
                }
            }
        }
        return outputToPrint;
    }
}
