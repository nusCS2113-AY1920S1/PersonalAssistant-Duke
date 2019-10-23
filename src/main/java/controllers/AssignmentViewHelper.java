package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import models.data.Project;
import models.member.Member;
import models.task.Task;

public class AssignmentViewHelper {
    private ArrayList<String> errorMessages;

    AssignmentViewHelper() {
        errorMessages = new ArrayList<>();
    }

    public ArrayList<Integer> parseMembers (String input, Project project) {
        String[] inputParts = input.split(" ");
        ArrayList<Integer> membersToView = new ArrayList<>();
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

    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }

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
                    currentNumber ++;
                }
            }
        }
        return outputToPrint;
    }

    public ArrayList<Integer> parseTasks(String input, Project project) {
        String[] inputParts = input.split(" ");
        ArrayList<Integer> tasksToView = new ArrayList<>();
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
                    currentNumber ++;
                }
            }
        }
        return outputToPrint;
    }

}
