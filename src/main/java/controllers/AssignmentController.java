package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import models.data.IProject;
import models.member.Member;
import models.task.Task;
import views.CLIView;

public class AssignmentController {
    private ArrayList<Integer> indexOfMembersToAssign;
    private ArrayList<Integer> indexOfMembersToRemove;
    private ArrayList<String> messageForView;

    /**
     * Class that assists with parsing of input for assigning or unassigning tasks, and
     * delivering of messages to the main UI to inform user about task assignments.
     */
    public AssignmentController() {
        this.indexOfMembersToAssign = new ArrayList<>();
        this.indexOfMembersToRemove = new ArrayList<>();
        this.messageForView = new ArrayList<>();
    }

    /**
     * Parses the input by the user to allow members to be assigned or removed. Checks if task
     * exists, and if member index numbers are correct.
     *
     * @param projectToManage Project which user is managing.
     * @param details Array of strings containing details of task assignment or unassignment.
     * @param consoleView The main user interface.
     */
    public void manageAssignment(IProject projectToManage, String[] details,
        CLIView consoleView) {
        int taskIndex = Integer.parseInt(details[0].substring(2).trim());

        if (taskIndex > projectToManage.getNumOfTasks() || taskIndex <= 0) {
            this.messageForView.add("The task you wish to assign (task index "
                + taskIndex + " ) does not exist!");
            this.messageForView.add("Please check the index number of the task and try again.");
            consoleView.consolePrint(this.messageForView.toArray(new String[0]));
        } else {
            Task selectedTask = projectToManage.getTask(taskIndex);
            HashSet<Integer> assignedIndexes = selectedTask.getAssignedIndexes(); //existing assignments
            boolean isAssigningMembers = false;
            boolean isRemovingMembers = false;
            for (String s : details) {
                if ("to/".equals(s)) {
                    isAssigningMembers = true;
                    isRemovingMembers = false;
                } else if ("rm/".equals(s)) {
                    isAssigningMembers = false;
                    isRemovingMembers = true;
                } else if (!s.contains("i/")) {
                    int indexNumber = Integer.parseInt(s);
                    if (projectToManage.memberIndexExists(indexNumber)) {
                        if (isAssigningMembers) {
                            if (assignedIndexes.contains(indexNumber)) {
                                this.messageForView.add("Member with index "
                                    + indexNumber
                                    + " (" + projectToManage.getMembers().getMember(indexNumber).getName()
                                    + ") has already been assigned this task!");
                            } else {
                                indexOfMembersToAssign.add(indexNumber);
                            }
                        }
                        if (isRemovingMembers) {
                            if (!assignedIndexes.contains(indexNumber)) {
                                this.messageForView.add("Cannot unassign member with index "
                                    + indexNumber
                                    + " (" + projectToManage.getMembers().getMember(indexNumber).getName()
                                    + ") because they are not assigned the task yet!");
                            } else {
                                indexOfMembersToRemove.add(indexNumber);
                            }
                        }
                    } else {
                        messageForView.add("Member with index number " + indexNumber + " does not exist!");
                    }
                }
            }
            consoleView.consolePrint(this.messageForView.toArray(new String[0]));
            ArrayList<String> forViewToPrint = new ArrayList<>();
            forViewToPrint.add("The task: " + selectedTask.getTaskName() + " has been assigned to:");
            for (Integer i : indexOfMembersToAssign) {
                Member memberToAssign = projectToManage.getMembers().getMember(i);
                selectedTask.assignMember(memberToAssign);
                forViewToPrint.add(memberToAssign.getName());
            }
            forViewToPrint.add("The task: " + selectedTask.getTaskName() + " has been unassigned from:");
            for (Integer i : indexOfMembersToRemove) {
                Member memberToRemove = projectToManage.getMembers().getMember(i);
                selectedTask.removeMember(memberToRemove);
                forViewToPrint.add(memberToRemove.getName());
            }
            consoleView.consolePrint(forViewToPrint.toArray(new String[0]));
        }
    }

    public static void viewTaskAssigned(IProject projectToManage, CLIView consoleView) {
        consoleView.viewAssignedTask(projectToManage);
    }

    public ArrayList<Integer> getAssigneesIndex() {
        return this.indexOfMembersToAssign;
    }

    public ArrayList<Integer> getUnassigneesIndex() {
        return this.indexOfMembersToRemove;
    }

    public ArrayList<String> getMessageForView() {
        return this.messageForView;
    }
}
