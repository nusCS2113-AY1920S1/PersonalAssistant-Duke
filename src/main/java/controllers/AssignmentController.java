package controllers;

import java.util.ArrayList;
import models.data.IProject;
import models.task.Task;
import views.CLIView;

public class AssignmentController {

    /**
     * Parses the input by the user to allow members to be assigned or removed
     * @param projectToManage
     * @param details Array of strings containing details of task assignment.
     * @param consoleView
     */
    public void assignOrRemove(IProject projectToManage, String[] details, CLIView consoleView) {
        int taskNumber = Integer.parseInt(details[0]);
        if (taskNumber > projectToManage.getNumOfTasks() || taskNumber <= 0) {
            consoleView.consolePrint("The task you wish to assign does not exist!",
                "Please check the index number of the task and try again.");
        } else {
            ArrayList<Integer> assign = new ArrayList<>(); //List of members to be assigned task
            ArrayList<Integer> unassign = new ArrayList<>();//List of members to be unassigned task
            boolean add = false;
            boolean remove = false;
            int indexNumber = 0;
            for (String s : details) {
                if (s.equals("to/")) {
                    add = true;
                    remove = false;
                } else if (s.equals("rm/")) {
                    add = false;
                    remove = true;
                } else {
                    indexNumber = Integer.parseInt(s);
                    if (projectToManage.memberIndexExists(indexNumber)) {
                        if (add) {
                            assign.add(indexNumber);
                        }
                        if (remove) {
                            unassign.add(indexNumber);
                        }
                    } else {
                        consoleView.consolePrint("Member with index number " + indexNumber + " does not exist!");
                    }
                }
            }
        }
    }

    public void assign(ArrayList<Integer> assign, Task task) {
        for (int i : assign) {
            //check if already assigned

        }


    }
}
