package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import models.data.IProject;
import models.task.Task;
import views.CLIView;

public class AssignmentControllerUtil {

    /**
     * Parses the input by the user to allow members to be assigned or removed. Checks if task
     * exists, and if member index numbers are correct.
     *
     * @param projectToManage Project which user is managing.
     * @param details Array of strings containing details of task assignment or unassignment.
     * @param consoleView The main user interface.
     */
    public static void manageAssignment(IProject projectToManage, String[] details,
        CLIView consoleView) {
        int taskNumber = Integer.parseInt(details[0].substring(2).trim());
        Task task = null;
        if (taskNumber > projectToManage.getNumOfTasks() || taskNumber <= 0) {
            consoleView.consolePrint("The task you wish to assign does not exist!",
                "Please check the index number of the task and try again.");
        } else {
            task = projectToManage.getTask(taskNumber);
            HashSet<Integer> assignedIndexes = task.getAssignedIndexes(); //existing assignments
            ArrayList<Integer> assign = new ArrayList<>(); //List of members to be assigned task
            ArrayList<Integer> unassign = new ArrayList<>();//List of members to be unassigned task
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
                                consoleView.consolePrint("Member with index "
                                    + indexNumber
                                    + " has already been assigned this task!");
                            } else {
                                assign.add(indexNumber);
                            }
                        }
                        if (remove) {
                            if (!assignedIndexes.contains(indexNumber)) {
                                consoleView.consolePrint("Cannot unassign member with index "
                                    + indexNumber
                                    + " because they are not assigned the task yet!");
                            } else {
                                unassign.add(indexNumber);
                            }
                        }
                    } else {
                        consoleView.consolePrint("Member with index number " + indexNumber + " does not exist!");
                    }
                }
            }
            consoleView.assignOrUnassignTask(assign, unassign, task, projectToManage);
        }
    }
}
