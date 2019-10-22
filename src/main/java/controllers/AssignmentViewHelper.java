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

    public ArrayList<String> getOutput(ArrayList<Integer> membersToView, Project project) {
        ArrayList<String> outputToPrint = new ArrayList<String>();
        outputToPrint.add("Here are each member's tasks:");
        HashMap<Member, ArrayList<Task>> memberAndIndividualTasks = project.getMembersIndividualTaskList();
        for (Integer index : membersToView) {
            Member member  = project.getMembers().getMember(index);
            outputToPrint.add("Tasks assigned to" + member.getName());
            for (Task task : memberAndIndividualTasks.get(member)) {
                outputToPrint.add(task.getDetails());
            }
        }
        return outputToPrint;
    }

}
