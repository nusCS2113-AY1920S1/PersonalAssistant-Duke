package controllers;

import models.data.IProject;
import models.task.Task;
import views.CLIView;

import java.util.ArrayList;
import java.util.HashSet;

public class AssignmentControllerUtil {
    private ArrayList<Integer> assign;
    private ArrayList<Integer> unassign;
    private ArrayList<String> messages;

    /**
     * Class that assists with parsing of input for assigning or unassigning tasks, and
     * delivering of messages to the main UI to inform user about task assignments.
     */
    public AssignmentControllerUtil() {
        this.assign = new ArrayList<>();
        this.unassign = new ArrayList<>();
        this.messages = new ArrayList<>();
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
        Task task = null;
        if (taskIndex > projectToManage.getNumOfTasks() || taskIndex <= 0) {
            this.messages.add("The task you wish to assign (task index "
                + taskIndex + " ) does not exist!");
            this.messages.add("Please check the index number of the task and try again.");
            consoleView.consolePrint(this.messages.toArray(new String[0]));
        } else {
            task = projectToManage.getTask(taskIndex);
            HashSet<Integer> assignedIndexes = task.getAssignedIndexes(); //existing assignments
            boolean add = false;
            boolean remove = false;
            for (String s : details) {
                if ("to/".equals(s)) {
                    add = true;
                    remove = false;
                } else if ("rm/".equals(s)) {
                    add = false;
                    remove = true;
                } else if (!s.contains("i/")) {
                    int indexNumber = Integer.parseInt(s);
                    if (projectToManage.memberIndexExists(indexNumber)) {
                        if (add) {
                            if (assignedIndexes.contains(indexNumber)) {
                                this.messages.add("Member with index "
                                    + indexNumber
                                    + " (" + projectToManage.getMembers().getMember(indexNumber).getName()
                                    + ") has already been assigned this task!");
                            } else {
                                assign.add(indexNumber);
                            }
                        }
                        if (remove) {
                            if (!assignedIndexes.contains(indexNumber)) {
                                this.messages.add("Cannot unassign member with index "
                                    + indexNumber
                                    + " (" + projectToManage.getMembers().getMember(indexNumber).getName()
                                    + ") because they are not assigned the task yet!");
                            } else {
                                unassign.add(indexNumber);
                            }
                        }
                    } else {
                        messages.add("Member with index number " + indexNumber + " does not exist!");
                    }
                }
            }
            consoleView.consolePrint(this.messages.toArray(new String[0]));
            consoleView.assignOrUnassignTask(assign, unassign, task, projectToManage);
        }
    }

    public static void viewTaskAssigned(IProject projectToManage, CLIView consoleView) {
        consoleView.viewAssignedTask(projectToManage);
    }

    public ArrayList<Integer> getAssigneesIndex() {
        return this.assign;
    }

    public ArrayList<Integer> getUnassigneesIndex() {
        return this.unassign;
    }

    public ArrayList<String> getMessages() {
        return this.messages;
    }
}
