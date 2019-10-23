package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import models.data.Project;
import models.member.Member;
import models.task.Task;

public class AssignmentViewHelper {
    private ArrayList<String> errorMessages;

    /**
     * Class that helps to parse user commands for viewing task assignments,
     * and retrieve the relevant information to show viewers.
     */
    AssignmentViewHelper() {
        errorMessages = new ArrayList<>();
    }

    /**
     * Retrieves a list of valid member index numbers from user input.
     * @param input String containing user input of index numbers member.
     * @param project The project being managed.
     * @return An ArrayList containing valid member index numbers to show assignments.
     */
    public ArrayList<Integer> parseMembers(String input, Project project) {
        ArrayList<Integer> membersToView = new ArrayList<>();
        if ("all".equals(input)) {
            for (int i = 1; i <= project.getNumOfMembers(); i++) {
                membersToView.add(i);
            }
            return membersToView;
        }
        String[] inputParts = input.split(" ");
        for (String index : inputParts) {
            try {
                index.trim();
                Integer indexNumber = Integer.parseInt(index);
                if (project.hasMemberIndex(indexNumber)) {
                    membersToView.add(indexNumber);
                } else {
                    errorMessages.add("Member with index " + index + "does not exist.");
                }
            } catch (NumberFormatException e) {
                errorMessages.add("Could not recognise member " + index
                    + ", please ensure it is an integer.");
            }
        }
        return membersToView;
    }

    /**
     * Returns output to show viewer the task assignments of members.
     * @param membersToView List of valid member index numbers.
     * @param project THe project being managed.
     * @return An ArrayList containing information requested by the user.
     */
    public ArrayList<String> getMemberOutput(ArrayList<Integer> membersToView, Project project) {
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
     * Returns a list of valid task numbers.
     * @param input List of task index numbers input by user.
     * @param project Project to be managed.
     * @return An ArrayList containing valid task index numbers to show assigned members.
     */
    public ArrayList<Integer> parseTasks(String input, Project project) {
        ArrayList<Integer> tasksToView = new ArrayList<>();
        if ("all".equals(input)) {
            for (int i = 1; i <= project.getNumOfTasks(); i++) {
                tasksToView.add(i);
            }
            return tasksToView;
        }
        String[] inputParts = input.split(" ");

        for (String index : inputParts) {
            try {
                index.trim();
                Integer indexNumber = Integer.parseInt(index);
                if (project.hasTaskIndex(indexNumber)) {
                    tasksToView.add(indexNumber);
                } else {
                    errorMessages.add("Task with index " + index + "does not exist.");
                }
            } catch (NumberFormatException e) {
                errorMessages.add("Could not recognise task " + index
                    + ", please ensure it is an integer.");
            }
        }
        return tasksToView;
    }

    /**
     * Returns output to show viewer the task assignments of members.
     * @param tasksToView List of valid task index numbers.
     * @param project Project to be managed.
     * @return An ArrayList containing information requested by the user.
     */
    public ArrayList<String> getTaskOutput(ArrayList<Integer> tasksToView, Project project) {
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

    /**
     * Returns error messages from unsuccessful commands.
     * @return An ArrayList containing strings informing users of unsuccessful commands.
     */
    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }
}
