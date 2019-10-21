package controllers;

import models.data.Project;
import models.member.IMember;
import models.member.Member;
import models.task.ITask;
import models.task.Task;
import repositories.ProjectRepository;
import util.factories.MemberFactory;
import util.factories.TaskFactory;
import util.log.DukeLogger;
import views.CLIView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectInputController implements IController {
    private Scanner manageProjectInput;
    private ProjectRepository projectRepository;
    private CLIView consoleView;
    private MemberFactory memberFactory;

    /**
     * Constructor for ProjectInputController takes in a View model and a ProjectRepository.
     * ProjectInputController is responsible for handling user input when user chooses to manage a project.
     * @param consoleView The main UI of ArchDuke.
     * @param projectRepository The object holding all projects.
     */
    public ProjectInputController(CLIView consoleView, ProjectRepository projectRepository) {
        this.manageProjectInput = new Scanner(System.in);
        this.projectRepository = projectRepository;
        this.consoleView = consoleView;
        this.memberFactory = new MemberFactory();
    }

    /**
     * Allows the user to manage the project by branching into the project of their choice.
     * @param input User input containing project index number (to add to project class).
     */
    public void onCommandReceived(String input) {
        DukeLogger.logInfo(ProjectInputController.class, "Managing project: " + input);
        int projectNumber = Integer.parseInt(input);
        Project projectToManage = projectRepository.getItem(projectNumber);
        this.consoleView.consolePrint("Now managing: " + projectToManage.getDescription());
        boolean isManagingAProject = true;
        while (isManagingAProject) {
            isManagingAProject = isManagingAProject(projectToManage, isManagingAProject);
        }
    }

    private boolean isManagingAProject(Project projectToManage, boolean isManagingAProject) {
        if (manageProjectInput.hasNextLine()) {
            String projectCommand = manageProjectInput.nextLine();
            DukeLogger.logInfo(ProjectInputController.class, "Managing:"
                    + projectToManage.getDescription() + ",input:'"
                    + projectCommand + "'");
            if (projectCommand.length() == 4 && ("exit").equals(projectCommand.substring(0, 4))) {
                isManagingAProject = projectExit(projectToManage);
            } else if (projectCommand.length() >= 11 && ("add member ").equals(projectCommand.substring(0, 11))) {
                projectAddMember(projectToManage, projectCommand);
            } else if (projectCommand.length() >= 12 && ("edit member ").equals(projectCommand.substring(0, 12))) {
                projectEditMember(projectToManage, projectCommand);
            } else if (projectCommand.length() >= 14 && ("delete member ").equals(projectCommand.substring(0,14))) {
                projectDeleteMember(projectToManage, projectCommand);
            } else if (projectCommand.length() == 12 && ("view members").equals(projectCommand)) {
                projectViewMembers(projectToManage);
            } else if (projectCommand.length() == 12 && ("view credits").equals(projectCommand)) {
                projectViewCredits();
            } else if (projectCommand.length() >= 9 && ("add task ").equals(projectCommand.substring(0, 9))) {
                projectAddTask(projectToManage, projectCommand);
            } else if (projectCommand.length() >= 10 && ("view tasks").equals(projectCommand.substring(0,10))) {
                projectViewTasks(projectToManage, projectCommand);
            } else if (projectCommand.length() == 19 && ("view assigned tasks").equals(projectCommand)) {
                projectViewAssignedTasks(projectToManage.getAssignedTaskList());
            } else if (projectCommand.length() > 25
                    && ("view task requirements i/").equals(projectCommand.substring(0, 25))) {
                projectViewTaskRequirements(projectToManage, projectCommand);
            } else if (projectCommand.length() > 23
                    && ("edit task requirements ").equals(projectCommand.substring(0, 23))) {
                projectEditTaskRequirements(projectToManage, projectCommand);
            } else if (projectCommand.length() > 10 && ("edit task ").equals(projectCommand.substring(0, 10))) {
                projectEditTask(projectToManage, projectCommand);
            } else if (projectCommand.length() >= 12 && ("delete task ").equals(projectCommand.substring(0,12))) {
                projectDeleteTask(projectToManage, projectCommand);
            } else if (projectCommand.length() >= 12 && ("assign task ").equals(projectCommand.substring(0,12))) {
                projectAssignTask(projectToManage, projectCommand);
            } else if ("bye".equals(projectCommand)) {
                consoleView.end();
            } else {
                consoleView.consolePrint("Invalid command. Try again!");
            }
        } else {
            consoleView.consolePrint("Please enter a command.");
        }
        return isManagingAProject;
    }

    /**
     * Manages the assignment to and removal of tasks from members.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    private void projectAssignTask(Project projectToManage, String projectCommand) {
        AssignmentController assignmentController = new AssignmentController(projectToManage);
        assignmentController.assignAndUnassign(projectCommand.substring(12));
        consoleView.consolePrint(assignmentController.getErrorMessages().toArray(new String[0]));
        consoleView.consolePrint(assignmentController.getSuccessMessages().toArray(new String[0]));
    }

    /**
     * Deletes a task from the project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    private void projectDeleteTask(Project projectToManage, String projectCommand) {
        int taskIndexNumber = Integer.parseInt(projectCommand.substring(12).split(" ")[0]);
        if (projectToManage.getNumOfTasks() >= taskIndexNumber) {
            consoleView.removeTask(projectToManage, taskIndexNumber);
        } else {
            consoleView.consolePrint("The task index entered is invalid.");
        }
    }

    /**
     * Updates the task details of a given task in the project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    private void projectEditTask(Project projectToManage, String projectCommand) {
        String [] updatedTaskDetails = projectCommand.split(" [itpdcs]\\/");
        int taskIndexNumber = Integer.parseInt(updatedTaskDetails[1]);
        if (projectToManage.getNumOfTasks() >= taskIndexNumber && taskIndexNumber > 0) {
            consoleView.editTask(projectToManage, projectCommand, taskIndexNumber);
        } else {
            consoleView.consolePrint("The task index entered is invalid.");
        }
    }

    /**
     * Updates the task requirements of a given task in the project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    private void projectEditTaskRequirements(Project projectToManage, String projectCommand) {
        String[] updatedTaskRequirements = projectCommand.split(" [ir]m?/");
        int taskIndexNumber = Integer.parseInt(updatedTaskRequirements[1]);
        boolean haveRemove = false;
        if (projectCommand.contains(" rm/")) {
            haveRemove = true;
        }
        if (projectToManage.getNumOfTasks() >= taskIndexNumber && taskIndexNumber > 0) {
            consoleView.editTaskRequirements(projectToManage, taskIndexNumber, updatedTaskRequirements,
                    haveRemove);
        } else {
            consoleView.consolePrint("The task index entered is invalid.");
        }
    }

    /**
     * Displays the tasks in the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    private void projectViewTaskRequirements(Project projectToManage, String projectCommand) {
        int taskIndex = Integer.parseInt(projectCommand.substring(25));
        if (projectToManage.getNumOfTasks() >= taskIndex && taskIndex > 0) {
            if (projectToManage.getTask(taskIndex).getNumOfTaskRequirements() == 0) {
                consoleView.consolePrint("This task has no specific requirements.");
            } else {
                consoleView.viewTaskRequirements(projectToManage, taskIndex);
            }
        } else {
            consoleView.consolePrint("The task index entered is invalid.");
        }
    }

    /**
     * Displays the assigned tasks in the current project.
     * @param assignedTaskList The list containing the assignment of the tasks.
     */
    private void projectViewAssignedTasks(ArrayList<String> assignedTaskList) {
        consoleView.consolePrint(assignedTaskList.toArray(new String[0]));
    }

    /**
     * Displays all the tasks in the given project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    private void projectViewTasks(Project projectToManage, String projectCommand) {
        if (("view tasks").equals(projectCommand)) {
            consoleView.viewAllTasks(projectToManage);
        } else if (projectCommand.length() >= 11) {
            String sortCriteria = projectCommand.substring(11);
            consoleView.viewSortedTasks(projectToManage, sortCriteria);
        }
    }

    /**
     * Adds a task to the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    private void projectAddTask(Project projectToManage, String projectCommand) {
        try {
            TaskFactory taskFactory = new TaskFactory();
            ITask newTask = taskFactory.createTask(projectCommand.substring(9));
            if (newTask.getDetails() != null) {
                consoleView.addTask(projectToManage, (Task) newTask);
            } else {
                consoleView.consolePrint("Failed to create new task. Please ensure all"
                        + "necessary parameters are given");
            }
        } catch (NumberFormatException | ParseException e) {
            consoleView.consolePrint("Please enter your task format correctly.");
        }
    }

    /**
     * Displays the members’ credits, their index number, name, and name of tasks completed.
     */
    private void projectViewCredits() {
        // TODO view all credits.
        consoleView.consolePrint("Not implemented yet");
    }

    /**
     * Displays all the members in the current project.
     * @param projectToManage The project specified by the user.
     */
    private void projectViewMembers(Project projectToManage) {
        consoleView.viewAllMembers(projectToManage);
    }

    /**
     * Deletes a member from the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    private void projectDeleteMember(Project projectToManage, String projectCommand) {
        int memberIndexNumber = Integer.parseInt(projectCommand.substring(14).split(" ")[0]);
        if (projectToManage.getNumOfMembers() >= memberIndexNumber) {
            Member memberToRemove = projectToManage.getMembers().getMember(memberIndexNumber);
            projectToManage.removeMember(memberToRemove);
            consoleView.consolePrint("Removed member with the index number " + memberIndexNumber);
        } else {
            consoleView.consolePrint("The member index entered is invalid.");
        }
    }

    /**
     * Updates the details of a given member in the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    private void projectEditMember(Project projectToManage, String projectCommand) {
        try {
            int memberIndexNumber = Integer.parseInt(projectCommand.substring(12).split(" ")[0]);
            if (projectToManage.getNumOfMembers() >= memberIndexNumber && memberIndexNumber > 0) {
                String updatedMemberDetails = projectCommand.substring(projectCommand.indexOf("n/"));
                consoleView.editMember(projectToManage, memberIndexNumber, updatedMemberDetails);
            } else {
                consoleView.consolePrint("The member index entered is invalid.");
            }
        } catch (StringIndexOutOfBoundsException e) {
            consoleView.consolePrint("Please enter the updated member details format correctly.");
        }
    }

    /**
     * Adds a member to the current project.
     * @param projectToManage The project specified by the user.
     * @param projectCommand The user input.
     */
    private void projectAddMember(Project projectToManage, String projectCommand) {
        String memberDetails = projectCommand.substring(11);
        int numberOfCurrentMembers = projectToManage.getNumOfMembers();
        memberDetails = memberDetails + " x/" + numberOfCurrentMembers;
        IMember newMember = memberFactory.create(memberDetails);
        if (newMember.getName() != null) {
            projectToManage.addMember((Member) newMember);
            consoleView.addMember(projectToManage, newMember.getDetails());
        } else {
            consoleView.consolePrint("Failed to add member. Please ensure you have entered "
                    + "at least the name of the new member.");
        }
    }

    /**
     * Exits the current project.
     * @param projectToManage The project specified by the user.
     * @return Boolean variable specifying the exit status.
     */
    private boolean projectExit(Project projectToManage) {
        boolean isManagingAProject;
        isManagingAProject = false;
        consoleView.exitProject(projectToManage.getDescription());
        return isManagingAProject;
    }

}
