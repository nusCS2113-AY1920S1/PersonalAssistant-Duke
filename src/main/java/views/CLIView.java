package views;

import controllers.ConsoleInputController;
import models.data.IProject;
import models.data.Project;
import models.member.Member;
import models.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIView {
    private static final String horiLine = "\t____________________________________________________________";
    private static final String indentation = "\t";

    private ConsoleInputController consoleInputController;

    public CLIView() {
        this.consoleInputController = new ConsoleInputController(this);
    }

    /**
     * Prints an indented and formatted message with a top and bottom border.
     * @param lines The lines to be printed in between the border.
     */
    public void consolePrint(String... lines) {
        System.out.println(horiLine);
        for (String message : lines) {
            System.out.println(indentation + message);
        }
        System.out.println(horiLine);
    }

    /**
     * Method to call when View model is started.
     */
    public void start() {
        Scanner sc = new Scanner(System.in);

        consolePrint("Hello! I'm Duke", "What can I do for you?");

        //noinspection InfiniteLoopStatement
        while (true) {
            String command = sc.nextLine();
            consoleInputController.onCommandReceived(command);
        }
    }

    /**
     * Method to be called when user says bye to exit the program.
     */
    public void end() {
        consolePrint("Bye. Hope to see you again soon!");
        System.exit(0);
    }

    /**
     * Method called when users wishes to view all Projects that are currently created or stored.
     * @param allProjects List of Projects returned to View model by the Controller from the Repository
     */
    public void viewAllProjects(ArrayList<Project> allProjects) {
        ArrayList<String> toPrint = new ArrayList<>();
        toPrint.add("Here are all the Projects you are managing:");
        for (int i = 0; i < allProjects.size(); i++) {
            toPrint.add("" + (i + 1) + ". " + allProjects.get(i).getDescription() + " "
                + allProjects.get(i).getMembers().getAllMemberDetails());
        }
        consolePrint(toPrint.toArray(new String[0]));
    }

    /**
     * Method called to inform the user that the project has exited.
     * @param projectName The name of the project exited.
     */
    public void exitProject(String projectName) {
        consolePrint("Exited project: " + projectName);
    }

    /**
     * Adds a member to the project.
     * @param projectToManage The project specified by the user.
     */
    public void addMember(IProject projectToManage, String memberDetails) {
        consolePrint("Added new member to: " + projectToManage.getDescription(), ""
            + "Member details: " + memberDetails);
    }

    /**
     * Edits the details of a member belonging to the project.
     * @param projectToManage The project specified by the user.
     * @param memberIndexNumber The member whose details are to be updated.
     * @param memberDetails The updated details of the member.
     */
    public void editMember(IProject projectToManage, int memberIndexNumber, String memberDetails) {
        projectToManage.editMember(memberIndexNumber, memberDetails);
        consolePrint("Updated member details with the index number " + memberIndexNumber);
    }

    /**
     * Adds a member to the project.
     * @param projectToManage The project specified by the user.
     * @param newTask A new task with details specified by the user.
     */
    public void addTask(IProject projectToManage, Task newTask) {
        projectToManage.addTask(newTask);
        consolePrint("Added new task to the list.");
    }

    /**
     * Deletes a task from the project.
     * @param projectToManage The project specified by the user.
     * @param taskIndexNumber The index of the task to be deleted.
     */
    public void removeTask(IProject projectToManage, int taskIndexNumber) {
        consolePrint("Removed " + projectToManage.getTask(taskIndexNumber).getTaskName());
        projectToManage.removeTask(taskIndexNumber);
    }

    /**
     * Shows the details of all the members in the project.
     * Can be updated later on to include more information (tasks etc).
     * @param projectToManage The project specified by the user.
     */
    public void viewAllMembers(Project projectToManage) {
        ArrayList<String> allMemberDetails = projectToManage.getMembers().getAllMemberDetails();
        consolePrint(allMemberDetails.toArray(new String[0]));
    }

    /**
     * Shows the details of all the task in the project.
     * @param projectToManage The project specified by the user.
     */
    public void viewAllTasks(IProject projectToManage) {
        ArrayList<String> allTaskDetails = projectToManage.getTasks().getAllTaskDetails();
        consolePrint(allTaskDetails.toArray(new String[0]));
    }

    /**
     * Shows a list of all tasks in the project, sorted based on certain criteria as chosen by the user.
     * @param projectToManage The project specified by the user.
     * @param sortCriteria Criteria to sort chosen by user.
     */
    public void viewSortedTasks(IProject projectToManage, String sortCriteria) {
        ArrayList<String> allTaskDetails = projectToManage.getTasks().getAllSortedTaskDetails(sortCriteria);
        consolePrint(allTaskDetails.toArray(new String[0]));
    }

    /**
     * Shows all the task that is assigned in the project.
     * @param projectToManage The project specified by the user.
     */
    public void viewAssignedTask(IProject projectToManage) {
        for (Task task: projectToManage.getTasks().getTaskList()) {
            ArrayList<String> allAssignedTasks = new ArrayList<>();
            allAssignedTasks.add(task.getTaskName() + " is assigned to: ");
            allAssignedTasks.addAll(task.getAssignedMembers().getAllMemberDetails());
            consolePrint(allAssignedTasks.toArray(new String[0]));
        }
    }

    /**
     * Shows specific requirements of a task.
     * @param projectToManage The project in which the aforementioned task belongs to.
     * @param taskIndex Index of task to be viewed.
     */
    public void viewTaskRequirements(IProject projectToManage, int taskIndex) {
        ArrayList<String> taskRequirements = projectToManage.getTask(taskIndex).getTaskRequirements();
        consolePrint(taskRequirements.toArray(new String[0]));
    }

    public void editTask(IProject projectToManage, String updatedTaskDetails, int taskIndexNumber) {
        projectToManage.editTask(updatedTaskDetails);
        consolePrint("The task has been updated!");
    }

    public void editTaskRequirements(IProject projectToManage, int taskIndexNumber, String[] updatedTaskRequirements,
                                     boolean haveRemove) {
        projectToManage.editTaskRequirements(taskIndexNumber, updatedTaskRequirements, haveRemove);
        consolePrint("The requirements of your specified task has been updated!");
    }
}
